package gui;

import gui.GraphBuilder;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.StackPane;
import smrt2.App;
import smrt2.GraphThread;
import smrt2.SmartTableModel;
import smrt2.SolverThread;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JMenuBar;
import java.awt.GridLayout;
import javax.swing.JTable;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;


public class TableViewer extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTabbedPane tabbedPane;
	private SmartTableModel tableModel;
	//static Double[][] data = {{1.0,5.0},{3.0,8.0}};
	static Object[] titles = {"first", "not first"};

	public TableViewer(SmartTableModel tableModel) {
		
		this.tableModel = tableModel;
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);
		
		table = new JTable();
		
		table.setModel(tableModel);
		
		JScrollPane tablePane = new JScrollPane(table);
		tabbedPane.addTab("Table", null, tablePane, null);
		setVisible(true);		
	}

	public void buildGraph(SolverThread st) {
		GraphThread t = new GraphThread(st, tabbedPane, this.tableModel);
		t.start();
	}
	
	public void buildTable(){
		this.table.setModel(this.tableModel);
	}

}
