package com.example.webhook.controller;

import java.util.List;

import lombok.Data;

@Data
public class Entry {

	private String id;

	private List<Changes> changes;

}
