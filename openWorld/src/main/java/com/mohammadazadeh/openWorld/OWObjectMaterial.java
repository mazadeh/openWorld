package com.mohammadazadeh.openWorld;

public class OWObjectMaterial {
	public String name;
	public float[] ambient;
	public float[] diffuse;
	public float[] specular;
	public float transparency;
	public float shininess;
	public int illumination;
	
	public OWObjectMaterial(String name)
	{
		this.name = name;
	}
}
