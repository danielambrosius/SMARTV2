package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.SwingConstants;

public class HelpWindow extends JFrame {

	private JPanel contentPane;

	public HelpWindow(String helpText) {
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JLabel textLabel = new JLabel("");
		textLabel.setVerticalAlignment(SwingConstants.TOP);
		textLabel.setOpaque(true);
		textLabel.setBackground(Color.WHITE);
		scrollPane.setViewportView(textLabel);
		textLabel.setText(helpText);
	}

}
