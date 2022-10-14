/**
 * 
 */
package com.example.webhook.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        return new ResponseEntity<String >(requestBody, HttpStatus.OK);
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
    public ResponseEntity<String> webHook1(HttpServletRequest request, HttpServletResponse httpResp) {
		String hubMode=request.getParameter("hub.mode");
		String challenge=request.getParameter("hub.challenge");
		String token=request.getParameter("hub.verify_token");
		System.out.println("hubMode :" + hubMode +"challenge :" + challenge + "verify_token :"+ token);
        //System.out.println(request.getRequestURI() + "?" + request.getQueryString());
      //  String myToken=null;	
        
        if(StringUtils.isNoneEmpty(hubMode) &&  StringUtils.isNoneEmpty(token)) {
        	
        	if( hubMode.equals("subscrbe") && token.equals(myToken)) {
        		return new ResponseEntity<String >(challenge, HttpStatus.OK);
        		
        	} else{
        		return new ResponseEntity<String >(challenge, HttpStatus.FORBIDDEN);
        	}
        	        	
        }else {
        	return new ResponseEntity<String >(challenge, HttpStatus.BAD_REQUEST);
        }       
	}
}
