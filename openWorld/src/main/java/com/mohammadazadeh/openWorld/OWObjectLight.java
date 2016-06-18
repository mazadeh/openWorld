package com.mohammadazadeh.openWorld;

import com.jogamp.opengl.GL2;

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
}
