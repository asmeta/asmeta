package org.asmeta.runtime_commander;

import java.util.LinkedList;
import java.util.List;

public class CommanderTreeNode<T> {
	CommanderTreeNodeType nodeType;
	CommanderTreeNode<T> parent;
	List<CommanderTreeNode<T>> children;
	private final int ID;
	static int nodeCounter = 0;
	T model;
	
	public CommanderTreeNode(CommanderTreeNodeType nodeType) throws Exception {
		if(nodeType == CommanderTreeNodeType.MODEL) { 
			throw new Exception("Model is missing!");
		}
		this.nodeType = nodeType;
		this.children = new LinkedList<CommanderTreeNode<T>>();
		nodeCounter++;
		this.ID = nodeCounter;
	}
	
	public CommanderTreeNode(CommanderTreeNodeType nodeType, T model) throws Exception {
		if(nodeType != CommanderTreeNodeType.MODEL) { // "MODEL" nodes cannot have children nodes!
			throw new Exception("Node type not valid!");
		}
		this.nodeType = nodeType;
		this.model = model;
		nodeCounter++;
		this.ID = nodeCounter;
	}
	
	public void addChild(CommanderTreeNode<T> child) {
		if(child == this || this.nodeType == CommanderTreeNodeType.MODEL) { // "MODEL" nodes cannot have children nodes!
			return;
		}
		if(this.nodeType == CommanderTreeNodeType.BID_PIPE_OPERATOR && this.children.size() == 2) { // "BID_PIPE_OPERATOR" has only 2 children!
			return;
		}
		child.parent = this;
		this.children.add(child);
	}
	
	public CommanderTreeNode<T> addChild(CommanderTreeNodeType nodeType) {
		if(nodeType == CommanderTreeNodeType.MODEL || this.nodeType == CommanderTreeNodeType.MODEL) { // "MODEL" nodes cannot have children nodes!
			return null;
		}
		if(this.nodeType == CommanderTreeNodeType.BID_PIPE_OPERATOR && this.children.size() == 2) { // "BID_PIPE_OPERATOR" has only 2 children!
			return null;
		}
		try {
			CommanderTreeNode<T> child = new CommanderTreeNode<>(nodeType);
			child.parent = this;
			this.children.add(child);
			return child;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public CommanderTreeNode<T> addChild(CommanderTreeNodeType nodeType, T model) {
		if(nodeType != CommanderTreeNodeType.MODEL || this.nodeType == CommanderTreeNodeType.MODEL) { // "MODEL" nodes cannot have children nodes!
			return null;
		}
		if(this.nodeType == CommanderTreeNodeType.BID_PIPE_OPERATOR && this.children.size() == 2) { // "BID_PIPE_OPERATOR" has only 2 children!
			return null;
		}
		try {
			CommanderTreeNode<T> child = new CommanderTreeNode<>(nodeType, model);
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
	public static CommanderTreeNode<String> buildTree(List<String[]> symbolsPriority) throws Exception {
		// DEBUG: Commander.printList(symbolsPriority);
		if(symbolsPriority == null) {
			return null;
		}
		CommanderTreeNode<String> rootNode = null;
		// 1. Find the operand with the lowest priority ( = 0) -> it will become the root node
		for(int i = 0; i < symbolsPriority.size(); i++) {
			if(symbolsPriority.get(i)[1].equals("0")) {
				if(symbolsPriority.get(i)[0].equals("|")) { // PIPE
					if(rootNode == null) {
						rootNode = new CommanderTreeNode<String>(CommanderTreeNodeType.PIPE_OPERATOR);
					}
					symbolsPriority.remove(i);
				} else if(symbolsPriority.get(i)[0].equals("||")) { // PAR cannot be a root operator!
					throw new Exception("Parallel operand cannot be a top level operator!");
				} else if(symbolsPriority.get(i)[0].equals("<|>")) { // BID_PIPE
					if(rootNode == null) {
						rootNode = new CommanderTreeNode<String>(CommanderTreeNodeType.BID_PIPE_OPERATOR);
					}
					symbolsPriority.remove(i);
				}
			}
		}
		
		// 2. Create the children (models) and link them to the root node (common parent)
		for(int i = 0; i < symbolsPriority.size(); i++) {
			if(symbolsPriority.get(i)[1].equals("0")) {
				if(!symbolsPriority.get(i)[0].equals("|") && 
				   !symbolsPriority.get(i)[0].equals("||") && 
				   !symbolsPriority.get(i)[0].equals("<|>") &&
				   rootNode != null) {
					rootNode.addChild(CommanderTreeNodeType.MODEL, symbolsPriority.get(i)[0]);
					symbolsPriority.remove(i);
				}
			}
		}
		
		// 3. Now we have to check if the list is empty, if not we continue building...
		CommanderTreeNode<String> aux = rootNode;
		CommanderTreeNode<String> temp = null;
		int currentPriority = 1;
		String[] symbolPriority;
		while(!symbolsPriority.isEmpty() && aux != null) {
			for(int i = 0; i < symbolsPriority.size(); i++) {
				symbolPriority = symbolsPriority.get(i);
				if(symbolPriority[1].equals(String.valueOf(currentPriority))) {
					if(symbolPriority[0].equals("|")) { // PIPE
						temp = aux.addChild(CommanderTreeNodeType.PIPE_OPERATOR);
						while(symbolsPriority.remove(symbolPriority)) {};
					} else if(symbolsPriority.get(i)[0].equals("||")) { // PAR
						temp = aux.addChild(CommanderTreeNodeType.PAR_OPERATOR);
						while(symbolsPriority.remove(symbolPriority)) {};
					} else if(symbolsPriority.get(i)[0].equals("<|>")) { // BID_PIPE
						temp = aux.addChild(CommanderTreeNodeType.BID_PIPE_OPERATOR);
						while(symbolsPriority.remove(symbolPriority)) {};
					} 
				}
			}
			aux = temp;
			
			for(int i = 0; i < symbolsPriority.size(); i++) {
				if(symbolsPriority.get(i)[1].equals(String.valueOf(currentPriority))) {
					if(!symbolsPriority.get(i)[0].equals("|") && 
					   !symbolsPriority.get(i)[0].equals("||") && 
					   !symbolsPriority.get(i)[0].equals("<|>") &&
					   aux != null) {
						aux.addChild(CommanderTreeNodeType.MODEL, symbolsPriority.get(i)[0]);
						symbolsPriority.remove(i);
					}
				}
			}
			
			if(symbolsPriority.size() == 1) {
				if(!symbolsPriority.get(0)[0].equals("|") &&
				   !symbolsPriority.get(0)[0].equals("||") &&
				   !symbolsPriority.get(0)[0].equals("<|>") &&
				   symbolsPriority.get(0)[1].equals(String.valueOf(currentPriority))) {
					aux.addChild(CommanderTreeNodeType.MODEL, symbolsPriority.get(0)[0]);
					symbolsPriority.remove(0);
				} else {
					throw new Exception("Error while building the tree!");
				}
			}
			//DEBUG: Commander.printList(symbolsPriority);
			currentPriority++;
		}
		
		//DEBUG: Commander.printList(symbolsPriority);
		return rootNode;
	}
	
	public int getID() {
		return this.ID;
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
			return 0;
		}
		return this.parent.getID();
	}
	
	public void printTree() {
		CommanderTreeNode<T> aux = this;
		if(aux != null) {
			System.out.println("\n" + aux.toString());
			if(aux.children != null) {
				for(CommanderTreeNode<T> child: children) {
					child.printTree();
				}
			}
		}
	}
	
	@Override
	public String toString() {
		String text = "";
		if(this.nodeType == CommanderTreeNodeType.MODEL) {
			text = "CommanderTreeNode ID: " + this.ID + 
					" | Type: " + this.nodeType.toString() + 
					"\nModel: " + this.model.toString() +
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
