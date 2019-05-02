package gui;
import smrt2.App;

import gui.FileChooser;
import javafx.scene.text.Font;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class ModelView extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField StateField;
	private JTextField EquationField;
	private JTable tableFormulas;
	private JButton btnAdd;
	private App app = new App(); //TODO: Why does this not work in the constructor??
	private Integer selectedTableRow;
	
	private String[] columnNames= {"State", "Equation"};
	private String[] varColumnNames = {"Parameter", "Unit", "Description"};
	private JTable tableParameters;
	private JTable tableStates;
	private JTextField textFieldParameter;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModelView frame = new ModelView();
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
	public ModelView() {
		setResizable(false);		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 625, 486);
		
		Object[] newOrOpen= {"New model","Open model", "Open Experiment"}; 
		int result = JOptionPane.showOptionDialog(null, "Open model or create new model", "SMRT v.2.0", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, newOrOpen, null);
		if (result==JOptionPane.YES_OPTION) {
			String modelName = JOptionPane.showInputDialog("Name of the new model:");
			app.newModel(modelName);
			} else if (result==JOptionPane.NO_OPTION) {
			String filePath = FileChooser.open("Model", "model");
			app.openModel(filePath);
		} else if (result == JOptionPane.CANCEL_OPTION) {
			String filePath = FileChooser.open("Experiment", "exp");
			app.openExperiment(filePath);
			ExperimentView expV = new ExperimentView(app);
			expV.setLocation(800, 100);
		} else if (result==JOptionPane.CLOSED_OPTION) {
			System.exit(0);
		}
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnModel = new JMenu("Model");
		menuBar.add(mnModel);
		
		JMenuItem mntmNew = new JMenuItem("New...");
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String modelName = JOptionPane.showInputDialog("Name of the new model:");
				app.newModel(modelName);
				updateGraphics();
			}
		});
		mnModel.add(mntmNew);
		
		JMenuItem mntmOpen = new JMenuItem("Open...");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filePath = FileChooser.open("Model", "model");
				app.openModel(filePath);
				updateGraphics();
			}
		});
		mnModel.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save...");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filePath = FileChooser.save("Model", "model", app.getModelName());
				app.saveModel(filePath);
				updateGraphics();
			}
		});
		mnModel.add(mntmSave);
		
		JMenu mnExperiment = new JMenu("Experiment");
		menuBar.add(mnExperiment);
		
		JMenuItem mntmNew_1 = new JMenuItem("New...");
		mntmNew_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean isCreated = app.newExperiment();
				if(isCreated) {

					new ExperimentView(app);
				}else {
					JOptionPane.showConfirmDialog(null,"Formulas are incorrect","Warning!!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mnExperiment.add(mntmNew_1);
		
		JMenuItem mntmOpen_1 = new JMenuItem("Open...");
		mntmOpen_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filePath = FileChooser.open("Experiment", "exp");
				app.openExperiment(filePath);
				updateGraphics();
				new ExperimentView(app);
				
			}
		});
		mnExperiment.add(mntmOpen_1);
		
		JMenuItem mntmSave_1 = new JMenuItem("Save...");
		mntmSave_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filePath = FileChooser.save("Experiment", "exp", app.getModelName());
				app.saveExperiment(filePath);
			}
		});
		mnExperiment.add(mntmSave_1);
		
		getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane);
		
		JPanel panelFormulas = new JPanel();
		panelFormulas.setBackground(Color.WHITE);
		tabbedPane.addTab("Formulas", null, panelFormulas, null);
		panelFormulas.setLayout(null);
		
		StateField = new JTextField();
		StateField.setBounds(22, 46, 76, 22);
		panelFormulas.add(StateField);
		StateField.setColumns(10);
		
		JLabel lblState = new JLabel("Dependent Variable");
		lblState.setBounds(22, 29, 123, 16);
		panelFormulas.add(lblState);
		
		EquationField = new JTextField();
		EquationField.setBounds(179, 46, 327, 22);
		panelFormulas.add(EquationField);
		EquationField.setColumns(10);
		
		JLabel lblDifferentialEquation = new JLabel("Equation");
		lblDifferentialEquation.setBounds(179, 29, 228, 16);
		panelFormulas.add(lblDifferentialEquation);
		
		JLabel label = new JLabel("=");
		label.setBounds(143, 49, 9, 16);
		panelFormulas.add(label);
		
		
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
		tableFormulas.setBounds(22, 118, 484, 252);
		panelFormulas.add(tableFormulas);
		
		JLabel lblEditMode = new JLabel("");
		lblEditMode.setBounds(379, 17, 99, 16);
		panelFormulas.add(lblEditMode);
		
		JRadioButton rdbtnOde = new JRadioButton("ODE");
		buttonGroup.add(rdbtnOde);
		rdbtnOde.setSelected(true);
		rdbtnOde.setBounds(120, 85, 53, 25);
		panelFormulas.add(rdbtnOde);
		
		JRadioButton rdbtnAlgebraicEquation = new JRadioButton("Algebraic Equation");
		buttonGroup.add(rdbtnAlgebraicEquation);
		rdbtnAlgebraicEquation.setBounds(189, 85, 135, 25);
		panelFormulas.add(rdbtnAlgebraicEquation);	
		
		btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String state = StateField.getText();
				String equation = EquationField.getText();
				boolean isOde = rdbtnOde.isSelected();
				lblEditMode.setText("");
				int addState = app.handleButtonAddFormula(state, equation, isOde);
				if (addState == 1){
					JOptionPane.showConfirmDialog(null,"State already exists, formula not added.","Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				} else if (addState == 2) {
					JOptionPane.showConfirmDialog(null,"State or equation field empty, formula not added.","Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				} else if (addState == 3) {
					int answer = JOptionPane.showConfirmDialog(null,"State is already in pre-defined parameters. Continue?","Warning", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (answer == 0) {
						app.handleDeleteUnboundParameter(state);
						app.handleButtonAddFormula(state, equation, isOde);
						updateGraphics();
					}
				} else {
					updateGraphics();
					btnAdd.setText("Add");
				}
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
				btnAdd.setText("OK");
			}
		});
		btnEdit.setBounds(518, 81, 84, 25);
		panelFormulas.add(btnEdit);
		
		JLabel lblEquationType = new JLabel("Equation type: ");
//		lblEquationType.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEquationType.setBounds(22, 85, 86, 25);
		panelFormulas.add(lblEquationType);
		
		JPanel panelParameters = new JPanel();
		tabbedPane.addTab("Parameters", null, panelParameters, null);
		panelParameters.setLayout(null);
		
		JScrollPane scrollPaneParameters = new JScrollPane();
		scrollPaneParameters.setBounds(22, 23, 484, 347);
		panelParameters.add(scrollPaneParameters);
		scrollPaneParameters.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneParameters.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		TableModel tableParamModel = new DefaultTableModel(null,varColumnNames);
		tableParameters = new JTable(tableParamModel);
		tableParameters.setFillsViewportHeight(true);
		scrollPaneParameters.setViewportView(tableParameters);
		
		JButton btnAddParameter = new JButton("Add");
		btnAddParameter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String parameter = textFieldParameter.getText();
				int isAdded = app.handleButtonAddParameter(parameter);
				if(isAdded == 1) {
					JOptionPane.showConfirmDialog(null,"Parameter already exists","Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				}
				if(isAdded == 2) {
					JOptionPane.showConfirmDialog(null,"Parameter already a State","Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				}
				textFieldParameter.setText("");
				updateGraphics();
			}
		});
		btnAddParameter.setBounds(518, 64, 84, 25);
		panelParameters.add(btnAddParameter);
		
		textFieldParameter = new JTextField();
		textFieldParameter.setBounds(518, 38, 84, 22);
		panelParameters.add(textFieldParameter);
		textFieldParameter.setColumns(10);
		
		JButton deleteBtn = new JButton("Delete");
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String rowParameter = tableParameters.getModel().getValueAt(tableParameters.getSelectedRow(), 0).toString().replace(" (Unbound)", "");
				app.handleDeleteUnboundParameter(rowParameter);
				updateGraphics();
			}
		});
		deleteBtn.setBounds(518, 93, 84, 25);
		panelParameters.add(deleteBtn);
		
		JButton btnSubmitUnitdescription = new JButton("Submit");
		btnSubmitUnitdescription.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleUnitDescriptionSubmit(tableParameters);
			}
		});
		btnSubmitUnitdescription.setBounds(520, 345, 84, 25);
		panelParameters.add(btnSubmitUnitdescription);
		
		JLabel lblUnitdescription = new JLabel("Unit/Description");
		lblUnitdescription.setBounds(526, 325, 76, 14);
		panelParameters.add(lblUnitdescription);
		
		JPanel panelStates = new JPanel();
		panelStates.setLayout(null);
		tabbedPane.addTab("Dependent variables", null, panelStates, null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(22, 23, 484, 347);
		panelStates.add(scrollPane);
		
		TableModel stateParamModel = new DefaultTableModel(null,varColumnNames);
		tableStates = new JTable(stateParamModel);
		tableStates.setFillsViewportHeight(true);
		scrollPane.setViewportView(tableStates);
		
		JButton button = new JButton("Submit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				handleUnitDescriptionSubmit(tableStates);
			}
		});
		button.setBounds(520, 345, 84, 25);
		panelStates.add(button);
		
		JLabel label_1 = new JLabel("Unit/Description");
		label_1.setBounds(526, 325, 76, 14);
		panelStates.add(label_1);
		
//		textFieldVariable = new JTextField();
//		textFieldVariable.setText("");
//		textFieldVariable.setColumns(10);
//		textFieldVariable.setBounds(44, 46, 76, 22);
//		
//		textFieldAlgebraicFormula = new JTextField();
//		textFieldAlgebraicFormula.setText("");
//		textFieldAlgebraicFormula.setColumns(10);
//		textFieldAlgebraicFormula.setBounds(179, 46, 320, 22);
//		
//		tableAlgebraicFormulas = new JTable((TableModel) null);
//		tableAlgebraicFormulas.setBorder(new LineBorder(new Color(0, 0, 0)));
//		tableAlgebraicFormulas.setBounds(22, 88, 484, 282);
		
		
		JButton helpButton = new JButton("?");
		helpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String text = "<html>";
					text += new BufferedReader(new FileReader("./lib/ModelHelpText.txt")).lines().collect(Collectors.joining("<br>"));
					new HelpWindow(text);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		helpButton.setToolTipText("Help");
		helpButton.setBounds(542, 330, 45, 45);
		panelFormulas.add(helpButton);
	
		
		updateGraphics(); // Makes the graphics nice
	}
	
	private void handleUnitDescriptionSubmit(JTable table) {
		String key;
		String unit;
		String description;
		
		if (table.getCellEditor() != null) {
			table.getCellEditor().stopCellEditing();
		}
		TableModel input = table.getModel();
		for (int i = 0; i < input.getRowCount(); i++) {
			key = (String) input.getValueAt(i, 0);
			unit = (String) input.getValueAt(i, 1);
			description = (String) input.getValueAt(i, 2);
			app.addDescriptionToVarTable(key, unit, description);
		}
		updateGraphics();
	}


	private void updateGraphics() {
		updateOdeTable();
		clearTextFields();
		updateParamTable();
		updateStateTable();
		this.setTitle("SmartV2 - Model - "+ app.getModelName());

	}
	
	private void updateStateTable() {
		DefaultTableModel tableModel = (DefaultTableModel) tableStates.getModel();
		tableModel.setDataVector(app.getStateNames(), varColumnNames);
		tableStates.setModel(tableModel);
	}
	
	private void updateParamTable() {
		DefaultTableModel tableModel = (DefaultTableModel) tableParameters.getModel();
		tableModel.setDataVector(app.getParameterNames(), varColumnNames);
		tableParameters.setModel(tableModel);
	}

	private void updateOdeTable() {
		DefaultTableModel tableModel = (DefaultTableModel) tableFormulas.getModel();
		tableModel.setDataVector(app.displayModelOdeList(), columnNames);
		tableFormulas.setModel(tableModel);
	}

	private void clearTextFields() {
		StateField.setText("");
		EquationField.setText("");
	}
}