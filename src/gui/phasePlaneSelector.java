package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import smrt2.SmartTableModel;

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JComboBox;



public class phasePlaneSelector extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<String> comboBoxX;
	private JComboBox<String> comboBoxY;


	/**
	 * Create the dialog.
	 */
	public phasePlaneSelector(SmartTableModel tableModel) {

		setBounds(100, 100, 450, 300);

		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};

		{
			JLabel lblX = new JLabel("x");
			GridBagConstraints gbc_lblX = new GridBagConstraints();
			gbc_lblX.anchor = GridBagConstraints.EAST;
			gbc_lblX.insets = new Insets(0, 0, 5, 5);
			gbc_lblX.gridx = 0;
			gbc_lblX.gridy = 1;
			this.add(lblX, gbc_lblX);
		}
		
		
		
		{
			comboBoxX = new JComboBox<String>(tableModel.getColumnNames());
			GridBagConstraints gbc_comboBoxX = new GridBagConstraints();
			gbc_comboBoxX.insets = new Insets(0, 0, 5, 0);
			gbc_comboBoxX.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBoxX.gridx = 1;
			gbc_comboBoxX.gridy = 1;
			
			this.add(comboBoxX, gbc_comboBoxX);
		}
		{
			JLabel lblY = new JLabel("y");
			GridBagConstraints gbc_lblY = new GridBagConstraints();
			gbc_lblY.anchor = GridBagConstraints.EAST;
			gbc_lblY.insets = new Insets(0, 0, 0, 5);
			gbc_lblY.gridx = 0;
			gbc_lblY.gridy = 2;
			this.add(lblY, gbc_lblY);
		}
		{
			comboBoxY = new JComboBox<String>(tableModel.getColumnNames());
			GridBagConstraints gbc_comboBoxY = new GridBagConstraints();
			gbc_comboBoxY.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBoxY.gridx = 1;
			gbc_comboBoxY.gridy = 2;
			this.add(comboBoxY, gbc_comboBoxY);
		}

	}
	
	public int[] getXYnames() {
		int[] selected = {comboBoxX.getSelectedIndex(), comboBoxY.getSelectedIndex()};
		
		return selected;
		
	}

}
