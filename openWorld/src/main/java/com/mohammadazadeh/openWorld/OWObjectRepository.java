package com.mohammadazadeh.openWorld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.jogamp.opengl.GLAutoDrawable;

public class OWObjectRepository {
	public Map<String, OWObject> objectList;
	public Map<String, OWObjectMaterial> materialList;
	public List<OWObjectCamera> cameraList;
	public List<OWObjectLight> lightList;
	public OWObject avatar;
	public OWObject avatarHolder;
	public OWMap map;
	
	public int avatarPosIndex;
	
	public OWObjectRepository()
	{
		objectList = new HashMap<String, OWObject>();
		materialList = new HashMap<String, OWObjectMaterial>();
		cameraList = new ArrayList<OWObjectCamera>();
		lightList = new ArrayList<OWObjectLight>();
	}
	
	public void display(GLAutoDrawable drawable) {
		for (Entry<String, OWObject> obj : objectList.entrySet())
		{
			obj.getValue().display(drawable);
		}
	}
}
