package org.asmeta.flattener;

import static org.junit.jupiter.api.Assertions.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import org.asmeta.flattener.agents.AgentFlattener;
import org.asmeta.parser.ASMParser;
import org.asmeta.parser.util.AsmPrinter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import asmeta.definitions.domains.AbstractTd;
import asmeta.structure.Asm;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;

class AgentFlattenerTest {
    @TempDir Path directory;

    private static final String ROBOTS = """
        signature:
            domain Robot subsetof Agent
            static robot1: Robot
            static robot2: Robot
            controlled passi: Robot -> Integer
        definitions:
            rule r_Incrementa = passi(self) := passi(self) + 1
            rule r_Robot = r_Incrementa[]
            main rule r_Main = forall $r in Robot with true do program($r)
        default init s0:
            function passi($r in Robot) = 0
            agent Robot: r_Robot[]
        """;

    @Test void robotsBecomeAbstractWithoutAddingState() throws Exception {
        Asm asm = flatten(ROBOTS);
        var domain = asm.getHeaderSection().getSignature().getDomain().get(0);
        assertInstanceOf(AbstractTd.class, domain);
        assertEquals("Robot", domain.getName());
        assertEquals(3, asm.getHeaderSection().getSignature().getFunction().size());
        assertTrue(asm.getDefaultInitialState().getAgentInitialization().isEmpty());
        for (var rule : asm.getBodySection().getRuleDeclaration()) {
            if (rule == asm.getMainrule()) continue;
            assertEquals(1, rule.getVariable().size());
            assertSame(domain, rule.getVariable().get(0).getDomain());
        }
        var loop = assertInstanceOf(ForallRule.class, asm.getMainrule().getRuleBody());
        var call = assertInstanceOf(MacroCallRule.class, loop.getDoRule());
        assertEquals("r_Robot", call.getCalledMacro().getName());
        assertSame(loop.getVariable().get(0), call.getParameters().get(0));
        String text = print(asm);
        assertFalse(text.contains("program("));
        assertFalse(text.contains("passi(self)"));
        assertEquals(text, print(new AgentFlattener(asm).flattenASM()));
    }

    @Test void globalAgentLoopUsesEachDomainsProgram() throws Exception {
        Asm asm = flatten("""
            signature:
                domain Worker subsetof Agent
                domain Controller subsetof Agent
                static worker: Worker
                static controller: Controller
            definitions:
                rule r_Work = skip
                rule r_Control = skip
                main rule r_Main = forall $a in Agent with true do program($a)
            default init s0:
                agent Worker: r_Work[]
                agent Controller: r_Control[]
            """);
        var block = assertInstanceOf(asmeta.transitionrules.basictransitionrules.BlockRule.class,
                asm.getMainrule().getRuleBody());
        assertEquals(2, block.getRules().size());
        for (int i = 0; i < 2; i++) {
            String domain = i == 0 ? "Worker" : "Controller";
            String program = i == 0 ? "r_Work" : "r_Control";
            var loop = assertInstanceOf(ForallRule.class, block.getRules().get(i));
            assertEquals(domain, loop.getVariable().get(0).getDomain().getName());
            var call = assertInstanceOf(MacroCallRule.class, loop.getDoRule());
            assertEquals(program, call.getCalledMacro().getName());
        }
        assertEquals(2, asm.getHeaderSection().getSignature().getFunction().size());
    }

    private Asm flatten(String body) throws Exception {
        Path library = Path.of("../../../../asm_examples/STDL/StandardLibrary.asm").toAbsolutePath().normalize();
        Path input = directory.resolve("Robots.asm");
        Files.writeString(input, "asm Robots\nimport \"" + library.toString().replace('\\', '/') + "\"\n" + body);
        return new AgentFlattener(ASMParser.setUpReadAsm(input.toFile()).getMain()).flattenASM();
    }

    private String print(Asm asm) {
        StringWriter text = new StringWriter();
        AsmPrinter printer = new AsmPrinter(new PrintWriter(text));
        printer.visit(asm);
        printer.flush();
        return text.toString();
    }
}
