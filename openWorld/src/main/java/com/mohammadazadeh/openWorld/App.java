package com.mohammadazadeh.openWorld;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Open World!" );
        
        OWObjectRepository repository = new OWObjectRepository();
        OWFrame frame = new OWFrame(repository);
		OWObjectLoaderX3D objectLoader = new OWObjectLoaderX3D(repository);
		
		try {
			objectLoader.load("./3D/", "Siavash.x3d");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		frame.init();
		frame.repaint();
    }
}
