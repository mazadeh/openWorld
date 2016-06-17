package com.mohammadazadeh.openWorld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jogamp.opengl.GLAutoDrawable;

public class OWObjectRepository {
	public List<OWObject> objectList;
	public Map<String, OWObjectMaterial> materialList;
	public List<OWObjectCamera> cameraList;
	public List<OWObjectLight> lightList;
	
	public OWObjectRepository()
	{
		objectList = new ArrayList<OWObject>();
		materialList = new HashMap<String, OWObjectMaterial>();
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
