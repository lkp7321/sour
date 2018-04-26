package com.sl.www.domain;

import java.util.HashSet;
import java.util.Set;

public class Department {
	
	private int id;
	private String name;
	private String description;
	
	private Department parent;//上级部门
	private Set<Department> children=new HashSet<Department>();//下级部门
	private Set<User> users=new HashSet<User>();//旗下员工                 
	public Department() {
		super();
	}
	public Department(int id, String name, String description,
			Department parent, Set<Department> children, Set<User> users) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.parent = parent;
		this.children = children;
		this.users = users;
	}
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
	public Department getParent() {
		return parent;
	}
	public void setParent(Department parent) {
		this.parent = parent;
	}
	public Set<Department> getChildren() {
		return children;
	}
	public void setChildren(Set<Department> children) {
		this.children = children;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	

}
