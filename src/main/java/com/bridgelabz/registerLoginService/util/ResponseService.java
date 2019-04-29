package com.bridgelabz.registerLoginService.util;

public class ResponseService {
   
	public static Response getResponse(int responseCode, String responseMessage, String token) {
		return new Response(responseCode, responseMessage, token);
	}
}
