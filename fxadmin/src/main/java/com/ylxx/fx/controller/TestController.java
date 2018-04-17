/*
	 * Copyright 2012-2014 the original author or authors.
	 *
	 * Licensed under the Apache License, Version 2.0 (the "License");
	 * you may not use this file except in compliance with the License.
	 * You may obtain a copy of the License at
	 *
	 *      http://www.apache.org/licenses/LICENSE-2.0
	 *
	 * Unless required by applicable law or agreed to in writing, software
	 * distributed under the License is distributed on an "AS IS" BASIS,
	 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	 * See the License for the specific language governing permissions and
	 * limitations under the License.
	 */
package com.ylxx.fx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 页面跳转
 * @author lz130
 *
 */
@Controller
public class TestController {
	
	/**
	 * 系统状态
	 * @return
	 */
	@RequestMapping("tosystemstate.do")
	public String tosysta(){
		return "personaloffer/system/systemstate";
	}
	/**
	 * P001
	 * 货币对管理
	 * @return
	 */
	@RequestMapping("tocuurpair.do")
	public String tocuurpair(){
		return "personaloffer/system/cuurencypair";		//实盘-系统管理-货币对管理；
	}
	@RequestMapping("tocuurpairs.do")
	public String tocuurpairs(){
		return "personaloffer/system/cuurencypairs";
	}
	@RequestMapping("tocuurpairst.do")
	public String tocuurpairst(){
		return "personaloffer/system/cuurencypairst";
	}
	/**
	 * 错误码管理
	 * @return
	 */
	@RequestMapping("toerrcode.do")
	public String toerrorcode(){
		return "personaloffer/system/errorcode";
	}
	/**
	 * 平盘通道控制
	 * @return
	 */
	@RequestMapping("toflatchan.do")
	public String toflatchan(){
		return "personaloffer/system/flatchannel";
	}
	/**
	 * 货币管理      &&  系统管理--币种管理
	 * @return
	 */
	@RequestMapping("tomanamon.do")
	public String tomanamon(){
		return "personaloffer/system/managemoney";
	}
	/**
	 * 机构管理
	 * @return
	 */
	@RequestMapping("tooutfit.do")
	public String tooutfit(){
		return "personaloffer/system/outfit";
	}
	/**
	 * 价格校验器设置
	 * @return
	 */
	@RequestMapping("topricevalida.do")
	public String topricevalida(){
		return "personaloffer/system/pricevalidator";
	}
	/**
	 * 品种管理
	 * @return
	 */
	@RequestMapping("toproductsmana.do")
	public String toproductsmana(){
		return "personaloffer/system/productsmana";
	}
	/**
	 * 品种对管理
	 * @return
	 */
	@RequestMapping("tovarie.do")
	public String tovarie(){
		return "personaloffer/system/varietymana";
	}
	/**
	 * 客户管理;
	 * @return
	 */
	@RequestMapping("tocustomer.do")
	public String tocustomer(){
		return "personaloffer/customer/customerman";
	}
	/*---------pricemana-----------*/
	//报价参数设置;
	@RequestMapping("toquotepar.do")
	public String toquotepar(){
		return "personaloffer/price/quoteparameter";
	}
	//报价平台 产品管理 报价参数设置;
	@RequestMapping("toquoquoteparset.do")
	public String toquoquoteparset(){
		return "personaloffer/promana/quoquoteparset";
	}
	
	//报价频率设置
	@RequestMapping("toquotefrequ.do")
	public String toquotefrequ(){
		return "personaloffer/price/quotefrequency";
	}
	//产品停牌
	@RequestMapping("toproductstop.do")
	public String toproductstop(){
		return "personaloffer/price/productstop";
	}
	//手工停牌
	@RequestMapping("tohandstop.do")
	public String tohandstop(){
		return "personaloffer/price/handstop";
	}
	
	
	//产品校验参数
	@RequestMapping("toproductcali.do")
	public String toproductcali(){
		return "personaloffer/price/productcalibrate";
	}
	//报价平台 --产品校验参数；
	@RequestMapping("toproductcali1.do")	
	public String toproductcali1(){
		return "personaloffer/price/productcalibrate1";
	}
	//报价平台-产品管理-产品校验参数；
	@RequestMapping("toproductcali2.do")	
	public String toproductcali2(){
		return "personaloffer/price/productcalibrate2";
	}
	//产品均价校验器
	@RequestMapping("toproductpri.do")
	public String toproductpri(){
		return "personaloffer/price/productprice";
	}
	//产品点差查询
	@RequestMapping("toproductpoint.do")
	public String toproductpoint(){
		return "personaloffer/price/productpoint";
	}
	//手工报价录入
	@RequestMapping("toquoteentry.do")
	public String toquoteentry(){
		return "personaloffer/price/quoteentry";
	}
	//手工报价审核
	@RequestMapping("toquoteaudit.do")
	public String toquoteaudit(){
		return "personaloffer/price/quoteaudit";
	}
	//手工报价启用
	@RequestMapping("toquoteenabled.do")
	public String toquoteenabled(){
		return "personaloffer/price/quoteenabled";
	}
	//报价操作查询
	@RequestMapping("toquoteoperation.do")
	public String toquoteoperation(){
		return "personaloffer/price/quoteoperation";
	}
	//点差干预上限设置
	@RequestMapping("tointervenemax.do")
	public String tointervenemax(){
		return "personaloffer/price/intervenemax";
	}
	//报价平台 产品管理 点差干预上限设置
	@RequestMapping("tointervenmaxquo.do")
	public String tointervenmaxquo(){
		return "personaloffer/price/intervenmaxquo";
	}
	//点差干预设置
	@RequestMapping("tointerveneset.do")
	public String tointerveneset(){
		return "personaloffer/price/interveneset";
	}
	//分类干预设置
	@RequestMapping("classionset.do")
	public String toclassionset(){
		return "personaloffer/price/classionset";
	}
	//总分行价格
	@RequestMapping("totalbranch.do")
	public String tototalbran(){
		return "personaloffer/price/totalbranch";
	}
	//客户价格
	@RequestMapping("tocustom.do")
	public String tocustom(){
		return "personaloffer/price/customer";
	}
	//总敞口
	@RequestMapping("tototalexposure.do")
	public String totalexposu(){
		return "personaloffer/price/totalexposure";
	}
	/*--------------交易管理----------*/
	/**
	 * P001
	 * 挂单流水查询
	 * @return
	 */
	@RequestMapping("toguadanwater.do")
	public String toguadanwater(){
		return "personaloffer/delamana/guadanwater";
	}
	/**
	 * P001,P002,P003
	 * 交易流水查询
	 * @return
	 */
	@RequestMapping("todealwater.do")
	public String todealwater(){
		return "personaloffer/delamana/dealwater";
	}
	//积存金-交易流水查询
	@RequestMapping("todealwater1.do")
	public String todealwater1(){
		return "personaloffer/delamana/dealwater1";
	}
	/**
	 * 最大优惠设置，最大优惠
	 * @return
	 */
	@RequestMapping("tomaxpre.do")
	public String tomaxpre(){
		return "personaloffer/delamana/maxpreferential";
	}
	//分行优惠设置
	@RequestMapping("tobranchdiscount.do")
	public String tobranchdiscount(){
		return "personaloffer/delamana/branchdiscount";
	}
	//交易参数设置
	@RequestMapping("todealparamter.do")
	public String todealparamter(){
		return "personaloffer/delamana/dealparamter";
	}
	//外币兑换流水查询
	@RequestMapping("toforecha.do")
	public String toforecha(){
		return "personaloffer/delamana/foreignexchange";
	}
	//出入金流水查询
	@RequestMapping("toentryandexit.do")
	public String toentryandexit(){
		return "personaloffer/delamana/entryandexit";
	}
	/**
	 * 客户签约信息
	 * @return
	 */
	@RequestMapping("tocustomersign.do")
	public String tocustomersign(){
		return "personaloffer/delamana/customersign";
	}
	//分行优惠查询
	@RequestMapping("tobranchdis.do")
	public String tobranchdis(){
		return "personaloffer/delamana/branchdiscountsearch";
	}
	//日历规则
	@RequestMapping("tocalendarrule.do")
	public String tocalendarrule(){
		return "personaloffer/delamana/calendarrules";
	}
	//交易产品日历
	@RequestMapping("todealprodut.do")
	public String todealprodut(){
		return "personaloffer/delamana/dealprodut";
	}
	//日历规则展示
	@RequestMapping("tocalshow.do")
	public String tocalshow(){
		return "personaloffer/delamana/calendarshow";
	}
	/*-------------监控管理----------------------*/
	//成交流水监控
	@RequestMapping("todealwate.do")
	public String todealwate(){
		return "personaloffer/monitoring/dealwater";
	}
	//平盘交易监控
	@RequestMapping("toplatdeal.do")
	public String toplatdeal(){
		return "personaloffer/monitoring/platdeal";
	}
	//异常数据监控
	@RequestMapping("toabnor.do")
	public String toabnor(){
		return "personaloffer/monitoring/abnormaldata";
	}
	//轧差敞口监控
	@RequestMapping("tonetexposure.do")
	public String tonetexposure(){
		return "personaloffer/monitoring/netexposure";
	}
	//累加敞口监控
	@RequestMapping("tocumulativeexp.do")
	public String tocumulativeexp(){
		return "personaloffer/monitoring/cumulativeexp";
	}
	//分行价格监控
	@RequestMapping("tohandexp.do")
	public String tohandexp(){
		return "personaloffer/monitoring/handexp";
	}
	@RequestMapping("tohandexp1.do")
	public String tohandexp1(){
		return "personaloffer/monitoring/handexp1";
	}
	@RequestMapping("tohandexp2.do")		//账户交易分行价格监控
	public String tohandexp2(){
		return "personaloffer/monitoring/handexp2";
	}
	//客户价格监控
	@RequestMapping("tocuspriexp.do")
	public String tocuspriexp(){
		return "personaloffer/monitoring/cuspriexp";
	}
	//实盘-总敞口监控
	@RequestMapping("tototalex.do")
	public String tototalex(){
		return "personaloffer/monitoring/totalexposure";
	}
	//分类敞口监控
	@RequestMapping("tocumulat.do")
	public String tocumulat(){
		return "personaloffer/monitoring/cumulativeexp";
	}
	//总敞口
	@RequestMapping("tototalopen.do")
	public String totalopen(){
		return "personaloffer/monitoring/totalopen";
	}
	//分类敞口
	@RequestMapping("toclassionopen.do")
	public String toclassionopen(){
		return "personaloffer/monitoring/classionopen";
	}
	//积存金-总敞口
	@RequestMapping("tototalopen1.do")
	public String totalopen1(){
		return "personaloffer/monitoring/totalopen1";
	}
	//积存金-分类敞口
	@RequestMapping("toclassionopen1.do")
	public String toclassionopen1(){
		return "personaloffer/monitoring/classionopen1";
	}
	//敞口监控
	@RequestMapping("toopenmonitor.do")
	public String openmonitor(){
		return "personaloffer/monitoring/openmonitor";
	}
	
	/*-----------------平盘管理------------------*/
	//手工敞口登记
	@RequestMapping("toopenreg.do")
	public String toopenreg(){
		return "personaloffer/flatmanage/openregiste";
	}
	//纸黄金手工敞口登记
	@RequestMapping("topagegoldregiste.do")
	public String pagegoldregiste(){
		return "personaloffer/flatmanage/pagegoldregiste";
	}
	//手工敞口抹账
	@RequestMapping("toopenacco.do")
	public String toopenacco(){
		return "personaloffer/flatmanage/openaccound";
	}
	//平盘交易查询
	@RequestMapping("toflattrad.do")
	public String toflattrad(){
		return "personaloffer/flatmanage/flattrading";
	}
	//清单汇总查询
	@RequestMapping("toliquidate.do")
	public String toliquidate(){
		return "personaloffer/flatmanage/liquidatesummary";
	}
	//不明交易处理
	@RequestMapping("tounknowntrans.do")
	public String tounknowntrans(){
		return "personaloffer/flatmanage/unknowntransaction";
	}
	//异常平盘处理
	@RequestMapping("toabnormalplate.do")
	public String toabnormalplate(){
		return "personaloffer/flatmanage/abnormalplate";
	}
	//平盘待处理查询
	@RequestMapping("toflatload.do")
	public String toflatload(){
		return "personaloffer/flatmanage/flatplateprocessed";
	}
	//对账交易复核
	@RequestMapping("tocheckbill.do")
	public String tocheckbill(){
		return "personaloffer/flatmanage/reconciliatetransacte";
	}
	/*----------参数设置----------*/
	//产品参数
	@RequestMapping("toproductpar.do")
	public String productpar(){
		return "personaloffer/paraset/productpar";
	}
	//最大优惠
	@RequestMapping("tobigdiscount.do")
	public String bigdiscount(){
		return "personaloffer/paraset/bigdiscount";
	}
	/**
	 * 分行优惠设置
	 * @return
	 */
	@RequestMapping("tohandparset.do")
	public String handparset(){
		return "personaloffer/paraset/handparset";
	}
 
	/*-------------------------- 积存金 ------------------------------------*/
	/**
	 * 积存金
	 * 报价参数设置
	 * @return
	 */
	@RequestMapping("toofferpataset.do")
	public String offerparaset(){
		return "personaloffer/paraset/offerparaset";
	}
	/**
	 * 积存金
	 * 利率配置
	 * @return
	 */
	@RequestMapping("tointerestrate.do")
	public String interestrate(){
		return "personaloffer/paraset/interestrateconfi";
	}
	/**
	 * 积存金
	 * 分红日
	 * @return
	 */
	@RequestMapping("tobonusday.do")
	public String bonusday(){
		return "personaloffer/paraset/bonusday";
	}
	/**
	 * 积存金
	 * 活动专户管理
	 * @return
	 */
	@RequestMapping("toactiveaccount.do")
	public String activecount(){
		return "personaloffer/paraset/activiaccount";
	}
	/**
	 * 活动专户查询
	 * @return
	 */
	@RequestMapping("toactiveaccountsear.do")
	public String activeaccountsear(){
		return "personaloffer/paraset/activaccountsear";
	}
	//积存金点差设置
	@RequestMapping("toreserveset.do")
	public String reserveset(){
		return "personaloffer/paraset/reserveset";
	}
	/**
	 * 积存金
	 * 修改客户等级
	 * @return
	 */
	@RequestMapping("tomodifyuserleval.do")
	public String modifyuserleval(){
		return "personaloffer/peogolddealman/modifyuserleval";
	}
	/**
	 * 积存金
	 * 添加客户等级
	 * @return
	 */
	@RequestMapping("toadduserleval.do")
	public String adduserleval(){
		return "personaloffer/peogolddealman/adduserleval";
	}
	/**
	 * 积存金
	 * 金生金查询
	 * @return
	 */
	@RequestMapping("togoldandgold.do")
	public String goldandgold(){
		return "personaloffer/peogolddealman/goldandgold";
	}
	/**
	 * 积存金
	 * 电商积存金
	 * @return
	 */
	@RequestMapping("toecbusiness.do")
	public String ecbusiness(){
		return "personaloffer/peogolddealman/ecbusiness";
	}
	//更改客户号
	@RequestMapping("tochangeusernum.do")
	public String changeusernum(){
		return "personaloffer/peogolddealman/changeusernum";
	}
	//添加优惠规则
	@RequestMapping("toadddiscountrules.do")
	public String adddiscountrules(){
		return "personaloffer/peogolddealman/adddiscountrules";
	}
	/*-------------------RV---------------------------------*/
	//国债流水查询；
	@RequestMapping("togoverloan.do")
	public String goverloan(){
		return "personaloffer/rvprefix/goverloan";
	}
	//现金流流水查询
	@RequestMapping("tocashflow.do")
	public String cashflow(){
		return "personaloffer/rvprefix/cashflow";
	}
	//实盘流水查询
	@RequestMapping("tofirmflow.do")
	public String firmflow(){
		return "personaloffer/rvprefix/firmflow";
	}
	//国债RPC请求
	@RequestMapping("togoverrpc.do")
	public String goverrpc(){
		return "personaloffer/rvprefix/goverrpc";
	}
	//现金流RPC请求；
	@RequestMapping("tocashrpc.do")
	public String cashrpc(){
		return "personaloffer/rvprefix/cashrpc";
	}
	//外汇RPC请求
	@RequestMapping("toforeignrpc.do")
	public String foreignrpc(){
		return "personaloffer/rvprefix/foreignrpc";
	}
	/*-------------------外汇实盘交易---------------------------------*/
	//平盘交易
	@RequestMapping("toflat.do")
	public String flat(){
		return "personaloffer/foreignprefix/flat";
	}
	//客户交易
	@RequestMapping("tocusto.do")
	public String custo(){
		return "personaloffer/foreignprefix/customer";
	}
	//交易明细信息
	@RequestMapping("todealdetail.do")
	public String dealdeta(){
		return "personaloffer/foreignprefix/dealdetails";
	}
	//交易异常信息
	@RequestMapping("todealabnor.do")
	public String dealabnor(){
		return "personaloffer/foreignprefix/dealabnor";
	}
 
	/*----------------------保证金前置-------------------------------*/
	//系统设置
	
	//系统参数设置
	@RequestMapping("tosysparset.do")
	public String sysparset(){
		return "personaloffer/systset/sysparset";
	}
	//交易流水查询
	@RequestMapping("todealwa.do")
	public String dealwa(){
		return "personaloffer/systset/dealwa";
	}
	//异常交易监控
	@RequestMapping("toabnormal.do")
	public String abnormal(){
		return "personaloffer/systset/abnormal";
	}
	//MT4用户组设置
	@RequestMapping("toMT4users.do")
	public String MT4user(){
		return "personaloffer/systset/MT4users";
	}
	//异常交易处理
	@RequestMapping("toabnorhandle.do")
	public String abnorhand(){
		return "personaloffer/systset/abnorhandle";
	}
	
	//日终处理
	//批量出金核对
	@RequestMapping("todatchgoldche.do")
	public String datchgoldche(){
		return "personaloffer/dayenprosess/datchgoldche";
	}
	//批量出金复核
	@RequestMapping("todatchgoldrev.do")
	public String datchgoldrev(){
		return "personaloffer/dayenprosess/datchgoldrev";
	}
	//余额并账录入
	@RequestMapping("tobalaninput.do")
	public String balaninput(){
		return "personaloffer/dayenprosess/balaninput";
	}
	//余额并账复核
	@RequestMapping("tobalanrev.do")
	public String balanrev(){
		return "personaloffer/dayenprosess/balanrev";
	}
	//客户未平头寸录入
	@RequestMapping("tocusinchinput.do")
	public String cusinchinput(){
		return "personaloffer/dayenprosess/cusinchinput";
	}
	//客户未平头寸复核
	@RequestMapping("tocusinchrev.do")
	public String cusinchrev(){
		return "personaloffer/dayenprosess/cusinchrev";
	}
	
	//报表管理
	//MT4日终报表
	@RequestMapping("toMT4report.do")
	public String MT4report(){
		return "personaloffer/dayenprosess/MT4report";
	}
 
	/*----------------------账户交易-------------------------------*/
	//监控管理
	//外币敞口监控
	@RequestMapping("toforcurexpmonitor.do")
	public String forcurexpmonitor(){
		return "account/monitoring/forcurexpmonitor";
	}
	//贵金属敞口监控
	@RequestMapping("topermetopenmonitor.do")
	public String permetopenmonitor(){
		return "account/monitoring/permetopenmonitor";
	}
	//结售汇敞口监控
	@RequestMapping("tojieshopenmonitor.do")
	public String jieshopenmonitor(){
		return "account/monitoring/jieshopenmonitor";
	}
	//手工平盘
	//原油手工平盘
	@RequestMapping("tocrudeoilflat.do")
	public String crudeoilflat(){
		return "account/handflatandriskman/crudeoilflat";
	}
	//原油手工平盘流水
	@RequestMapping("tocrudeoilflatwater.do")
	public String crudeoilflatwater(){
		return "account/handflatandriskman/crudeoilflatwater";
	}
	//风控管理
	//账户外汇追保
	@RequestMapping("towhinsurance.do")
	public String whinsurance(){
		return "account/handflatandriskman/whinsurance";
	}
	//账户贵金属追保
	@RequestMapping("togjsinsurance.do")
	public String gjsinsurance(){
		return "account/handflatandriskman/gjsinsurance";
	}
	//账户外汇强平
	@RequestMapping("towhstrongflat.do")
	public String whsrongflat(){
		return "account/handflatandriskman/whstrongflat";
	}
	//账户贵金属强平
	@RequestMapping("togjsstrongflat.do")
	public String gjssrongflat(){
		return "account/handflatandriskman/gjsstrongflat";
	}
	
	//交易管理
	//签约信息查询
	@RequestMapping("tosigninformation.do")
	public String signinformation(){
		return "account/accountdealman/signinformation";
	}
	//交易流水查询
	@RequestMapping("todealrecord.do")
	public String dealrecord(){
		return "account/accountdealman/dealrecord";
	}
	//持仓信息查询
	@RequestMapping("topositionnews.do")
	public String positionnews(){
		return "account/accountdealman/positionnews";
	}
	//贵金属交易查询
	@RequestMapping("toprecioustrading.do")
	public String precioustrading(){
		return "account/accountdealman/precioustrading";
	}
	//营销查询
	@RequestMapping("tomarketing.do")
	public String marketing(){
		return "account/accountdealman/marketing";
	}
	//调整日配置信息查询
	@RequestMapping("toadjustmentday.do")
	public String adjustmentday(){
		return "account/accountdealman/adjustmentday";
	}
	//K线查询
	@RequestMapping("toklinequrey.do")
	public String klinequrey(){
		return "account/accountdealman/klinequrey";
	}
	//账户余额查询
	@RequestMapping("toaccountbalance.do")
	public String accountbalance(){
		return "account/accountdealman/accountbalance";
	}
	
	/*-------------------------报价平台---------------------------*/
	//报价平台管理
	//审计管理
	@RequestMapping("toauditmana.do")
	public String auditmana(){
		return "personaloffer/priceplatfolder/auditmana";
	}
	//交叉盘计算
	@RequestMapping("tooverlapp.do")
	public String overlapping(){
		return "personaloffer/priceplatfolder/overlapping";
	}
	@RequestMapping("tooverlapp1.do")
	public String overlapping1(){
		return "personaloffer/priceplatfolder/overlapping1";
	}
	//策略字典管理
	@RequestMapping("topolicydicti.do")
	public String policydicti(){
		return "personaloffer/priceplatfolder/policydicti";
	}
	//历史价格查询
	@RequestMapping("tohistorysear.do")
	public String historysear(){
		return "personaloffer/priceplatfolder/historysear";
	}
	
	//价格源管理
	//账户交易；
	@RequestMapping("toaccounttrans.do")
	public String account(){
		return "personaloffer/accounttrans/accounttrans";
	}
	@RequestMapping("toaccounttran.do")
	public String accounttran(){
		return "personaloffer/accounttrans/accounttran";
	}
	//外汇点差查询；
	@RequestMapping("toforeignexc.do")
	public String foreignexc(){
		return "personaloffer/accounttrans/foreignexc";
	}
	//价格监控;
	//价格源监控
	@RequestMapping("topricemnit.do")
	public String pricemoint(){
		return "personaloffer/pricemonit/pricemonit";
	}
	//利率市场查询
	@RequestMapping("tointerrate.do")
	public String interrate(){
		return "personaloffer/pricemonit/interrate";
	}
	//告警管理
	//告警级别设置
	@RequestMapping("toalarm.do")
	public String alarm(){
		return "personaloffer/alarmmana/alarm";
	}
	//告警用户设置
	@RequestMapping("toalarmuser.do")
	public String alarmuser(){
		return "personaloffer/alarmmana/alarmuser";
	}
	//告警方式配置
	@RequestMapping("toalarmmode.do")
	public String alarmmode(){
		return "personaloffer/alarmmana/alarmmode";
	}
	//告警代码管理
	@RequestMapping("toalarmcode.do")
	public String alarmcode(){
		return "personaloffer/alarmmana/alarmcode";
	}
	//告警事件查询
	@RequestMapping("toalarmsearh.do")
	public String alarmsearh(){
		return "personaloffer/alarmmana/alarmsearh";
	}
	//发布管理  价格使用申请
	@RequestMapping("topriceuse.do")
	public String priceuse(){
		return "personaloffer/publishmana/priceuse";
	}
	//产品管理
	//产品设置
	@RequestMapping("toproductset.do")
	public String productset(){
		return "personaloffer/promana/productset";
	}
	//货币对配置
	@RequestMapping("tocuurpairconfig.do")
	public String cuurpair(){
		return "personaloffer/promana/cuurpairconfig";
	}
	//产品加工信息
	@RequestMapping("topromach.do")
	public String promach(){
		return "personaloffer/promana/promach";
	}
	//交易开闭市管理
	@RequestMapping("totradeocmana.do")
	public String tradeocmana(){
		return "personaloffer/promana/tradeocmana";
	}
	//产品源监控
	@RequestMapping("toprosource.do")
	public String prosource(){
		return "personaloffer/promana/prosource";
	}
	
	//价格源管理
	//校验器设置
	@RequestMapping("tocalibrator.do")
	public String calibrator(){
		return "personaloffer/pricesormana/calibrator";
	}
	//货币对配置
	@RequestMapping("tocurpairconf.do")
	public String curpairconf(){
		return "personaloffer/pricesormana/curpairconf";
	}
	//校验器设置
	@RequestMapping("tovalida.do")
	public String validator(){
		return "personaloffer/pricesormana/validator";
	}
	//手工快速停牌
	@RequestMapping("tohandqukst.do")
	public String handqukst(){
		return "personaloffer/pricesormana/handqukst";
	}
	//价格接收设置
	@RequestMapping("topriceget.do")
	public String priceget(){
		return "personaloffer/pricesormana/priceget";
	}
	//价格接收展示
	@RequestMapping("toprigetsh.do")
	public String prigetsh(){
		return "personaloffer/pricesormana/prigetsh";
	}
	//价格加工设置
	@RequestMapping("toprimachiset.do")
	public String primachiset(){
		return "personaloffer/pricesormana/primachiset";
	}
	//价格加工展示
	@RequestMapping("toprimachshow.do")
	public String primachshow(){
		return "personaloffer/pricesormana/primachshow";
	}
	//价格源注册
	@RequestMapping("toprisoureg.do")
	public String prisoureg(){
		return "personaloffer/pricesormana/prisoureg";
	}
	
	/*-------------------------个人结售汇平台---------------------------*/
	//交易管理
	//撤销与修改流水查询
	@RequestMapping("tomodifywater.do")
	public String modifywater(){
		return "settlement/fxdiscount/modifywater";
	}
	//个人结售汇优惠
	//机构优惠
	@RequestMapping("toagentdiscount.do")
	public String agentdiscount(){
		return "settlement/fxdiscount/agentdiscount";
	}
	//客户等级优惠
	@RequestMapping("touserlevaldiscount.do")
	public String userlevaldiscount(){
		return "settlement/fxdiscount/userlevaldiscount";
	}
	//渠道优惠
	@RequestMapping("tochanneldiscount.do")
	public String channeldiscount(){
		return "settlement/fxdiscount/channeldiscount";
	}
	//卡bin优惠
	@RequestMapping("tocardbin.do")
	public String cardbin(){
		return "settlement/fxdiscount/cardbin";
	}
	//交易折美元优惠
	@RequestMapping("todollardiscount.do")
	public String dollardiscount(){
		return "settlement/fxdiscount/dollardiscount";
	}
	/*个人结售汇   前置系统管理 */
	//登记外管局流水查询
	@RequestMapping("toregistsafe.do")
	public String registsafe(){
		return "personaloffer/frontsystmana/registsafe";
	}
	//国别代码对照查询
	@RequestMapping("toconuntrycode.do")
	public String conuntrycode(){
		return "personaloffer/frontsystmana/conuntrycode";
	}
	//电子柜员管理
	@RequestMapping("toelectrict.do")
	public String electrict(){
		return "personaloffer/frontsystmana/electricteller";
	}
	//外管局当前折算汇率
	@RequestMapping("tocuurcon.do")
	public String cuurcon(){
		return "personaloffer/frontsystmana/cuurcon";
	}
	//客户优惠查询
	@RequestMapping("tocusdiscount.do")
	public String cusdiscount(){
		return "personaloffer/frontsystmana/cusdiscount";
	}
	//东方支付流水查询
	@RequestMapping("toesternay.do")
	public String esternay(){
		return "personaloffer/frontsystmana/esternay";
	}
	
	/**
	 * 积存金
	 * 交易流水查询 ；
	 * @return
	 */
	@RequestMapping("toreverdealwater.do")
	public String reverdealwater(){
		return "personaloffer/alarmmana/reversedealwater";
	}
	/**
	 * 积存金 --签约流水查询；
	 * @return
	 */
	@RequestMapping("tosignflod.do")
	public String sigflod(){
		return "personaloffer/delamana/Sigflod";
	}
	/**
	 * 积存金
	 * 定期申购历史价格查询；
	 * @return
	 */
	@RequestMapping("toreghistor.do")
	public String reghistor(){
		return "personaloffer/delamana/reghistor";
	}
	/**
	 * 积存金
	 * 定期申购签约解约；
	 * @return
	 */
	@RequestMapping("toregsigncon.do")
	public String regsigncon(){
		return "personaloffer/delamana/regsigncon";
	}
	/**
	 * 积存金
	 * 批量转账明细
	 * @return
	 */
	@RequestMapping("tobattrandetails.do")
	public String battrandetails(){
		return "personaloffer/delamana/battrandetails";
	}
	/**
	 * 积存金
	 * 批量转账
	 * @return
	 */
	@RequestMapping("tobattran.do")
	public String battran(){
		return "personaloffer/delamana/battran";
	}
	//账户信息统计
	@RequestMapping("toaccounttotal.do")
	public String toaccounttotal(){
		return "personaloffer/price/accounttotal";
	}
	//交易信息统计
	@RequestMapping("todealtotal.do")
	public String todealtotal(){
		return "personaloffer/price/dealtotal";
	}
	//签约信息统计
	@RequestMapping("tosigninfo.do")
	public String tosigninfo(){
		return "personaloffer/price/signinfo";
	}
	/*//结售汇-分行优惠查询
	@RequestMapping("tobranchdis1.do")
	public String tobranchdis1(){
		return "personaloffer/delamana/branchdiscountsearch1";
	}*/
}	
