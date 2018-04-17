package com.ylxx.fx.service.accumu.businesspara;

import java.util.List;
import java.util.Map;

/**
 * 电商积存金
 * @author lz130
 *
 */
public interface IAccumuAddFavruleService {
	/**
	 * 获取下拉列表
	 * @return
	 */
	String getBoxList() ;
	/**
	 * 查询
	 * @param ornm
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	String getValueList(String ornm,Integer pageNo,Integer pageSize);
	/*//添加区间
	public AddFavruleBean getBeValueList(String lable,String small,String data,String big);*/
	//添加非区间的优惠（保存按钮）
	public String insertFavrule(String idog, String yhbm, String yhmc,
			String defau, String bian, String con,String maxyh);
	//规则修改保存按钮
	public String doUpdateFav(String idog, String yhbm,String tiao, String yhmc,
			String bian, String defau, String maxyh,String con);
	/**
	 * 查询电商商户号 
	 * @return
	 */
	String queryShnoInfo();
	/**
	 * 删除优惠规则
	 * @param ogcd
	 * @param fvid
	 * @return
	 */
	String getDeleteList(String ogcd,String fvid);
		//电商积存金查询 
		public String getTransferTotal(String trdtbegin ,  String trdtend,
				String shno ,String dirc );
	/**
	 * 电商积存金查询
	 * @param trdtbegin
	 * @param trdtend
	 * @param shno
	 * @param dirc
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	String selectTranfer(String trdtbegin,String trdtend,String shno,String dirc,Integer pageNo,Integer pageSize);
	/**
	 * 金生金查询
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	String selectGold(Integer pageNo,Integer pageSize,String pdtp);
	/**
	 * 金生金查询Excel
	 * @param pdtp
	 * @return
	 */
	List<Map<String, Object>> selectAllGold(String pdtp);
	/**
	 * 金生金总交易额查询
	 * @return
	 */
	String selectSumcblv(String pdtp);
	/**
	 * 电商积存金手动中止	
	 * @param shno
	 * @param fldt
	 * @param mafl
	 * @return
	 */
	String stopOrStrat(String shno,String fldt,String mafl);
	//初始化页面后获取该机构的目前的优惠
		public String getSearchList(String ogcd);
		public String getinitList(String ogcd);
		
		public String FavruleList(String ogcd,String fvid);
		public String LableList(String ogcd,String fvid);
}

