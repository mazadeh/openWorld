package com.mohammadazadeh.openWorld;

import java.util.List;
import java.util.ArrayList;

public class OWObjectFace {
	public List<Integer> vertexIndices;
	public List<Integer> textureIndices;
	public List<Integer> normalIndices;
	
	public OWObjectFace()
	{
		vertexIndices = new ArrayList<Integer>();
		textureIndices = new ArrayList<Integer>();
		normalIndices = new ArrayList<Integer>();
	}
}
