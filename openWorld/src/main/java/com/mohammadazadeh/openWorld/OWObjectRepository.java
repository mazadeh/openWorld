package com.mohammadazadeh.openWorld;

import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GLAutoDrawable;

public class OWObjectRepository {
	public List<OWObject> objectList;
	public List<OWObjectMaterial> materialList;
	public List<OWObjectCamera> cameraList;
	public List<OWObjectLight> lightList;
	
	public OWObjectRepository()
	{
		objectList = new ArrayList<OWObject>();
		materialList = new ArrayList<OWObjectMaterial>();
		cameraList = new ArrayList<OWObjectCamera>();
		lightList = new ArrayList<OWObjectLight>();
	}
	
	public void display(GLAutoDrawable drawable) {
		for (OWObject obj : objectList)
		{
			obj.display(drawable);
		}
	}
}
