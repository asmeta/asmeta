package org.asmeta.flattener;

import static org.junit.jupiter.api.Assertions.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;

import org.asmeta.flattener.agents.AgentFlattener;
import org.asmeta.parser.ASMParser;
import org.asmeta.parser.util.AsmPrinter;
import org.asmeta.parser.util.ImportFlattener;
import org.asmeta.simulator.UpdateClashException;
import org.asmeta.simulator.main.Simulator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import asmeta.definitions.domains.AbstractTd;
import asmeta.structure.Asm;

class AgentFlattenerTest {
    @TempDir Path directory;

    private static final String SIGNATURE = """
        signature:
            domain Robot subsetof Agent
            static robot1: Robot
            static robot2: Robot
            controlled passi: Robot -> Integer
        definitions:
        """;
    private static final String INIT = """
        default init s0:
            function passi($r in Robot) = 0
            agent Robot: r_Robot[]
        """;

    @Test void parallelProgramsAndNestedMacroCalls() throws Exception {
        Path input = model("Robots", SIGNATURE + """
            rule r_Incrementa = passi(self) := passi(self) + 1
            rule r_Robot = r_Incrementa[]
            main rule r_Main =
                forall $r in Robot with true do program($r)
            """ + INIT);
        Map<String, String> expected = simulate(input, 3);
        Asm flattened = flatten(input);
        assertEquals("Robots", flattened.getName());
        assertInstanceOf(AbstractTd.class, flattened.getHeaderSection().getSignature().getDomain().get(0));
        assertTrue(flattened.getDefaultInitialState().getAgentInitialization().isEmpty());
        String text = print(flattened);
        assertFalse(java.util.regex.Pattern.compile("\\bprogram\\(").matcher(text).find());
        assertFalse(text.contains("passi(self)"));
        assertEquals(expected, simulate(save(flattened), 3));
        assertEquals("3", expected.get("passi(robot1)"));
        assertEquals("3", expected.get("passi(robot2)"));
    }

    @Test void sequentialCallsKeepTheirOrder() throws Exception {
        Path input = model("SequentialRobots", SIGNATURE + """
            rule r_Robot =
                if self = robot1 then
                    passi(self) := passi(self) + 1
                else
                    passi(self) := passi(robot1)
                endif
            main rule r_Main =
                seq
                    program(robot1)
                    program(robot2)
                endseq
            """ + INIT);
        Map<String, String> expected = simulate(input, 2);
        assertEquals(expected, simulate(save(flatten(input)), 2));
        assertEquals("2", expected.get("passi(robot2)"));
    }

    @Test void parallelClashesRemainClashes() throws Exception {
        Path input = model("ClashingRobots", SIGNATURE.replace("definitions:", "controlled total: Integer\ndefinitions:") + """
            rule r_Robot = total := if self = robot1 then 1 else 2 endif
            main rule r_Main = par program(robot1) program(robot2) endpar
            """ + INIT);
        assertThrows(UpdateClashException.class, () -> simulate(input, 1));
        Path flattened = save(flatten(input));
        assertThrows(UpdateClashException.class, () -> simulate(flattened, 1));
    }

    @Test void programWithoutSelfAndNonAgentModels() throws Exception {
        Path input = model("NoSelf", SIGNATURE + """
            rule r_Robot = passi(robot1) := passi(robot1) + 1
            main rule r_Main = program(robot2)
            """ + INIT);
        assertEquals(simulate(input, 2), simulate(save(flatten(input)), 2));
        Path plain = model("Plain", """
            signature:
                controlled count: Integer
            definitions:
                main rule r_Main = count := count + 1
            default init s0:
                function count = 0
            """);
        Asm asm = ASMParser.setUpReadAsm(plain.toFile()).getMain();
        assertSame(asm, new AgentFlattener(asm).flattenASM());
    }

    @Test void separateProgramsForSeparateDomains() throws Exception {
        Path input = model("TwoKinds", """
            signature:
                domain Worker subsetof Agent
                domain Controller subsetof Agent
                static worker: Worker
                static controller: Controller
                controlled count: Worker -> Integer
                controlled enabled: Controller -> Boolean
            definitions:
                rule r_Work = count(self) := count(self) + 1
                rule r_Control = enabled(self) := not enabled(self)
                main rule r_Main = par program(worker) program(controller) endpar
            default init s0:
                function count($w in Worker) = 0
                function enabled($c in Controller) = false
                agent Worker: r_Work[]
                agent Controller: r_Control[]
            """);
        assertEquals(simulate(input, 3), simulate(save(flatten(input)), 3));
    }

    @Test void sharedHelpersAreSpecialisedForEachDomain() throws Exception {
        Path input = model("SharedHelper", """
            signature:
                domain Worker subsetof Agent
                domain Controller subsetof Agent
                static worker: Worker
                static controller: Controller
                controlled count: Integer
            definitions:
                rule r_Shared = if isDef(self) then count := count + 1 endif
                rule r_Work = r_Shared[]
                rule r_Control = r_Shared[]
                main rule r_Main = seq program(worker) program(controller) endseq
            default init s0:
                function count = 0
                agent Worker: r_Work[]
                agent Controller: r_Control[]
            """);
        Map<String, String> expected = simulate(input, 2);
        Asm asm = AsmetaMultipleFlattener.flatten(ASMParser.setUpReadAsm(input.toFile()).getMain(), AgentFlattener.class);
        assertEquals(expected, simulate(save(asm), 2));
        assertEquals("4", expected.get("count"));
    }

    @Test void importedProgramsAreFlattenedAfterModules() throws Exception {
        model("RobotModule", """
            signature:
                domain Robot subsetof Agent
                static robot1: Robot
                controlled passi: Robot -> Integer
            definitions:
                rule r_Robot = passi(self) := passi(self) + 1
            """);
        Path module = directory.resolve("RobotModule.asm");
        Files.writeString(module, Files.readString(module).replace("asm RobotModule", "module RobotModule")
                .replace("signature:", "export *\nsignature:"));
        Path main = model("ImportedRobots", """
            import RobotModule
            signature:
            definitions:
                main rule r_Main = program(robot1)
            default init s0:
                function passi($r in Robot) = 0
                agent Robot: r_Robot[]
            """);
        Map<String, String> expected = simulate(main, 2);
        Asm asm = ImportFlattener.flatten(ASMParser.setUpReadAsm(main.toFile()), directory.toString());
        new AgentFlattener(asm).flattenASM();
        assertEquals(expected, simulate(save(asm), 2));
    }

    @Test void dynamicCreationAndMissingPrograms() throws Exception {
        Path dynamic = model("DynamicRobots", SIGNATURE.replace("domain Robot", "dynamic domain Robot") + """
            rule r_Robot = skip
            main rule r_Main = extend Robot with $r do skip
            """ + INIT);
        assertNotNull(flatten(dynamic));
        Path missing = model("MissingProgram", SIGNATURE + """
            main rule r_Main = program(robot1)
            default init s0:
                function passi($r in Robot) = 0
            """);
        assertTrue(assertThrows(IllegalArgumentException.class, () -> flatten(missing))
                .getMessage().contains("no program"));
    }

    @Test void genericAgentValuesAndCollectionsKeepTheirIdentity() throws Exception {
        Path input = model("GenericAgents", """
            signature:
                domain Worker subsetof Agent
                domain Controller subsetof Agent
                static worker: Worker
                static controller: Controller
                controlled current: Agent
                controlled history: Seq(Agent)
                controlled count: Agent -> Integer
            definitions:
                rule r_Work = count(self) := count(self) + 1
                rule r_Control = count(self) := count(self) + 10
                main rule r_Main = seq
                    program(current)
                    current := if current = worker then controller else worker endif
                endseq
            default init s0:
                function current = worker
                function history = [worker, controller]
                function count($a in Agent) = 0
                agent Worker: r_Work[]
                agent Controller: r_Control[]
            """);
        assertEquals(simulate(input, 3), simulate(save(flatten(input)), 3));
    }

    @Test void bareAgentAndSelfDependentDerivedFunctions() throws Exception {
        Path input = model("BareAgents", """
            signature:
                static firstAgent: Agent
                static secondAgent: Agent
                controlled count: Agent -> Integer
                derived mine: Integer
            definitions:
                function mine = count(self)
                rule r_Count = count(self) := mine + 1
                main rule r_Main = forall $a in Agent with true do program($a)
            default init s0:
                function count($a in Agent) = 0
                agent Agent: r_Count[]
            """);
        assertEquals(simulate(input, 2), simulate(save(flatten(input)), 2));
    }

    @Test void dynamicallyCreatedAgentsCanRunImmediately() throws Exception {
        Path input = model("NewAgents", """
            signature:
                dynamic domain Robot subsetof Agent
                controlled total: Integer
            definitions:
                rule r_Count = total := total + 1
                main rule r_Main = extend Robot with $new do program($new)
            default init s0:
                function total = 0
                agent Robot: r_Count[]
            """);
        assertEquals(simulate(input, 2), simulate(save(flatten(input)), 2));
    }

    @Test void identityFunctionsAndGenericDomainRanges() throws Exception {
        Path input = model("AgentNames", """
            signature:
                domain Worker subsetof Agent
                static worker: Worker
                static other: Agent
                controlled name: String
                controlled recovered: Agent
                controlled count: Agent -> Integer
            definitions:
                rule r_Work = count(self) := count(self) + 1
                rule r_Other = count(self) := count(self) + 10
                main rule r_Main = par
                    name := id(worker)
                    recovered := getAgent("worker")
                    forall $a in Agent with true do program($a)
                endpar
            default init s0:
                function count($a in Agent) = 0
                agent Worker: r_Work[]
                agent Agent: r_Other[]
            """);
        // The existing simulator does not implement id/getAgent. Verify the ordinary
        // lowered definitions directly, including dispatch over the complete universe.
        Map<String, String> state = simulate(save(flatten(input)), 2);
        assertEquals("worker", state.get("name"));
        assertEquals("worker", state.get("recovered"));
        assertEquals("2", state.get("count(worker)"));
        assertEquals("20", state.get("count(other)"));
    }

    @Test void programValuesCanBeStoredAndExecuted() throws Exception {
        Path input = model("CapturedProgram", """
            signature:
                static firstAgent: Agent
                static secondAgent: Agent
                controlled callback: Rule
                controlled count: Agent -> Integer
            definitions:
                rule r_Count = count(self) := count(self) + 1
                main rule r_Main = seq
                    callback := program(firstAgent)
                    callback
                endseq
            default init s0:
                function count($a in Agent) = 0
                agent Agent: r_Count[]
            """);
        Map<String, String> state = simulate(save(flatten(input)), 2);
        assertEquals("2", state.get("count(firstAgent)"));
    }

    @Test void flatteningIsIdempotent() throws Exception {
        Path input = model("Repeat", SIGNATURE + """
            rule r_Robot = passi(self) := passi(self) + 1
            main rule r_Main = program(robot1)
            """ + INIT);
        Asm asm = ASMParser.setUpReadAsm(input.toFile()).getMain();
        AgentFlattener flattener = new AgentFlattener(asm);
        flattener.flattenASM();
        String first = print(asm);
        flattener.flattenASM();
        new AgentFlattener(asm).flattenASM();
        assertEquals(first, print(asm));
    }

    private Asm flatten(Path input) throws Exception {
        return new AgentFlattener(ASMParser.setUpReadAsm(input.toFile()).getMain()).flattenASM();
    }

    private Path model(String name, String body) throws Exception {
        Path library = Path.of("../../../../asm_examples/STDL/StandardLibrary.asm").toAbsolutePath().normalize();
        Path file = directory.resolve(name + ".asm");
        Files.writeString(file, "asm " + name + "\nimport \"" + library.toString().replace('\\', '/') + "\"\n" + body);
        return file;
    }

    private String print(Asm asm) {
        StringWriter text = new StringWriter();
        AsmPrinter printer = new AsmPrinter(new PrintWriter(text));
        printer.visit(asm);
        printer.flush();
        return text.toString();
    }

    private Path save(Asm asm) throws Exception {
        Path output = Files.createDirectories(directory.resolve("flat")).resolve(asm.getName() + ".asm");
        Files.writeString(output, print(asm));
        return output;
    }

    private Map<String, String> simulate(Path input, int steps) throws Exception {
        Simulator simulator = Simulator.createSimulator(input.toString());
        simulator.run(steps);
        Map<String, String> locations = new TreeMap<>();
        simulator.getCurrentState().getLocationMap().forEach((location, value) -> {
            if (!location.getSignature().getName().equals("self") && !location.getSignature().getName().startsWith("agent__"))
                locations.put(location.toString(), value.toString());
        });
        return locations;
    }
}
