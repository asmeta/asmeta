package asmeta.asmeta_zeromq.registry;

import com.google.gson.Gson;
import org.zeromq.*;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PortManager {
	private static final Gson gson = new Gson();

	private final String registryAddr;
	private final Map<String, Deque<Integer>> poolsByRole = new HashMap<>(); 
	private final Map<String, Set<String>> assignedByRole = new ConcurrentHashMap<>(); 

	public PortManager(Properties props) {
		this.registryAddr = props.getProperty("REGISTRY_ADDR", "tcp://127.0.0.1:5570");
		// carico pool chiavi tipo PORT_POOL.producer, PORT_POOL.consumer
		for (String k : props.stringPropertyNames()) {
			if (k.startsWith("PORT_POOL.")) {
				String role = k.substring("PORT_POOL.".length());
				Deque<Integer> dq = new ArrayDeque<>();
				for (String p : props.getProperty(k).split(",")) {
					String t = p.trim();
					if (!t.isEmpty())
						dq.add(Integer.parseInt(t));
				}
				poolsByRole.put(role, dq);
				assignedByRole.put(role, Collections.synchronizedSet(new HashSet<>()));
			}
		}
	}

	public void run() {
		try (ZContext ctx = new ZContext()) {
			ZMQ.Socket rep = ctx.createSocket(SocketType.REP);
			rep.bind(registryAddr);
			System.out.println("[REGISTRY] Listening on " + registryAddr);

			while (!Thread.currentThread().isInterrupted()) {
				String req = rep.recvStr();
				Map<String, Object> m = gson.fromJson(req, Map.class);
				String op = (String) m.get("op");
				Map<String, Object> resp = new HashMap<>();

				try {
					switch (op) {
					case "ALLOC": {
						String role = (String) m.get("role");
						String host = (String) m.get("host");
						String endpoint = alloc(role, host);
						if (endpoint == null) {
							resp.put("ok", false);
							resp.put("err", "NO_PORT");
						} else {
							resp.put("ok", true);
							resp.put("endpoint", endpoint);
						}
						break;
					}
					case "ALLOC_FAIL": {
						String role = (String) m.get("role");
						String endpoint = (String) m.get("endpoint");
						release(role, endpoint);
						resp.put("ok", true);
						break;
					}
					case "REGISTER": {
						String role = (String) m.get("role");
						String endpoint = (String) m.get("endpoint");
						assignedByRole.getOrDefault(role, Collections.synchronizedSet(new HashSet<>())).add(endpoint);
						resp.put("ok", true);
						break;
					}
					case "LOOKUP": {
						List<String> roles = (List<String>) m.get("roles");
						Map<String, Object> eps = new HashMap<>();
						for (String r : roles) {
							Set<String> s = assignedByRole.getOrDefault(r, Collections.emptySet());
							eps.put(r, new ArrayList<>(s));
						}
						resp.put("ok", true);
						resp.put("endpoints", eps);
						break;
					}
					case "RELEASE": {
						String role = (String) m.get("role");
						String endpoint = (String) m.get("endpoint");
						assignedByRole.getOrDefault(role, Collections.emptySet()).remove(endpoint);
						release(role, endpoint);
						resp.put("ok", true);
						break;
					}
					default:
						resp.put("ok", false);
						resp.put("err", "UNKNOWN_OP");
					}
				} catch (Exception e) {
					resp.put("ok", false);
					resp.put("err", e.getMessage());
				}
				rep.send(gson.toJson(resp));
			}
		}
	}

	private synchronized String alloc(String role, String host) {
		Deque<Integer> q = poolsByRole.get(role);
		if (q == null || q.isEmpty())
			return null;
		int port = q.pollFirst(); // prendi porta libera
		return "tcp://" + host + ":" + port;
	}

	private synchronized void release(String role, String endpoint) {
		try {
			int idx = endpoint.lastIndexOf(':');
			int port = Integer.parseInt(endpoint.substring(idx + 1));
			Deque<Integer> q = poolsByRole.get(role);
			if (q != null && !q.contains(port))
				q.addFirst(port);
		} catch (Exception ignored) {
		}
	}

	// Carico solo la sezione "common." del file unificato
	public static Properties loadCommon(String unifiedPath) {
		try (InputStream in = PortManager.class.getClassLoader().getResourceAsStream(unifiedPath)) {
			if (in == null)
				throw new RuntimeException("Config not found: " + unifiedPath);
			Properties all = new Properties();
			all.load(in);
			Properties out = new Properties();
			String pref = "common.";
			for (String k : all.stringPropertyNames()) {
				if (k.startsWith(pref))
					out.put(k.substring(pref.length()), all.getProperty(k));
			}
			return out;
		} catch (Exception e) {
			throw new RuntimeException("Cannot load " + unifiedPath, e);
		}
	}

	public static void main(String[] args) {
		Properties p = loadCommon("configs/producerconsumer/zmq_config_unified.properties");
		new PortManager(p).run();
	}
}
