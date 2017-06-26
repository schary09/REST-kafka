package com.test;

public class RESTDAO {

	private String id;

	private String name;

	public RESTDAO(){
		id = "";
		name = "";
	}
	public RESTDAO(String id, String name){
		this.id = id;
		this.name = name;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RESTDAO create(RESTDAO newobj) {
		return newobj;
		// add to db
	}
	public RESTDAO findByName(String name){
		RESTDAO n = new RESTDAO("22",name);
		return n;

	}
	public RESTDAO findById(String id){
		RESTDAO n = new RESTDAO("33","goo");
		return n;
	}

}

