package com.mohammadazadeh.openWorld;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class OWFrame extends JFrame implements KeyListener {

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
		view.addKeyListener(this);
		contentPane.add(view);
		
		menuPanel = new OWMenuPanel(view);
		menuPanel.setBounds((width + height) / 2, 0, (width - height) / 2, height);
		contentPane.add(menuPanel);
		
		view.init();
		repaint();
	}

	public void init()
	{
		menuPanel.init();
	}
	
	@Override
	public void repaint()
	{
		view.repaint();
		menuPanel.repaint();
		super.repaint();
	}

	@Override
	public void keyPressed(KeyEvent event) {
		switch (event.getKeyCode())
		{
		case KeyEvent.VK_UP:
			view.selectedCamera.rotationTeta[1]++;
			break;
		case KeyEvent.VK_DOWN:
			view.selectedCamera.rotationTeta[1]--;
			break;
		case KeyEvent.VK_LEFT:
			view.selectedCamera.rotationTeta[0]++;
			break;
		case KeyEvent.VK_RIGHT:
			view.selectedCamera.rotationTeta[0]--;
			break;
		case KeyEvent.VK_MINUS:
			view.selectedCamera.position[0]++;
			break;
		case KeyEvent.VK_EQUALS:
			view.selectedCamera.position[0]--;
			break;
		}
		view.repaint();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
