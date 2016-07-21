package com.mohammadazadeh.openWorld;

import java.util.ArrayList;
import java.util.Random;

public class OWMap extends OWObject {
	public int width;
	public int length;
	public float thickness;

	public OWMap(String name, int width, int length, float thickness) {
		super(name);
		
		this.width = width;
		this.length = length;
		this.thickness = thickness;
		
		vertices = new ArrayList<float[]>();
		faces = new ArrayList<OWObjectFace>();
		Random rn = new Random();
		
		for (int w = 0; w < width; w++)
		{
			for (int l = 0; l < length; l++)
			{
				float[] vertex = new float[3];
				vertex[0] = w * thickness;
				vertex[2] = l * thickness;
				vertices.add(vertex);
			}
		}
		for (int w = 1; w < width; w++)
		{
			for (int l = 1; l < length; l++)
			{
				OWObjectFace face = new OWObjectFace();
				float h;
				// h = (rn.nextInt() % width) / (float)length;
				h = 2 * (float) Math.sin((5 * Math.PI / (length * width)) * l * w);
				face.vertexIndices.add(w * length + l);
				face.vertexIndices.add(w * length + l - 1);
				face.vertexIndices.add((w - 1) * length + l - 1);
				face.vertexIndices.add((w - 1) * length + l);
				for (int index : face.vertexIndices) {
					vertices.get(index)[1] = h;
				}
				faces.add(face);
			}
		}
	}
}
