package com.sl.www.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.sl.www.base.BaseAction;
import com.sl.www.domain.Department;
import com.sl.www.service.DepartmentService;
import com.sl.www.util.DepartmentUtil;

@Controller
@Scope("prototype")
public class DepartmentAction extends BaseAction<Department>{
	
	private Integer parentId;
	
	
	public String list() throws Exception{
		
//		List<Department> list=departmentService.findAll();
//		ActionContext.getContext().put("list", list);
		
		//查询顶级部门
		if (parentId==null) {
			List<Department> list=departmentService.findTopList();
			ActionContext.getContext().put("list", list);
		}else{
			List<Department> list=departmentService.findChildrenList(parentId);
			ActionContext.getContext().put("list", list);
			
			ActionContext.getContext().put("parent", departmentService.getById(parentId));
		
		}
		//查询非顶级部门
		
		
		return "list";
	}
	
	public String addUI() throws Exception{
		//准备下拉框数据
//		List<Department> list=departmentService.findAll();
//		ActionContext.getContext().put("list", list);
		
		List<Department> treeList=DepartmentUtil.getTreeList(departmentService.findTopList());
		ActionContext.getContext().put("list", treeList);
		
		return "addUI";
	}
	
	public String add() throws Exception{
		//封装没有封装到model中的属性
		model.setParent(departmentService.getById(parentId));
		
		departmentService.save(model);
		return "toList";
	}
	
	public String editUI() throws Exception{
		//回显数据
		//根据id在数据库中找
		Department department=departmentService.getById(model.getId());
		//放栈顶
		ActionContext.getContext().getValueStack().push(department);
		
		//准备下拉框数据
		List<Department> list=departmentService.findAll();
		ActionContext.getContext().put("list", list);
		
		//回显上级部门
		if (department.getParent()!=null) {
			parentId=department.getParent().getId();
		}
		
		return "editUI";
	}
	
	
	public String edit() throws Exception{
		//第一步，从数据库中拿到原来的对象 
		Department department=departmentService.getById(model.getId());
		//第二步：设置新的属性
		department.setParent(departmentService.getById(parentId));
		department.setName(model.getName());
		department.setDescription(model.getDescription());
		//第三步：更新到数据库中
		departmentService.update(department);
		return "toList";
	}
	
	public String delete() throws Exception{
		departmentService.delete(model.getId());
		return "toList";
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	

}
