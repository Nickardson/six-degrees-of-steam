package com.github.nickardson.steamdegree;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class Settings extends JSONObject {
	public Settings(File settingsFile) throws JSONException, FileNotFoundException, IOException {
		super(IOUtils.toString(new FileInputStream(settingsFile), Charset.forName("UTF-8")));
	}
	
	private static Settings instance;
	public static synchronized Settings getConfigSettings() {
		if (instance == null) {
			try {
				instance = new Settings(new File("config.json"));
			} catch (JSONException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return instance;
	}
}
