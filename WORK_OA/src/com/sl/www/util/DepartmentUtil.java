package com.sl.www.util;

import java.util.ArrayList;
import java.util.List;

import com.sl.www.domain.Department;

/*
 * 用于获得部门树的工具类
 */
public class DepartmentUtil {
    
	/**
	 * 根据顶级部门获得部门树结构列表
	 * @param list 顶级部门，根据顶级部门造树
	 * @return
	 */
	public static List<Department> getTreeList(List<Department> list) {
		List<Department> treeList=new ArrayList<Department>();
		for (Department department : list) {
			walkTree(department,"┣",treeList);
		}
		
		return treeList;
	}

	private static void walkTree(Department department, String prefix,List<Department> treeList) {
		//组装集合
		//System.out.println();
		//做一个copy
		Department copy=new Department();
		copy.setId(department.getId());
		copy.setName(prefix+department.getName());
		
		
		treeList.add(copy);
		
		//递归调用
		for (Department department2 : department.getChildren()) {
			walkTree(department2, "　"+prefix, treeList);
		}
		
	}
	

}
