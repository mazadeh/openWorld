package com.mohammadazadeh.openWorld;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class OWObjectLoaderX3D extends DefaultHandler{
	private OWObjectRepository repository;
	
	private OWObject object;
	
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
			break;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		switch (qName)
		{
		case "Transform":
			if (object.parentObject == null)
			{
				repository.objectList.add(object);
			}
			else
				object = object.parentObject;
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
