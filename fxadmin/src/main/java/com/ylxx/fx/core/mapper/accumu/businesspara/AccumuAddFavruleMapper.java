package com.ylxx.fx.core.mapper.accumu.businesspara;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.Trd_CapitalTransfer;
/**
 * 电商积存金
 * @author lz130
 *
 */
public interface AccumuAddFavruleMapper{
	/**
	 * 优惠规则获取下拉列表
	 * @return
	 */
	public List<HashMap<String, String>> getBoxList();
	
	/**
	 * 优惠规则查询
	 * @param ornm
	 * @return
	 */
	List<HashMap<String,String>> getValueList(@Param("ornm")String ornm);
	/**
	 * 添加最大优惠
	 * @param idog
	 * @param maxyh
	 */
	void insetMaxyh(@Param("idog")String idog,@Param("maxyh")String maxyh);
	//更新最大优惠
	public void updateMaxyh(@Param("maxyh")String maxyh,@Param("idog")String idog);
	//添加非区间的优惠(保存按钮)
	public void insertFavrule1(@Param("idog")String idog,@Param("bm")String bm,@Param("yhmc")String yhmc,@Param("rule")String rule);
	//修改规则保存按钮
	public void updatefavrule(@Param("yhmc")String yhmc,@Param("rule")String rule,@Param("idog")String idog,@Param("yhbm")String yhbm);
	/**
	 * 查询电商商户号 
	 * @return
	 */
	List<HashMap<String,String>> queryShnoInfo();
	//删除优惠规则
	public void getDeleteList(@Param("ogcd")String ogcd,@Param("fvid")String fvid);
	//电商积存金查询 
	public int getTransferTotal(@Param("trdtbegin")String trdtbegin , @Param("trdtend") String trdtend,
			@Param("shno")String shno ,@Param("dirc")String dirc );
	/**
	 * 电商积存金查询
	 * @param trdtbegin
	 * @param trdtend
	 * @param shno
	 * @param dirc
	 * @return
	 */
	public List<Trd_CapitalTransfer> selectTranfer(@Param("trdtbegin")String trdtbegin,@Param("trdtend")String trdtend,@Param("shno")String shno,@Param("dirc")String dirc);
	/**
	 * 金生金查询
	 */
	List<Map<String, Object>> selectGold(@Param("pdtp")String pdtp);
	/**
	 * 金生金
	 * 交易总额查询
	 * @return
	 */
	String selectSumcblv(@Param("pdtp")String pdtp);
	/**
	 * 电商积存金手动中止
	 * @param shno
	 * @param fldt
	 * @param mafl
	 * @param day
	 * @param time
	 */
	void stopOrStrat(@Param("shno")String shno,@Param("fldt")String fldt,@Param("mafl")String mafl,@Param("day")String day,@Param("time")String time);
	//初始化页面后获取该机构的目前的优惠
	public List<HashMap<String, String>> getSearchList(@Param("ogcd")String ogcd);
	public String getMax(@Param("ogcd")String ogcd);
	public String getinitList(@Param("ogcd")String ogcd);
	
	public List<HashMap<String, String>> FavruleList(@Param("ogcd")String ogcd,@Param("fvid")String fvid);
	public String LableList(@Param("ogcd")String ogcd,@Param("fvid")String fvid);
}