package com.prrin.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Products implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private int quantity;
	private double price;

	public Products(String name, int quantity, double price) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	}

}