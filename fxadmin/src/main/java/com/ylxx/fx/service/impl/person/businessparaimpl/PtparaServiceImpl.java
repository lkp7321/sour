package com.ylxx.fx.service.impl.person.businessparaimpl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.person.businesspara.PtparaMapper;
import com.ylxx.fx.service.LogfileCmdService;
import com.ylxx.fx.service.person.businesspara.PtparaService;
import com.ylxx.fx.service.po.Logfile;
import com.ylxx.fx.service.po.Ptpara;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
/**
 * 交易参数设置
 * @author lz130
 *
 */
@Service("ptparaService")
public class PtparaServiceImpl implements PtparaService {
	private static final Logger log = LoggerFactory.getLogger(PtparaServiceImpl.class);
	@Resource
	private PtparaMapper ptparamap;
	@Resource(name="logfileCmdService")
	private LogfileCmdService logfileCmdService;

	/**
	 * 查询所有业务参数
	 */
	@Override
	public PageInfo<Ptpara> getptparalist(String prod, Integer pageNo, Integer pageSize) {
		List<Ptpara> list = null;
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
		try {
			list = ptparamap.selectPtpara(prod);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		PageInfo<Ptpara> page = new PageInfo<Ptpara>(list);
		return page;
	}
	/**
	 * 修改业务参数
	 */
	@Override
	public boolean updateptpara(CurrUser curUser, Ptpara ptpara) {
		int a = 0;
		try {
			a = ptparamap.upPtpara(curUser.getProd(), ptpara);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(a>0){
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "更新产品参数:参数编号:"
					+ ptpara.getPaid() + ",参数名称:" + ptpara.getRemk()
					+ ",参数值:" + ptpara.getValu() + ",参数状态:"
					+ ptpara.getStat() + "成功!时间:"
					+ DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("产品参数");
			loginfo.setRemk("修改");
			loginfo.setVold("登录产品:" + curUser.getProd() + "更新产品参数:参数编号:"
					+ ptpara.getPaid() + ",参数名称:" + ptpara.getRemk()
					+ ",参数值:" + ptpara.getValu() + ",参数状态:"
					+ ptpara.getStat());
			loginfo.setVnew("成功");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
			return true;
		}else{
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "更新产品参数:参数编号:"
					+ ptpara.getPaid() + ",参数名称:" + ptpara.getRemk()
					+ ",参数值:" + ptpara.getValu() + ",参数状态:"
					+ ptpara.getStat() + "失败!时间:"
					+ DataTimeClass.getCurDateTime());
			return false;
		}
	}
}
