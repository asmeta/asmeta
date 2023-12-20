package asmeta.fmvclib.view;

import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;

import asmeta.fmvclib.controller.ButtonColumn;

@SuppressWarnings("serial")
public class XButtonModel extends AbstractTableModel {

	int nItems;
	LinkedList<Boolean> values = new LinkedList<Boolean>();

	public void updateValue(int row) {
		if (row < values.size())
			if (values.get(row))
				values.set(row, false);
			else
				values.set(row, true);
		fireTableCellUpdated(row, 0);
	}

	public XButtonModel(int nFlights) {
		this.nItems = nFlights;
		for (int i=0; i<nFlights; i++)
			values.add(false);
	}

	@Override
	public Class<?> getColumnClass(int col) {
		return ButtonColumn.class;
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return true;
	}

	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public int getRowCount() {
		return values.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		if (row >= values.size()) return "";
		return values.get(row) ? "X" : "";
	}
	
	public void removeRow(int row) {
        values.remove(row);
        this.nItems--;
    }
	
	public void addRow() {
        values.add(false);
        this.nItems++;
    }
}