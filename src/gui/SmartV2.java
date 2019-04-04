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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SmartV2 extends JFrame {
	private JTextField textField;
	private JTextField textField_1;
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
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		tabbedPane.addTab("Formulas", null, panel, null);
		panel.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(44, 46, 76, 22);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("State Variable");
		lblNewLabel_1.setBounds(44, 24, 91, 16);
		panel.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(179, 46, 320, 22);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblDifferentialEquation = new JLabel("Differential equation");
		lblDifferentialEquation.setBounds(179, 24, 228, 16);
		panel.add(lblDifferentialEquation);
		
		JLabel label = new JLabel("/dt = ");
		label.setBounds(132, 49, 62, 16);
		panel.add(label);
		
		JLabel lblDdt = new JLabel("d ");
		lblDdt.setBounds(22, 49, 17, 16);
		panel.add(lblDdt);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Model.setFormula();
				//Model.setState();
			}
		});
		btnAdd.setBounds(520, 45, 66, 25);
		panel.add(btnAdd);
		
		Object rowData[][] = { { "Row1-Column1", "Row1-Column2"},
                { "Row2-Column1", "Row2-Column2"} };
		Object columnNames[] = { "Column One", "Column Two"};
		tableFormulas = new JTable(rowData,columnNames);
		tableFormulas.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableFormulas.setBounds(22, 88, 484, 282);
		
		panel.add(tableFormulas);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Parameters", null, panel_1, null);
		panel_1.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setBounds(0, 0, 602, 370);
		panel_1.add(panel_2);
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
		
		JLabel lblNewLabel = new JLabel("New label");
		
		
	}
}
