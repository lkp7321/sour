package com.ylxx.fx.service.accumu.businesspara;

public interface IAccumuTransService {

	//查询买币种
	public String selbyexnm(String prod) throws Exception;
	//查询交易码
	public String querytrancode() throws Exception;
	//为第一个机构下拉框赋值
	public String queryOneOrgan() throws Exception;
	//为第二个机构下拉框赋值
	public String queryTwoOrgan(String comaogcd) throws Exception;
	//条件获得对应的数据
	public String selectAccumuTranlist(String comdata1, String comdata3, String strcuac, String trdtbegin,
			String trdtend, String byexnm, String comaogcd, String combogcd, Integer pageNo, Integer pageSize);
	
}

