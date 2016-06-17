package com.mohammadazadeh.openWorld;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class OWFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	final private int width = 1024;
	final private int height = 576;
	
	private OWObjectRepository repository;
	
	private JPanel contentPane;
	private OWView view;
	private OWMenuPanel menuPanel;

	/**
	 * Create the frame.
	 */
	public OWFrame(OWObjectRepository repository) {
		super("Open World!");
		
		this.repository = repository;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setLocationRelativeTo(null);
		setVisible(true);
		
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		view = new OWView(this.repository);
		view.setBounds(0, 0, (width + height) / 2, height);
		contentPane.add(view);
		
		menuPanel = new OWMenuPanel(this.repository);
		menuPanel.setBounds((width + height) / 2, 0, (width - height) / 2, height);
		contentPane.add(menuPanel);
		
		view.init();
		repaint();
	}
	
	@Override
	public void repaint()
	{
		view.repaint();
		menuPanel.repaint();
		super.repaint();
	}
	
	public void init()
	{
		menuPanel.init();
	}
}
