package org.asmeta.runtime_composer;

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
import org.asmeta.runtime_container.Esit;
import org.asmeta.runtime_container.RunOutput;
import org.asmeta.simulationUI.CompositionType;
import org.asmeta.simulationUI.SimGUI;

public class CompositionManager implements IModelComposition {
	// SimulationContainer ID is set to 0 for testing purposes only.
	// The architecture is ready to be distributed (not implemented yet).
	private static final int TEST_ID = 0;
	
	private List<AsmetaModel> compositionModelList;
	private Map<AsmetaModel, RunOutput> outputMap;
	private CompositionTreeNode compositionTree;
	private RunOutput lastOutput;
	private Map<String, String> lastParLocationValue;
	private boolean multiConsole;
	static ByteArrayOutputStream initialConsole;
	private long remainingExecutionTime;
	
	public CompositionManager(CompositionTreeNode compositionTree, boolean multiConsole) {
		this.compositionTree = compositionTree;
		compositionModelList = new ArrayList<>();
		outputMap = new HashMap<>();
		this.multiConsole = multiConsole;
		lastParLocationValue = null;
		lastOutput = null;
		initialConsole = null;
		
		compositionModelList = compositionModelsLookUp();
	}
	
	public CompositionManager(CompositionTreeNode compositionTree, ByteArrayOutputStream initialConsole, boolean multiConsole) {
		this.compositionTree = compositionTree;
		compositionModelList = new ArrayList<>();
		outputMap = new HashMap<>();
		this.multiConsole = multiConsole;
		lastParLocationValue = null;
		lastOutput = null;
		CompositionManager.initialConsole = initialConsole;
		
		compositionModelList = compositionModelsLookUp();
	}
	
	// Execute one step of the models composed in the compositionTree (id just for checking)
	public void runStep(int id, Map<String, String> locationValue) throws CompositionException {
		boolean correct = false;
		for(AsmetaModel model: compositionModelList) {
			if(model.getModelId() == id) {
				correct = true;
				break;
			}
		}
		
		if(correct) {
			evaluateCompositionTree(compositionTree, locationValue, -1, -1);
		}
		lastOutput = null;
		lastParLocationValue = null;
	}
	
	// runUntilEmpty, runStepTimeout, runUntilEmptyTimeout e rollback per la composizione
	public void runUntilEmpty(int id, Map<String, String> locationValue, int max) throws CompositionException {
		boolean correct = false;
		for(AsmetaModel model: compositionModelList) {
			if(model.getModelId() == id) {
				correct = true;
				break;
			}
		}
		
		if(correct) {
			evaluateCompositionTree(compositionTree, locationValue, max, -1);
		}
		lastOutput = null;
		lastParLocationValue = null;
	}
	
	public void runStepTimeout(int id, Map<String, String> locationValue, int timeout) throws CompositionException {
		boolean correct = false;
		for(AsmetaModel model: compositionModelList) {
			if(model.getModelId() == id) {
				correct = true;
				break;
			}
		}
		
		if(correct) {
			this.remainingExecutionTime = timeout;
			evaluateCompositionTree(compositionTree, locationValue, -1, timeout);
		}
		lastOutput = null;
		lastParLocationValue = null;
	}
	
	public void runUntilEmptyTimeout(int id, Map<String, String> locationValue, int max, int timeout) throws CompositionException {
		boolean correct = false;
		for(AsmetaModel model: compositionModelList) {
			if(model.getModelId() == id) {
				correct = true;
				break;
			}
		}
		
		if(correct) {
			this.remainingExecutionTime = timeout;
			evaluateCompositionTree(compositionTree, locationValue, max, timeout);
		}
		lastOutput = null;
		lastParLocationValue = null;
	}
		
	private List<AsmetaModel> compositionModelsLookUp() {
		if(SimGUI.containerInstance != null) {
			return SimGUI.containerInstance.loadedModels;
		} else if(Commander.containerInstance != null) {
			return Commander.containerInstance.loadedModels;
		}
		
		return null;
	}
	
	private void runComposedModels(List<AsmetaModel> modelList, CompositionType compType, Map<String, String> locationValue, int max, int timeout) throws CompositionException {
		if(modelList.isEmpty()) {
			lastOutput = null;
			lastParLocationValue = null;
			return;
		}
		if(modelList.size() == 1 && compType != null) {
			compType = null;
		}
		
		outputMap.clear();
		for(AsmetaModel model: modelList) {
			outputMap.put(model, model.getLastOutput());
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
						compositionOutput = model.run(locationValue, max, timeout);
						model.output = compositionOutput;
						timeout -= (int) model.getExecutionTime();
						this.remainingExecutionTime -= model.getExecutionTime();
					} else {
						if(compositionOutput.getEsit() == Esit.SAFE) {
							Map<String, String> modelOutput = compositionOutput.getControlledvalues();
							compositionOutput = model.run(modelOutput, max, timeout);
							model.output = compositionOutput;
							timeout -= (int) model.getExecutionTime();
							this.remainingExecutionTime -= model.getExecutionTime();
						} else {
							System.err.println("Composition rollback!\n");
							if(max < 0) {
								compositionRollback();
							} else {
								compositionRollbackToState();
							}
						}
					}
				}
			break;
			case BID_PIPE:
				if(modelList.size() == 2) {
					AsmetaModel first = modelList.get(0);
					AsmetaModel second = modelList.get(1);
					if(multiConsole) {
						if(first.getModelName().equals(compositionTree.getSource().getModelName()) && initialConsole != null) {
							first.outputConsole = initialConsole;
						} else {
							System.setErr(new PrintStream(first.outputConsole));
							System.setOut(new PrintStream(first.outputConsole));
						}
					} else {
						first.outputConsole = null;
					}
					compositionOutput = first.run(locationValue, max, timeout);
					first.output = compositionOutput;
					this.remainingExecutionTime -= first.getExecutionTime();
					if(multiConsole) {
						System.setErr(new PrintStream(second.outputConsole));
						System.setOut(new PrintStream(second.outputConsole));
					} else {
						second.outputConsole = null;
					}
					if(compositionOutput.getEsit() == Esit.SAFE) {
						assertTrue(compositionOutput == first.getLastOutput());
						timeout -= (int) first.getExecutionTime();
						compositionOutput = second.run(first.getLastOutput().getControlledvalues(), max, timeout);
						second.output = compositionOutput;
						this.remainingExecutionTime -= second.getExecutionTime();
					} else {
						System.err.println("Composition rollback!\n");
						if(max < 0) {
							compositionRollback();
						} else {
							compositionRollbackToState();
						}
					}
					if(multiConsole) {
						System.setErr(new PrintStream(first.outputConsole));
						System.setOut(new PrintStream(first.outputConsole));
					}
					if(compositionOutput.getEsit() == Esit.SAFE) {
						assertTrue(compositionOutput == second.getLastOutput());
						timeout -= (int) second.getExecutionTime();
						compositionOutput = first.run(second.getLastOutput().getControlledvalues(), max, timeout);
						first.output = compositionOutput;
						this.remainingExecutionTime -= first.getExecutionTime();
					} else {
						System.err.println("Composition rollback!\n");
						if(max < 0) {
							compositionRollback();
						} else {
							compositionRollbackToState();
						}
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
						modelList.get(i).output = modelList.get(i).run(locationValue, max, timeout);
						timeout -= (int) modelList.get(i).getExecutionTime();
						this.remainingExecutionTime -= modelList.get(i).getExecutionTime();
					}
					
					Map<String, String> finalOutput = new HashMap<>();
					for(int i = 0; i < modelList.size(); i++) {
						if(modelList.get(i).output.getEsit() != Esit.SAFE) {
							System.err.println("Composition rollback!\n");
							if(max < 0) {
								compositionRollback();
							} else {
								compositionRollbackToState();
							}
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
					if(model.getModelName().equals(compositionTree.getSource().getModelName()) && initialConsole != null) {
						model.outputConsole = initialConsole;
					} else {
						System.setErr(new PrintStream(model.outputConsole));
						System.setOut(new PrintStream(model.outputConsole));
					}
				} else {
					model.outputConsole = null;
				}
				compositionOutput = model.run(locationValue, max, timeout);
				model.output = compositionOutput;
				this.remainingExecutionTime -= model.getExecutionTime();
			} else {
				throw new CompositionException("Undefined composition type!");
			}
		}
		
		lastOutput = compositionOutput;
	}
	
	private void runComposedModels(List<AsmetaModel> modelList, CompositionType compType, int max, int timeout) throws CompositionException {
		runComposedModels(modelList, compType, null, max, timeout);
	}
	
	private void runTreeFromRunOutput(List<CompositionTreeNode> nodeList, CompositionType compType, RunOutput input, int max, int timeout) throws CompositionException {
		if(nodeList == null || nodeList.isEmpty()) {
			return;
		}
		
		List<AsmetaModel> modelList = new ArrayList<>();
		for(CompositionTreeNode node: nodeList) {
			if(node.getType() != CompositionTreeNodeType.MODEL) {
				return;
			}
			modelList.add(getModelFromModelList(node.getModelName(), TEST_ID));
		}
		runComposedModels(modelList, compType, input.getControlledvalues(), max, timeout);
	}
	
	private void runTreeFromLocationValue(List<CompositionTreeNode> nodeList, CompositionType compType, Map<String, String> locationValue, int max, int timeout) throws CompositionException {
		if(nodeList == null || nodeList.isEmpty()) {
			return;
		}
		
		List<AsmetaModel> modelList = new ArrayList<>();
		for(CompositionTreeNode node: nodeList) {
			if(node.getType() != CompositionTreeNodeType.MODEL) {
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
		if(bidNode == null || bidNode.getType() != CompositionTreeNodeType.BID_PIPE_OPERATOR || bidNode.childrenCount() != 2) {
			return null;
		}
		List<AsmetaModel> modelList1 = new ArrayList<>();
		List<AsmetaModel> modelList2 = new ArrayList<>();
		if(bidNode.getChildren().get(0).getType() == CompositionTreeNodeType.MODEL) {
			modelList1.add(getModelFromModelList(bidNode.getChildren().get(0).getModelName(), TEST_ID));
		} else if(bidNode.getChildren().get(0).getType() == CompositionTreeNodeType.PIPE_OPERATOR) {
			for(CompositionTreeNode child: bidNode.getChildren().get(0).getChildren()) {
				if(child.getType() != CompositionTreeNodeType.MODEL) {
					return null;
				}
				modelList1.add(getModelFromModelList(child.getModelName(), Commander.containerInstance.getSimulatorId()));
			}
		} else {
			return null;
		}
		
		if(bidNode.getChildren().get(1).getType() == CompositionTreeNodeType.MODEL) {
			modelList2.add(getModelFromModelList(bidNode.getChildren().get(1).getModelName(), TEST_ID));
		} else if(bidNode.getChildren().get(1).getType() == CompositionTreeNodeType.PIPE_OPERATOR) {
			for(CompositionTreeNode child: bidNode.getChildren().get(1).getChildren()) {
				if(child.getType() != CompositionTreeNodeType.MODEL) {
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
	private void evaluateCompositionTree(CompositionTreeNode node, Map<String, String> locationValue, int max, int timeout) throws CompositionException { 
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
					runTreeFromRunOutput(node.getChildren(), CompositionType.PIPE, lastOutput, max, (int) Math.ceil(this.remainingExecutionTime));
				} else {
					runTreeFromLocationValue(node.getChildren(), CompositionType.PIPE, locationValue, max, (int) Math.ceil(this.remainingExecutionTime));
				}
			} else {
				for(CompositionTreeNode child: node.getChildren()) {
					if(lastOutput == null) {
						evaluateCompositionTree(child, locationValue, max, (int) Math.ceil(this.remainingExecutionTime));
					} else {
						evaluateCompositionTree(child, max, (int) Math.ceil(this.remainingExecutionTime));
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
					runTreeFromRunOutput(node.getChildren(), CompositionType.BID_PIPE, lastOutput, max, (int) Math.ceil(this.remainingExecutionTime));
				} else {
					runTreeFromLocationValue(node.getChildren(), CompositionType.BID_PIPE, locationValue, max, (int) Math.ceil(this.remainingExecutionTime));
				}
				
			} else {
				List<AsmetaModel> resultList = convertBidToPipe(node);
				if(resultList != null) {
					if(lastOutput != null) {
						runComposedModels(resultList, CompositionType.PIPE, lastOutput.getControlledvalues(), max, (int) Math.ceil(this.remainingExecutionTime));
					} else {
						runComposedModels(resultList, CompositionType.PIPE, locationValue, max, (int) Math.ceil(this.remainingExecutionTime));
					}
				} else {
					for(CompositionTreeNode child: node.getChildren()) {
						if(lastParLocationValue != null) {
							evaluateCompositionTree(child, lastParLocationValue, max, (int) Math.ceil(this.remainingExecutionTime));
							lastParLocationValue = null;
						} else if(lastOutput == null) {
							evaluateCompositionTree(child, locationValue, max, (int) Math.ceil(this.remainingExecutionTime));
						} else {
							evaluateCompositionTree(child, max, (int) Math.ceil(this.remainingExecutionTime));
						}
					}
					if(lastParLocationValue != null) {
						evaluateCompositionTree(node.getChildren().get(0), lastParLocationValue, max, (int) Math.ceil(this.remainingExecutionTime));
						lastParLocationValue = null;
					} else {
						evaluateCompositionTree(node.getChildren().get(0), max, (int) Math.ceil(this.remainingExecutionTime));
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
					runTreeFromRunOutput(resultList, CompositionType.PARALLEL, lastOutput, max, (int) Math.ceil(this.remainingExecutionTime));
				} else {
					runTreeFromLocationValue(resultList, CompositionType.PARALLEL, locationValue, max, (int) Math.ceil(this.remainingExecutionTime));
				}
			} else {
				for(CompositionTreeNode child: node.getChildren()) {
					if(lastOutput == null) {
						evaluateCompositionTree(child, locationValue, max, (int) Math.ceil(this.remainingExecutionTime));
					} else {
						evaluateCompositionTree(child, max, (int) Math.ceil(this.remainingExecutionTime));
					}
				}
			}
		break;
		case MODEL: 
			List<CompositionTreeNode> singleModelList = new ArrayList<>(); 
			singleModelList.add(node);
			if(lastParLocationValue != null) {
				runTreeFromLocationValue(singleModelList, null, lastParLocationValue, max, (int) Math.ceil(this.remainingExecutionTime));
				lastParLocationValue = null;
			} else if(lastOutput != null) {
				runTreeFromRunOutput(singleModelList, null, lastOutput, max, (int) Math.ceil(this.remainingExecutionTime));
			} else {
				runTreeFromLocationValue(singleModelList, null, locationValue, max, (int) Math.ceil(this.remainingExecutionTime));
			}
		break;
		default: throw new CompositionException("Undefined composition type!");
		}
	}
	
	private void evaluateCompositionTree(CompositionTreeNode node, int max, int timeout) throws CompositionException {
		evaluateCompositionTree(node, null, max, timeout);
	}
	
	public void compositionRollback() throws CompositionRollbackException {
		if(outputMap != null && !outputMap.isEmpty()) {
			for(AsmetaModel model: compositionModelList) {
				if(outputMap.containsKey(model)) {
					if(outputMap.get(model) != model.getLastOutput()) {
						model.getSimulationContainer().rollback(model.getModelId());
					}
				}
			}
		} else {
			throw new CompositionRollbackException("The composition model list is undefined or empty!");
		}
	}
	
	public void compositionRollbackToState() throws CompositionRollbackException {
		if(outputMap != null && !outputMap.isEmpty()) {
			for(AsmetaModel model: compositionModelList) {
				if(outputMap.containsKey(model)) {
					if(outputMap.get(model) != model.getLastOutput()) {
						model.getSimulationContainer().rollbackToState(model.getModelId());
					}
				}
			}
		} else {
			throw new CompositionRollbackException("The composition model list is undefined or empty!");
		}
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
