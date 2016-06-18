package com.mohammadazadeh.openWorld;

public class OWObjectCamera extends OWObject{
	private static int cameraId = 0;
	
	public int id;
	public float fieldOfView;
	public float[] centerOfRotation;
	public float[] orientation;
	public OWObjectCamera(String name)
	{
		super(name);
		this.id = cameraId++;
	}

}
