package com.mohammadazadeh.openWorld;

import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public class OWObject {
	public String name;
	public List<float[]> vertices;
	public List<float[]> normals;
	public List<OWObjectFace> faces;
	public OWObjectMaterial material;
	public float[] position;
	public float[] rotation;
	public float[] rotationTeta;
	public float[] scale;
	
	public OWObject parentObject;
	public List<OWObject> subObjects;
	
	public OWObject(String name)
	{
		this.name = name;
		parentObject = null;
		subObjects = new ArrayList<OWObject>();
	}
	
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		
		gl.glFlush();
	}
}
