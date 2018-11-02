package de.ostfalia.gruppe5.userInformation;

import java.io.Serializable;

public abstract class User implements Serializable{

	private Integer id;
	
	private String name;
	
	public User() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
