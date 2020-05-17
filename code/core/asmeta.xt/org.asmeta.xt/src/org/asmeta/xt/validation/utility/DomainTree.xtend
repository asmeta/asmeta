package org.asmeta.xt.validation.utility

import java.util.ArrayList
import java.util.Enumeration
import java.util.List
import javax.swing.JTree
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.TreeNode
import org.asmeta.xt.asmetal.BagDomain
import org.asmeta.xt.asmetal.Domain
import org.asmeta.xt.asmetal.MapDomain
import org.asmeta.xt.asmetal.PowersetDomain
import org.asmeta.xt.asmetal.ProductDomain
import org.asmeta.xt.asmetal.RuleDomain
import org.asmeta.xt.asmetal.SequenceDomain
import org.asmeta.xt.asmetal.StructuredTD

class DomainTree extends JTree {
	
	new( DefaultMutableTreeNode node ) {
		super(node)
	}
	
	public static def DefaultMutableTreeNode getNodeFromString( String domain ) {
		var DefaultMutableTreeNode node = new DefaultMutableTreeNode
		node.userObject = domain
		return node	
	}
	
	public static def DefaultMutableTreeNode getComposedNode( String parent_domain, List<Domain> domains ) {
		var DefaultMutableTreeNode node = new DefaultMutableTreeNode
		node.userObject = parent_domain
		
		for ( Domain dom : domains ) {
			node.add( getNodeFromDomain(dom) )
		}
		
		return node	
	}
	
	public static def DefaultMutableTreeNode getComposedNodeFromNodes( String parent_domain, List<DefaultMutableTreeNode> nodes ) {
		var DefaultMutableTreeNode node = new DefaultMutableTreeNode
		node.userObject = parent_domain
		
		for ( DefaultMutableTreeNode n : nodes ) {
			if ( n !== null ) node.add( n )
			else node.add( getNodeFromString("NULLO") )
		}
		
		return node	
	}
	
	public static def DefaultMutableTreeNode getNodeFromDomain( Domain domain ) {
		
		var DefaultMutableTreeNode node = new DefaultMutableTreeNode
		
		if ( domain instanceof StructuredTD ) {
			node.userObject = domain.name
			if ( domain instanceof ProductDomain ) {
				for ( Domain dom : domain.domains )
					node.add( getNodeFromDomain(dom) )
			}
			else if ( domain instanceof RuleDomain ) {
				for ( Domain dom : domain.domains )
					node.add( getNodeFromDomain(dom) )
			}
			else if ( domain instanceof SequenceDomain ) {
				node.add( getNodeFromDomain(domain.domain) )
			}
			else if ( domain instanceof BagDomain ) {
				node.add( getNodeFromDomain(domain.domain) )
			}
			else if ( domain instanceof PowersetDomain ) {
				node.add( getNodeFromDomain(domain.baseDomain) )
			}
			else if ( domain instanceof MapDomain ) {
				node.add( getNodeFromDomain(domain.sourceDomain) )
				node.add( getNodeFromDomain(domain.targetDomain) )
			}
		}
		else {
			if ( domain === null ) node.userObject = null
			else {
				node.userObject = domain.name
			} 
			
		}
		
		return node
		
	}
	
	public def String getCodeFromTree() {
		return getCodeFromTree( this.model.root as TreeNode)	
	}
	
	public static def String getCodeFromTree( TreeNode node ) {
		
		if ( node === null ) return ""
		
		if ( node.childCount === 0 ) {
			return node.toString()
		}
		
		var String res = "("
		
		var TreeNode n
		for (var Enumeration e = node.children(); e.hasMoreElements();) {
        	n = e.nextElement() as TreeNode
       	 	res += getCodeFromTree(n) + ","
      	}
		res = res.substring( 0, res.length-1 )
		res += ")"
		
		res = node.toString + res
		
		return res
		
	}
	
	public static def DomainTree getTreeFromDomain( Domain domain ) {

		var root = getNodeFromDomain( domain )
		var DomainTree tree = new DomainTree(root)
	
		return tree
	}
	
	public def void printTree() {
		println("** Albero ")
		printNode( this.model.root as DefaultMutableTreeNode, "*-" )
		println("**")
	}
	
	public def static void printNode( DefaultMutableTreeNode node, String start ) {
		
		println( start + node )
		
		if ( node === null ) {
			println(start + "null")
			return
		}
		
		if ( node.childCount === 0 ) return
		
		var DefaultMutableTreeNode n
		for (var Enumeration e = node.children(); e.hasMoreElements();) {
        	n = e.nextElement() as DefaultMutableTreeNode
       	 	printNode(n, start + "-")
      	}	
	}
	
	public def static DomainTree mergeTree( ArrayList<DomainTree> domains  ) {

		if ( domains.size === 1 ) return domains.get(0)
		
		var ArrayList<DefaultMutableTreeNode> nodes = new ArrayList
		
		for ( DomainTree tree : domains ) {
			nodes.add( tree.model.root as DefaultMutableTreeNode )
		}
		
		var root = DomainTree.getComposedNodeFromNodes( "Prod", nodes )
		
		return new DomainTree(root)
		
	}
	
	def static getArity(DomainTree tree) {
		// if the root is a product domain, return the size of the children
		if ( tree.model !== null && tree.model.root !== null && tree.model.root.toString.equals("Prod") ) {
			var root =  tree.model.root as DefaultMutableTreeNode
			return root.childCount
		}
		
		return 1
	}
	
}