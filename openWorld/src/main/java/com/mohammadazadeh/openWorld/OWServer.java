package com.mohammadazadeh.openWorld;

import java.awt.event.KeyEvent;
import java.io.*;
import java.net.*;
import java.util.LinkedHashMap;
import java.util.Map;

import com.sun.net.httpserver.*;
import com.google.gson.Gson;

public class OWServer {

	private OWObjectRepository repository;
	private int port;
	
	public OWServer(int port, OWObjectRepository repository) throws IOException
	{
		this.port = port;
		this.repository = repository;
		HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/openWorld", new ServerHandler(repository));
        server.start();
	}
}

class ServerHandler implements HttpHandler {
	private OWObjectRepository repository;
	
	public ServerHandler(OWObjectRepository repository) {
		this.repository = repository;
	}
	
    public void handle(HttpExchange t) throws IOException {
    	String operation = t.getRequestURI().getPath().replace("/openWorld/", "");
		String[] paramPairs = null;
		String query = t.getRequestURI().getQuery();
		Map<String, String> params = null;
		StringBuffer response = new StringBuffer();
		
		Gson gson = new Gson();
		
		if (query != null)
		{
			paramPairs = query.split("\\&");
			params = new LinkedHashMap<String, String>();
			response.append("Parameters:\n");
			for (String pp : paramPairs)
			{
				String prm = pp.substring(0, pp.indexOf('='));
				String vlu = pp.substring(pp.indexOf('=') + 1);
				params.put(prm, vlu);
				response.append(prm);
				response.append(" ");
				response.append(vlu);
				response.append("\n");
			}
			System.err.println(response.toString());
			if(params.get("move") != null )
			{
				switch (params.get("move"))
				{
				case "up":
					if (repository.avatarPosIndex < repository.map.length * repository.map.width - repository.map.length)
						repository.avatarPosIndex += repository.map.length;
					break;
				case "down":
					if (repository.avatarPosIndex >= repository.map.length)
						repository.avatarPosIndex -= repository.map.length;
					break;
				case "left":
					if ((repository.avatarPosIndex > 0) && (repository.avatarPosIndex % repository.map.length != 0))
						repository.avatarPosIndex --;
					break;
				case "right":
					if ((repository.avatarPosIndex < repository.map.length * repository.map.width - 1) && ((repository.avatarPosIndex + 1) % repository.map.length != 0))
						repository.avatarPosIndex ++;
					break;
				}
			}
			if(params.get("light") != null && params.get("light").equals("switch"))
			{
				repository.lighting = ! repository.lighting;
			}
		}
		response.append("Position Index: ");
		response.append(repository.avatarPosIndex);
        String rspns = response.toString();
        System.err.println(rspns);
        t.sendResponseHeaders(200, rspns .getBytes().length);
        OutputStream os = t.getResponseBody();
        os.write(rspns .getBytes());
        os.close();
    }
}