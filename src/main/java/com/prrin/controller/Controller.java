package com.prrin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prrin.service.IPdfService;

@RestController
@RequestMapping("/api/html-pdf/v1")
public class Controller {

	@Autowired
	IPdfService service;

	@GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> get() {
		try {
			return new ResponseEntity<Integer>(service.factura(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Exception>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/getDocumento", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getDocumento() {
		try {
			return new ResponseEntity<Integer>(service.documento(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Exception>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}