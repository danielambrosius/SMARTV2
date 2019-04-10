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

import java.awt.Color;

public class ExperimentView extends JFrame {

	private JPanel contentPane;
	private JScrollPane scrollPaneParameters;
	private JTable parameterTable;
	private JScrollPane scrollPaneStates;
	private JTable stateTable;
	private final String[] paramaterColumnNames = {"Parameter","value"};
	private final String[] stateColumnNames = {"State","value"};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExperimentView frame = new ExperimentView();
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
	public ExperimentView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 555, 414);
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
		
		Object rowData[][] = { { "example state name", "example formula"} };
		
		
		// Creating table from default table model
		TableModel tableParamModel = new DefaultTableModel(rowData, paramaterColumnNames);
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
		
		
	}

}
