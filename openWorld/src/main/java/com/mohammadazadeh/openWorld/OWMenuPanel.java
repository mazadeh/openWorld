package com.mohammadazadeh.openWorld;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;

public class OWMenuPanel extends JPanel implements ActionListener, ChangeListener{

	private static final long serialVersionUID = 1L;

	private OWObjectRepository repository;
	
	private JLabel cameraLabel;
	private JComboBox<Object> cameraBox;

	private JLabel objectLabel;
	private JComboBox<Object> objectBox;
	
	private JLabel locationLabel;
	private JSpinner locationX;
	private JSpinner locationY;
	private JSpinner locationZ;
	
	private JLabel rotationLabel;
	private JSpinner rotationX;
	private JSpinner rotationY;
	private JSpinner rotationZ;
	/**
	 * Create the panel.
	 */
	public OWMenuPanel(OWObjectRepository repository) {
		this.repository = repository;
		setLayout(null);
		setBackground(new Color(0f, 1f, 1f));
		
		cameraLabel = new JLabel("Camera");
		cameraLabel.setBounds(10, 15, 100, 22);
		
		cameraBox = new JComboBox<Object>();
		cameraBox.setBounds(10, 40, 180, 22);
		cameraBox.setActionCommand("camera list");
		cameraBox.addActionListener(this);

		objectLabel = new JLabel("Object List");
		objectLabel.setBounds(10, 75, 100, 22);
		
		objectBox = new JComboBox<Object>();
		objectBox.setBounds(10, 100, 180, 22);
		objectBox.setActionCommand("object list");
		objectBox.addActionListener(this);

		locationLabel = new JLabel("Location");
		locationLabel.setBounds(10, 135, 100, 22);

		locationX = new JSpinner();
		locationX.setName("Location X");
		locationX.setBounds(10, 160, 100, 22);
		locationX.addChangeListener(this);

		locationY = new JSpinner();
		locationY.setName("Location Y");
		locationY.setBounds(10, 190, 100, 22);
		locationY.addChangeListener(this);

		locationZ = new JSpinner();
		locationZ.setName("Location Z");
		locationZ.setBounds(10, 220, 100, 22);
		locationZ.addChangeListener(this);
		
		
		rotationLabel = new JLabel("Rotation");
		rotationLabel.setBounds(10, 255, 100, 22);

		rotationX = new JSpinner();
		rotationX.setName("Rotation X");
		rotationX.setBounds(10, 280, 100, 22);
		rotationX.addChangeListener(this);

		rotationY = new JSpinner();
		rotationY.setName("Rotation Y");
		rotationY.setBounds(10, 310, 100, 22);
		rotationY.addChangeListener(this);

		rotationZ = new JSpinner();
		rotationZ.setName("Rotation Z");
		rotationZ.setBounds(10, 340, 100, 22);
		rotationZ.addChangeListener(this);
		
		add(cameraLabel);
		add(cameraBox);
		add(objectLabel);
		add(objectBox);
		add(locationLabel);
		add(locationX);
		add(locationY);
		add(locationZ);
		add(rotationLabel);
		add(rotationX);
		add(rotationY);
		add(rotationZ);
	}
	
	public void init()
	{
		for (OWObjectCamera camera : repository.cameraList)
		{
			cameraBox.addItem(camera.name);
		}
		for (OWObject object : repository.objectList)
		{
			objectBox.addItem(object.name);
		}
		repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
