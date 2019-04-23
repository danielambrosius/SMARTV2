package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import smrt2.App;
import smrt2.Experiment;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class ExperimentView extends JFrame {

	private JPanel contentPane;
	private JTable parameterTable;
	private JTable stateTable;
	private final String[] parameterColumnNames = {"Parameter","value"};
	private final String[] stateColumnNames = {"State","value"};
	private App myApp;
	private JTextField textTimeStep;
	private JTextField textTimeStart;
	private JTextField textTimeEnd;

	public ExperimentView(App app) {
		this.myApp = app;
		buildGui();
		this.setVisible(true);
		updateGraphics();
	}

	private void buildGui() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 575, 461);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblParameters = new JLabel("Parameters");
		lblParameters.setBounds(22, 6, 76, 16);
		contentPane.add(lblParameters);
		
		JLabel lblStates = new JLabel("States");
		lblStates.setBounds(22, 178, 56, 16);
		contentPane.add(lblStates);
		
		JScrollPane scrollPaneParameters = new JScrollPane();
		scrollPaneParameters.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneParameters.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneParameters.setBounds(32, 35, 430, 132);
		contentPane.add(scrollPaneParameters);
		
		Object rowData[][] = { { "example name", "example value"} };
		
		// Creating table from default table model
		TableModel tableParamModel = new DefaultTableModel(rowData, parameterColumnNames);
		parameterTable = new JTable(tableParamModel);
		parameterTable.setFillsViewportHeight(true);
		parameterTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPaneParameters.setViewportView(parameterTable);
		
		JScrollPane scrollPaneStates = new JScrollPane();
		scrollPaneStates.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneStates.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneStates.setBounds(32, 207, 430, 132);
		contentPane.add(scrollPaneStates);
		
		TableModel tableStateModel = new DefaultTableModel(rowData, stateColumnNames);
		stateTable = new JTable(tableStateModel);
		stateTable.setFillsViewportHeight(true);
		scrollPaneStates.setViewportView(stateTable);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				safeEdit();
			}
		});
		btnEdit.setBounds(474, 39, 76, 25);
		contentPane.add(btnEdit);
		
		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				safeEdit();
				
				TableViewer table = new TableViewer(myApp);
				myApp.runExperiment();
				table.updateGraph();
				table.updateTable();
			}
		});
		btnRun.setBounds(474, 77, 76, 25);
		contentPane.add(btnRun);
		
		textTimeStep = new JTextField();
		textTimeStep.setBounds(94, 352, 60, 22);
		contentPane.add(textTimeStep);
		textTimeStep.setColumns(10);
		
		JLabel lblTimeStep = new JLabel("Time Step");
		lblTimeStep.setBounds(32, 352, 66, 16);
		contentPane.add(lblTimeStep);
		
		JLabel lblTimeStart = new JLabel("Time Start");
		lblTimeStart.setBounds(169, 355, 66, 16);
		contentPane.add(lblTimeStart);
		
		textTimeStart = new JTextField();
		textTimeStart.setBounds(238, 352, 60, 22);
		contentPane.add(textTimeStart);
		textTimeStart.setColumns(10);
		
		JLabel lblTimeEnd = new JLabel("Time End");
		lblTimeEnd.setBounds(310, 355, 56, 16);
		contentPane.add(lblTimeEnd);
		
		textTimeEnd = new JTextField();
		textTimeEnd.setBounds(370, 352, 60, 22);
		contentPane.add(textTimeEnd);
		textTimeEnd.setColumns(10);
	}
	
	public void safeEdit() {
		String errorMessage = "";
		if (stateTable.getCellEditor() != null) {
			stateTable.getCellEditor().stopCellEditing();
		}
		if (parameterTable.getCellEditor() != null) {
			parameterTable.getCellEditor().stopCellEditing();
		}
		ArrayList<String> stateList = new ArrayList<String>();
		ArrayList<String> paramList = new ArrayList<String>();
		
		for(int i = 0; i < stateTable.getModel().getRowCount();i++){
			if (myApp.checkIfDouble((String) stateTable.getModel().getValueAt(i,1))) {
				stateList.add((String) stateTable.getModel().getValueAt(i,1)); //get the all row values at column index 0
			} else {
				stateList.add(myApp.getStateNamesValues()[i][1]);
				errorMessage += String.format("%s: %s\n",myApp.getStateNamesValues()[i][0], (String) stateTable.getModel().getValueAt(i,1));
			}
		}
		for(int i = 0; i < parameterTable.getModel().getRowCount();i++){
			if (myApp.checkIfDouble((String) parameterTable.getModel().getValueAt(i,1))) {
				paramList.add((String) parameterTable.getModel().getValueAt(i,1)); //get the all row values at column index 0
			} else {
				paramList.add(myApp.getParameterNamesValues()[i][1]);
				errorMessage += String.format("%s: %s\n",myApp.getParameterNamesValues()[i][0], (String) parameterTable.getModel().getValueAt(i,1));
			}
		}	
		String timeStep = textTimeStep.getText();
		String timeStart = textTimeStart.getText();
		String timeEnd = textTimeEnd.getText();
		if (myApp.checkIfDouble(timeStep) == false) {
			errorMessage += "timeStep: " + timeStep+ "\n";
			timeStep = Double.toString(myApp.getTimeValues()[2]);
		}
		if (myApp.checkIfDouble(timeStart) == false) {
			errorMessage += "timeStart: " + timeStart +"\n";
			timeStart = Double.toString(myApp.getTimeValues()[0]);
		}
		if (myApp.checkIfDouble(timeEnd) == false) {
			errorMessage += "timeEnd: " + timeEnd + "\n";
			timeEnd = Double.toString(myApp.getTimeValues()[1]);
		}
		myApp.setValues(stateList,paramList, timeStep, timeStart, timeEnd);
		if (!errorMessage.isEmpty()){
			JOptionPane.showConfirmDialog(null,"TypeError for:\n" + errorMessage,"ValueError!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		}
		updateGraphics();
	}
	
	public void updateGraphics() {
		stateTableUpdate();
		parameterTableUpdate();
		timeLabelsUpdate();
	}
	
	public void stateTableUpdate() {
		DefaultTableModel tableModel = (DefaultTableModel) stateTable.getModel();
		tableModel.setDataVector(myApp.getStateNamesValues(), stateColumnNames);
		stateTable.setModel(tableModel);
	}
	
	public void parameterTableUpdate() {
		DefaultTableModel tableModel = (DefaultTableModel) parameterTable.getModel();
		tableModel.setDataVector(myApp.getParameterNamesValues(), parameterColumnNames);
		parameterTable.setModel(tableModel);
	}
	
	public void timeLabelsUpdate() {
		double[] timeValues = myApp.getTimeValues();
		textTimeStart.setText(""+ timeValues[0]);
		textTimeEnd.setText(""+ timeValues[1]);
		textTimeStep.setText(""+ timeValues[2]);
	}
}
