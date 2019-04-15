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
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class ExperimentView extends JFrame {

	private JPanel contentPane;
	private JScrollPane scrollPaneParameters;
	private JTable parameterTable;
	private JScrollPane scrollPaneStates;
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
		
		scrollPaneParameters = new JScrollPane();
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
		
		scrollPaneStates = new JScrollPane();
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
				ArrayList stateList = new ArrayList();
				ArrayList paramList = new ArrayList();
				for(int i = 0; i < stateTable.getModel().getRowCount();i++){
					stateList.add(stateTable.getModel().getValueAt(i,1)); //get the all row values at column index 0
				}
				for(int i = 0; i < parameterTable.getModel().getRowCount();i++){
					paramList.add(parameterTable.getModel().getValueAt(i,1)); //get the all row values at column index 0
				}
				String timeStep = textTimeStep.getText();
				String timeStart = textTimeStart.getText();
				String timeEnd = textTimeEnd.getText();
				myApp.setValues(stateList,paramList, timeStep, timeStart, timeEnd);
				updateGraphics();
			}
		});
		btnEdit.setBounds(474, 39, 76, 25);
		contentPane.add(btnEdit);
		
		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myApp.runExperiment();
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
