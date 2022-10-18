package com.example.webhook.controller;

import java.security.Timestamp;

import lombok.Data;

@Data
public class Messages {
	
	private String from;
	
	private String id;
	
	private String timestamp;
	
	private Text text;
	
	private String type;

}
