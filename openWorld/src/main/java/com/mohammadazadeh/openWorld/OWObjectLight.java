package com.mohammadazadeh.openWorld;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public class OWObjectLight extends OWObject{
	private static int lightSourceId = GL2.GL_LIGHT0;
	
	public int id;
	public float radius;
	public float ambientIntensity;
	public float intensity;
	public float beamWidth;
	public float cutOffAngle;
	public float[] color;
	public float[] direction;
	
	public OWObjectLight(String name)
	{
		super(name);
		this.id = lightSourceId++;
		
		if (lightSourceId > GL2.GL_LIGHT7)
		{
			lightSourceId = GL2.GL_LIGHT7;
			System.err.println("Too many light sources!");
		}
	}
	
	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		
		gl.glPushMatrix();
		if (position != null)
			gl.glTranslatef(position[0], position[1], position[2]);
		if (rotation != null)
			gl.glRotatef(rotation[3], rotation[0], rotation[1], rotation[2]);
		if (rotationTeta != null)
		{
			gl.glRotatef(rotationTeta[0], 1, 0, 0);
			gl.glRotatef(rotationTeta[1], 0, 1, 0);
			gl.glRotatef(rotationTeta[2], 0, 0, 1);
		}
		if (scale != null)
			gl.glScalef(scale[0], scale[1], scale[2]);
		
		gl.glColor3f(color[0], color[1], color[2]);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, color, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, color, 0);
	    
		gl.glBegin(GL2.GL_POLYGON);
		{
			gl.glVertex3f( 1, -1, 0);
			gl.glVertex3f(-1, -1, 0);
			gl.glVertex3f(-1,  1, 0);
			gl.glVertex3f( 1,  1, 0);
		}
		gl.glEnd();
		//gl.glLoadIdentity();
		
		float[] lightPos = {0f, 0f, 0f,1.0f};        		// light position
    	float[] noAmbient = { 0.125f, 0.125f, 0.125f, 1f };	// low ambient light
    	float[] spec =    { 0.6f, 0.6f, 0.6f, 5f }; 
		
    	gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable (this.id);
		gl.glLightfv(this.id, GL2.GL_AMBIENT, noAmbient, 0);
		gl.glLightfv(this.id, GL2.GL_SPECULAR, spec, 0);
		gl.glLightfv(this.id, GL2.GL_SPOT_DIRECTION, direction, 0);
		gl.glLightfv(this.id, GL2.GL_DIFFUSE, color, 0);
		gl.glLightfv(this.id, GL2.GL_POSITION,lightPos, 0);
		gl.glLightf (this.id, GL2.GL_SPOT_CUTOFF, cutOffAngle);
		
		
		gl.glPopMatrix();
	}
}
