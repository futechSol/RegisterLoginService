package com.bridgelabz.registerLoginService.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utility {
private static final Logger logger = LoggerFactory.getLogger(Utility.class);
    
	/**
	 * get the server local IP address
	 * @return local IP address
	 */
	public static String getLocalHostIPaddress() {
		String localIP = "";
		try {
			InetAddress localhost = InetAddress.getLocalHost();
			localIP = localhost.getHostAddress().trim();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} 
		return localIP;
	}
	
	/**
	 * get the server public IP address
	 * @return public IP
	 */
	public static String getHostPublicIP() {
		// Find public IP address 
		String systemipaddress = ""; 
		try
		{  
			URL url_name = new URL("http://bot.whatismyipaddress.com"); 
			BufferedReader sc = new BufferedReader(new InputStreamReader(url_name.openStream())); 
			// reads system IPAddress 
			systemipaddress = sc.readLine().trim(); 
		} 
		catch (Exception e) 
		{ 
			systemipaddress = "Cannot Execute Properly"; 
		} 
		logger.info("Host Public IP : " + systemipaddress);
		logger.trace("Note Creation");
		return systemipaddress;
	}
}
