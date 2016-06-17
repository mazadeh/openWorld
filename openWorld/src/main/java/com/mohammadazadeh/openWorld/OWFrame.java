package com.mohammadazadeh.openWorld;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class OWFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	final private int width = 1024;
	final private int height = 576;
	
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public OWFrame() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setLocationRelativeTo(null);
		setVisible(true);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
