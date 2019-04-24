package gui;
import smrt2.App;

import gui.FileChooser;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class SmartV2 extends JFrame {
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
		Object[] newOrOpen= {"New model","Open model"}; 
		int result = JOptionPane.showOptionDialog(null, "Open model or create new model", "SMRT v.2.0", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, newOrOpen, null);
		if (result==JOptionPane.YES_OPTION) {
			String modelName = JOptionPane.showInputDialog("Name of the new model:");
			app.newModel(modelName);
			}
		if (result==JOptionPane.NO_OPTION) {
			String filePath = FileChooser.open("Model", "model");
			app.openModel(filePath);
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
				String filePath = FileChooser.save("Model", "model");
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
				String filePath = FileChooser.save("Experiment", "exp");
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
		
		btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String state = StateField.getText();
				String equation = EquationField.getText();
				lblEditMode.setText("");
				boolean isAdded = app.handleButtonAddOde(state, equation);
				if (!isAdded){
					JOptionPane.showConfirmDialog(null,"State is not added","Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				}
				btnAdd.setText("Add");
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
				btnAdd.setText("OK");
			}
		});
		btnEdit.setBounds(518, 81, 84, 25);
		panelFormulas.add(btnEdit);
		
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
		
		JPanel panelStates = new JPanel();
		panelStates.setLayout(null);
		tabbedPane.addTab("States", null, panelStates, null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(22, 23, 569, 347);
		panelStates.add(scrollPane);
		
		TableModel stateParamModel = new DefaultTableModel(null,varColumnNames);
		tableStates = new JTable(stateParamModel);
		tableStates.setFillsViewportHeight(true);
		scrollPane.setViewportView(tableStates);
		
		updateGraphics(); // Makes the graphics nice
	}
	
	public void updateGraphics() {
		updateOdeTable();
		clearTextFields();
		updateParamTable();
		updateStateTable();
		this.setTitle("SmartV2 - "+ app.getModelName());

	}
	
	public void updateStateTable() {
		DefaultTableModel tableModel = (DefaultTableModel) tableStates.getModel();
		tableModel.setDataVector(app.getStateNames(), varColumnNames);
		tableStates.setModel(tableModel);
	}
	
	public void updateParamTable() {
		DefaultTableModel tableModel = (DefaultTableModel) tableParameters.getModel();
		tableModel.setDataVector(app.getParameterNames(), varColumnNames);
		tableParameters.setModel(tableModel);
	}

	public void updateOdeTable() {
		// Updating table
		DefaultTableModel tableModel = (DefaultTableModel) tableFormulas.getModel();
		tableModel.setDataVector(app.displayModelOdeList(), columnNames);
		tableFormulas.setModel(tableModel);
	}

	public void clearTextFields() {
		StateField.setText("");
		EquationField.setText("");
	}
}