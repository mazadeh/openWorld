package com.mohammadazadeh.openWorld;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;

public class OWView extends JPanel implements GLEventListener{

	private static final long serialVersionUID = 1L;
	
	private OWObjectRepository repository;
	
	private GLCanvas glCanvas;
	/**
	 * Create the panel.
	 */
	public OWView(OWObjectRepository repository) {
		this.repository = repository;
		setBackground(new Color(1f, 1f, 0f));
		
		GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);
		capabilities.setDoubleBuffered(true);
		glCanvas = new GLCanvas(capabilities);
		glCanvas.addGLEventListener(this);
		add(glCanvas);
		glCanvas.display();
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
		
	}

	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
	}

	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		//GLU glu = GLU.createGLU(gl);
		
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glClearColor(0.392f, 0.584f, 0.929f, 1.0f);
		gl.glLoadIdentity();
		
		repository.display(drawable);
		
		gl.glBegin(GL2.GL_POLYGON);
		{
			gl.glColor3f(1f, 0f, 0f);
			gl.glVertex3f(-1, -1, -2);
			gl.glColor3f(0f, 0f, 1f);
			gl.glVertex3f( 1, -1, -2);
			gl.glColor3f(0f, 1f, 0f);
			gl.glVertex3f( 1,  1, -2);
			gl.glColor3f(1f, 1f, 1f);
			gl.glVertex3f(-1,  1, -2);
		}
		gl.glEnd();
		
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
	
	@Override
	public void repaint()
	{
		// TODO: Force OpenGL to repaint.
		super.repaint();
	}
}
