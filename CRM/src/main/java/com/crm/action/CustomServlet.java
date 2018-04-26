package com.crm.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crm.entity.Custom;
import com.crm.service.CustomService;
import com.crm.service.impl.CustomServiceImpl;
import com.crm.util.Pager;

public class CustomServlet extends HttpServlet{
	private CustomService service=new CustomServiceImpl();
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String uri=request.getRequestURI();
		String action=uri.substring(uri.lastIndexOf("/"),uri.lastIndexOf("."));
		if(action.equals("/list")){
			customList(request,response);
		}else if(action.equals("/modify")){
			cutomDetailMasage(request,response);
		}else if(action.equals("/update")){
			update(request,response);
		}else if(action.equals("/delete")){
			deleteCustom(request,response);
		}else if(action.equals("/add")){
			addCustom(request,response);
		}
	}

	/**
	 * 查询客户列表
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void customList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int pageSize=4;
		int currPage=1;
		String cusId="";
		int userId=(Integer)request.getSession().getAttribute("userId");
		currPage=Integer.parseInt(request.getParameter("currPage"));
		if(request.getParameter("cusId")!=null){
			cusId=request.getParameter("cusId");
		}
		Pager<Custom> pager=service.getPager(currPage, pageSize, cusId,userId);
		request.setAttribute("pager", pager);
		request.setAttribute("allPage", pager.getPageSum());
		request.getRequestDispatcher("customList.jsp").forward(request,response);
	}

	/**
	 * 查询当前客户信息
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void cutomDetailMasage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Custom custom = service.getDetailMasage(id);
        request.setAttribute("custom", custom);
        request.getRequestDispatcher("customDetail.jsp").forward(request, response);
    }

    /**
     * 修改
     * @param request
     * @param response
     * @throws IOException
     */
	public void update(HttpServletRequest request, HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("utf-8");
		String cusId=request.getParameter("cusid");
		String cusName=request.getParameter("name");
		String level=request.getParameter("level");
		System.out.println("level====="+level);
		String area=request.getParameter("area");
		String dept=request.getParameter("dept");
		String industry=request.getParameter("industry");
		String type=request.getParameter("type");
		int id=Integer.parseInt(request.getParameter("id"));
		int rows=service.updateCustom(cusId, cusName, level, area, dept, industry, type, id);
		System.out.println(rows);
		if(rows==1){
			response.sendRedirect("list.do?currPage=1");
		}
	}

    /**
     * 删除
     * @param request
     * @param response
     * @throws IOException
     */
	public void deleteCustom(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String[] id=request.getParameterValues("del");
		int rows=service.deleteCustom(id);
		System.out.println("rows="+rows);
		if(rows>0){
			response.sendRedirect("list.do?currPage=1");
		}
	}

    /**
     * 添加
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
	public void addCustom(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		request.setCharacterEncoding("utf-8");
		String cusid=request.getParameter("cusid");
		String name=request.getParameter("name");
		String level=request.getParameter("levle");
		String area=request.getParameter("area");
		int managerid=Integer.parseInt(request.getParameter("managerid"));
		String dept=request.getParameter("dept");
		String inderstry=request.getParameter("inderstry");
		String type=request.getParameter("type");
		int size=service.findByCusId(cusid);
		String msg="";
		if(size==0){
			int rows=service.addCustom(cusid, name, level, area, managerid, dept, inderstry, type);
			if(rows>0){
				response.sendRedirect("list.do?currPage=1");
			}else{
				msg="添加失败";
				request.setAttribute("msg",msg);
				request.getRequestDispatcher("addCustom.jsp").forward(request, response);
			}
		}else{
			msg="添加成功";
			request.setAttribute("msg",msg);
			request.getRequestDispatcher("addCustom.jsp").forward(request, response);
		}
}

}
