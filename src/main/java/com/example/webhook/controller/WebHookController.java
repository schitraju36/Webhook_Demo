/**
 * 
 */
package com.example.webhook.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Santhosh
 *
 */
@RestController
public class WebHookController {

	@Value("${TOKEN}")
	private String token;

	@Value("${MYTOKEN}")
	private String myToken;

	@PostMapping // http://localhost:8080/api/webhook
	public ResponseEntity<String> print(@RequestBody String requestBody) {
		System.out.println("###### Webhook #####" + requestBody);
		return new ResponseEntity<String>(requestBody, HttpStatus.OK);
	}

	/*
	 * @GetMapping("/webhooks/{mode}/{challenge}/{token}") public
	 * ResponseEntity<String> webHook(@RequestParam("mode") String
	 * mode,@RequestParam("challenge") String challenge,@RequestParam("token")
	 * String token) { System.out.println("###### Webhook #####" + mode); String
	 * hubMode=mode;
	 * 
	 * return new ResponseEntity<String >(mode, HttpStatus.OK); }
	 */

	@GetMapping("/webhooks")
	public ResponseEntity<String> webHook1( @RequestParam(required=false) Map<String,String> qparams, HttpServletRequest request, HttpServletResponse httpResp) {
		String hubMode = qparams.get("hub.mode");
		String challenge = qparams.get("hub.challenge");
		String token = qparams.get("hub.verify_token");
		System.out.println("hubMode : " + hubMode + "challenge : " + challenge + "verify_token : " + token);

		
			if (hubMode.equals("subscribe") && token.equals(myToken)) {
				return new ResponseEntity<String>(challenge, HttpStatus.OK);

			} else {
				return new ResponseEntity<String>(challenge, HttpStatus.FORBIDDEN);
			}

		}
	
}
