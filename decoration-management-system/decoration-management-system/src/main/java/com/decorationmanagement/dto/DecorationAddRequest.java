package com.decorationmanagement.dto;

import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

public class DecorationAddRequest {
	
    private int id;
	
	private String name;
	
	private String description;
	
	private double price;
	
	private MultipartFile image;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}	
	
}
