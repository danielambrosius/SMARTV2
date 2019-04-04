package gui;
import smrt2.Model;

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

import java.awt.event.ActionListener;
import java.util.Arrays;
import java.awt.event.ActionEvent;

public class SmartV2 extends JFrame {
	private JTextField StateField;
	private JTextField EquationField;
	private JTable tableFormulas;
	private JTable table_1;

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
	Model myModel =new Model();

	/**
	 * Create the frame.
	 */
	public SmartV2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 625, 486);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnModel = new JMenu("Model");
		menuBar.add(mnModel);
		
		JMenuItem mntmNew = new JMenuItem("New...");
		mnModel.add(mntmNew);
		
		JMenuItem mntmOpen = new JMenuItem("Open...");
		mnModel.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save...");
		mnModel.add(mntmSave);
		
		JMenu mnExperiment = new JMenu("Experiment");
		menuBar.add(mnExperiment);
		
		JMenuItem mntmNew_1 = new JMenuItem("New...");
		mnExperiment.add(mntmNew_1);
		
		JMenuItem mntmOpen_1 = new JMenuItem("Open...");
		mnExperiment.add(mntmOpen_1);
		
		JMenuItem mntmSave_1 = new JMenuItem("Save...");
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
		
		//Object rowData[][] = { { "A", "k"} };
		resetTable(panelFormulas);
		//Object rowData[][]= myModel.displayOdeList();
		//Object columnNames[] = { "State", "Equation"};
		//tableFormulas = new JTable(rowData,columnNames);
		//tableFormulas.getColumnModel().getColumn(0).setMaxWidth(100);
		//DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		//centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		//for (int x = 0; x < tableFormulas.getColumnModel().getColumnCount(); x++) {
			//tableFormulas.getColumnModel().getColumn(x).setCellRenderer(centerRenderer);
		//}

		//tableFormulas.setBorder(new LineBorder(new Color(0, 0, 0)));
		//tableFormulas.setBounds(22, 88, 484, 282);
		
		//panelFormulas.add(tableFormulas);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String state = StateField.getText().replace(" ", "");
				String equation = EquationField.getText().replace(" ", "");
				if (!state.isEmpty() && !equation.isEmpty()) {
					//System.out.println(equation);
					myModel.addOde(state, equation);
					//((DefaultTableModel)tableFormulas.getModel());
					resetTable(panelFormulas);
				}
			}
		});
		panelFormulas.add(btnAdd);
		btnAdd.setBounds(520, 45, 66, 25);
		JPanel panelParameters = new JPanel();
		tabbedPane.addTab("Parameters", null, panelParameters, null);
		panelParameters.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setBounds(0, 0, 602, 370);
		panelParameters.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblParameter = new JLabel("Parameter");
		lblParameter.setBounds(12, 13, 85, 16);
		panel_2.add(lblParameter);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnRefresh.setBounds(493, 25, 97, 25);
		panel_2.add(btnRefresh);
		
		table_1 = new JTable();
		table_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		table_1.setBounds(12, 66, 463, 291);
		panel_2.add(table_1);
		
	}
	public void resetTable(JPanel panelFormulas) {
		// set the odelist to displaylist
		Object displayList[][]= myModel.displayOdeList();
		//Print text from odeList
		//for (int i = 0; i < displayList.length; i++) {
		//	for (int j = 0; j < displayList[i].length; j++) {
		//	System.out.println(displayList[i][j]);
		//	}
		//}
		//Table format
		Object columnNames[] = { "State", "Equation"};
		tableFormulas = new JTable(displayList,columnNames);
		tableFormulas.getColumnModel().getColumn(0).setMaxWidth(100);
		//center text
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int x = 0; x < tableFormulas.getColumnModel().getColumnCount(); x++) {
			tableFormulas.getColumnModel().getColumn(x).setCellRenderer(centerRenderer);
			}
		
		tableFormulas.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableFormulas.setBounds(22, 88, 484, 282);
		panelFormulas.remove(tableFormulas);
		panelFormulas.add(tableFormulas);
	}
}