package org.asmeta.visualdesigner.views;

import java.util.HashMap;
import java.util.Map;

import org.asmeta.visualdesigner.figures.RuleFigure;
import org.asmeta.visualdesigner.model.DiagramModel;
import org.asmeta.visualdesigner.model.RuleNode;
import org.asmeta.visualdesigner.model.Transition;
import org.asmeta.visualdesigner.selection.SelectionManager;
import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class DesignView extends ViewPart {

    private SelectionManager selectionManager = new SelectionManager();
    private Map<RuleNode, RuleFigure> figures = new HashMap<>();

    private Figure root;

    private RuleNode draggedNode;
    private RuleFigure draggedFigure;
    private Point lastMouseLocation;
    
    private DiagramModel model;
    private int ruleCounter = 3;
    private RuleNode transitionSource;

    @Override
    public void createPartControl(Composite parent) {
        FigureCanvas canvas = new FigureCanvas(parent);

        root = new Figure();
        root.setLayoutManager(new XYLayout());

        canvas.setContents(root);
        
        Menu menu = new Menu(canvas);
        canvas.setMenu(menu);

        MenuItem newRuleItem = new MenuItem(menu, SWT.NONE);
        newRuleItem.setText("New Rule");

        newRuleItem.addListener(SWT.Selection, event -> {
            RuleNode newRule = new RuleNode(
                "Rule " + ruleCounter,
                100 + ruleCounter * 20,
                100 + ruleCounter * 20,
                100,
                50
            );

            ruleCounter++;

            model.getRules().add(newRule);

            refreshDiagram();
        });

        model = createSampleModel();
        render(model);
    }

    private DiagramModel createSampleModel() {
        DiagramModel model = new DiagramModel();

        RuleNode ruleA = new RuleNode("Rule A", 50, 50, 100, 50);
        RuleNode ruleB = new RuleNode("Rule B", 250, 50, 100, 50);

        model.getRules().add(ruleA);
        model.getRules().add(ruleB);

        model.getTransitions().add(new Transition(ruleA, ruleB));

        return model;
    }
    
    private void refreshDiagram() {
        root.removeAll();
        figures.clear();
        render(model);

        root.revalidate();
        root.repaint();
    }

    private void render(DiagramModel model) {
        figures.clear();

        for (RuleNode node : model.getRules()) {
            RuleFigure figure = new RuleFigure(node.getName());

            figures.put(node, figure);

            figure.addMouseListener(new MouseListener() {

                @Override
                public void mousePressed(MouseEvent me) {
                    selectionManager.setSelectedNode(node);
                    updateSelection();

                    draggedNode = node;
                    draggedFigure = figure;
                    lastMouseLocation = new Point(me.x, me.y);

                    me.consume();
                }

                @Override
                public void mouseReleased(MouseEvent me) {
                    draggedNode = null;
                    draggedFigure = null;
                    lastMouseLocation = null;

                    me.consume();
                }

                @Override
                public void mouseDoubleClicked(MouseEvent me) {
                    if (transitionSource == null) {
                        transitionSource = node;
                        selectionManager.setSelectedNode(node);
                        updateSelection();
                    } else {
                        if (transitionSource != node) {
                            model.getTransitions().add(
                                new Transition(transitionSource, node)
                            );
                            transitionSource = null;
                            refreshDiagram();
                        }
                    }

                    me.consume();
                }
            });

            figure.addMouseMotionListener(new MouseMotionListener() {

                @Override
                public void mouseDragged(MouseEvent me) {
                    if (draggedNode == null || draggedFigure == null || lastMouseLocation == null) {
                        return;
                    }

                    int dx = me.x - lastMouseLocation.x;
                    int dy = me.y - lastMouseLocation.y;

                    draggedNode.setX(draggedNode.getX() + dx);
                    draggedNode.setY(draggedNode.getY() + dy);

                    root.setConstraint(
                        draggedFigure,
                        new Rectangle(
                            draggedNode.getX(),
                            draggedNode.getY(),
                            draggedNode.getWidth(),
                            draggedNode.getHeight()
                        )
                    );

                    lastMouseLocation = new Point(me.x, me.y);

                    root.revalidate();
                    root.repaint();

                    me.consume();
                }

                @Override
                public void mouseEntered(MouseEvent me) {
                }

                @Override
                public void mouseExited(MouseEvent me) {
                }

                @Override
                public void mouseHover(MouseEvent me) {
                }

                @Override
                public void mouseMoved(MouseEvent me) {
                }
            });

            root.add(
                figure,
                new Rectangle(
                    node.getX(),
                    node.getY(),
                    node.getWidth(),
                    node.getHeight()
                )
            );
        }

        for (Transition transition : model.getTransitions()) {
            RuleFigure sourceFigure = figures.get(transition.getSource());
            RuleFigure targetFigure = figures.get(transition.getTarget());

            PolylineConnection connection = new PolylineConnection();

            connection.setSourceAnchor(new ChopboxAnchor(sourceFigure));
            connection.setTargetAnchor(new ChopboxAnchor(targetFigure));

            PolygonDecoration decoration = new PolygonDecoration();
            connection.setTargetDecoration(decoration);

            root.add(connection);
        }
    }

    private void updateSelection() {
        for (Map.Entry<RuleNode, RuleFigure> entry : figures.entrySet()) {
            RuleNode node = entry.getKey();
            RuleFigure figure = entry.getValue();

            figure.setSelected(selectionManager.isSelected(node));
        }
    }

    @Override
    public void setFocus() {
    }
}