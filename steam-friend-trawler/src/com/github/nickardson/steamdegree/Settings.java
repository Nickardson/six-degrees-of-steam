package com.github.nickardson.steamdegree;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class Settings {
	private static JSONObject objConfig;
	public static synchronized JSONObject getConfigSettings() {
		if (objConfig == null) {
			objConfig = loadFromFile(new File("config.json"));
		}
		
		return objConfig;
	}
	
	private static JSONObject loadFromFile(File file) {
		try {
			return new JSONObject(IOUtils.toString(new FileInputStream(file), Charset.forName("UTF-8")));
		} catch (JSONException | IOException e) {
			e.printStackTrace();
			return new JSONObject();
		}
	}
}
