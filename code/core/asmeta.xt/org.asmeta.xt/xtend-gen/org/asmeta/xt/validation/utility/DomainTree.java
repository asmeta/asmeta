package org.asmeta.xt.validation.utility;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import org.asmeta.xt.asmetal.BagDomain;
import org.asmeta.xt.asmetal.Domain;
import org.asmeta.xt.asmetal.MapDomain;
import org.asmeta.xt.asmetal.PowersetDomain;
import org.asmeta.xt.asmetal.ProductDomain;
import org.asmeta.xt.asmetal.RuleDomain;
import org.asmeta.xt.asmetal.SequenceDomain;
import org.asmeta.xt.asmetal.StructuredTD;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class DomainTree extends JTree {
  public DomainTree(final DefaultMutableTreeNode node) {
    super(node);
  }

  public static DefaultMutableTreeNode getNodeFromString(final String domain) {
    DefaultMutableTreeNode node = new DefaultMutableTreeNode();
    node.setUserObject(domain);
    return node;
  }

  public static DefaultMutableTreeNode getComposedNode(final String parent_domain, final List<Domain> domains) {
    DefaultMutableTreeNode node = new DefaultMutableTreeNode();
    node.setUserObject(parent_domain);
    for (final Domain dom : domains) {
      node.add(DomainTree.getNodeFromDomain(dom));
    }
    return node;
  }

  public static DefaultMutableTreeNode getComposedNodeFromNodes(final String parent_domain, final List<DefaultMutableTreeNode> nodes) {
    DefaultMutableTreeNode node = new DefaultMutableTreeNode();
    node.setUserObject(parent_domain);
    for (final DefaultMutableTreeNode n : nodes) {
      if ((n != null)) {
        node.add(n);
      } else {
        node.add(DomainTree.getNodeFromString("NULLO"));
      }
    }
    return node;
  }

  public static DefaultMutableTreeNode getNodeFromDomain(final Domain domain) {
    DefaultMutableTreeNode node = new DefaultMutableTreeNode();
    if ((domain instanceof StructuredTD)) {
      node.setUserObject(((StructuredTD)domain).getName());
      if ((domain instanceof ProductDomain)) {
        EList<Domain> _domains = ((ProductDomain)domain).getDomains();
        for (final Domain dom : _domains) {
          node.add(DomainTree.getNodeFromDomain(dom));
        }
      } else {
        if ((domain instanceof RuleDomain)) {
          EList<Domain> _domains_1 = ((RuleDomain)domain).getDomains();
          for (final Domain dom_1 : _domains_1) {
            node.add(DomainTree.getNodeFromDomain(dom_1));
          }
        } else {
          if ((domain instanceof SequenceDomain)) {
            node.add(DomainTree.getNodeFromDomain(((SequenceDomain)domain).getDomain()));
          } else {
            if ((domain instanceof BagDomain)) {
              node.add(DomainTree.getNodeFromDomain(((BagDomain)domain).getDomain()));
            } else {
              if ((domain instanceof PowersetDomain)) {
                node.add(DomainTree.getNodeFromDomain(((PowersetDomain)domain).getBaseDomain()));
              } else {
                if ((domain instanceof MapDomain)) {
                  node.add(DomainTree.getNodeFromDomain(((MapDomain)domain).getSourceDomain()));
                  node.add(DomainTree.getNodeFromDomain(((MapDomain)domain).getTargetDomain()));
                }
              }
            }
          }
        }
      }
    } else {
      if ((domain == null)) {
        node.setUserObject(null);
      } else {
        node.setUserObject(domain.getName());
      }
    }
    return node;
  }

  public String getCodeFromTree() {
    Object _root = this.getModel().getRoot();
    return DomainTree.getCodeFromTree(((TreeNode) _root));
  }

  public static String getCodeFromTree(final TreeNode node) {
    if ((node == null)) {
      return "";
    }
    int _childCount = node.getChildCount();
    boolean _tripleEquals = (_childCount == 0);
    if (_tripleEquals) {
      return node.toString();
    }
    String res = "(";
    TreeNode n = null;
    for (Enumeration e = node.children(); e.hasMoreElements();) {
      {
        Object _nextElement = e.nextElement();
        n = ((TreeNode) _nextElement);
        String _res = res;
        String _codeFromTree = DomainTree.getCodeFromTree(n);
        String _plus = (_codeFromTree + ",");
        res = (_res + _plus);
      }
    }
    int _length = res.length();
    int _minus = (_length - 1);
    res = res.substring(0, _minus);
    String _res = res;
    res = (_res + ")");
    String _string = node.toString();
    String _plus = (_string + res);
    res = _plus;
    return res;
  }

  public static DomainTree getTreeFromDomain(final Domain domain) {
    DefaultMutableTreeNode root = DomainTree.getNodeFromDomain(domain);
    DomainTree tree = new DomainTree(root);
    return tree;
  }

  public void printTree() {
    InputOutput.<String>println("** Albero ");
    Object _root = this.getModel().getRoot();
    DomainTree.printNode(((DefaultMutableTreeNode) _root), "*-");
    InputOutput.<String>println("**");
  }

  public static void printNode(final DefaultMutableTreeNode node, final String start) {
    InputOutput.<String>println((start + node));
    if ((node == null)) {
      InputOutput.<String>println((start + "null"));
      return;
    }
    int _childCount = node.getChildCount();
    boolean _tripleEquals = (_childCount == 0);
    if (_tripleEquals) {
      return;
    }
    DefaultMutableTreeNode n = null;
    for (Enumeration e = node.children(); e.hasMoreElements();) {
      {
        Object _nextElement = e.nextElement();
        n = ((DefaultMutableTreeNode) _nextElement);
        DomainTree.printNode(n, (start + "-"));
      }
    }
  }

  public static DomainTree mergeTree(final ArrayList<DomainTree> domains) {
    int _size = domains.size();
    boolean _tripleEquals = (_size == 1);
    if (_tripleEquals) {
      return domains.get(0);
    }
    ArrayList<DefaultMutableTreeNode> nodes = new ArrayList<DefaultMutableTreeNode>();
    for (final DomainTree tree : domains) {
      Object _root = tree.getModel().getRoot();
      nodes.add(((DefaultMutableTreeNode) _root));
    }
    DefaultMutableTreeNode root = DomainTree.getComposedNodeFromNodes("Prod", nodes);
    return new DomainTree(root);
  }

  public static int getArity(final DomainTree tree) {
    if ((((tree.getModel() != null) && (tree.getModel().getRoot() != null)) && tree.getModel().getRoot().toString().equals("Prod"))) {
      Object _root = tree.getModel().getRoot();
      DefaultMutableTreeNode root = ((DefaultMutableTreeNode) _root);
      return root.getChildCount();
    }
    return 1;
  }
}
