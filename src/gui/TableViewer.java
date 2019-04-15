package gui;

import gui.GraphBuilder;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.StackPane;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
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
	//static Double[][] data = {{1.0,5.0},{3.0,8.0}};
	static Object[] titles = {"first", "not first"};

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//
//				
//				try {
//					TableViewer frame = new TableViewer(data, titles);
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public TableViewer(Object[][] dataToDisplay, Object[] header) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);
		
		table = new JTable();
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setDataVector(dataToDisplay, header);
		table.setModel(tableModel);		
		
		JScrollPane tablePane = new JScrollPane(table);
		tabbedPane.addTab("Table", null, tablePane, null);

		JFXPanel graphPane = new JFXPanel();
		tabbedPane.addTab("Graph", null, graphPane, null);

		
		StackPane pane = new StackPane();
		GraphBuilder GB = new GraphBuilder(1, (Double[][]) dataToDisplay);
		LineChart<Number, Number> lineChart = GB.start();
			    
	    pane.getChildren().add(lineChart);
		Scene scene = new Scene(pane);
		graphPane.setScene(scene);
		
	}

}
