package gui;

import smrt2.TableWriter;
import smrt2.SmartTableModel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.StackPane;

import javax.swing.JMenuBar;
import java.awt.GridLayout;
import javax.swing.JTable;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JProgressBar;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;


public class TableViewer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTabbedPane tabbedPane;
	private SmartTableModel tableModel;
	private JProgressBar progressBar;
	static Object[] titles = {"first", "not first"};

	public TableViewer(SmartTableModel tableModel) {
		this.tableModel = tableModel;
		this.setTitle("SmartV2 - " + this.tableModel.getName());
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new TableSaver(tableModel);
			}
		});
		mnFile.add(mntmSave);
		
		JMenu mnPlot = new JMenu("Plot");
		menuBar.add(mnPlot);
		
		JMenuItem mntmPhasePlane = new JMenuItem("phase plane");
		mntmPhasePlane.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PhasePlaneSelector pps = new PhasePlaneSelector(tableModel);
				
				int result = JOptionPane.showConfirmDialog(null, pps, "hello", JOptionPane.OK_CANCEL_OPTION);
				
				if(result == JOptionPane.OK_OPTION) {
					addCustomGraph(pps.getXYnames());
					
				}
				
				//pps.setVisible(true);
			}
		});
		mnPlot.add(mntmPhasePlane);
		
		JMenuItem mntmTimePlot = new JMenuItem("time plot");
		mntmTimePlot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MultipleStateSelector mss = new MultipleStateSelector(tableModel);
				
				int result = JOptionPane.showConfirmDialog(null, mss, "hello", JOptionPane.OK_CANCEL_OPTION);
				
				if(result == JOptionPane.OK_OPTION) {
					addMultipleStateGraph(mss.getStateNames());
					
				}

			}
		});
		mnPlot.add(mntmTimePlot);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		contentPane.setLayout(gbl_contentPane);
		
		progressBar = new JProgressBar();
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.fill = GridBagConstraints.BOTH;
		gbc_progressBar.weightx = 1.0;
		gbc_progressBar.insets = new Insets(0, 50, 0, 50);
		gbc_progressBar.gridx = 0;
		gbc_progressBar.gridy = 0;
		contentPane.add(progressBar, gbc_progressBar);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.weighty = 1.0;
		gbc_panel.weightx = 1.0;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		contentPane.add(panel, gbc_panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panel.add(tabbedPane);
		
		table = new JTable();
		
		table.setModel(tableModel);
		
		JScrollPane tablePane = new JScrollPane(table);
		tabbedPane.addTab("Table", null, tablePane, null);
		setVisible(true);		
	}
	
	protected void buildProgressBar(double[] timeValues) {
		ProgressThread pt = new ProgressThread(progressBar, this.tableModel, timeValues);
		pt.start();
	}

	protected void buildGraph(SolverThread st) {
		GraphThread t = new GraphThread(st, tabbedPane, this.tableModel);
		t.start();
	}
	
	protected void buildTable(){
		this.table.setModel(this.tableModel);
	}
	
	
	private void addCustomGraph(int[] xy) {
		JFXPanel graphPane = new JFXPanel();
		
		String plotName = tableModel.getColumnName(xy[0]) + " " + tableModel.getColumnName(xy[1]);
		tabbedPane.addTab(plotName, null, graphPane, null);
		StackPane pane = new StackPane();
		GraphBuilder GB = new GraphBuilder(tableModel, xy, plotName);
		LineChart<Number, Number> lineChart = GB.start();
			    
	    pane.getChildren().add(lineChart);
		Scene scene = new Scene(pane);
		graphPane.setScene(scene);
	}
	
	private void addMultipleStateGraph(int[] statesToPlot) {
		JFXPanel graphPane = new JFXPanel();
		
		String plotName = "";
		for(int i : statesToPlot) {
			plotName += tableModel.getColumnName(i)+" ";
		}		

		
		tabbedPane.addTab(plotName, null, graphPane, null);
		StackPane pane = new StackPane();
		GraphBuilder GB = new GraphBuilder(tableModel, statesToPlot);
		LineChart<Number, Number> lineChart = GB.start();
			    
	    pane.getChildren().add(lineChart);
		Scene scene = new Scene(pane);
		graphPane.setScene(scene);
	}
	

}
