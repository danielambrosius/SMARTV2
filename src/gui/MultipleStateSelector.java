package gui;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.*;

import smrt2.SmartTableModel;

public class MultipleStateSelector extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable table;
    private Object[][] data;

    public MultipleStateSelector(SmartTableModel tableModel) {
        Object[] columnNames = {"State","Plot"};
        data = new Object[tableModel.getColumnCount()-1][2];
        
        for (int i = 0; i < tableModel.getColumnCount()-1; i++) {
			data[i][0] = tableModel.getColumnName(i+1);
			data[i][1] = false;
		}

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
    	
        table = new JTable(model) {

            private static final long serialVersionUID = 1L;

            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
        };
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);
    }

    protected int[] getStateNames() {
    	
    	List<Integer> selectedStates = new ArrayList<Integer>();
    	for(int i=0; i < data.length ; i++) {
    		if((boolean) table.getModel().getValueAt(i, 1)) {
    			selectedStates.add(i+1);
    		}
    	}
    	int[] selected = new int[selectedStates.size()]	;

    	for (int j = 0; j < selectedStates.size(); j++) {
			selected[j] = selectedStates.get(j);
		}
    	
		return selected;
	}
}