package asmeta.fmvclib.controller;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

/**
 * The ButtonColumn class provides a renderer and an editor that looks like a
 * JButton. The renderer and editor will then be used for a specified column in
 * the table. The TableModel will contain the String to be displayed on the
 * button.
 *
 * The button can be invoked by a mouse click or by pressing the space bar when
 * the cell has focus. Optionally a mnemonic can be set to invoke the button.
 * When the button is invoked the provided Action is invoked. The source of the
 * Action will be the table. The action command will contain the model row
 * number of the button that was clicked.
 * 
 * @author Andrea Bombarda (partially)
 * 
 */
@SuppressWarnings("serial")
public class ButtonColumn extends AbstractCellEditor
		implements TableCellRenderer, TableCellEditor, ActionListener, MouseListener {
	/**
	 * The table in which the ButtonColumn is added
	 */
	private JTable table;
	/**
	 * The action linked to a click of a button
	 */
	private Action action;
	/**
	 * The border style to be used
	 */
	private Border focusBorder;
	/**
	 * The button appearance (rendered)
	 */
	private JButton renderButton;
	/**
	 * The button editor
	 */
	private JButton editButton;
	/**
	 * The associated value in the editor
	 */
	private Object editorValue;
	/**
	 * Keeps track of the state of the column: in edit mode or not
	 */
	private boolean isButtonColumnEditor;

	/**
	 * Create the ButtonColumn to be used as a renderer and editor. The renderer and
	 * editor will automatically be installed on the TableColumn of the specified
	 * column.
	 *
	 * @param table  the table containing the button renderer/editor
	 * @param column the column to which the button renderer/editor is added
	 */
	public ButtonColumn(JTable table, int column) {
		this(table, null, column);
	}

	/**
	 * Create the ButtonColumn to be used as a renderer and editor. The renderer and
	 * editor will automatically be installed on the TableColumn of the specified
	 * column.
	 *
	 * @param table  the table containing the button renderer/editor
	 * @param action the Action to be invoked when the button is invoked
	 * @param column the column to which the button renderer/editor is added
	 */
	public ButtonColumn(JTable table, Action action, int column) {
		this.table = table;
		this.action = action;

		renderButton = new JButton();
		editButton = new JButton();
		editButton.setFocusPainted(false);
		editButton.addActionListener(this);
		setFocusBorder(new LineBorder(Color.BLUE));

		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(column).setCellRenderer(this);
		columnModel.getColumn(column).setCellEditor(this);
		table.addMouseListener(this);
	}

	/**
	 * Get foreground color of the button when the cell has focus
	 *
	 * @return the foreground color
	 */
	public Border getFocusBorder() {
		return focusBorder;
	}

	/**
	 * The foreground color of the button when the cell has focus
	 *
	 * @param focusBorder the foreground color
	 */
	public void setFocusBorder(Border focusBorder) {
		this.focusBorder = focusBorder;
		editButton.setBorder(focusBorder);
	}

	/**
	 * Get the rendered object in editor mode in each cell of the button Column
	 * 
	 * @param table      the table containing the column
	 * @param value      the value to be set
	 * @param isSelected is the cell selected now?
	 * @param row        the row
	 * @param column     the column
	 * @return the component to be rendered in editor mode
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		if (value == null) {
			editButton.setText("");
			editButton.setIcon(null);
		} else if (value instanceof Icon) {
			editButton.setText("");
			editButton.setIcon((Icon) value);
		} else {
			editButton.setText(value.toString());
			editButton.setIcon(null);
		}

		this.editorValue = value;
		return editButton;
	}

	/**
	 * Get the value in edit mode
	 * 
	 * @return the value in edit mode
	 */
	@Override
	public Object getCellEditorValue() {
		return editorValue;
	}

	/**
	 * Get the rendered object in each cell of the button Column
	 * 
	 * @param table      the table containing the column
	 * @param value      the value to be set
	 * @param isSelected is the cell selected now?
	 * @param hasFocus   does the cell have focus?
	 * @param row        the row
	 * @param column     the column
	 * @return the component to be rendered
	 */
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (isSelected) {
			renderButton.setForeground(table.getSelectionForeground());
			renderButton.setBackground(table.getSelectionBackground());
		} else {
			renderButton.setForeground(table.getForeground());
			renderButton.setBackground(UIManager.getColor("Button.background"));
		}

		if (hasFocus) {
			renderButton.setBorder(focusBorder);
		} else {
			renderButton.setBorder(focusBorder);
		}

		if (value == null) {
			renderButton.setText("");
			renderButton.setIcon(null);
		} else if (value instanceof Icon) {
			renderButton.setText("");
			renderButton.setIcon((Icon) value);
		} else {
			renderButton.setText(value.toString());
			renderButton.setIcon(null);
		}

		return renderButton;
	}

	/**
	 * The button has been pressed. Stop editing and invoke the custom Action
	 * 
	 * @param e the event
	 */
	public void actionPerformed(ActionEvent e) {
		int row = table.convertRowIndexToModel(table.getEditingRow());
		fireEditingStopped();

		// Invoke the Action
		ActionEvent event = new ActionEvent(table, ActionEvent.ACTION_PERFORMED, "" + row);
		action.actionPerformed(event);
	}

	/**
	 * When the mouse is pressed the editor is invoked. If you then then drag the
	 * mouse to another cell before releasing it, the editor is still active. Make
	 * sure editing is stopped when the mouse is released.
	 * 
	 * @param e the MouseEvent
	 */
	public void mousePressed(MouseEvent e) {
		if (table.isEditing() && table.getCellEditor() == this)
			isButtonColumnEditor = true;
	}

	/**
	 * When the mouse is released the editor is deactivated
	 * 
	 * @param e the MouseEvent
	 */
	public void mouseReleased(MouseEvent e) {
		if (isButtonColumnEditor && table.isEditing())
			table.getCellEditor().stopCellEditing();

		isButtonColumnEditor = false;
	}

	/**
	 * Click with the mouse
	 * 
	 * THIS METHOD IS NOT USED
	 * 
	 * @param e the MouseEvent
	 */
	public void mouseClicked(MouseEvent e) {
	}

	/**
	 * Mouse entered in the button area
	 * 
	 * THIS METHOD IS NOT USED
	 * 
	 * @param e the MouseEvent
	 */
	public void mouseEntered(MouseEvent e) {
	}

	/**
	 * Mouse exited from the button area
	 * 
	 * THIS METHOD IS NOT USED
	 * 
	 * @param e the MouseEvent
	 */
	public void mouseExited(MouseEvent e) {
	}

	/**
	 * Set the action to be performed when clicking on the button
	 * 
	 * @param action the action
	 * @param action
	 */
	public void setAction(Action action) {
		this.action = action;
	}
}