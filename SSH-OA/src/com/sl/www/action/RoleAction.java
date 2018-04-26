package com.sl.www.action;

import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.sl.www.base.BaseAction;
import com.sl.www.domain.Privilege;
import com.sl.www.domain.Role;
import com.sl.www.service.RoleService;

@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role>{
	
	private Integer[] privilegeIds;
	
	public String list() throws Exception{
		List<Role> list=roleService.findAll();
		ActionContext.getContext().put("list", list);
		return "list";
	}
	
	public String addUI() throws Exception{
		
		return "addUI";
	}
	
	public String add() throws Exception{
		roleService.save(model);
		return "toList";
	}
	
	public String editUI() throws Exception{
		//回显数据
		//根据id在数据库中找
		Role role=roleService.getById(model.getId());
		//放栈顶
		ActionContext.getContext().getValueStack().push(role);
		return "editUI";
	}
	
	
	public String edit() throws Exception{
		//第一步，从数据库中拿到原来的对象 
		Role role=roleService.getById(model.getId());
		//第二步：设置新的属性
		role.setName(model.getName());
		role.setDescription(model.getDescription());
		//第三步：更新到数据库中
		roleService.update(role);
		return "toList";
	}
	
	public String delete() throws Exception{
		roleService.delete(model.getId());
		return "toList";
	}
	
	/** 设置权限页面 */
	public String setPrivilegeUI() throws Exception {
		// 准备回显的数据
		Role role = roleService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(role);

		if (role.getPrivileges() != null) {
			privilegeIds = new Integer[role.getPrivileges().size()];
			int index = 0;
			for (Privilege priv : role.getPrivileges()) {
				privilegeIds[index++] = priv.getId();
			}
		}

		// 准备数据 privilegeList
		List<Privilege> privilegeList = privilegeService.findAll();
		ActionContext.getContext().put("privilegeList", privilegeList); 

		return "setPrivilegeUI";
	}

	/** 设置权限 */
	public String setPrivilege() throws Exception {
		// 1，从数据库中获取原对象
		Role role = roleService.getById(model.getId());

		// 2，设置要修改的属性
		List<Privilege> privilegeList = privilegeService.getByIds(privilegeIds);
		role.setPrivileges(new HashSet<Privilege>(privilegeList));

		// 3，更新到数据库
		roleService.update(role);

		return "toList";
	}
	
	// ---

	public Integer[] getPrivilegeIds() {
		return privilegeIds;
	}

	public void setPrivilegeIds(Integer[] privilegeIds) {
		this.privilegeIds = privilegeIds;
	}



}
