package com.mohammadazadeh.openWorld;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	
	public transient OWObject parentObject;
	public Map<String, OWObject> subObjects;
	
	public OWObject(String name)
	{
		this.name = name;
		parentObject = null;
		subObjects = new HashMap<String, OWObject>();
		rotationTeta = new float[3];
	}
	
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		
		gl.glPushMatrix();
		{
			if (position != null)
				gl.glTranslatef(position[0], position[1], position[2]);
			if (rotation != null)
				gl.glRotatef(rotation[3], rotation[0], rotation[1], rotation[2]);
			if (rotationTeta != null)
			{
				gl.glRotatef(rotationTeta[0], 1, 0, 0);
				gl.glRotatef(rotationTeta[1], 0, 1, 0);
				gl.glRotatef(rotationTeta[2], 0, 0, 1);
			}
			if (scale != null)
				gl.glScalef(scale[0], scale[1], scale[2]);
			if (material != null)
			{
				gl.glColor3f(material .diffuse[0], material .diffuse[1], material .diffuse[2]);
				gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, material .diffuse, 0);
				gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, material .specular, 0);
			    gl.glMaterialf(GL2.GL_FRONT_AND_BACK , GL2.GL_SHININESS, material .shininess);
			}
			if (faces != null)
				for (OWObjectFace face : faces)
				{
					gl.glBegin(GL2.GL_POLYGON);
						for (Integer index : face.vertexIndices)
						{
							if (normals != null)
								gl.glNormal3f(normals.get(index)[0], normals.get(index)[1], normals.get(index)[2]);
							gl.glVertex3f(vertices.get(index)[0], vertices.get(index)[1], vertices.get(index)[2]);
						}
					gl.glEnd();
				}
			if (subObjects != null)
				for (Entry<String, OWObject> obj : subObjects.entrySet())
					obj.getValue().display(drawable);
		}
		gl.glPopMatrix();
	}
}
