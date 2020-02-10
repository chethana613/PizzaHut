package com.squad.pizzahut.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Food {
	
	@Id
	private Integer foodId;
	private String foodName;
	private Double price;
	private Double rating;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	private String description;
	
	private String imageUrl;

}
