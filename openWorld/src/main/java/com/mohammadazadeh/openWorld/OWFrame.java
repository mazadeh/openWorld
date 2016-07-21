package com.mohammadazadeh.openWorld;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class OWFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	final private int width = 1024;
	final private int height = 576;
	
	private OWObjectRepository repository;
	private JMenu mnFile;
	private JMenuItem mntmLoadObject;
	private JPanel contentPane;
	private JMenuBar menuBar;
	
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
		
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1024, 25);
		mnFile = new JMenu("File");
		mntmLoadObject = new JMenuItem("Load Object");
		mntmLoadObject.addActionListener(this);
		mntmLoadObject.setActionCommand("Load Object");

		view = new OWView(this.repository);
		view.setBounds(0, 25, 800, 551);
		view.addKeyListener(view);
		
		menuPanel = new OWMenuPanel(view);
		menuPanel.setBounds(800, 25, 224, 551);
		
		mnFile.add(mntmLoadObject);
		menuBar.add(mnFile);
		contentPane.add(menuBar);
		contentPane.add(view);
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
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		switch (command)
		{
		case "Load Object":
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Extensible 3D", "x3d"));
			int returnValue = fileChooser.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				System.out.println(selectedFile.getName());
				OWObjectLoaderX3D objectLoader = new OWObjectLoaderX3D(repository);
				try {
					objectLoader.load(selectedFile);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				init();
				repaint();
	        }
			break;
		}
	}
	
	public void startAnimator()
	{
		view.startAnimator();
	}
}
