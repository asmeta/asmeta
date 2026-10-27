package asmeta.asmetal2java.codegen.generator;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import asmeta.asmetal2java.codegen.application.TranslatorImpl;
import asmeta.asmetal2java.codegen.config.TranslatorOptionsImpl;

/** Exercises the public translation pipeline, including module and agent flattening. */
class AgentTranslationTest {
    @TempDir Path directory;

    @Test void agentsReuseAbstractDomainSettersAndGetters() throws Exception {
        verifyTranslation(false);
    }

    @Test void importedAgentsAreFlattenedBeforeBothJavaGenerators() throws Exception {
        verifyTranslation(true);
    }

    @Test void genericDispatchCollectionsAndContextualFunctionsCompileAndRun() throws Exception {
        Path output = translate("GenericAgents", """
            signature:
                domain Worker subsetof Agent
                domain Controller subsetof Agent
                static worker: Worker
                static controller: Controller
                controlled current: Agent
                controlled count: Agent -> Integer
                controlled history: Seq(Agent)
                controlled pair: Prod(Agent, Agent)
                derived mine: Integer
            definitions:
                function mine = count(self)
                rule r_Work = count(self) := mine + 1
                rule r_Control = count(self) := mine + 10
                main rule r_Main = seq
                    program(current)
                    current := if current = worker then controller else worker endif
                endseq
            default init s0:
                function current = worker
                function count($a in Agent) = 0
                function history = [worker, controller]
                function pair = (worker, controller)
                agent Worker: r_Work[]
                agent Controller: r_Control[]
            """);
        try (URLClassLoader loader = loader(output)) {
            Class<?> type = loader.loadClass("GenericAgents_ATG");
            Object wrapper = instance(type);
            invoke(type, wrapper, "step", null, null);
            invoke(type, wrapper, "step", null, null);
            assertEquals(1, invoke(type, wrapper, "get_count_fromDomain_worker", null, null));
            assertEquals(10, invoke(type, wrapper, "get_count_fromDomain_controller", null, null));
            assertEquals("abstract_worker", invoke(type, wrapper, "get_current", null, null));
            assertTrue(java.util.Arrays.stream(type.getDeclaredMethods()).noneMatch(m -> m.getName().startsWith("get_agent__")));
        }
    }

    @Test void bareAgentAndDynamicCreationCompileAndKeepNames() throws Exception {
        Path output = translate("DynamicAgents", """
            signature:
                dynamic domain Robot subsetof Agent
                controlled total: Integer
                controlled latest: Agent
            definitions:
                rule r_Count = total := total + 1
                main rule r_Main = extend Robot with $new do seq
                    latest := $new
                    program($new)
                endseq
            default init s0:
                function total = 0
                agent Robot: r_Count[]
            """);
        try (URLClassLoader loader = loader(output)) {
            Class<?> type = loader.loadClass("DynamicAgents_ATG");
            Object wrapper = instance(type);
            invoke(type, wrapper, "step", null, null);
            invoke(type, wrapper, "step", null, null);
            assertEquals(2, invoke(type, wrapper, "get_total", null, null));
            assertEquals("abstract_robot!2", invoke(type, wrapper, "get_latest", null, null));
        }
    }

    @Test void identityLookupAndCapturedProgramsCompileAndRun() throws Exception {
        Path output = translate("AgentCallbacks", """
            signature:
                static firstAgent: Agent
                static secondAgent: Agent
                controlled count: Agent -> Integer
                controlled callback: Rule
                controlled name: String
                controlled recovered: Agent
            definitions:
                rule r_Count = count(self) := count(self) + 1
                main rule r_Main = seq
                    callback := program(firstAgent)
                    callback
                    name := id(firstAgent)
                    recovered := getAgent(name)
                endseq
            default init s0:
                function count($a in Agent) = 0
                agent Agent: r_Count[]
            """);
        try (URLClassLoader loader = loader(output)) {
            Class<?> type = loader.loadClass("AgentCallbacks_ATG");
            Object wrapper = instance(type);
            invoke(type, wrapper, "step", null, null);
            assertEquals(1, invoke(type, wrapper, "get_count_fromDomain_firstAgent", null, null));
            assertEquals("firstAgent", invoke(type, wrapper, "get_name", null, null));
            assertEquals("abstract_firstAgent", invoke(type, wrapper, "get_recovered", null, null));
            assertTrue(java.util.Arrays.stream(type.getDeclaredMethods()).noneMatch(m -> m.getName().equals("get_callback")));
        }
    }

    @Test void choiceTracesKeepOriginalRuleSignaturesAndDomains() throws Exception {
        Path output = translate("AgentChoices", """
            signature:
                domain Worker subsetof Agent
                static worker: Worker
                static other: Agent
                monitored allowed: Worker -> Boolean
                controlled current: Agent
            definitions:
                rule r_Choose($caller in Agent) =
                    choose $picked in {worker} with self = $caller do current := $picked
                rule r_Work = r_Choose[self]
                main rule r_Main = program(worker)
            default init s0:
                agent Worker: r_Work[]
            """);
        try (URLClassLoader loader = loader(output)) {
            Class<?> type = loader.loadClass("AgentChoices_ATG");
            Class<?> execution = loader.loadClass("AgentChoices");
            Object wrapper = instance(type);
            Method start = execution.getDeclaredMethod("__asmetaStartChoiceRecording");
            Method stop = execution.getDeclaredMethod("__asmetaStopChoiceRecording");
            start.setAccessible(true); stop.setAccessible(true);
            start.invoke(null);
            invoke(type, wrapper, "step", null, null);
            String[][] trace = (String[][]) stop.invoke(null);
            assertEquals(1, trace.length);
            assertEquals("r_Choose(Agent)", trace[0][1]);
            assertEquals("Agent", trace[0][4]);
            assertEquals("worker", trace[0][6]);
            assertEquals("abstract_worker", invoke(type, wrapper, "get_current", null, null));
            assertNotNull(type.getDeclaredMethod("set_allowed_fromDomain_worker", boolean.class));
            assertTrue(java.util.Arrays.stream(type.getDeclaredMethods())
                    .noneMatch(m -> m.getName().equals("set_allowed_fromDomain_other")));
        }
    }

    @Test void overloadsOnAgentDomainsRemainDistinctAfterErasure() throws Exception {
        Path output = translate("AgentOverloads", """
            signature:
                domain Worker subsetof Agent
                domain Controller subsetof Agent
                static worker: Worker
                static controller: Controller
                static identity: Worker -> Agent
                static identity: Controller -> Agent
                controlled workerResult: Agent
                controlled controllerResult: Agent
                controlled count: Agent -> Integer
            definitions:
                function identity($a in Worker) = $a
                function identity($a in Controller) = $a
                rule r_Run($a in Worker) = count($a) := 1
                rule r_Run($a in Controller) = count($a) := 10
                rule r_Work = par workerResult := identity(worker) r_Run[worker] endpar
                rule r_Control = par controllerResult := identity(controller) r_Run[controller] endpar
                main rule r_Main = par program(worker) program(controller) endpar
            default init s0:
                function count($a in Agent) = 0
                agent Worker: r_Work[]
                agent Controller: r_Control[]
            """);
        try (URLClassLoader loader = loader(output)) {
            Class<?> type = loader.loadClass("AgentOverloads_ATG");
            Object wrapper = instance(type);
            invoke(type, wrapper, "step", null, null);
            assertEquals("abstract_worker", invoke(type, wrapper, "get_workerResult", null, null));
            assertEquals("abstract_controller", invoke(type, wrapper, "get_controllerResult", null, null));
            assertEquals(1, invoke(type, wrapper, "get_count_fromDomain_worker", null, null));
            assertEquals(10, invoke(type, wrapper, "get_count_fromDomain_controller", null, null));
        }
    }

    @Test void agentValuedFunctionsAreNotTreatedAsNewIdentities() throws Exception {
        Path output = translate("AgentAliases", """
            signature:
                domain Worker subsetof Agent
                static worker: Worker
                static defaultAgent: Agent
                controlled selected: Agent
                derived selectedAgent: Agent
                controlled count: Agent -> Integer
            definitions:
                function defaultAgent = worker
                function selectedAgent = selected
                rule r_Work = count(self) := count(self) + 1
                main rule r_Main = program(selectedAgent)
            default init s0:
                function selected = defaultAgent
                function count($a in Agent) = 0
                agent Worker: r_Work[]
            """);
        try (URLClassLoader loader = loader(output)) {
            Class<?> type = loader.loadClass("AgentAliases_ATG");
            Object wrapper = instance(type);
            invoke(type, wrapper, "step", null, null);
            assertEquals(1, invoke(type, wrapper, "get_count_fromDomain_worker", null, null));
            assertTrue(java.util.Arrays.stream(type.getDeclaredMethods())
                    .noneMatch(m -> m.getName().equals("get_count_fromDomain_defaultAgent")));
        }
    }

    private Path translate(String name, String body) throws Exception {
        Path library = Path.of("../../../../asm_examples/STDL/StandardLibrary.asm").toAbsolutePath().normalize();
        Path input = directory.resolve(name + ".asm");
        Files.writeString(input, "asm " + name + "\nimport \"" + library.toString().replace('\\', '/') + "\"\n" + body);
        TranslatorOptionsImpl options = new TranslatorOptionsImpl();
        options.setValue("formatter", false);
        options.setValue("copyAsm", false);
        options.setValue("export", true);
        options.setValue("coverOutputs", false);
        TranslatorImpl translator = new TranslatorImpl(options);
        translator.setInput(input.toString());
        translator.setWorkingDir(directory.resolve("work").toString());
        Path output = directory.resolve("output");
        translator.setOutput(output.toString());
        translator.setCompilerVersion("17");
        translator.setMode("testGen");
        translator.generate();
        return output;
    }

    private URLClassLoader loader(Path output) throws Exception {
        return new URLClassLoader(new java.net.URL[] { output.toUri().toURL() }, getClass().getClassLoader());
    }

    private Object instance(Class<?> type) throws Exception {
        var constructor = type.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    private void verifyTranslation(boolean imported) throws Exception {
        Path library = Path.of("../../../../asm_examples/STDL/StandardLibrary.asm").toAbsolutePath().normalize();
        String libraryImport = "import \"" + library.toString().replace('\\', '/') + "\"\n";
        String declarations = """
            signature:
                domain Robot subsetof Agent
                static robot1: Robot
                static robot2: Robot
                monitored muovi: Robot -> Boolean
                controlled passi: Robot -> Integer
            definitions:
                rule r_Incrementa = passi(self) := passi(self) + 1
                rule r_Robot = if muovi(self) then r_Incrementa[] endif
            """;
        String main = """
                main rule r_Main = forall $r in Robot with true do program($r)
            default init s0:
                function passi($r in Robot) = 0
                agent Robot: r_Robot[]
            """;
        Path input = directory.resolve("Robots.asm");
        if (imported) {
            Files.writeString(directory.resolve("RobotModule.asm"),
                    "module RobotModule\n" + libraryImport + "export *\n" + declarations);
            Files.writeString(input, "asm Robots\n" + libraryImport
                    + "import RobotModule\nsignature:\ndefinitions:\n" + main);
        } else {
            Files.writeString(input, "asm Robots\n" + libraryImport + declarations + main);
        }
        String original = Files.readString(input);
        TranslatorOptionsImpl options = new TranslatorOptionsImpl();
        options.setValue("formatter", false);
        options.setValue("copyAsm", false);
        options.setValue("export", true);
        options.setValue("coverOutputs", false);
        TranslatorImpl translator = new TranslatorImpl(options);
        translator.setWorkingDir(directory.resolve("work").toString());
        translator.setInput(input.toString());
        Path output = directory.resolve("output");
        translator.setOutput(output.toString());
        translator.setCompilerVersion("17");
        translator.setMode("testGen");
        translator.generate();
        assertEquals(original, Files.readString(input), "The original ASM remains the Avalla scenario target");
        assertTrue(Files.exists(output.resolve("Robots.java")));
        assertTrue(Files.exists(output.resolve("Robots_ATG.java")));
        try (URLClassLoader loader = new URLClassLoader(new java.net.URL[] { output.toUri().toURL() },
                getClass().getClassLoader())) {
            Class<?> type = loader.loadClass("Robots_ATG");
            var constructor = type.getDeclaredConstructor();
            constructor.setAccessible(true);
            Object wrapper = constructor.newInstance();
            invoke(type, wrapper, "set_muovi_fromDomain_robot1", boolean.class, true);
            invoke(type, wrapper, "set_muovi_fromDomain_robot2", boolean.class, false);
            invoke(type, wrapper, "step", null, null);
            assertEquals(1, invoke(type, wrapper, "get_passi_fromDomain_robot1", null, null));
            assertEquals(0, invoke(type, wrapper, "get_passi_fromDomain_robot2", null, null));
        }
    }

    private Object invoke(Class<?> type, Object instance, String name, Class<?> parameter, Object value) throws Exception {
        Method method = parameter == null ? type.getDeclaredMethod(name) : type.getDeclaredMethod(name, parameter);
        method.setAccessible(true);
        return parameter == null ? method.invoke(instance) : method.invoke(instance, value);
    }
}
