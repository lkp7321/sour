package com.ylxx.fx.controller.pere.presysmanager;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.CustFavVo;
import com.ylxx.fx.service.pere.presysmanager.ICustFavService;
@Controller
//@RequestMapping("fx")
@Scope("prototype")
public class CustFavController {
	@Resource(name="custFavService")
	private ICustFavService custFavService;
	
	 /**
	  * 查询客户优惠信息
	  * @param cuno pageNo pageSize ogcd stat
	  * @return  查询成功00 查询无结果01 查询异常02
	  */ 
	@RequestMapping(value="/selcCustFavList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	 public String selcCustFavList(@RequestBody CustFavVo custFav) throws Exception {
		return custFavService.selcCustFavList(custFav.getCuno(),custFav.getOgcd(),custFav.getStat(),custFav.getPageNo(),custFav.getPageSize());
	}
	 /**
	  * 删除客户优惠
	  * @param String cuno
	  * @param String userKey
	  * @return 删除失败01 ;删除成功 00
	  */
	@RequestMapping(value="/deleteCustFav.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	 public String deleteCustFav(@RequestBody CustFavVo custFav){
		 return custFavService.deleteCustFav(custFav.getUserKey(),custFav.getCuno().trim());
	 }
	/**
	 * 柜员添加页面初始化柜员机构列表
	 * @param String ogcd ognm pageno pagesize
	 * @return 查询成功00 查询无结果01 查询异常02
	 * */
	@RequestMapping(value="/selectETBhid.do",produces="application/json;charset=UTF-8")
	@ResponseBody
		public String selectETBhid(@RequestBody CustFavVo custFav){
			return custFavService.selectETBhid(custFav.getOgcd(),custFav.getOgnm(),custFav.getPageNo(),custFav.getPageSize());
		}
 	/**
	  * 修改客户优惠
	  * @param String userKey fvjh fvgh stat ogcd cuno
	  * @param CustFavVo custFav
	  * @return false：修改失败 01;true:修改成功00
	  */
	@RequestMapping(value="/updCustFav.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	 public String updCustFav(@RequestBody CustFavVo custFav){
		return custFavService.updCustFav(custFav);
	}
	 /**
	  * 客户优惠：是否存在此cuno
	  * @param String userKey
	  * @param String cuno
	  * @return 该用户已存在00  该用户可以使用 01  查重失败02
	  */
	@RequestMapping(value="/selCunoExist.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	 public String selCunoExist(@RequestBody CustFavVo custFav) {
		return custFavService.selCunoExist(custFav.getCuno(),custFav.getUserKey());
	}
	 /**
	  * 新增客户优惠
	  * @param String userKey  cuno, fvjh, fvgh, ogcd, stat
	  * @param CustFavVo custFav
	  * @return 添加失败01 添加成功00
	  */
	@RequestMapping(value="/insCustFav.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	 public String insCustFav(@RequestBody CustFavVo custFav) {
	/*	CustFavVo custFav=new CustFavVo();
		custFav.setCuno("11111");
		custFav.setFvjh("1");
		custFav.setFvgh("1");
		custFav.setOgcd("zx01");
		custFav.setStat("0");*/
		return custFavService.insCustFav(custFav);
	}
}
