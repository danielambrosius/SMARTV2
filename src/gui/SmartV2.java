package gui;
import smrt2.App;
import smrt2.Model;
import gui.FileChooser;
//
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JToolBar;
import java.awt.GridBagConstraints;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.annotation.Target;
import javax.swing.JTextArea;

public class SmartV2 extends JFrame {
	private JTextField StateField;
	private JTextField EquationField;
	private JTable tableFormulas;
	private JTextArea txtParameterDisplay;
	private JTextArea txtStateDisplay;
	private App app = new App(); //TODO: Why does this not work in the constructor??
	private Integer selectedTableRow;
	
	private String columnNames[] = {"State", "Equation"};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SmartV2 frame = new SmartV2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Create the frame.
	 */
	public SmartV2() {
		setResizable(false);		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 625, 486);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnModel = new JMenu("Model");
		menuBar.add(mnModel);
		
		JMenuItem mntmNew = new JMenuItem("New...");
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				app.newModel(null);
				updateGraphics();
				//TODO: add a way of giving a name to name a model.
			}
		});
		mnModel.add(mntmNew);
		
		JMenuItem mntmOpen = new JMenuItem("Open...");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				app.openModel();
				updateGraphics();
			}
		});
		mnModel.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save...");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				app.saveModel();
			}
		});
		mnModel.add(mntmSave);
		
		JMenu mnExperiment = new JMenu("Experiment");
		menuBar.add(mnExperiment);
		
		JMenuItem mntmNew_1 = new JMenuItem("New...");
		mntmNew_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				app.newExperiment();
			}
		});
		mnExperiment.add(mntmNew_1);
		
		JMenuItem mntmOpen_1 = new JMenuItem("Open...");
		mntmOpen_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				app.openExperiment();
			}
		});
		mnExperiment.add(mntmOpen_1);
		
		JMenuItem mntmSave_1 = new JMenuItem("Save...");
		mntmSave_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				app.saveExperiment();
			}
		});
		mnExperiment.add(mntmSave_1);
		
		JMenuItem mntmRun = new JMenuItem("Run...");
		mnExperiment.add(mntmRun);
		getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane);
		
		JPanel panelFormulas = new JPanel();
		panelFormulas.setBackground(Color.WHITE);
		tabbedPane.addTab("Formulas", null, panelFormulas, null);
		panelFormulas.setLayout(null);
		
		StateField = new JTextField();
		StateField.setBounds(44, 46, 76, 22);
		panelFormulas.add(StateField);
		StateField.setColumns(10);
		
		JLabel lblState = new JLabel("State Variable");
		lblState.setBounds(44, 24, 91, 16);
		panelFormulas.add(lblState);
		
		EquationField = new JTextField();
		EquationField.setBounds(179, 46, 320, 22);
		panelFormulas.add(EquationField);
		EquationField.setColumns(10);
		
		JLabel lblDifferentialEquation = new JLabel("Differential equation");
		lblDifferentialEquation.setBounds(179, 24, 228, 16);
		panelFormulas.add(lblDifferentialEquation);
		
		JLabel label = new JLabel("/dt   = ");
		label.setBounds(132, 49, 62, 16);
		panelFormulas.add(label);
		
		JLabel lblDdt = new JLabel("d ");
		lblDdt.setBounds(22, 49, 17, 16);
		panelFormulas.add(lblDdt);
		
		
		Object rowData[][] = { { "example state name", "example formula"} };
		
		// Creating table from default table model
		TableModel tableModel = new DefaultTableModel(rowData, columnNames);
		tableFormulas = new JTable(tableModel);
		tableFormulas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				selectedTableRow =  table.getSelectedRow();
			}
		});
		
		
		// Center the text in the cells
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int x = 0; x < tableFormulas.getColumnModel().getColumnCount(); x++) {
			tableFormulas.getColumnModel().getColumn(x).setCellRenderer(centerRenderer);
		}
		
		// Set table lay-out
		tableFormulas.getColumnModel().getColumn(0).setMaxWidth(100);
		tableFormulas.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableFormulas.setBounds(22, 88, 484, 282);
		panelFormulas.add(tableFormulas);
		
		JLabel lblEditMode = new JLabel("");
		lblEditMode.setBounds(379, 17, 99, 16);
		panelFormulas.add(lblEditMode);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String state = StateField.getText();
				String equation = EquationField.getText();
				lblEditMode.setText("");
				app.handleButtonAddOde(state, equation);
				updateGraphics();
			}
		});
		
		panelFormulas.add(btnAdd);
		btnAdd.setBounds(518, 45, 84, 25);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				app.handleDeleteOde(selectedTableRow);
				selectedTableRow = null;
				updateGraphics();
			}
		});
		btnDelete.setBounds(518, 118, 84, 25);
		panelFormulas.add(btnDelete);
		
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String[] odeArray = app.handleEditOde(selectedTableRow);
				StateField.setText(odeArray[0]);
				lblEditMode.setText("Edit mode...");
				EquationField.setText(odeArray[1]);
				selectedTableRow = null;
				//updateGraphics();
			}
		});
		btnEdit.setBounds(518, 81, 84, 25);
		panelFormulas.add(btnEdit);
		
		JPanel panelParameters = new JPanel();
		tabbedPane.addTab("Parameters", null, panelParameters, null);
		panelParameters.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setBounds(0, 0, 602, 370);
		panelParameters.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblParameter = new JLabel("Parameter");
		lblParameter.setBounds(33, 13, 85, 16);
		panel_2.add(lblParameter);
		
		JLabel lblStates = new JLabel("States");
		lblStates.setBounds(327, 13, 56, 16);
		panel_2.add(lblStates);
		
		txtParameterDisplay = new JTextArea();
		txtParameterDisplay.setEditable(false);
		txtParameterDisplay.setBounds(33, 42, 184, 315);
		panel_2.add(txtParameterDisplay);
		
		txtStateDisplay = new JTextArea();
		txtStateDisplay.setEditable(false);
		txtStateDisplay.setBounds(323, 42, 184, 315);
		panel_2.add(txtStateDisplay);
	}
	
	public void updateGraphics() {
		updateOdeTable();
		clearTextFields();
		updateParamStateTextFields();
//		updateParamTable();
	}
	
	public void updateParamStateTextFields() {
		String parameters = app.getVariableString();
		String states = app.getStateString();
		txtParameterDisplay.setText(parameters);
		txtStateDisplay.setText(states);
	}


	public void updateOdeTable() {
		// Updating table
		DefaultTableModel tableModel = (DefaultTableModel) tableFormulas.getModel();
		tableModel.setDataVector(app.displayModelOdeList(), columnNames);
		tableFormulas.setModel(tableModel);
	}
	
//	public void updateParamTable() {
//		// Updating table
//		DefaultTableModel tableModel = (DefaultTableModel) tableVariables.getModel();
//		tableModel.setDataVector(app.displayVariablesList(), columnNames);
//		tableVariables.setModel(tableModel);
//	}

	
	public void clearTextFields() {
		StateField.setText("");
		EquationField.setText("");
	}
}