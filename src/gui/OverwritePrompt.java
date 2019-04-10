package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

public class OverwritePrompt extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OverwritePrompt frame = new OverwritePrompt();
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
	public OverwritePrompt() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 391, 205);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblFileAlreadyExists = new JLabel("<html>File already exists.<br>Do you want to replace it?");
		lblFileAlreadyExists.setBounds(99, 23, 166, 67);
		contentPane.add(lblFileAlreadyExists);
		
		Icon myPicture = new ImageIcon("//GroupG//data//warning.png");
		JLabel warningIcon = new JLabel();
		warningIcon.setIcon(new ImageIcon("C:\\Users\\shash\\OneDrive - WageningenUR\\Software Engineering\\Java projects\\GroupG\\data\\Naamloos.png"));
		warningIcon.setBounds(0, 23, 87, 111);
		contentPane.add(warningIcon);
		
		JButton btnYes = new JButton("Yes");
		btnYes.setBounds(109, 103, 97, 25);
		contentPane.add(btnYes);
		
		JButton btnNo = new JButton("No");
		btnNo.setBounds(218, 103, 97, 25);
		contentPane.add(btnNo);
		

	}
}
