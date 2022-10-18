package com.example.webhook.controller;

import java.util.List;

import lombok.Data;

@Data
public class Value {
	
	private String messaging_product;
	
	private MetaData metadata;
	
	private List<Contacts> contacts;

	private List<Messages> messages;

}
