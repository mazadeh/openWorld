package com.mohammadazadeh.openWorld;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map.Entry;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class OWMenuPanel extends JPanel implements ActionListener, ChangeListener, TreeSelectionListener{

	private static final long serialVersionUID = 1L;

	private OWObjectRepository repository;
	private OWObject selectedObject;
	private OWView view;
	
	private JLabel cameraLabel;
	private JComboBox<Object> cameraBox;

	private JLabel objectLabel;
	private JScrollPane objectScrollPane;
	private JTree objectTree;

	JTabbedPane settingPane;
	private JPanel positionPanel;
	private JPanel lightPanel;
	private JPanel cameraPanel;
	
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
	public OWMenuPanel(OWView view) {
		setLayout(null);
		setBackground(new Color(0f, 1f, 1f));
		
		this.view = view;
		repository = view.getRepository();
		
		cameraLabel = new JLabel("Select View");
		cameraLabel.setBounds(10, 10, 100, 22);
		
		cameraBox = new JComboBox<Object>();
		cameraBox.setBounds(10, 35, 180, 22);
		cameraBox.setActionCommand("camera list");
		cameraBox.addActionListener(this);

		objectLabel = new JLabel("Object List");
		objectLabel.setBounds(10, 65, 100, 22);
		
		objectScrollPane = new JScrollPane();
		objectScrollPane.setBounds(10, 90, 180, 100);
		
		objectTree = new JTree();
		objectScrollPane.setViewportView(objectTree);
		objectTree.setModel(null);
		objectTree.addTreeSelectionListener(this);
		
		settingPane = new JTabbedPane(JTabbedPane.TOP);
		settingPane.setBounds(10, 200, 180, 360);
		{
			positionPanel = new JPanel();
			positionPanel.setLayout(null);
			{
				locationLabel = new JLabel("Location");
				locationLabel.setBounds(5, 5, 100, 22);
				locationX = new JSpinner();
				locationX.setName("Location X");
				locationX.addChangeListener(this);
				locationX.setBounds(5, 30, 100, 22);
				locationY = new JSpinner();
				locationY.setName("Location Y");
				locationY.addChangeListener(this);
				locationY.setBounds(5, 60, 100, 22);
				locationZ = new JSpinner();
				locationZ.setName("Location Z");
				locationZ.addChangeListener(this);
				locationZ.setBounds(5, 90, 100, 22);
				
				rotationLabel = new JLabel("Rotation");
				rotationLabel.setBounds(5, 115, 100, 22);
				rotationX = new JSpinner();
				rotationX.setName("Rotation X");
				rotationX.addChangeListener(this);
				rotationX.setBounds(5, 140, 100, 22);
				rotationY = new JSpinner();
				rotationY.setName("Rotation Y");
				rotationY.addChangeListener(this);
				rotationY.setBounds(5, 170, 100, 22);
				rotationZ = new JSpinner();
				rotationZ.setName("Rotation Z");
				rotationZ.addChangeListener(this);
				rotationZ.setBounds(5, 200, 100, 22);
				
				positionPanel.add(locationLabel);
				positionPanel.add(locationX);
				positionPanel.add(locationY);
				positionPanel.add(locationZ);
				positionPanel.add(rotationLabel);
				positionPanel.add(rotationX);
				positionPanel.add(rotationY);
				positionPanel.add(rotationZ);
			}
			lightPanel = new JPanel();
			lightPanel.setLayout(null);
			{
				
			}
			cameraPanel = new JPanel();
			cameraPanel.setLayout(null);
			{
				
			}

			settingPane.addTab("Position", null, positionPanel, null);
			settingPane.addTab("Lighting", null, lightPanel, null);
			settingPane.addTab("Camera", null, cameraPanel, null);
		}
		
		add(cameraLabel);
		add(cameraBox);
		add(objectLabel);
		add(objectScrollPane);
		add(settingPane);
	}
	
	public void init()
	{
		for (OWObjectCamera camera : repository.cameraList)
		{
			cameraBox.addItem(camera.name);
		}
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Objects");
		for (Entry<String, OWObject> obj : repository.objectList.entrySet())
		{
			addObjectTree(obj.getValue(), root);
		}
		objectTree.setModel(new DefaultTreeModel(root));
		repaint();
	}
	
	public void settingPaneInit()
	{
		if (selectedObject.position != null)
		{
			locationX.setEnabled(true);
			locationY.setEnabled(true);
			locationZ.setEnabled(true);
			
			locationX.setValue(selectedObject.position[0]);
			locationX.setValue(selectedObject.position[1]);
			locationX.setValue(selectedObject.position[2]);
		}
		else
		{
			locationX.setEnabled(false);
			locationY.setEnabled(false);
			locationZ.setEnabled(false);
		}
		if (selectedObject.rotation != null)
		{
			rotationX.setEnabled(true);
			rotationY.setEnabled(true);
			rotationZ.setEnabled(true);
			
			rotationX.setValue(selectedObject.rotation[0]);
			rotationX.setValue(selectedObject.rotation[1]);
			rotationX.setValue(selectedObject.rotation[2]);
		}
		else
		{
			rotationX.setEnabled(false);
			rotationY.setEnabled(false);
			rotationZ.setEnabled(false);
		}
		repaint();
	}
	
	public void addObjectTree(OWObject object, DefaultMutableTreeNode node)
	{
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(object.name);
		if (object.subObjects != null)
		{
			for (Entry<String, OWObject> obj : object.subObjects.entrySet())
			{
				addObjectTree(obj.getValue(), childNode);
			}
		}
		node.add(childNode);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		if (command.equals("camera list"))
		{
			view.selectedCamera = repository.cameraList.get(cameraBox.getSelectedIndex());
			view.repaint();
		}
	}

	@Override
	public void stateChanged(ChangeEvent event) {
		String sourceName = ((JSpinner) event.getSource()).getName();
		float value;
		
		if (sourceName.equals("Location X") && selectedObject.position != null)
		{
			if (locationX.getValue() instanceof Integer)
				value = (int)locationX.getValue();
			else
				value = (float)locationX.getValue();
			selectedObject.position[0] = value;
		}
		if (sourceName.equals("Location Y") && selectedObject.position != null)
		{
			if (locationY.getValue() instanceof Integer)
				value = (int)locationY.getValue();
			else
				value = (float)locationY.getValue();
			selectedObject.position[1] = value;
		}
		if (sourceName.equals("Location Z") && selectedObject.position != null)
		{
			if (locationZ.getValue() instanceof Integer)
				value = (int)locationZ.getValue();
			else
				value = (float)locationZ.getValue();
			selectedObject.position[2] = value;
		}
		if (sourceName.equals("Rotation X") && selectedObject.rotationTeta != null)
		{
			if (rotationX.getValue() instanceof Integer)
				value = (int)rotationX.getValue();
			else
				value = (float)rotationX.getValue();
			selectedObject.rotationTeta[0] = value;
		}
		if (sourceName.equals("Rotation Y") && selectedObject.rotationTeta != null)
		{
			if (rotationY.getValue() instanceof Integer)
				value = (int)rotationY.getValue();
			else
				value = (float)rotationY.getValue();
			selectedObject.rotationTeta[1] = value;
		}
		if (sourceName.equals("Rotation Z") && selectedObject.rotationTeta != null)
		{
			if (rotationZ.getValue() instanceof Integer)
				value = (int)rotationZ.getValue();
			else
				value = (float)rotationZ.getValue();
			selectedObject.rotationTeta[2] = value;
		}
		view.repaint();
	}

	@Override
	public void valueChanged(TreeSelectionEvent event) {
		Object[] path = event.getPath().getPath();
		if (path.length < 2)
		{
			return;
		}
		OWObject obj = repository.objectList.get(path[1].toString());
		for (int index = 2; index < path.length; index++)
		{
			obj = obj.subObjects.get(path[index].toString());
		}
		selectedObject = obj;
		settingPaneInit();
	}
}
