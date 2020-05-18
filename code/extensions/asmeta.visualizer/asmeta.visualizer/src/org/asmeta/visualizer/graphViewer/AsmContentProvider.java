package org.asmeta.visualizer.graphViewer;

import org.asmeta.visualizer.graphViewer.GraphEdgesAdder.Edge;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.zest.core.viewers.IGraphContentProvider;

import asmeta.transitionrules.basictransitionrules.Rule;

/**
 * A graph content provider. 
 * quando viene chiesto getElements, costruisce il grafo vero e proprio usando GraphEdgesAdder
 */
public class AsmContentProvider implements IGraphContentProvider {
	GraphEdgesAdder.Edge[] edges;

	/**
	 * Gets the source Object for the given relationship. Note, at least one of the source
	 * or destination must not be null. If both are null, then nothing can be displayed in
	 * the graph (a relationship cannot exist without nodes to be connected to). However,
	 * if one of getSource() or getDestination() returns null, then the resulting graph will
	 * contain an unconnected node for the non-null object returned from the other method.
	 * @param rel the relationship.
	 * @return the source, or null for an unconnected destination.
	 */
	@Override
	public Object getSource(Object rel){
		return ((GraphEdgesAdder.Edge)rel).getSource();
	}

	/**
	 * Gets the target Object for the given relationship. Note, at least one of the source
	 * or destination must not be null. If both are null, then nothing can be displayed in
	 * the graph (a relationship cannot exist without nodes to be connected to). However,
	 * if one of getSource() or getDestination() returns null, then the resulting graph will
	 * contain an unconnected node for the non-null object returned from the other method.
	 * @param rel the relationship.
	 * @return the destination, or null for an unconnected source.
	 */
	@Override
	public Object getDestination(Object rel){
		return ((GraphEdgesAdder.Edge)rel).getDestination();
	}
	
	/**
	 * Returns all the relationships in the graph for the given input.
	 * @input the input model object.
	 * @return all the relationships in the graph for the given input.
	 */
	@Override
	public Object[] getElements(Object input){
		if (edges == null){
			Rule r = (Rule) input;
			GraphEdgesAdder gpa = new GraphEdgesAdder();
			Node startingNode = gpa.visit(r);
			// if only one node
			if (gpa.edges.isEmpty()){
				PointNode start  = new PointNode();
				gpa.edges.add(new Edge(start, "", startingNode));
			}
			edges = gpa.edges.toArray(new GraphEdgesAdder.Edge[gpa.edges.size()]);			
		}
		return edges;
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}
}
