package com.kilotrees.core;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.kilotrees.util.InetAddressUtil;

public class Config {

	public static Logger log = Logger.getLogger(Config.class);

	private static String webAppPath = null;

	public static JSONObject json = null;

	public static void init(ServletContext ctx) {
		webAppPath = ctx.getRealPath("/");

		refreshConfigJSON();

		String zipFilePath = getZipFilePath();
		new File(zipFilePath).mkdirs();
	}

	public static String getZipFilePath() {
		String webServerPath = json.optString("WebServer_Path", webAppPath);
		String zipFilesPath = webServerPath + "/files/zips";
		return zipFilesPath;
	}
	
	public static String getWebServerStoragePath() {
		String webServerPath = json.optString("WebServer_Path", webAppPath);
		return webServerPath;
	}

	/**
	 * 加载config.json
	 */
	public static void refreshConfigJSON() {
		File configFile = new File(webAppPath + "/config.json");
		if (configFile.exists()) {
			try {
				FileInputStream fins = new FileInputStream(configFile);
				byte[] buf = new byte[(int) configFile.length()];
				fins.read(buf);
				fins.close();
				String string = new String(buf, "utf-8");
				
				JSONObject json = new JSONObject(string);
				String YUN_SERVER_IP = json.optString("YUN_SERVER_IP");
				String YUN_SERVER_PORT = json.optString("YUN_SERVER_PORT");
				
				if (YUN_SERVER_IP.isEmpty()) {
					String LANIP = InetAddressUtil.getLANIPAddress();
					string = string.replaceAll("\\[YUN_SERVER_IP\\]", LANIP);
					YUN_SERVER_IP = LANIP;
				}else {
					string = string.replaceAll("\\[YUN_SERVER_IP\\]", YUN_SERVER_IP);
				}
				if (YUN_SERVER_PORT.isEmpty()) {
					String LANPORT = "9090";
					string = string.replaceAll("\\[YUN_SERVER_PORT\\]", LANPORT);
					YUN_SERVER_PORT = LANPORT;
				}else {
					string = string.replaceAll("\\[YUN_SERVER_PORT\\]", YUN_SERVER_PORT);
				}
				
				Config.json = new JSONObject(string);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}
}
