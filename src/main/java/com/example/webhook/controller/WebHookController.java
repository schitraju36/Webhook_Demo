/**
 * 
 */
package com.example.webhook.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

	@PostMapping("/webhook")
	public ResponseEntity<Object> testHookGet(@RequestParam(required = false) Map<String, String> qparams,
			HttpServletRequest request) {
		String hubMode = qparams.get("hub.mode");
		String challenge = qparams.get("hub.challenge");
		String token1 = qparams.get("hub.verify_token");
		// System.out.println("hubMode : " + hubMode + " challenge : " + challenge + "
		// verify_token : " + token);
		System.out.println(request.getQueryString());
		if (hubMode.equals("subscribe") && token.equals(myToken)) {
			System.out.println(request.getQueryString());
			return new ResponseEntity<Object>(challenge, HttpStatus.OK);

		} else {
			return new ResponseEntity<Object>(challenge, HttpStatus.FORBIDDEN);
		}
	}

	@PostMapping("/webhook1")
	public String testHookPost(@RequestBody TextMsg request,
			@RequestParam(required = false) Map<String, String> qparams) {
		 String response=null;
		String token1 = qparams.get("hub.verify_token");
		List<Messages> msg=request.getEntry().get(0).getChanges().get(0).getValue().getMessages();
		if (request.getObject() != null) {

			if (request.getEntry() != null && request.getEntry().get(0).getChanges() != null
					&& request.getEntry().get(0).getChanges().get(0).getValue() != null
					&& request.getEntry().get(0).getChanges().get(0).getValue().getMessages().get(0) != null) {

				String phon_no_id = request.getEntry().get(0).getChanges().get(0).getValue().getMetadata()
						.getPhone_number_id();
				String from = request.getEntry().get(0).getChanges().get(0).getValue().getMessages().get(0).getFrom();
				String msg_body = request.getEntry().get(0).getChanges().get(0).getValue().getMessages().get(0).getText().getBody();

				System.out.println("phon_no_id: " + phon_no_id + "from: " + from + "msg_body: " + msg_body);

				// set headers
				 RestTemplate restTemplate = new RestTemplate();
				  String url="https://graph.facebook.com/v13.0/"+phon_no_id+"/messages?access_token="+token;
				  HttpHeaders headers = new HttpHeaders();
			      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			      HttpEntity<TextMsg> entity = new HttpEntity<TextMsg>(request,headers);
			      
			     response= restTemplate.exchange(
			    		  url, HttpMethod.POST, entity, String.class).getBody();

			}

		}
		   return response;

	}

}
