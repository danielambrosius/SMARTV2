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
import javax.swing.table.TableModel;

import smrt2.App;
import smrt2.Experiment;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ExperimentView extends JFrame {

	private JPanel contentPane;
	private JScrollPane scrollPaneParameters;
	private JTable parameterTable;
	private JScrollPane scrollPaneStates;
	private JTable stateTable;
	private final String[] parameterColumnNames = {"Parameter","value"};
	private final String[] stateColumnNames = {"State","value"};
	private App myApp;


	public ExperimentView(App app) {
		this.myApp = app;
		buildGui();
		this.setVisible(true);
		updateGraphics();
	}

	private void buildGui() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 575, 414);
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
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAdd.setBounds(474, 39, 76, 25);
		contentPane.add(btnAdd);
	}
	
	public void updateGraphics() {
		stateTableUpdate();
		parameterTableUpdate();
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
}
