package com.mohammadazadeh.openWorld;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class OWFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	final private int width = 1024;
	final private int height = 576;
	
	private JPanel contentPane;
	private OWView view;

	/**
	 * Create the frame.
	 */
	public OWFrame() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setLocationRelativeTo(null);
		setVisible(true);
		
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		view = new OWView();
		view.setBounds(0, 0, (width + height) / 2, height);
		contentPane.add(view);
		view.init();
		//setContentPane(view);
		repaint();
	}

}
