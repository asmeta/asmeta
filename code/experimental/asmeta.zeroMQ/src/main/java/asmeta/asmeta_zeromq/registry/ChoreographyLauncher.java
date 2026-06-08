package asmeta.asmeta_zeromq.registry;

import asmeta.asmeta_zeromq.ZeroMQWA;
import asmeta.asmeta_zeromq.ast.ISimulationNode;
import asmeta.asmeta_zeromq.parser.CompositionParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

//Launcher for co-simulation in Choreographed mode. 
//Dynamically generates the AST, calculates the sockets and launches the ASMs in separate JVM processes

public class ChoreographyLauncher extends SimulationLauncher {

    public ChoreographyLauncher(String configPath) {
        super(configPath);
    }

    @Override
    protected List<ZeroMQWA> setupComposition(Properties systemConfig) throws Exception {
        return new ArrayList<>(); 
    }

    @Override
    public void run() throws Exception {
        Properties systemConfig = loadUnifiedConfig(configPath);

        // Reading the formula and parsing AST
        String compositionalFormula = systemConfig.getProperty("setup.setup_comp");
        if (compositionalFormula == null || compositionalFormula.isEmpty()) {
            throw new IllegalArgumentException("Missing composition formula.");
        }
        System.out.println("[ChoreographyLauncher] : Parsing the formula" + compositionalFormula);

        CompositionParser parser = new CompositionParser();
        //the reference to the root of tree
        ISimulationNode astRoot = parser.parse(compositionalFormula);

        //Wiring calculation via Visitor
        ChoreographyVisitor visitor = new ChoreographyVisitor();
        
        String envFuncs = systemConfig.getProperty("common.ASM_ENVIRONMENT_FUNCTIONS", "");
        List<String> envTopics = new ArrayList<>();
        if (!envFuncs.isEmpty()) {
            envTopics = List.of(envFuncs.split(","));
        } else {
            envTopics.add("env_output");
        }
        //Start the recursive traversal of the tree
        astRoot.accept(visitor, envTopics);
        //a map that associates each model name with the list of topics it must subscribe to, generates by Visitor
        Map<String, List<String>> subscriptionsMap = visitor.getModelSubscriptions();

        List<Process> activeProcesses = new ArrayList<>();
        
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String mainClassName = "asmeta.asmeta_zeromq.registry.ModelProcess";

        for (Map.Entry<String, List<String>> entry : subscriptionsMap.entrySet()) {
            String modelName = entry.getKey();
            List<String> requiredInputTopics = entry.getValue();

            String argMode = "CHOREOGRAPHED";
            String argPub = "OUT_" + modelName;
            String argSub = requiredInputTopics.isEmpty() ? "NONE" : String.join(",", requiredInputTopics);

            	ProcessBuilder builder = new ProcessBuilder(
                javaBin, "-cp", classpath, mainClassName, 
                configPath, modelName, 
                argMode, argPub, argSub
            );

            builder.inheritIO(); 
            Process process = builder.start();
            activeProcesses.add(process);
        }

        Thread.sleep(6000);              
        startEnvironment();
        System.out.println("[ChoreographyLauncher] Environment started");


        for (Process p : activeProcesses) {
            p.waitFor();
        }
    }

    public static void main(String[] args) {
        try {
            String configPath = "configs/MRM/zmq_config_MRM.properties"; 
            //String configPath = "configs/incDecMulti/zmq_config_IncDecMulti.properties"; 
            ChoreographyLauncher launcher = new ChoreographyLauncher(configPath);
            launcher.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}