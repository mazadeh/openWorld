package com.mohammadazadeh.openWorld;

import java.util.List;

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
}
