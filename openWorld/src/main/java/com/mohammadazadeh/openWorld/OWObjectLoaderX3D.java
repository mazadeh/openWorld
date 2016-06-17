package com.mohammadazadeh.openWorld;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class OWObjectLoaderX3D extends DefaultHandler{
	private OWObjectRepository repository;

	private OWObject object;
	private OWObjectMaterial material;
	private boolean isNewMTL;
	
	public OWObjectLoaderX3D(OWObjectRepository repository)
	{
		this.repository = repository;
	}
	
	public void load(String path, String name) throws ParserConfigurationException, SAXException, IOException
	{
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

	    for (int i = 0; i < listOfFiles.length; i++) {
	      //if (listOfFiles[i].isFile()) {
	        System.out.println("File " + listOfFiles[i].getName());
	      //} else if (listOfFiles[i].isDirectory()) {
	      //  System.out.println("Directory " + listOfFiles[i].getName());
	      //}
	    }
		
		File input = new File(path + name);
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();
		saxParser.parse(input, this);
	}
	
	@Override
	public void startDocument()
	{
		
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		Scanner scan;
		
		switch (qName)
		{
		case "Transform":
			if (object == null)
			{
				object = new OWObject(attributes.getValue("DEF"));
			}
			else
			{
				OWObject obj = new OWObject(attributes.getValue("DEF"));
				obj.parentObject = object;
				object.subObjects.add(obj);
				object = obj;
			}
			if (attributes.getValue("translation") != null)
			{
				scan = new Scanner(attributes.getValue("translation"));
				object.position = new float[3];
				object.position[0] = scan.nextFloat();
				object.position[1] = scan.nextFloat();
				object.position[2] = scan.nextFloat();
			}
			if (attributes.getValue("rotation") != null)
			{
				scan = new Scanner(attributes.getValue("rotation"));
				object.rotation = new float[4];
				object.rotation[0] = scan.nextFloat();
				object.rotation[1] = scan.nextFloat();
				object.rotation[2] = scan.nextFloat();
				object.rotation[3] = 180 / (float)Math.PI * scan.nextFloat();
			}
			if (attributes.getValue("scale") != null)
			{
				scan = new Scanner(attributes.getValue("scale"));
				object.scale = new float[3];
				object.scale[0] = scan.nextFloat();
				object.scale[1] = scan.nextFloat();
				object.scale[2] = scan.nextFloat();
			}
			break;
		case "Coordinate":
			if (attributes.getValue("point") != null)
			{
				scan = new Scanner(attributes.getValue("point"));
				object.vertices = new ArrayList<float[]>();
				while (scan.hasNext())
				{
					float[] vertex = new float[3];
					vertex[0] = scan.nextFloat();
					vertex[1] = scan.nextFloat();
					vertex[2] = scan.nextFloat();
					object.vertices.add(vertex);
				}
				scan.close();
			}
			break;
		case "Normal":
			if (attributes.getValue("vector") != null)
			{
				scan = new Scanner(attributes.getValue("vector"));
				object.normals = new ArrayList<float[]>();
				while (scan.hasNext())
				{
					float[] normal = new float[3];
					normal[0] = scan.nextFloat();
					normal[1] = scan.nextFloat();
					normal[2] = scan.nextFloat();
					object.normals.add(normal);
				}
				scan.close();
			}
			break;
		case "IndexedFaceSet":
			scan = new Scanner(attributes.getValue("coordIndex"));
			object.faces = new ArrayList<OWObjectFace>();
			while (scan.hasNext())
			{
				OWObjectFace face = new OWObjectFace();
				int index = scan.nextInt();
				while (index != -1)
				{
					face.vertexIndices.add(index);
					index = scan.nextInt();
				}
				object.faces.add(face);
			}
			scan.close();
			break;
		case "Material":
			if (attributes.getValue("USE") != null)
			{
				material = repository.materialList.get(attributes.getValue("USE"));
				isNewMTL = false;
			}
			else if (attributes.getValue("DEF") != null)
			{
				material = new OWObjectMaterial(attributes.getValue("DEF"));
				isNewMTL = true;
				if (attributes.getValue("diffuseColor") != null)
				{
					scan = new Scanner(attributes.getValue("diffuseColor"));
					material.diffuse = new float[3];
					material.diffuse[0] = scan.nextFloat();
					material.diffuse[1] = scan.nextFloat();
					material.diffuse[2] = scan.nextFloat();
				}
				if (attributes.getValue("specularColor") != null)
				{
					scan = new Scanner(attributes.getValue("specularColor"));
					material.specular = new float[3];
					material.specular[0] = scan.nextFloat();
					material.specular[1] = scan.nextFloat();
					material.specular[2] = scan.nextFloat();
				}
				if (attributes.getValue("shininess") != null)
				{
					scan = new Scanner(attributes.getValue("shininess"));
					material.shininess = scan.nextFloat();
				}
				if (attributes.getValue("transparency") != null)
				{
					scan = new Scanner(attributes.getValue("transparency"));
					material.transparency = scan.nextFloat();
				}
			}
			break;
		case "SpotLight":
			OWObjectLight light = new OWObjectLight(attributes.getValue("DEF"));
			light.parentObject = object;
			object.subObjects.add(light);
			object = light;
			
			if (attributes.getValue("radius") != null)
			{
				light.radius = Float.parseFloat(attributes.getValue("radius"));
			}
			if (attributes.getValue("ambientIntensity") != null)
			{
				light.ambientIntensity = Float.parseFloat(attributes.getValue("ambientIntensity"));
			}
			if (attributes.getValue("intensity") != null)
			{
				light.intensity = Float.parseFloat(attributes.getValue("intensity"));
			}
			if (attributes.getValue("beamWidth") != null)
			{
				light.beamWidth = Float.parseFloat(attributes.getValue("beamWidth"));
			}
			if (attributes.getValue("cutOffAngle") != null)
			{
				light.cutOffAngle = 180 * Float.parseFloat(attributes.getValue("cutOffAngle")) / (float)Math.PI;
			}
			if (attributes.getValue("color") != null)
			{
				scan = new Scanner(attributes.getValue("color"));
				
				light.color = new float[3];
				light.color[0] = 1.5f * scan.nextFloat();
				light.color[1] = 1.5f * scan.nextFloat();
				light.color[2] = 1.5f * scan.nextFloat();
			}
			if (attributes.getValue("direction") != null)
			{
				scan = new Scanner(attributes.getValue("direction"));
				
				light.direction = new float[3];
				light.direction[0] = scan.nextFloat();
				light.direction[1] = scan.nextFloat();
				light.direction[2] = scan.nextFloat();
			}
			if (attributes.getValue("location") != null)
			{
				scan = new Scanner(attributes.getValue("location"));
				
				light.location = new float[3];
				light.location[0] = scan.nextFloat();
				light.location[1] = scan.nextFloat();
				light.location[2] = scan.nextFloat();
			}
			break;
		case "Viewpoint":
			OWObjectCamera camera = new OWObjectCamera(attributes.getValue("DEF"));
			camera.parentObject = object;
			object.subObjects.add(camera);
			object = camera;

			if (attributes.getValue("radius") != null)
			{
				camera.fieldOfView = Float.parseFloat(attributes.getValue("fieldOfView"));
			}
			if (attributes.getValue("centerOfRotation") != null)
			{
				scan = new Scanner(attributes.getValue("centerOfRotation"));
				
				camera.centerOfRotation = new float[3];
				camera.centerOfRotation[0] = scan.nextFloat();
				camera.centerOfRotation[1] = scan.nextFloat();
				camera.centerOfRotation[2] = scan.nextFloat();
			}
			if (attributes.getValue("position") != null)
			{
				scan = new Scanner(attributes.getValue("position"));
				
				camera.position = new float[3];
				camera.position[0] = scan.nextFloat();
				camera.position[1] = scan.nextFloat();
				camera.position[2] = scan.nextFloat();
			}
			if (attributes.getValue("orientation") != null)
			{
				scan = new Scanner(attributes.getValue("orientation"));
				
				camera.orientation = new float[4];
				camera.orientation[0] = scan.nextFloat();
				camera.orientation[1] = scan.nextFloat();
				camera.orientation[2] = scan.nextFloat();
				camera.orientation[3] = scan.nextFloat();
			}
			break;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		switch (qName)
		{
		case "Transform":
			if (object.parentObject == null) {
				object.material = material;
				repository.objectList.add(object);
			}
			else {
				object = object.parentObject;
			}
			break;
		case "SpotLight":
			repository.lightList.add((OWObjectLight) object);
			object = object.parentObject;
			break;
		case "Viewpoint":
			repository.cameraList.add((OWObjectCamera) object);
			object = object.parentObject;
			break;
		case "Material":
			if (material != null && isNewMTL) {
				repository.materialList.put(material.name, material);
			}
			break;
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		
	}
	
	@Override
	public void endDocument()
	{
		System.out.println("Loading finished successfully.");
	}
}
