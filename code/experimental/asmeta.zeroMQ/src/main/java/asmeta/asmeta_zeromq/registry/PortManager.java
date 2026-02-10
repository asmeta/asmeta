package asmeta.asmeta_zeromq.registry;

import java.util.*;
import java.util.stream.Collectors;

public class PortManager {
    private final Queue<String> availablePorts;
    private final Map<String, String> modelPortMap;

    public PortManager(Properties config) {
        this.availablePorts = new LinkedList<>();
        this.modelPortMap = new HashMap<>();
        
        // 1) Estrae il pool di porte dai commons (es: 5561,5564)
        String poolPort = config.getProperty("common.PORT_POOL", "");
        if (!poolPort.isEmpty()) {
            availablePorts.addAll(Arrays.stream(poolPort.split(","))
                    .map(String::trim)
                    .collect(Collectors.toList())); //alla fine otteniamo ["5561","5564"]
        }
    }

    public String allocatePortFor(String modelName) {
        if (modelPortMap.containsKey(modelName)) {
            return modelPortMap.get(modelName);
        }
    
        String port = availablePorts.poll();
        if (port == null) {
            throw new RuntimeException("Errore: Port pool esaurito per l'istanza: " + modelName);
        }
        
        modelPortMap.put(modelName, port);
        System.out.println("[ALLOCATOR] Assigned port " + port + " to " + modelName);
        return port;
    }

}