package gui;

import smrt2.CSVWriter;
import smrt2.GraphThread;
import smrt2.ProgressThread;
import smrt2.SmartTableModel;
import smrt2.SolverThread;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import java.awt.GridLayout;
import javax.swing.JTable;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
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
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String filePath = FileChooser.save("csv","csv");
				if (filePath != null) {
					new CSVWriter(tableModel,filePath);
				}
				
			}
		});
		mnFile.add(mntmSave);
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
	
	public void buildProgressBar(double[] timeValues) {
		ProgressThread pt = new ProgressThread(progressBar, this.tableModel, timeValues);
		pt.start();
	}

	public void buildGraph(SolverThread st) {
		GraphThread t = new GraphThread(st, tabbedPane, this.tableModel);
		t.start();
	}
	
	public void buildTable(){
		this.table.setModel(this.tableModel);
	}

}
