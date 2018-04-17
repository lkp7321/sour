package com.ylxx.fx.service.impl.accumu.businessparaimpl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ylxx.fx.core.domain.RateVo;
import com.ylxx.fx.core.mapper.accumu.businesspara.RateMapper;
import com.ylxx.fx.service.accumu.businesspara.IRateService;
import com.ylxx.fx.service.po.RateBean;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 利率配置
 * @author lz130
 *
 */
@Service("rateService")
public class RateServiceImpl implements IRateService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RateServiceImpl.class);
	
	@Resource
	private RateMapper rateMapper;

	/**
	 * 查询利率配置数据
	 * @return
	 * @throws Exception
	 */
	public List<RateBean> searchList() throws Exception {
		LOGGER.info("查询利率信息：");
		return rateMapper.searchList();
	}

	/**
	 * 向利率历史表里添加记录
	 * @param rateVo
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public Boolean doInsertHis(RateVo rateVo) throws Exception {
		RateBean rateBean = new RateBean();
		BeanUtils.copyProperties(rateVo, rateBean);
		Boolean result = false;
		try {
			int count =	rateMapper.doInsertHis(rateBean);
			if(count > 0){
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	/**
	 * 向利率表里添加记录
	 * @param rateVo
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public Boolean doInsert(RateVo rateVo) throws Exception {
		RateBean rateBean = new RateBean();
		BeanUtils.copyProperties(rateVo, rateBean);
		Boolean result = false;
		try {
			int count =	rateMapper.doInsert(rateBean);
			if(count > 0){
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 删除利率历史表记录
	 * @param rateVo
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void doDelete(String time, String pdtp) throws Exception {
		try {
			rateMapper.doDelete(time, pdtp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新利率表里的记录
	 * @param rateVo
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public Boolean doUpdate(RateVo rateVo) throws Exception {
		RateBean rateBean = new RateBean();
		BeanUtils.copyProperties(rateVo, rateBean);
		Boolean result = false;
		try {
			int count =	rateMapper.doUpdate(rateBean);
			if(count > 0){
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 向利率表里添加记录(插入之前写入历史表)	
	 * @param rateVo
	 * @return
	 */
	@Transactional
	public String insertWithInsertHis(RateVo rateVo) throws Exception {
		String time = DataTimeClass.getNowDay();
		rateVo.setUpdt(time);
		String resultReturn = ResultDomain.getRtnMsg(ErrorCodePrice.E_21.getCode(), null);
		RateBean rateBean = new RateBean();
		BeanUtils.copyProperties(rateVo, rateBean);
		try {
			//1. 插利率历史表
			rateMapper.doInsertHis(rateBean);
			//2. 插入利率表
			rateMapper.doInsert(rateBean);
			resultReturn = ResultDomain.getRtnMsg(ErrorCodePrice.E_20.getCode(), null);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return resultReturn;
	}
	
	 /**
	 * 更新利率表里的记录(更新之前写入历史表)	
	 * @param rateVo
	 * @return
	 */
	@Transactional
	public String updateWithInsertHis(RateVo rateVo) throws Exception {
		String time = DataTimeClass.getNowDay();
		rateVo.setUpdt(time);
		// TODO 原程序逻辑在利率插入后更新会两次写入历史表且主键相同导致更新时历史表插入产生主键冲突，现为更新功能好用注掉更新时历史表插入操作
		RateBean rateBean = new RateBean();
		BeanUtils.copyProperties(rateVo, rateBean);
		try {
			rateMapper.doUpdate(rateBean);
			return ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
		}catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
		}
	}
}
