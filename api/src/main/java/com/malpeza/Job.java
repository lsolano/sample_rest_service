package com.malpeza;

public class Job {
	private int id;
	private String name;

	public Job() {
		
	}
	
	public Job(int id, String name) {
		
	}

	public final int getId() {
		return id;
	}

	public final void setId(int id) {
		this.id = id;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString(){
		return String.format("%s(id: %d, name: %s)%n", this.getClass().getSimpleName(), id, name);
	}


	
	
}
