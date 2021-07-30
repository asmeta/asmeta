package org.asmeta.runtime_commander;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CompositionTreeNode {
	CompositionTreeNodeType nodeType;
	CompositionTreeNode parent;
	List<CompositionTreeNode> children;
	private final int ID;
	private static int nodeCounter = 0;
	private String modelName;
	
	public CompositionTreeNode(int id, CompositionTreeNodeType nodeType) throws Exception {
		if(nodeType == CompositionTreeNodeType.MODEL) { 
			throw new Exception("Model is missing!");
		}
		this.nodeType = nodeType;
		this.children = new LinkedList<CompositionTreeNode>();
		nodeCounter++;
		this.ID = id;
	}
	
	public CompositionTreeNode(int id, CompositionTreeNodeType nodeType, String model) throws Exception {
		if(nodeType != CompositionTreeNodeType.MODEL) { // "MODEL" nodes cannot have children nodes!
			throw new Exception("Node type not valid!");
		}
		this.nodeType = nodeType;
		this.modelName = model;
		nodeCounter++;
		this.ID = id;
	}
	
	public void addChild(CompositionTreeNode child) {
		if(child == this || this.nodeType == CompositionTreeNodeType.MODEL) { // "MODEL" nodes cannot have children nodes!
			return;
		}
		if(this.nodeType == CompositionTreeNodeType.BID_PIPE_OPERATOR && this.children.size() == 2) { // "BID_PIPE_OPERATOR" has only 2 children!
			return;
		}
		child.parent = this;
		this.children.add(child);
	}
	
	public CompositionTreeNode addChild(int childId, CompositionTreeNodeType nodeType) {
		if(nodeType == CompositionTreeNodeType.MODEL || this.nodeType == CompositionTreeNodeType.MODEL) { // "MODEL" nodes cannot have children nodes!
			return null;
		}
		if(this.nodeType == CompositionTreeNodeType.BID_PIPE_OPERATOR && this.children.size() == 2) { // "BID_PIPE_OPERATOR" has only 2 children!
			return null;
		}
		try {
			CompositionTreeNode child = new CompositionTreeNode(childId, nodeType);
			child.parent = this;
			this.children.add(child);
			return child;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public CompositionTreeNode addChild(int childId, CompositionTreeNodeType nodeType, String model) {
		if(nodeType != CompositionTreeNodeType.MODEL || this.nodeType == CompositionTreeNodeType.MODEL) { // "MODEL" nodes cannot have children nodes!
			return null;
		}
		if(this.nodeType == CompositionTreeNodeType.BID_PIPE_OPERATOR && this.children.size() == 2) { // "BID_PIPE_OPERATOR" has only 2 children!
			return null;
		}
		try {
			CompositionTreeNode child = new CompositionTreeNode(childId, nodeType, model);
			child.parent = this;
			this.children.add(child);
			return child;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// symbolsPriority is a "clean" list (List<String[]>). Its structure should look like:
	// [["S1", "0"], ["S2", "1"], ...] -> [[symbol1, priority1], [symbol2, priority2], ..., [symbolN, priorityN]]
	// Example: S1 | ((S2 | S3) || S4) | S5 -> [[S1, 0], [|, 0], [S2, 2], [|, 2], [S3, 2], [||, 1], [S4, 1], [|, 0], [S5, 0]]
	// This function has to build a CommanderTree from the provided symbolsPriority list
	// Tests: autosetup S1 | (S2 | S3 | S4 | S5) | S6
	// 		  autosetup S1 | ((S2 | S3) || S4) | S5
	//		  autosetup S1 | ((S2 | S3 | S4) <|> (S5 | S6)) | S7
	public static CompositionTreeNode buildTree(List<String[]> symbolsPriority) throws Exception {
		//Commander.printList(symbolsPriority);
		if(symbolsPriority == null) {
			return null;
		}
		CompositionTreeNode rootNode = null;
		// 1. Find the operand with the lowest priority ( = 0) -> it will become the root node
		for(int i = 0; i < symbolsPriority.size(); i++) {
			if(symbolsPriority.get(i)[1].equals("0")) {
				if(symbolsPriority.get(i)[0].equals("|")) { // PIPE
					if(rootNode == null) {
						rootNode = new CompositionTreeNode(Integer.valueOf(symbolsPriority.get(i)[2]), CompositionTreeNodeType.PIPE_OPERATOR);
					}
					symbolsPriority.remove(i--);
				} else if(symbolsPriority.get(i)[0].equals("||")) { // PAR cannot be a root operator!
					throw new Exception("Parallel operand cannot be a top level operator!");
				} else if(symbolsPriority.get(i)[0].equals("<|>")) { // BID_PIPE
					if(rootNode == null) {
						rootNode = new CompositionTreeNode(Integer.valueOf(symbolsPriority.get(i)[2]), CompositionTreeNodeType.BID_PIPE_OPERATOR);
					}
					symbolsPriority.remove(i--);
				}
			}
		}
		//Commander.printList(symbolsPriority);
		
		// 2. Now we have to check if the list is empty, if not we continue building...
		CompositionTreeNode aux = rootNode;
		CompositionTreeNode temp = null;
		int currentPriority = 0;
		String[] symbolPriority;
		while(!symbolsPriority.isEmpty() && aux != null) {
			//temp = null;
			//System.out.println(symbolsPriority.size());
			for(int i = 0; i < symbolsPriority.size(); i++) {
				//System.out.print("i = " + i + " | cp = " + currentPriority + ": ");
				//Commander.printList(symbolsPriority);
				symbolPriority = symbolsPriority.get(i);
				if(symbolPriority[1].equals(String.valueOf(currentPriority))) {
					if(symbolPriority[0].equals("|")) { // PIPE
						temp = aux.addChild(Integer.valueOf(symbolPriority[2]), CompositionTreeNodeType.PIPE_OPERATOR);
						symbolsPriority.remove(i--);
					} else if(symbolPriority[0].equals("||")) { // PAR
						temp = aux.addChild(Integer.valueOf(symbolPriority[2]), CompositionTreeNodeType.PAR_OPERATOR);
						symbolsPriority.remove(i--);
					} else if(symbolPriority[0].equals("<|>")) { // BID_PIPE
						temp = aux.addChild(Integer.valueOf(symbolPriority[2]), CompositionTreeNodeType.BID_PIPE_OPERATOR);
						symbolsPriority.remove(i--);
					} else {
						aux.addChild(Integer.valueOf(symbolPriority[2]), CompositionTreeNodeType.MODEL, symbolPriority[0]);
						symbolsPriority.remove(i--);
					}
				} else if(symbolPriority[1].equals(String.valueOf(currentPriority + 1))) {
					if(symbolPriority[0].equals("|")) { // PIPE
						temp = aux.addChild(Integer.valueOf(symbolPriority[2]), CompositionTreeNodeType.PIPE_OPERATOR);
						symbolsPriority.remove(i--);
						for(int j = i; j < symbolsPriority.size() - 1; j++) {
							if(!symbolsPriority.get(j + 1)[0].equals("|") &&
							   !symbolsPriority.get(j + 1)[0].equals("<|>") &&
							   !symbolsPriority.get(j + 1)[0].equals("||") &&
							   !symbolsPriority.get(j)[0].equals("|") &&
							   !symbolsPriority.get(j)[0].equals("<|>") &&
							   !symbolsPriority.get(j)[0].equals("||") &&
							   symbolsPriority.get(j)[1].equals(String.valueOf(currentPriority + 1))) {
								temp.addChild(Integer.valueOf(symbolsPriority.get(j)[2]), CompositionTreeNodeType.MODEL, symbolsPriority.get(j)[0]);
								symbolsPriority.remove(j--);
							}
							//aux.printTree();
							//System.out.println("--------------------------------------------------------------");
						}
					} else if(symbolPriority[0].equals("||")) { // PAR
						temp = aux.addChild(Integer.valueOf(symbolPriority[2]), CompositionTreeNodeType.PAR_OPERATOR);
						symbolsPriority.remove(i--);
						for(int j = i; j < symbolsPriority.size() - 1; j++) {
							if(!symbolsPriority.get(j + 1)[0].equals("|") &&
							   !symbolsPriority.get(j + 1)[0].equals("<|>") &&
							   !symbolsPriority.get(j + 1)[0].equals("||") &&
							   !symbolsPriority.get(j)[0].equals("|") &&
							   !symbolsPriority.get(j)[0].equals("<|>") &&
							   !symbolsPriority.get(j)[0].equals("||") &&
							   symbolsPriority.get(j)[1].equals(String.valueOf(currentPriority + 1))) {
								temp.addChild(Integer.valueOf(symbolsPriority.get(j)[2]), CompositionTreeNodeType.MODEL, symbolsPriority.get(j)[0]);
								symbolsPriority.remove(j--);
							}
						}
					} else if(symbolPriority[0].equals("<|>")) { // BID_PIPE
						temp = aux.addChild(Integer.valueOf(symbolPriority[2]), CompositionTreeNodeType.BID_PIPE_OPERATOR);
						symbolsPriority.remove(i--);
						for(int j = i; j < symbolsPriority.size() - 1; j++) {
							if(!symbolsPriority.get(j + 1)[0].equals("|") &&
							   !symbolsPriority.get(j + 1)[0].equals("<|>") &&
							   !symbolsPriority.get(j + 1)[0].equals("||") &&
							   !symbolsPriority.get(j)[0].equals("|") &&
							   !symbolsPriority.get(j)[0].equals("<|>") &&
							   !symbolsPriority.get(j)[0].equals("||") &&
							   symbolsPriority.get(j)[1].equals(String.valueOf(currentPriority + 1))) {
								temp.addChild(Integer.valueOf(symbolsPriority.get(j)[2]), CompositionTreeNodeType.MODEL, symbolsPriority.get(j)[0]);
								symbolsPriority.remove(j--);
							}
						}
					}
				}
			}
			
			if(temp != null) {
				aux = temp;
			}
			if(symbolsPriority.size() == 1) {
				if(!symbolsPriority.get(0)[0].equals("|") &&
				   !symbolsPriority.get(0)[0].equals("||") &&
				   !symbolsPriority.get(0)[0].equals("<|>")) {
					if(symbolsPriority.get(0)[1].equals("0")) {
						rootNode.addChild(Integer.valueOf(symbolsPriority.get(0)[2]), CompositionTreeNodeType.MODEL, symbolsPriority.get(0)[0]);
					} else {
						aux.addChild(Integer.valueOf(symbolsPriority.get(0)[2]), CompositionTreeNodeType.MODEL, symbolsPriority.get(0)[0]);
					}
					symbolsPriority.remove(0);
				} else {
					//Commander.printList(symbolsPriority);
					throw new Exception("Error while building the tree!\n" + aux.toString() + "\n");
				}
			}
			currentPriority++;
		}
		
		if(rootNode != null && rootNode.checkTree()) {
			return rootNode;
		} else {
			return null;
		}
	}
	
	// Test: autosetup S1 | ((S2 | (S3 || S4) | S5) <|> S6)  -> Correct
	//		 autosetup S1 | ((S2 | (S3 || S4)) <|> S6) 		 -> Not correct
	private boolean checkTree() {
		//CompositionTreeNode node = this;
		if(this.childrenCount() != 0) {
			for(CompositionTreeNode child: this.children) {
				if(child.nodeType == CompositionTreeNodeType.PAR_OPERATOR) {
					if(this.getType() == CompositionTreeNodeType.PIPE_OPERATOR && this.childrenCount() <= 2) {
						return false;
					} else if(this.getType() == CompositionTreeNodeType.BID_PIPE_OPERATOR && this.childrenCount() != 2) {
						return false;
					}
				}
				if(child.nodeType != CompositionTreeNodeType.MODEL) {
					if(!child.checkTree()) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public CompositionTreeNode getNodeById(int id) {
		if(id <= 0 || id > getNodeNumber()) {
			return null;
		}
		if(this.getID() == id) {
			return this;
		}
		
		if(this.childrenCount() != 0) {
			for(CompositionTreeNode child: this.children) {
				if(child.getNodeById(id) != null) {
					return child.getNodeById(id);
				}
			}
		}
		return null;
	}
	
	public CompositionTreeNodeType getType() {
		return this.nodeType;
	}
	
	public String getModelName() {
		return this.modelName;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public CompositionTreeNode getParent() {
		return this.parent;
	}
	
	public List<CompositionTreeNode> getChildren() {
		Collections.sort(this.children, new SortTreeById());
		return this.children;
	}
	
	public List<CompositionTreeNode> getAllChildrenModels() {
		List<CompositionTreeNode> allChildrenList = new ArrayList<>();
		if(this.getType() == CompositionTreeNodeType.MODEL) {
			allChildrenList.add(this);
			return allChildrenList;
		}
		if(this.childrenCount() != 0) {
			for(CompositionTreeNode child: this.getChildren()) {
				allChildrenList.addAll(child.getAllChildrenModels());
			}
		}
		Collections.sort(allChildrenList, new SortTreeById());
		return allChildrenList;
	}
	
	public boolean isRoot() {
		return this.ID == 1;
	}
	
	public int childrenCount() {
		if(this.children == null) {
			return 0;
		} 
		return this.children.size();
	}
	
	public int getParentID() {
		if(this.parent == null) {
			return -1;
		}
		return this.parent.getID();
	}
	
	public CompositionTreeNode getSource() {
		if(this.isRoot() && this.childrenCount() != 0) {
			if(this.getChildren().get(0).getType() == CompositionTreeNodeType.MODEL) {
				return this.getChildren().get(0);
			} else {
				return this.getChildren().get(0).getSource();
			}
		} else {
			return this.parent.getSource();
		}
	}
	
	public CompositionTreeNode getRoot() {
		if(this.isRoot()) {
			return this;
		} else {
			return (this.parent.getSource());
		}
	}
	
	// Test: autosetup S1 <|> ((S2 | S3 | S4) <|> S5)
	//		 autosetup S1 | ((S2 | S3 | S4) <|> S5)
	//		 autosetup S1 | ((S2 | (S3 || S4) | S6) <|> S5)
	public CompositionTreeNode getSink() {
		CompositionTreeNode root = this.getRoot();
		if(root != null) {
			CompositionTreeNode aux = root;
			while(aux.childrenCount() != 0) {
				if(aux.nodeType == CompositionTreeNodeType.BID_PIPE_OPERATOR) { // BID_PIPE
					if(aux.children.get(0).nodeType == CompositionTreeNodeType.MODEL) {
						return aux.children.get(0);
					} else {
						aux = aux.children.get(0);
					}
				} else if(aux.nodeType == CompositionTreeNodeType.PIPE_OPERATOR) { // PIPE 
					if(aux.children.get(aux.childrenCount() - 1).nodeType == CompositionTreeNodeType.MODEL) {
						return aux.children.get(aux.childrenCount() - 1);
					} else {
						aux = aux.children.get(aux.childrenCount() - 1);
					}
				} else if(aux.nodeType == CompositionTreeNodeType.PAR_OPERATOR) { // PARALLEL
					aux = aux.getNodeById(getID() + 1); 
				}
			}
			return aux;
		}
		return null;
	}
	
	public void printTree() {
		CompositionTreeNode aux = this;
		if(aux != null) {
			System.out.println("\n" + aux.toString());
			if(aux.children != null) {
				for(CompositionTreeNode child: children) {
					child.printTree();
				}
			}
		}
	}
	
	public static int getNodeNumber() {
		return nodeCounter;
	}
	
	@Override
	public String toString() {
		String text = "";
		if(this.nodeType == CompositionTreeNodeType.MODEL) {
			text = "CommanderTreeNode ID: " + this.ID + 
					" | Type: " + this.nodeType.toString() + 
					"\nModel: " + this.modelName.toString() +
					"\nNumber of children: " + this.childrenCount() + 
					"\nParent ID: " + this.getParentID();
		} else {
			text = "CommanderTreeNode ID: " + this.ID + 
					" | Type: " + this.nodeType.toString() + 
					"\nNumber of children: " + this.childrenCount() + 
					"\nParent ID: " + this.getParentID();
		}
		return text;
	}
}
