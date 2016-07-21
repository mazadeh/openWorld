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
		frame.repaint();
		
		try {
			// objectLoader.load("./3D/", "Floor.x3d");
			objectLoader.load("./3D/", "Siavash.x3d");
			objectLoader.load("./3D/", "Free Camera.x3d");
			// repository.floor = repository.objectList.get("Floor.x3d").subObjects.get("Floor_TRANSFORM").subObjects.get("Floor_ifs_TRANSFORM");
			repository.avatarHolder = repository.objectList.get("Siavash.x3d");
			repository.avatar = repository.avatarHolder.subObjects.get("Avatar_TRANSFORM").subObjects.get("Body_TRANSFORM");
			
			repository.avatarHolder.position = new float[3];
			repository.avatar.position = new float[3];
			
			repository.avatarPosIndex = 10100;
			OWMap map = new OWMap("Map 1", 200, 200, 1);
			repository.objectList.put("Map 1", map);
			repository.map = map;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		frame.startAnimator();
		frame.init();
		frame.repaint();
		
    }
}
