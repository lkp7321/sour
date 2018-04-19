package com.sl.www.test;

import java.util.ArrayList;
import java.util.List;

import com.sl.www.domain.Department;
import com.sun.org.apache.commons.digester.SetNextRule;

public class Practice {
	
	/*
	 * 需求：打印以下部门结构
	 * ┣开发部
	 *   ┣开发一部
	 *      ┣java组
	 *      ┣.net组
	 *   ┣开发二部
	 *      ┣android组
	 *      ┣ios组
	 * 
	 */
	public static void main(String[] args) {
		List<Department> list=findTopList();
		for (Department department : list) {
			print(department,"┣");
		}
		
	}
	
	public static void print(Department dept,String prefix){
		System.out.println(prefix+dept.getName());
		for (Department department : dept.getChildren()) {
			print(department,"　"+prefix);
		}
	}
	
	
	/*
	 * 准备数据
	 * 
	 */
	public static List<Department> findTopList(){
		List<Department> list=new ArrayList<Department>();
		Department dept=new Department();
		dept.setName("开发部");
		
		Department dept_1=new Department();
		dept_1.setName("开发一部");
		dept_1.setParent(dept);
		
		Department dept_2=new Department();
		dept_2.setName("开发二部");
		dept_2.setParent(dept);
		
		Department dept_1_1=new Department();
		dept_1_1.setName("java组");
		dept_1_1.setParent(dept_1);
		
		Department dept_1_2=new Department();
		dept_1_2.setName(".net组");
		dept_1_2.setParent(dept_1);
		
		Department dept_2_1=new Department();
		dept_2_1.setName("android组");
		dept_2_1.setParent(dept_2);
		
		Department dept_2_2=new Department();
		dept_2_2.setName("ios组");
		dept_2_2.setParent(dept_2);
		
		dept.getChildren().add(dept_1);
		dept.getChildren().add(dept_2);
		
		dept_1.getChildren().add(dept_1_1);
		dept_1.getChildren().add(dept_1_2);
		
		dept_2.getChildren().add(dept_2_1);
		dept_2.getChildren().add(dept_2_2);
		
		list.add(dept);
		
		return list;
	}

}
