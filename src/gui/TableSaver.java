package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JRadioButton;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import smrt2.CSVWriter;
import smrt2.SmartTableModel;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TableSaver extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldCustom;
	private JRadioButton rdBtnComma;
	private JRadioButton rdBtnTab;
	private JRadioButton rdBtnSemicolon;
	private JRadioButton rdBtnCustom;
	private JButton btnOk;
	private SmartTableModel tableModel;
//
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					SeperatorSelector frame = new SeperatorSelector();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//
//	/**
//	 * Create the frame.
//	 */
	public TableSaver(SmartTableModel tableModel) {
		this.tableModel = tableModel;
		buildInterface();
	}
		
	public void buildInterface() {
		setResizable(false);
		setVisible(true);
		setTitle("Select table seperator");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 227, 215);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblSeperator = new JLabel("Choose youre seperator");
		GridBagConstraints gbc_lblSeperator = new GridBagConstraints();
		gbc_lblSeperator.gridwidth = 2;
		gbc_lblSeperator.insets = new Insets(0, 0, 5, 0);
		gbc_lblSeperator.gridx = 0;
		gbc_lblSeperator.gridy = 0;
		contentPane.add(lblSeperator, gbc_lblSeperator);
		
		rdBtnComma = new JRadioButton("Comma", true);
		rdBtnComma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdBtnComma.isSelected()) {
					rdBtnSemicolon.setSelected(false);
					rdBtnTab.setSelected(false);
					rdBtnCustom.setSelected(false);
				}
				else {
					rdBtnComma.setSelected(true);
				}
			}
		});
		GridBagConstraints gbc_rdBtnComma = new GridBagConstraints();
		gbc_rdBtnComma.anchor = GridBagConstraints.WEST;
		gbc_rdBtnComma.insets = new Insets(0, 0, 5, 5);
		gbc_rdBtnComma.gridx = 0;
		gbc_rdBtnComma.gridy = 1;
		contentPane.add(rdBtnComma, gbc_rdBtnComma);
		
		rdBtnTab = new JRadioButton("Tab");
		rdBtnTab.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdBtnTab.isSelected()) {
					rdBtnSemicolon.setSelected(false);
					rdBtnComma.setSelected(false);
					rdBtnCustom.setSelected(false);
				}
				else {
					rdBtnTab.setSelected(true);
				}
			}
		});
		GridBagConstraints gbc_rdBtnTab = new GridBagConstraints();
		gbc_rdBtnTab.anchor = GridBagConstraints.WEST;
		gbc_rdBtnTab.insets = new Insets(0, 0, 5, 5);
		gbc_rdBtnTab.gridx = 0;
		gbc_rdBtnTab.gridy = 2;
		contentPane.add(rdBtnTab, gbc_rdBtnTab);
		
		rdBtnSemicolon = new JRadioButton("Semicolon");
		rdBtnSemicolon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdBtnSemicolon.isSelected()) {
					rdBtnComma.setSelected(false);
					rdBtnTab.setSelected(false);
					rdBtnCustom.setSelected(false);
				}
				else {
					rdBtnSemicolon.setSelected(true);
				}
			}
		});
		GridBagConstraints gbc_rdBtnSemicolon = new GridBagConstraints();
		gbc_rdBtnSemicolon.anchor = GridBagConstraints.WEST;
		gbc_rdBtnSemicolon.insets = new Insets(0, 0, 5, 5);
		gbc_rdBtnSemicolon.gridx = 0;
		gbc_rdBtnSemicolon.gridy = 3;
		contentPane.add(rdBtnSemicolon, gbc_rdBtnSemicolon);
		
		rdBtnCustom = new JRadioButton("Custom:");
		rdBtnCustom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdBtnCustom.isSelected()) {
					rdBtnComma.setSelected(false);
					rdBtnTab.setSelected(false);
					rdBtnSemicolon.setSelected(false);
				}
				else {
					rdBtnCustom.setSelected(true);
				}
			}
		});
		GridBagConstraints gbc_rdBtnCustom = new GridBagConstraints();
		gbc_rdBtnCustom.anchor = GridBagConstraints.WEST;
		gbc_rdBtnCustom.insets = new Insets(0, 0, 5, 5);
		gbc_rdBtnCustom.gridx = 0;
		gbc_rdBtnCustom.gridy = 4;
		contentPane.add(rdBtnCustom, gbc_rdBtnCustom);
		
		textFieldCustom = new JTextField();
		GridBagConstraints gbc_textFieldCustom = new GridBagConstraints();
		gbc_textFieldCustom.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldCustom.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldCustom.gridx = 1;
		gbc_textFieldCustom.gridy = 4;
		contentPane.add(textFieldCustom, gbc_textFieldCustom);
		textFieldCustom.setColumns(10);
		
		btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmSeperator();
			}
		});
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnOk.gridwidth = 2;
		gbc_btnOk.insets = new Insets(5, 60, 0, 60);
		gbc_btnOk.gridx = 0;
		gbc_btnOk.gridy = 5;
		contentPane.add(btnOk, gbc_btnOk);
	}

	protected void confirmSeperator() {
		String seperator = "";
		if (rdBtnComma.isSelected()) {
			seperator = ",";
		}
		else if (rdBtnTab.isSelected()) {
			seperator = "\t";
		}
		else if (rdBtnSemicolon.isSelected()) {
			seperator = ";";
		}
		else if (rdBtnCustom.isSelected()) {
			if (textFieldCustom.getText().length() > 0 && textFieldCustom.getText().length() < 5) {
				seperator = textFieldCustom.getText();
			}
			else {
				JOptionPane.showConfirmDialog(null,"Valid seperators between 1-5 characters",
						"Warning!!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		dispose();	
		String filePath = null;
		if (seperator == ",") {
			filePath = FileChooser.save("csv","csv", tableModel.getName());
		} else if(seperator == "\t") {
			filePath = FileChooser.save("tsv","tsv", tableModel.getName());
		} else {
			filePath = FileChooser.save("txt","txt", tableModel.getName());
		}
		if (filePath != null) {
			new CSVWriter(tableModel,filePath, seperator);
		}

	}
	

}
