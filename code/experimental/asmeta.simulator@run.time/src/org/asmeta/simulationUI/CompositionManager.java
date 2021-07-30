package org.asmeta.simulationUI;

/**
 * @author Michele Zenoni
 */

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.asmeta.runtime_commander.Commander;
import org.asmeta.runtime_commander.CompositionTreeNode;
import org.asmeta.runtime_commander.CompositionTreeNodeType;
import org.asmeta.runtime_container.Esit;
import org.asmeta.runtime_container.RunOutput;
import org.asmeta.runtime_container.SimulationContainer;

public class CompositionManager implements IModelComposition {
	private List<AsmetaModel> compositionModelList;
	private CompositionTreeNode compositionTree;
	private RunOutput lastOutput;
	private Map<String, String> lastParLocationValue;
	private boolean multiConsole;
	static ByteArrayOutputStream initialConsole;
	
	public CompositionManager(CompositionTreeNode compositionTree, boolean multiConsole) {
		this.compositionTree = compositionTree;
		compositionModelList = new ArrayList<>();
		this.multiConsole = multiConsole;
		lastParLocationValue = null;
		lastOutput = null;
		initialConsole = null;
		initialize();
	}
	
	public CompositionManager(CompositionTreeNode compositionTree, ByteArrayOutputStream initialConsole, boolean multiConsole) {
		this.compositionTree = compositionTree;
		compositionModelList = new ArrayList<>();
		this.multiConsole = multiConsole;
		lastParLocationValue = null;
		lastOutput = null;
		CompositionManager.initialConsole = initialConsole;
		initialize();
	}
	
	private void initialize() {
		SimulationContainer contInstance1 = SimGUI.containerInstance;
		SimulationContainer contInstance2 = new SimulationContainer();
		
		compositionModelList = compositionModelsLookUp();
	}
	
	// Execute one step of the models composed in the compositionTree (id just for checking)
	public void runStep(int id, Map<String, String> locationValue) throws ModelCreationException, CompositionException {
		evaluateCompositionTree(compositionTree, locationValue);
		lastOutput = null;
		lastParLocationValue = null;
	}
	
	// TODO: implementare per sistema distribuito: check di tutti le istanze di SimulationContainer e unione delle List<AsmetaModel> loadedModels
	private List<AsmetaModel> compositionModelsLookUp() {
		//List<AsmetaModel> unionModelList = Commander.containerInstance.loadedModels;
		//unionModelList.addAll(SimGUI.containerInstance.loadedModels);
		return SimGUI.containerInstance.loadedModels;
	}

	private void runCompositionStep(List<AsmetaModel> modelList, CompositionType compType, Map<String, String> locationValue) throws CompositionException {
		if(modelList.isEmpty()) {
			lastOutput = null;
			lastParLocationValue = null;
			return;
		}
		if(modelList.size() == 1 && compType != null) {
			compType = null;
		}
		RunOutput compositionOutput = new RunOutput(Esit.UNSAFE, "rout not intialized");
		if(compType != null) {
			switch(compType) {
			case PIPE:
				for(AsmetaModel model: modelList) {
					if(multiConsole) {
						if(model.getModelName().equals(compositionTree.getSource().getModelName()) && initialConsole != null) {
							model.outputConsole = initialConsole;
						} else {
							System.setErr(new PrintStream(model.outputConsole));
							System.setOut(new PrintStream(model.outputConsole));
						}
					} else {
						model.outputConsole = null;
					}
					if(model == modelList.get(0)) {
						compositionOutput = model.runStep(locationValue);
						model.output = compositionOutput;
					} else {
						if(compositionOutput.getEsit() == Esit.SAFE) {
							Map<String, String> modelOutput = compositionOutput.getControlledvalues();
							compositionOutput = model.runStep(modelOutput);
							model.output = compositionOutput;
						} else {
							System.err.println("Composition model rollback!\n");
							compositionRollback();
						}
					}
				}
			break;
			case BID_PIPE:
				if(modelList.size() == 2) {
					AsmetaModel first = modelList.get(0);
					AsmetaModel second = modelList.get(1);
					if(multiConsole) {
						if(initialConsole != null) {
							first.outputConsole = initialConsole;
						} else {
							System.setErr(new PrintStream(first.outputConsole));
							System.setOut(new PrintStream(first.outputConsole));
						}
					} else {
						first.outputConsole = null;
					}
					compositionOutput = first.runStep(locationValue);
					first.output = compositionOutput;
					if(multiConsole) {
						System.setErr(new PrintStream(second.outputConsole));
						System.setOut(new PrintStream(second.outputConsole));
					} else {
						second.outputConsole = null;
					}
					if(compositionOutput.getEsit() == Esit.SAFE) {
						assertTrue(compositionOutput == first.getLastOutput());
						compositionOutput = second.runStep(first.getLastOutput().getControlledvalues());
						second.output = compositionOutput;
					} else {
						System.err.println("Composition model rollback!\n");
						compositionRollback();
					}
					if(multiConsole) {
						System.setErr(new PrintStream(first.outputConsole));
						System.setOut(new PrintStream(first.outputConsole));
					}
					if(compositionOutput.getEsit() == Esit.SAFE) {
						assertTrue(compositionOutput == second.getLastOutput());
						compositionOutput = first.runStep(second.getLastOutput().getControlledvalues());
						first.output = compositionOutput;
					} else {
						System.err.println("Composition model rollback!\n");
						compositionRollback();
					}
				} else {
					throw new CompositionException("Bidirectional pipe error, too many models!");
				}
			break;
			case PARALLEL: // modelList for PARALLEL should look like: [parModel1, parModel2, ..., parModelN]
				if(modelList.size() >= 2) {
					for(int i = 0; i < modelList.size(); i++) {
						if(multiConsole) {
							System.setErr(new PrintStream(modelList.get(i).outputConsole));
							System.setOut(new PrintStream(modelList.get(i).outputConsole));
						} else {
							modelList.get(i).outputConsole = null;
						}
						modelList.get(i).output = modelList.get(i).runStep(locationValue);
					}
					
					Map<String, String> finalOutput = new HashMap<>();
					for(int i = 0; i < modelList.size(); i++) {
						if(modelList.get(i).output.getEsit() != Esit.SAFE) {
							compositionRollback();
						}
						finalOutput.putAll(modelList.get(i).output.getControlledvalues());
					}
					if(multiConsole) {
						System.setErr(new PrintStream(initialConsole));
						System.setOut(new PrintStream(initialConsole));
					}
					lastParLocationValue = finalOutput;
					compositionOutput = null;
				} else {
					throw new CompositionException("Parallel fork join execution error!\n" +
												   "The model list for PARALLEL should look like: " +
												   "[parModel1, parModel2, ..., parModelN]");
				}
			break;
			default: throw new CompositionException("Undefined composition type!");
			}
		} else {
			if(modelList.size() == 1) {
				AsmetaModel model = modelList.get(0);
				if(multiConsole) {
					System.setErr(new PrintStream(model.outputConsole));
					System.setOut(new PrintStream(model.outputConsole));
				} else {
					model.outputConsole = null;
				}
				compositionOutput = model.runStep(locationValue);
				model.output = compositionOutput;
			} else {
				throw new CompositionException("Undefined composition type!");
			}
		}
		
		lastOutput = compositionOutput;
	}
	
	private void runCompositionStep(List<AsmetaModel> modelList, CompositionType compType) throws CompositionException {
		runCompositionStep(modelList, compType, null);
	}
	
	private void runCompositionStep(List<CompositionTreeNode> nodeList, CompositionType compType, RunOutput input) throws CompositionException {
		if(nodeList == null || nodeList.isEmpty()) {
			return;
		}
		
		List<AsmetaModel> modelList = new ArrayList<>();
		for(CompositionTreeNode node: nodeList) {
			if(node.getType() != CompositionTreeNodeType.MODEL) {
				return;
			}
			modelList.add(getModelFromModelList(node.getModelName(), SimGUI.containerInstance.getSimulatorId()));
		}
		runCompositionStep(modelList, compType, input.getControlledvalues());
	}
	
	private void runTreeFromLocationValue(List<CompositionTreeNode> nodeList, CompositionType compType, Map<String, String> locationValue) throws CompositionException {
		if(nodeList == null || nodeList.isEmpty()) {
			return;
		}
		
		List<AsmetaModel> modelList = new ArrayList<>();
		for(CompositionTreeNode node: nodeList) {
			if(node.getType() != CompositionTreeNodeType.MODEL) {
				return;
			}
			modelList.add(getModelFromModelList(node.getModelName(), SimGUI.containerInstance.getSimulatorId()));
		}
		runCompositionStep(modelList, compType, locationValue);
	}
	
	private List<AsmetaModel> convertBidToPipe(List<AsmetaModel> modelList1, List<AsmetaModel> modelList2) { // Ottimizzazione
		modelList2.addAll(modelList1);
		modelList1.addAll(modelList2);
		return modelList1;
	}
	
	private List<AsmetaModel> convertBidToPipe(CompositionTreeNode bidNode) { // Ottimizzazione
		if(bidNode == null || bidNode.getType() != CompositionTreeNodeType.BID_PIPE_OPERATOR || bidNode.childrenCount() != 2) {
			return null;
		}
		List<AsmetaModel> modelList1 = new ArrayList<>();
		List<AsmetaModel> modelList2 = new ArrayList<>();
		if(bidNode.getChildren().get(0).getType() == CompositionTreeNodeType.MODEL) {
			modelList1.add(getModelFromModelList(bidNode.getChildren().get(0).getModelName(), SimGUI.containerInstance.getSimulatorId()));
		} else if(bidNode.getChildren().get(0).getType() == CompositionTreeNodeType.PIPE_OPERATOR) {
			for(CompositionTreeNode child: bidNode.getChildren().get(0).getChildren()) {
				if(child.getType() != CompositionTreeNodeType.MODEL) {
					return null;
				}
				modelList1.add(getModelFromModelList(child.getModelName(), SimGUI.containerInstance.getSimulatorId()));
			}
		} else {
			return null;
		}
		
		if(bidNode.getChildren().get(1).getType() == CompositionTreeNodeType.MODEL) {
			modelList2.add(getModelFromModelList(bidNode.getChildren().get(1).getModelName(), SimGUI.containerInstance.getSimulatorId()));
		} else if(bidNode.getChildren().get(1).getType() == CompositionTreeNodeType.PIPE_OPERATOR) {
			for(CompositionTreeNode child: bidNode.getChildren().get(1).getChildren()) {
				if(child.getType() != CompositionTreeNodeType.MODEL) {
					return null;
				}
				modelList2.add(getModelFromModelList(child.getModelName(), SimGUI.containerInstance.getSimulatorId()));
			}
		} else {
			return null;
		}
		
		return convertBidToPipe(modelList1, modelList2);
	}
	
	// Recursive function to evaluate complex composition trees
	private void evaluateCompositionTree(CompositionTreeNode node, Map<String, String> locationValue) throws CompositionException, ModelCreationException { 
		if(node == null) {
			lastOutput = null;
			lastParLocationValue = null;
			return;
		}
		switch(node.getType()) {
		case PIPE_OPERATOR:
			boolean simplePipe = true;
			for(CompositionTreeNode child: node.getChildren()) {
				if(child.getType() != CompositionTreeNodeType.MODEL) {
					simplePipe = false;
				}
			}
			if(simplePipe) {
				if(lastOutput != null) {
					runCompositionStep(node.getChildren(), CompositionType.PIPE, lastOutput);
				} else {
					runTreeFromLocationValue(node.getChildren(), CompositionType.PIPE, locationValue);
				}
			} else {
				for(CompositionTreeNode child: node.getChildren()) {
					if(lastOutput == null) {
						evaluateCompositionTree(child, locationValue);
					} else {
						evaluateCompositionTree(child);
					}
				}
			} 
		break;
		case BID_PIPE_OPERATOR:
			boolean simpleBid = true;
			for(CompositionTreeNode child: node.getChildren()) {
				if(child.getType() != CompositionTreeNodeType.MODEL) {
					simpleBid = false;
				}
			}
			if(simpleBid) {
				if(lastOutput != null) {
					runCompositionStep(node.getChildren(), CompositionType.BID_PIPE, lastOutput);
				} else {
					runTreeFromLocationValue(node.getChildren(), CompositionType.BID_PIPE, locationValue);
				}
				
			} else {
				List<AsmetaModel> resultList = convertBidToPipe(node);
				if(resultList != null) {
					if(lastOutput != null) {
						runCompositionStep(resultList, CompositionType.PIPE, lastOutput.getControlledvalues());
					} else {
						runCompositionStep(resultList, CompositionType.PIPE, locationValue);
					}
				} else {
					for(CompositionTreeNode child: node.getChildren()) {
						if(lastParLocationValue != null) {
							evaluateCompositionTree(child, lastParLocationValue);
							lastParLocationValue = null;
						} else if(lastOutput == null) {
							evaluateCompositionTree(child, locationValue);
						} else {
							evaluateCompositionTree(child);
						}
					}
					if(lastParLocationValue != null) {
						evaluateCompositionTree(node.getChildren().get(0), lastParLocationValue);
						lastParLocationValue = null;
					} else {
						evaluateCompositionTree(node.getChildren().get(0));
					}
				}
			}
		break;
		case PAR_OPERATOR:
			boolean simplePar = true;
			for(CompositionTreeNode child: node.getChildren()) {
				if(child.getType() != CompositionTreeNodeType.MODEL) {
					simplePar = false;
				}
			}
			if(simplePar) {
				List<CompositionTreeNode> resultList = node.getChildren();
				if(lastOutput != null) {
					runCompositionStep(resultList, CompositionType.PARALLEL, lastOutput);
				} else {
					runTreeFromLocationValue(resultList, CompositionType.PARALLEL, locationValue);
				}
			} else {
				for(CompositionTreeNode child: node.getChildren()) {
					if(lastOutput == null) {
						evaluateCompositionTree(child, locationValue);
					} else {
						evaluateCompositionTree(child);
					}
				}
			}
		break;
		case MODEL: 
			List<CompositionTreeNode> singleModelList = new ArrayList<>(); 
			singleModelList.add(node);
			if(lastParLocationValue != null) {
				runTreeFromLocationValue(singleModelList, null, lastParLocationValue);
				lastParLocationValue = null;
			} else if(lastOutput != null) {
				runCompositionStep(singleModelList, null, lastOutput);
			} else {
				runTreeFromLocationValue(singleModelList, null, locationValue);
			}
		break;
		default: throw new CompositionException("Undefined composition type!");
		}
	}
	
	private void evaluateCompositionTree(CompositionTreeNode node) throws CompositionException, ModelCreationException {
		evaluateCompositionTree(node, null);
	}
	
	// TODO: da implementare
	private void compositionRollback() {
		return;
	}
	
	// TODO: implementare anche runUntilEmpty, runStepTimeout e runUntilEmptyTimeout per la composizione
	public void runUntilEmpty(int id, Map<String, String> locationValue) {
		
	}
	
	public void runStepTimeout(int id, Map<String, String> locationValue, int timeout) {
		
	}
	
	public void runUntilEmptyTimeout(int id, Map<String, String> locationValue, int timeout) {
		
	}
	
	public AsmetaModel getModelFromModelList(String modelName, int simContainerID) {
		if(compositionModelList == null || compositionModelList.isEmpty()) {
			return null;
		}
		
		for(AsmetaModel model: compositionModelList) {
			if(model.getModelName().equals(modelName) && model.getSimulatorId() == simContainerID) {
				return model;
			}
		}
		return null;
	}
	
	public AsmetaModel getModelFromModelList(int asmetaModelID, int simContainerID) {
		if(compositionModelList == null || compositionModelList.isEmpty()) {
			return null;
		}
		
		for(AsmetaModel model: compositionModelList) {
			if(model.getModelId() == asmetaModelID && model.getSimulatorId() == simContainerID) {
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
