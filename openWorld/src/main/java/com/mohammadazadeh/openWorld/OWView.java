package com.mohammadazadeh.openWorld;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.FloatBuffer;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JPanel;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.PMVMatrix;

public class OWView extends JPanel implements GLEventListener, KeyListener{

	private static final long serialVersionUID = 1L;
	
	private OWObjectRepository repository;
	private OWObjectCamera cameraFreeView;
	public  OWObjectCamera selectedCamera;
	private PMVMatrix pmvMatrix;
	private float[] cameraEye;
	private float[] cameraCenter;
	
	private GLCanvas glCanvas;
	private FPSAnimator animator;
	// private Queue<KeyEvent> keyEvents;
	/**
	 * Create the panel.
	 */
	public OWView(OWObjectRepository repository) {
		cameraFreeView = new OWObjectCamera("Free View");
		cameraFreeView.position = new float[3];
		cameraFreeView.rotationTeta = new float[3];
		cameraFreeView.position[0] = 10;
		cameraFreeView.position[1] = 4.5f;
		cameraFreeView.position[2] = 10;
		cameraFreeView.rotationTeta[0] = 45;
		this.repository = repository;
		this.repository.cameraList.add(cameraFreeView);
		selectedCamera = cameraFreeView;

		pmvMatrix = new PMVMatrix();
		cameraEye = new float[3];
		cameraCenter = new float[3];
		
		setBackground(new Color(1f, 1f, 0f));
		
		GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);
		capabilities.setDoubleBuffered(true);
		glCanvas = new GLCanvas(capabilities);
		glCanvas.addGLEventListener(this);
		add(glCanvas);
		glCanvas.display();
		
		// keyEvents = new LinkedList<KeyEvent>();
	}

	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
    	GLU glu = GLU.createGLU(gl);
    	
    	System.out.println("GLCanvas Initializartion");

    	gl.glMatrixMode(GL2.GL_PROJECTION);
    	gl.glLoadIdentity();
    	Dimension dim = glCanvas.getSize();
    	glu.gluPerspective(60.0f, dim.getWidth() / dim.getHeight(), 0.1f,1000.0f);
    	
        gl.glMatrixMode(GL2.GL_MODELVIEW);

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glClearColor(0.392f, 0.584f, 0.929f, 1.0f);
        gl.glLoadIdentity();
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glEnable(GL2.GL_DOUBLEBUFFER);
        
        animator = new FPSAnimator(drawable, 5);
        //animator.setRunAsFastAsPossible(true);
	}

	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
	}

	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glClearColor(0.392f, 0.584f, 0.929f, 1.0f);
		gl.glLoadIdentity();

		/*
		KeyEvent keyEvent;
    	while ((keyEvent = keyEvents.poll()) != null)
    	{
    		doKeyEvent(keyEvent);
    	}
		*/
		doCamera(drawable);

		try{
			if (repository.avatar != null && repository.avatarHolder != null)
			{
				repository.avatarHolder.position[0] = repository.map.vertices.get(repository.avatarPosIndex)[0];
				repository.avatarHolder.position[2] = repository.map.vertices.get(repository.avatarPosIndex)[2];
				repository.avatar.position[2] = repository.map.vertices.get(repository.avatarPosIndex)[1];
			}
		}
		catch (Exception ex)
		{
			
		}
		repository.display(drawable);
		if (! repository.lighting)
			gl.glDisable(GL2.GL_LIGHTING);
		
		if (repository.objectList.size() == 0)
		{
			gl.glScaled(3, 3, 3);
			gl.glBegin(GL2.GL_POLYGON);
			{
				gl.glColor3f(1f, 1f, 1f);
				gl.glVertex3f(0, 0, 0);
				gl.glColor3f(1f, 0f, 0f);
				gl.glVertex3f(1, 0, 0);
				gl.glColor3f(0f, 1f, 0f);
				gl.glVertex3f(0,  1, 0);
			}
			gl.glBegin(GL2.GL_POLYGON);
			{
				gl.glColor3f(1f, 1f, 1f);
				gl.glVertex3f(0, 0, 0);
				gl.glColor3f(1f, 0f, 0f);
				gl.glVertex3f(1, 0, 0);
				gl.glColor3f(0f, 0f, 1f);
				gl.glVertex3f(0,  0, 1);
			}
			gl.glEnd();
			gl.glBegin(GL2.GL_POLYGON);
			{
				gl.glColor3f(1f, 1f, 1f);
				gl.glVertex3f(0, 0, 0);
				gl.glColor3f(0f, 1f, 0f);
				gl.glVertex3f(0, 1, 0);
				gl.glColor3f(0f, 0f, 1f);
				gl.glVertex3f(0,  0, 1);
			}
			gl.glEnd();
		}
		gl.glFlush();
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		
	}

	public void init()
	{
		Dimension dim = getSize();
		glCanvas.setBounds(0, 0, (int) dim.getWidth(), (int) dim.getHeight());
	}
	
	public OWObjectRepository getRepository()
	{
		return repository;
	}
	
	@Override
	public void repaint()
	{
		// TODO: Force OpenGL to repaint.
		if (glCanvas != null)
			glCanvas.display();
		super.repaint();
	}
	
	@Override
	public void addKeyListener(KeyListener listener)
	{
		glCanvas.addKeyListener(listener);
	}
	
	private void doCamera(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
    	GLU glu = GLU.createGLU(gl);
    	
		pmvMatrix.glLoadIdentity();
		doCameraHandler(selectedCamera);
		FloatBuffer buff = FloatBuffer.allocate(16);
		pmvMatrix.glGetFloatv(GL2.GL_MODELVIEW_MATRIX, buff );
		cameraEye[0] = buff.get(12) / buff.get(15);
		cameraEye[1] = buff.get(13) / buff.get(15);
		cameraEye[2] = buff.get(14) / buff.get(15);
		pmvMatrix.glTranslatef(0, 0, -1);
		pmvMatrix.glGetFloatv(GL2.GL_MODELVIEW_MATRIX, buff );
		cameraCenter[0] = buff.get(12) / buff.get(15);
		cameraCenter[1] = buff.get(13) / buff.get(15);
		cameraCenter[2] = buff.get(14) / buff.get(15);
		
		glu.gluLookAt(
				cameraEye[0], cameraEye[1], cameraEye[2],
				cameraCenter[0], cameraCenter[1], cameraCenter[2],
				0, 1, 0);
	}
	
	private void doCameraHandler(OWObject object)
	{
		if (object == null)
			return;
		if (object.parentObject != null)
		{
			doCameraHandler(object.parentObject);
		}
		
		if (object.position != null)
			pmvMatrix.glTranslatef(object.position[0], object.position[1], object.position[2]);
		if (object.rotation != null)
			pmvMatrix.glRotatef(object.rotation[3], object.rotation[0], object.rotation[1], object.rotation[2]);
		if (object.rotationTeta != null)
		{
			pmvMatrix.glRotatef(object.rotationTeta[0], 1, 0, 0);
			pmvMatrix.glRotatef(object.rotationTeta[1], 0, 1, 0);
			pmvMatrix.glRotatef(object.rotationTeta[2], 0, 0, 1);
		}
		if (object.scale != null)
			pmvMatrix.glScalef(object.scale[0], object.scale[1], object.scale[2]);
	}

	public void startAnimator() {
		animator.start();
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		// keyEvents.add(event);
		doKeyEvent(event);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private void doKeyEvent(KeyEvent event)
	{
		switch (event.getKeyCode())
		{
		case KeyEvent.VK_UP:
			if (repository.avatarPosIndex < repository.map.length * repository.map.width - repository.map.length)
				repository.avatarPosIndex += repository.map.length;
			break;
		case KeyEvent.VK_DOWN:
			if (repository.avatarPosIndex >= repository.map.length)
				repository.avatarPosIndex -= repository.map.length;
			break;
		case KeyEvent.VK_LEFT:
			if ((repository.avatarPosIndex > 0) && (repository.avatarPosIndex % repository.map.length != 0))
				repository.avatarPosIndex --;
			break;
		case KeyEvent.VK_RIGHT:
			if ((repository.avatarPosIndex < repository.map.length * repository.map.width - 1) && ((repository.avatarPosIndex + 1) % repository.map.length != 0))
				repository.avatarPosIndex ++;
			break;
		case KeyEvent.VK_MINUS:
			selectedCamera.position[0]++;
			break;
		case KeyEvent.VK_EQUALS:
			selectedCamera.position[0]--;
			break;
		}
		//repaint();
	}
}
