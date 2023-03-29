package org.asmeta.runtime_composer;

/**
 * @author Michele Zenoni
 */

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.asmeta.parser.ASMParser;
import org.asmeta.runtime_commander.Commander;
import org.asmeta.runtime_container.Esit;
import org.asmeta.runtime_container.RunOutput;
import org.asmeta.simulationUI.CompositionType;
import org.asmeta.simulationUI.SimGUI;

import asmeta.AsmCollection;
import asmeta.definitions.Function;
import asmeta.definitions.impl.MonitoredFunctionImpl;

public class CompositionManager implements IModelComposition {
	// SimulationContainer ID is set to 0 for testing purposes only.
	// The architecture is ready to be distributed (not implemented yet).
	private static final int TEST_ID = 0;

	private List<AsmetaModel> compositionModelList;

	private Map<AsmetaModel, RunOutput> outputMap;
	private Map<String, Map<String, String>> myLastOutput;
	private CompositionTreeNode compositionTree;
	private RunOutput lastOutput;
	private Map<String, String> lastParLocationValue;
	private boolean multiConsole;
	static ByteArrayOutputStream initialConsole;
	private long remainingExecutionTime;

	private Map<String, String> outputPreviousRun; /*
													 * 2021_12_01 Silvia: Output of the second model of the previous
													 * run. This is the input to the first model in the current run
													 * Initially it is empty
													 */
	
	HashMap<String, ArrayList<String>> monitoredModelsMap = new HashMap<String, ArrayList<String>>(); /*09/02/2022: get all monitored for all models*/
	CompositionRunType compRunType; //is the user using GUI or commander?
	JPanel contentPane;
	

	public CompositionManager(CompositionTreeNode compositionTree, boolean multiConsole, CompositionRunType compRunType) {
		this.compositionTree = compositionTree;
		this.compRunType=compRunType;
		compositionModelList = new ArrayList<>();
		outputMap = new HashMap<>();
		this.multiConsole = multiConsole;
		lastParLocationValue = null;
		lastOutput = null;
		initialConsole = null;

		compositionModelList = compositionModelsLookUp();
		// SILVIA 09/02/2022 -> get all monitored for all the models
		monitoredModelsMap = getMonitoredForModels();
	}

	public CompositionManager(CompositionTreeNode compositionTree, ByteArrayOutputStream initialConsole,
			boolean multiConsole, CompositionRunType compRunType, JPanel contentPane) {
		this.contentPane=contentPane;
		this.compositionTree = compositionTree;
		this.compRunType=compRunType;
		compositionModelList = new ArrayList<>();
		outputMap = new HashMap<>();
		this.multiConsole = multiConsole;
		lastParLocationValue = null;
		lastOutput = null;
		CompositionManager.initialConsole = initialConsole;

		compositionModelList = compositionModelsLookUp();
		// SILVIA 09/02/2022 -> get all monitored for all the models
		monitoredModelsMap = getMonitoredForModels();

		outputPreviousRun = new HashMap<String, String>();
	}

	private HashMap<String, ArrayList<String>> getMonitoredForModels() {
		HashMap<String, ArrayList<String>> monitoredMap = new HashMap<String, ArrayList<String>>();
		for (AsmetaModel model : compositionModelList) {
			monitoredMap.put(model.getModelName(), getAllMonitored(new ArrayList<>(), model.getModelPath()));
		}
		
		/*
		 ArrayList<String> monList = 
		 for (AsmetaModel model : compositionModelList) {
			System.out.println(model.getModelName());
			ArrayList<String> monList = monitoredMap.get(model.getModelName());
			for (String mon: monList)
				System.out.println(mon);
		}*/
		
		return monitoredMap;
	}

	private ArrayList<String> getAllMonitored(ArrayList<String> monitoredList, String modelPath) {
		String root = modelPath.substring(0, modelPath.lastIndexOf("/") + 1);
		if (root.isEmpty()) {
			root = modelPath.substring(0, modelPath.lastIndexOf("\\") + 1);
		}
		if (!modelPath.equals("")) {
			File asmFile = new File(modelPath);
			if (asmFile.exists()) {
				AsmCollection asm;
				try {
					asm = ASMParser.setUpReadAsm(asmFile);
					for (int i = 0; i < asm.getMain().getHeaderSection().getSignature().getFunction().size(); i++) {
						Function f = asm.getMain().getHeaderSection().getSignature().getFunction().get(i);
						if (f instanceof MonitoredFunctionImpl) {
							if (f.getArity() == 0) {
								monitoredList.add(f.getName());
							} else {
								monitoredList.add(f.getName() + "(" + f.getDomain().getName() + ")");
							}
						}
					}

					int importSize = asm.getMain().getHeaderSection().getImportClause().size();
					for (int i = 0; i < importSize; i++) {
						String moduleName = asm.getMain().getHeaderSection().getImportClause().get(i).getModuleName();
						if (!moduleName.toLowerCase().endsWith("standardlibrary")) { // Skips the StandardLibrary.asm
							monitoredList = getAllMonitored(monitoredList, root + moduleName + ".asm");
						}
					}
				} catch (Exception e) {
					monitoredList = null;
				}
			}
		}
		return monitoredList;
	}

	// Execute one step of the models composed in the compositionTree (id just for
	// checking)
	public void runStep(int id, Map<String, String> locationValue) throws CompositionException {
		boolean correct = false;
		for (AsmetaModel model : compositionModelList) {
			if (model.getModelId() == id) {
				correct = true;
				break;
			}
		}

		if (correct) {
			// 2021_12_01 Silvia: If there is the output of the second model in bid pipe use
			// it as input of the first model in the next run
			if (compositionTree.getType() == CompositionTreeNodeType.BID_PIPE_OPERATOR && outputPreviousRun != null) {
				for (Map.Entry<String, String> pair : outputPreviousRun.entrySet()) {
					locationValue.put(pair.getKey(), pair.getValue());
				}
				evaluateCompositionTree(compositionTree, locationValue, -1, -1);
			} else
				evaluateCompositionTree(compositionTree, locationValue, -1, -1);
		}
		lastOutput = null;
		lastParLocationValue = null;
	}

	// runUntilEmpty, runStepTimeout, runUntilEmptyTimeout e rollback per la
	// composizione
	public void runUntilEmpty(int id, Map<String, String> locationValue, int max) throws CompositionException {
		boolean correct = false;
		for (AsmetaModel model : compositionModelList) {
			if (model.getModelId() == id) {
				correct = true;
				break;
			}
		}

		if (correct) {
			evaluateCompositionTree(compositionTree, locationValue, max, -1);
		}
		lastOutput = null;
		lastParLocationValue = null;
	}

	public void runStepTimeout(int id, Map<String, String> locationValue, int timeout) throws CompositionException {
		boolean correct = false;
		for (AsmetaModel model : compositionModelList) {
			if (model.getModelId() == id) {
				correct = true;
				break;
			}
		}

		if (correct) {
			this.remainingExecutionTime = timeout;
			evaluateCompositionTree(compositionTree, locationValue, -1, timeout);
		}
		lastOutput = null;
		lastParLocationValue = null;
	}

	public void runUntilEmptyTimeout(int id, Map<String, String> locationValue, int max, int timeout)
			throws CompositionException {
		boolean correct = false;
		for (AsmetaModel model : compositionModelList) {
			if (model.getModelId() == id) {
				correct = true;
				break;
			}
		}

		if (correct) {
			this.remainingExecutionTime = timeout;
			evaluateCompositionTree(compositionTree, locationValue, max, timeout);
		}
		lastOutput = null;
		lastParLocationValue = null;
	}

	private List<AsmetaModel> compositionModelsLookUp() {
		if (SimGUI.containerInstance != null) {
			return SimGUI.containerInstance.loadedModels;
		} else if (Commander.containerInstance != null) {
			return Commander.containerInstance.loadedModels;
		}

		return null;
	}

	private void runComposedModels(List<AsmetaModel> modelList, CompositionType compType,
			Map<String, String> locationValue, int max, int timeout) throws CompositionException {
		if (modelList.isEmpty()) {
			lastOutput = null;
			lastParLocationValue = null;
			return;
		}
		if (modelList.size() == 1 && compType != null) {
			compType = null;
		}

		outputMap.clear();
		for (AsmetaModel model : modelList) {
			outputMap.put(model, model.getLastOutput());
		}

	
		
		RunOutput compositionOutput = new RunOutput(Esit.UNSAFE, "rout not intialized");
		if (compType != null) {
			switch (compType) {
			case PIPE:
				for (AsmetaModel model : modelList) {
					if (multiConsole) {
						if (model.getModelName().equals(compositionTree.getSource().getModelName())
								&& initialConsole != null) {
							model.outputConsole = initialConsole;
						} else {
							System.setErr(new PrintStream(model.outputConsole));
							System.setOut(new PrintStream(model.outputConsole));
						}
					} else {
						model.outputConsole = null;
					}
					if (model == modelList.get(0)) {
						//Silvia 09/02/2022
						//compositionOutput = model.run(locationValue, max, timeout);
						compositionOutput = model.run(addUnboundLocation(model.getModelName(),locationValue), max, timeout);
						model.output = compositionOutput;
						myLastOutput.put(model.getModelName(), compositionOutput.getOutvalues());
						timeout -= (int) model.getExecutionTime();
						this.remainingExecutionTime -= model.getExecutionTime();
					} else {
						if (compositionOutput.getEsit() == Esit.SAFE) {
							Map<String, String> modelOutput = compositionOutput.getOutvalues();
							// Map<String, String> modelOutput = model.getOutValues();
							// TODO UNBOUND MAPPAVUOTA tutte le monitorate del modello, cerco in out e le
							// init, le altre le chiedo all'ambiente
							compositionOutput = model.run(addUnboundLocation(model.getModelName(),modelOutput), max, timeout);
							model.output = compositionOutput;
							myLastOutput.put(model.getModelName(), compositionOutput.getOutvalues());
							timeout -= (int) model.getExecutionTime();
							this.remainingExecutionTime -= model.getExecutionTime();
						} else {
							System.err.println("Composition rollback!\n");
							if (max < 0) {
								compositionRollback();
								return;
							} else {
								compositionRollbackToState();
								return;
							}
						}
					}
				}
				break;
			case BID_PIPE:
				if (modelList.size() == 2) {
					AsmetaModel first = modelList.get(0);
					AsmetaModel second = modelList.get(1);
					if (multiConsole) {
						if (first.getModelName().equals(compositionTree.getSource().getModelName())
								&& initialConsole != null) {
							first.outputConsole = initialConsole;
						} else {
							System.setErr(new PrintStream(first.outputConsole));
							System.setOut(new PrintStream(first.outputConsole));
						}
					} else {
						first.outputConsole = null;
					}
					// System.err.println("RUN FIRST " + first.getModelName() + "\n");
					compositionOutput = first.run(addUnboundLocation(first.getModelName(),locationValue), max, timeout);
					first.output = compositionOutput;
					myLastOutput.put(first.getModelName(), compositionOutput.getOutvalues());
					this.remainingExecutionTime -= first.getExecutionTime();
					if (multiConsole) {
						System.setErr(new PrintStream(second.outputConsole));
						System.setOut(new PrintStream(second.outputConsole));
					} else {
						second.outputConsole = null;
					}
					if (compositionOutput.getEsit() == Esit.SAFE) {
						assertTrue(compositionOutput == first.getLastOutput());
						timeout -= (int) first.getExecutionTime();
						// System.err.println("RUN SECOND " + second.getModelName() + "\n");
						compositionOutput = second.run(addUnboundLocation(second.getModelName(),first.getLastOutput().getOutvalues()), max, timeout);
						// TODO UNBOUND
						// compositionOutput = second.run(first.getOutValues(), max, timeout);
						second.output = compositionOutput;
						myLastOutput.put(second.getModelName(), compositionOutput.getOutvalues());
						// System.err.println(compositionOutput.getEsit() + " " +
						// compositionOutput.getOutvalues().toString() + "\n");
						this.remainingExecutionTime -= second.getExecutionTime();
						// 2021_12_01 Silvia: Store the output of the second model in bid pipe to use it
						// as input of the first model in the next run
						outputPreviousRun = compositionOutput.getOutvalues();
					} else {
						System.err.println("Composition rollback!\n");
						if (max < 0) {
							compositionRollback();
							return;
						} else {
							compositionRollbackToState();
							return;
						}
					}
					if (multiConsole) {
						System.setErr(new PrintStream(first.outputConsole));
						System.setOut(new PrintStream(first.outputConsole));
					}

					/*
					 * 2021_12_01 Silvia: QUESTO NON VA BENE! riesegue il modello 1 con solo gli
					 * output di 2 e quindi mancano tutte le monitorate
					 * if(compositionOutput.getEsit() == Esit.SAFE) { System.err.println("QUI \n");
					 * assertTrue(compositionOutput == second.getLastOutput()); timeout -= (int)
					 * second.getExecutionTime(); System.err.println("RUN FIRST 2" +
					 * first.getModelName() + "\n"); compositionOutput =
					 * first.run(second.getLastOutput().getOutvalues(), max, timeout);
					 * //compositionOutput = first.run(second.getOutValues(), max, timeout);
					 * first.output = compositionOutput; this.remainingExecutionTime -=
					 * first.getExecutionTime(); } else {
					 * System.err.println("Composition rollback!\n"); if(max < 0) {
					 * compositionRollback(); return; } else { compositionRollbackToState(); return;
					 * } }
					 */
				} else {
					throw new CompositionException("Bidirectional pipe error, too many models!");
				}
				break;
			case PARALLEL: // modelList for PARALLEL should look like: [parModel1, parModel2, ...,
							// parModelN]
				if (modelList.size() >= 2) {
					for (int i = 0; i < modelList.size(); i++) {
						if (multiConsole) {
							System.setErr(new PrintStream(modelList.get(i).outputConsole));
							System.setOut(new PrintStream(modelList.get(i).outputConsole));
						} else {
							modelList.get(i).outputConsole = null;
						}
						modelList.get(i).output = modelList.get(i).run(addUnboundLocation(modelList.get(i).getModelName(),locationValue), max, timeout);
						myLastOutput.put(modelList.get(i).getModelName(), compositionOutput.getOutvalues());
						timeout -= (int) modelList.get(i).getExecutionTime();
						this.remainingExecutionTime -= modelList.get(i).getExecutionTime();
					}

					Map<String, String> finalOutput = new HashMap<>();
					for (int i = 0; i < modelList.size(); i++) {
						if (modelList.get(i).output.getEsit() != Esit.SAFE) {
							System.err.println("Composition rollback!\n");
							if (max < 0) {
								compositionRollback();
								return;
							} else {
								compositionRollbackToState();
								return;
							}
						}
						finalOutput.putAll(modelList.get(i).output.getOutvalues());
						// finalOutput.putAll(modelList.get(i).getOutValues());
					}
					if (multiConsole) {
						System.setErr(new PrintStream(initialConsole));
						System.setOut(new PrintStream(initialConsole));
					}
					lastParLocationValue = finalOutput;
					compositionOutput = null;
				} else {
					throw new CompositionException(
							"Parallel fork join execution error!\n" + "The model list for PARALLEL should look like: "
									+ "[parModel1, parModel2, ..., parModelN]");
				}
				break;
			default:
				throw new CompositionException("Undefined composition type!");
			}
		} else {
			if (modelList.size() == 1) {
				AsmetaModel model = modelList.get(0);
				if (multiConsole) {
					if (model.getModelName().equals(compositionTree.getSource().getModelName())
							&& initialConsole != null) {
						model.outputConsole = initialConsole;
					} else {
						System.setErr(new PrintStream(model.outputConsole));
						System.setOut(new PrintStream(model.outputConsole));
					}
				} else {
					model.outputConsole = null;
				}
				compositionOutput = model.run(addUnboundLocation(model.getModelName(),locationValue), max, timeout);
				myLastOutput.put(model.getModelName(), compositionOutput.getOutvalues());
				model.output = compositionOutput;
				this.remainingExecutionTime -= model.getExecutionTime();
			} else {
				throw new CompositionException("Undefined composition type!");
			}
		}
		lastOutput = compositionOutput;

		for (AsmetaModel model : modelList) {
			System.out.println("OUTPUT MODEL " + model.getModelName() + " " + model.getLastOutput().toString());
		}
	}

	private Map<String, String> addUnboundLocation(String modelname, Map<String, String> locationValue) {
		ArrayList<String> monList = monitoredModelsMap.get(modelname);		
		System.out.println(modelname);
		for (Map.Entry<String, String> entry : locationValue.entrySet()) {
		    System.out.println("EXISTING " + entry.getKey() + ":" + entry.getValue().toString());
		}
		for (String m: monList)
			if (!locationValue.containsKey(m)) {
				if (compRunType==CompositionRunType.SimGUI) {
					locationValue.put(m,getInputGUI(m,modelname));
					System.out.println("FUNCTION " + m);
				}
			}
		return locationValue;
	}

	private String getInputGUI(String monitored, String modelname) {
		Map<String, Object[]> enumDomainFunction = new HashMap<>();
		String inputValue = new String();
		String domainValue = new String();
		String monitoredName = new String();
		String domainName = new String();
		boolean nArity = false;
		Object[] options;
		if(monitored.contains("(") && monitored.contains(")")) {
			nArity = true;
			monitoredName = monitored.substring(0, monitored.indexOf('('));
			domainName = monitored.substring(monitored.indexOf('(') + 1, monitored.indexOf(')'));
			domainValue = (String) JOptionPane.showInputDialog( // expected input syntax: x,y,z,... -> ex.: 50,120 or just 50
						contentPane, 												// parent component
						"Insert '" + monitoredName + "' domain value/values:\n" + 
						"Domain type: [ " + domainName + " ]", 						// message
						"Domain input", 											// title
						JOptionPane.PLAIN_MESSAGE, 									// message type
						null, 														// icon
						null, 														// options
						null														// initial default value
			);
		}
		
		if(nArity) {
			options = enumDomainFunction.get(monitoredName);
			try {
				if(domainValue != null && !domainValue.isEmpty()) {
					monitored = monitoredName + "(" + domainValue + ")";
				} else {
					throw new Exception();
				}
			} catch(Exception e) {
				JOptionPane.showMessageDialog(contentPane, "Error: check the model and the input!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			options = enumDomainFunction.get(monitored);
		}
		
		if(options != null) {
			if(options.length == 0) {
				options = null;
			}
		}
		if(options != null) {
			Random seed = new Random();
			inputValue = (String) options[seed.nextInt(options.length)];
			System.out.println("Generated input for '" + monitored + "': " + inputValue);
		} else {
			inputValue = (String) JOptionPane.showInputDialog(
					contentPane, 								// parent component
					"MY Insert " + modelname + " " + monitored + " value:", 			// message
					"Input", 									// title
					JOptionPane.PLAIN_MESSAGE, 					// message type
					null, 										// icon
					options, 									// options
					null										// initial default value
			);
		}
		return inputValue;
	}

	private void runComposedModels(List<AsmetaModel> modelList, CompositionType compType, int max, int timeout)
			throws CompositionException {
		runComposedModels(modelList, compType, null, max, timeout);
	}

	private void runTreeFromRunOutput(List<CompositionTreeNode> nodeList, CompositionType compType, RunOutput input,
			int max, int timeout) throws CompositionException {
		if (nodeList == null || nodeList.isEmpty()) {
			return;
		}

		List<AsmetaModel> modelList = new ArrayList<>();
		for (CompositionTreeNode node : nodeList) {
			if (node.getType() != CompositionTreeNodeType.MODEL) {
				return;
			}
			modelList.add(getModelFromModelList(node.getModelName(), TEST_ID));
		}
		runComposedModels(modelList, compType, input.getOutvalues(), max, timeout);
	}

	private void runTreeFromLocationValue(List<CompositionTreeNode> nodeList, CompositionType compType,
			Map<String, String> locationValue, int max, int timeout) throws CompositionException {
		if (nodeList == null || nodeList.isEmpty()) {
			return;
		}

		List<AsmetaModel> modelList = new ArrayList<>();
		for (CompositionTreeNode node : nodeList) {
			if (node.getType() != CompositionTreeNodeType.MODEL) {
				return;
			}
			modelList.add(getModelFromModelList(node.getModelName(), TEST_ID));
		}
		runComposedModels(modelList, compType, locationValue, max, timeout);
	}

	private List<AsmetaModel> convertBidToPipe(List<AsmetaModel> modelList1, List<AsmetaModel> modelList2) { // Ottimizzazione
		modelList2.addAll(modelList1);
		modelList1.addAll(modelList2);
		return modelList1;
	}

	private List<AsmetaModel> convertBidToPipe(CompositionTreeNode bidNode) { // Ottimizzazione
		if (bidNode == null || bidNode.getType() != CompositionTreeNodeType.BID_PIPE_OPERATOR
				|| bidNode.childrenCount() != 2) {
			return null;
		}
		List<AsmetaModel> modelList1 = new ArrayList<>();
		List<AsmetaModel> modelList2 = new ArrayList<>();
		if (bidNode.getChildren().get(0).getType() == CompositionTreeNodeType.MODEL) {
			modelList1.add(getModelFromModelList(bidNode.getChildren().get(0).getModelName(), TEST_ID));
		} else if (bidNode.getChildren().get(0).getType() == CompositionTreeNodeType.PIPE_OPERATOR) {
			for (CompositionTreeNode child : bidNode.getChildren().get(0).getChildren()) {
				if (child.getType() != CompositionTreeNodeType.MODEL) {
					return null;
				}
				modelList1
						.add(getModelFromModelList(child.getModelName(), Commander.containerInstance.getSimulatorId()));
			}
		} else {
			return null;
		}

		if (bidNode.getChildren().get(1).getType() == CompositionTreeNodeType.MODEL) {
			modelList2.add(getModelFromModelList(bidNode.getChildren().get(1).getModelName(), TEST_ID));
		} else if (bidNode.getChildren().get(1).getType() == CompositionTreeNodeType.PIPE_OPERATOR) {
			for (CompositionTreeNode child : bidNode.getChildren().get(1).getChildren()) {
				if (child.getType() != CompositionTreeNodeType.MODEL) {
					return null;
				}
				modelList2.add(getModelFromModelList(child.getModelName(), TEST_ID));
			}
		} else {
			return null;
		}

		return convertBidToPipe(modelList1, modelList2);
	}

	// Recursive function to evaluate complex composition trees
	private void evaluateCompositionTree(CompositionTreeNode node, Map<String, String> locationValue, int max,
			int timeout) throws CompositionException {
		if (node == null) {
			lastOutput = null;
			lastParLocationValue = null;
			return;
		}
		switch (node.getType()) {
		case PIPE_OPERATOR:
			boolean simplePipe = true;
			for (CompositionTreeNode child : node.getChildren()) {
				if (child.getType() != CompositionTreeNodeType.MODEL) {
					simplePipe = false;
				}
			}
			if (simplePipe) {
				if (lastOutput != null) {
					runTreeFromRunOutput(node.getChildren(), CompositionType.PIPE, lastOutput, max,
							(int) Math.ceil(this.remainingExecutionTime));
				} else {
					runTreeFromLocationValue(node.getChildren(), CompositionType.PIPE, locationValue, max,
							(int) Math.ceil(this.remainingExecutionTime));
				}
			} else {
				for (CompositionTreeNode child : node.getChildren()) {
					if (lastOutput == null) {
						evaluateCompositionTree(child, locationValue, max,
								(int) Math.ceil(this.remainingExecutionTime));
					} else {
						evaluateCompositionTree(child, max, (int) Math.ceil(this.remainingExecutionTime));
					}
				}
			}
			break;
		case BID_PIPE_OPERATOR:
			boolean simpleBid = true;
			for (CompositionTreeNode child : node.getChildren()) {
				if (child.getType() != CompositionTreeNodeType.MODEL) {
					simpleBid = false;
				}
			}
			if (simpleBid) {
				if (lastOutput != null) {
					runTreeFromRunOutput(node.getChildren(), CompositionType.BID_PIPE, lastOutput, max,
							(int) Math.ceil(this.remainingExecutionTime));
				} else {
					runTreeFromLocationValue(node.getChildren(), CompositionType.BID_PIPE, locationValue, max,
							(int) Math.ceil(this.remainingExecutionTime));
				}

			} else {
				List<AsmetaModel> resultList = convertBidToPipe(node);
				if (resultList != null) {
					if (lastOutput != null) {
						runComposedModels(resultList, CompositionType.PIPE, lastOutput.getOutvalues(), max,
								(int) Math.ceil(this.remainingExecutionTime));
					} else {
						runComposedModels(resultList, CompositionType.PIPE, locationValue, max,
								(int) Math.ceil(this.remainingExecutionTime));
					}
				} else {
					for (CompositionTreeNode child : node.getChildren()) {
						if (lastParLocationValue != null) {
							evaluateCompositionTree(child, lastParLocationValue, max,
									(int) Math.ceil(this.remainingExecutionTime));
							lastParLocationValue = null;
						} else if (lastOutput == null) {
							evaluateCompositionTree(child, locationValue, max,
									(int) Math.ceil(this.remainingExecutionTime));
						} else {
							evaluateCompositionTree(child, max, (int) Math.ceil(this.remainingExecutionTime));
						}
					}
					if (lastParLocationValue != null) {
						evaluateCompositionTree(node.getChildren().get(0), lastParLocationValue, max,
								(int) Math.ceil(this.remainingExecutionTime));
						lastParLocationValue = null;
					} else {
						evaluateCompositionTree(node.getChildren().get(0), max,
								(int) Math.ceil(this.remainingExecutionTime));
					}
				}
			}
			break;
		case PAR_OPERATOR:
			boolean simplePar = true;
			for (CompositionTreeNode child : node.getChildren()) {
				if (child.getType() != CompositionTreeNodeType.MODEL) {
					simplePar = false;
				}
			}
			if (simplePar) {
				List<CompositionTreeNode> resultList = node.getChildren();
				if (lastOutput != null) {
					runTreeFromRunOutput(resultList, CompositionType.PARALLEL, lastOutput, max,
							(int) Math.ceil(this.remainingExecutionTime));
				} else {
					runTreeFromLocationValue(resultList, CompositionType.PARALLEL, locationValue, max,
							(int) Math.ceil(this.remainingExecutionTime));
				}
			} else {
				for (CompositionTreeNode child : node.getChildren()) {
					if (lastOutput == null) {
						evaluateCompositionTree(child, locationValue, max,
								(int) Math.ceil(this.remainingExecutionTime));
					} else {
						evaluateCompositionTree(child, max, (int) Math.ceil(this.remainingExecutionTime));
					}
				}
			}
			break;
		case MODEL:
			List<CompositionTreeNode> singleModelList = new ArrayList<>();
			singleModelList.add(node);
			if (lastParLocationValue != null) {
				runTreeFromLocationValue(singleModelList, null, lastParLocationValue, max,
						(int) Math.ceil(this.remainingExecutionTime));
				lastParLocationValue = null;
			} else if (lastOutput != null) {
				runTreeFromRunOutput(singleModelList, null, lastOutput, max,
						(int) Math.ceil(this.remainingExecutionTime));
			} else {
				runTreeFromLocationValue(singleModelList, null, locationValue, max,
						(int) Math.ceil(this.remainingExecutionTime));
			}
			break;
		default:
			throw new CompositionException("Undefined composition type!");
		}
	}

	private void evaluateCompositionTree(CompositionTreeNode node, int max, int timeout) throws CompositionException {
		evaluateCompositionTree(node, null, max, timeout);
	}

	public void compositionRollback() throws CompositionRollbackException {
		if (outputMap != null && !outputMap.isEmpty()) {
			for (AsmetaModel model : compositionModelList) {
				if (outputMap.containsKey(model)) {
					if (outputMap.get(model) != model.getLastOutput()) {
						System.out.println("Composed model single-step-rollback:");
						System.out.println(
								"Model ID: " + model.getModelId() + " | Model name: " + model.getModelName() + "\n");
						model.getSimulationContainer().rollback(model.getModelId());
					}
				}
			}
		} else {
			throw new CompositionRollbackException("The composition model list is undefined or empty!");
		}
	}

	public void compositionRollbackToState() throws CompositionRollbackException {
		if (outputMap != null && !outputMap.isEmpty()) {
			for (AsmetaModel model : compositionModelList) {
				if (outputMap.containsKey(model)) {
					if (outputMap.get(model) != model.getLastOutput()) {
						System.out.println("Composed model multi-step-rollback:");
						System.out.println(
								"Model ID: " + model.getModelId() + " | Model name: " + model.getModelName() + "\n");
						model.getSimulationContainer().rollbackToState(model.getModelId());
					}
				}
			}
		} else {
			throw new CompositionRollbackException("The composition model list is undefined or empty!");
		}
	}

	public AsmetaModel getModelFromModelList(String modelName, int simContainerID) {
		if (compositionModelList == null || compositionModelList.isEmpty()) {
			return null;
		}

		for (AsmetaModel model : compositionModelList) {
			if (model.getModelName().equals(modelName) && model.getSimulatorId() == simContainerID) {
				return model;
			}
		}
		return null;
	}

	public AsmetaModel getModelFromModelList(int asmetaModelID, int simContainerID) {
		if (compositionModelList == null || compositionModelList.isEmpty()) {
			return null;
		}

		for (AsmetaModel model : compositionModelList) {
			if (model.getModelId() == asmetaModelID && model.getSimulatorId() == simContainerID) {
				return model;
			}
		}
		return null;
	}

	public CompositionTreeNode getCompositionTreeRoot() {
		return this.compositionTree.getRoot();
	}

	public RunOutput getLastOutput() {
		return this.lastOutput;
	}
}
