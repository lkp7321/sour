package com.ylxx.qt.service.impl;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ylxx.qt.core.mapper.PriceSheetMapper;
import com.ylxx.qt.service.ISegmentationSetService;
import com.ylxx.qt.service.po.ParameterdetailsBean;
import com.ylxx.qt.service.po.PriceSheet;

@Service("segmentationsetservice")
public class SegmentationSetServiceImpl implements ISegmentationSetService {
	@Resource
	private PriceSheetMapper priceSheetMapper;

	@Override
	public Map<String, String> CreateSegmentSet(ParameterdetailsBean pb)
			throws Exception {
		DecimalFormat df = new DecimalFormat("#.00");
		Map<String,String> map = new HashMap<String, String>();
		double highpri = 0.0;//最高价
		double lowpri = 0.0;//最低价
//		double highrange = 0.0;
//		double lowrange = 0.0;
		double segmentprice = 0.0;//分割价
		double sellspace = 0.0;//做空区间
		double buyspace = 0.0;//做多区间
		String str_sell="";
		String str_buy="";
		List<PriceSheet> list = priceSheetMapper.getByid1(pb.getProductid());
		double tick = 1;
		if(list.size()>0){
			tick = list.get(0).getPricetick();//最小变动价位
		}
		
		//int bull = pb.getBullandbearratio()/10;//多
		double bear = pb.getBullandbearratio()%10;//空
		String[] strArray = pb.getWeightset().split(",");
		for (String str : strArray) {
			highpri += Double.parseDouble(str.split(";")[0])*Double.parseDouble(str.split(";")[2]);
			lowpri += Double.parseDouble(str.split(";")[1])*Double.parseDouble(str.split(";")[2]);
		}
		double temp = highpri-(highpri-lowpri)*(bear/10);//最高价-（最高价-最低价）*（空的占比）
		segmentprice = (int)(temp/tick);
		segmentprice = segmentprice*tick;//分割价
		//0是均分档，1是值分档
		if(pb.getSegmenttype()==0){
			sellspace = (int) ((highpri-segmentprice)/tick)/pb.getShortsegment();//（最高价-分割价）/最小变动价位）/空分档
			buyspace = (int) ((segmentprice-lowpri)/tick)/pb.getLongsegment();//（分割价-最低价）/最小变动价位）/多分档
			//均值做空低-->高
			for(int i=1;i<=pb.getShortsegment();i++){
				int num = 0-i;
				double h = 0.0;//高价
				double l = 0.0;//低价
				str_sell = str_sell+num;
				l = segmentprice+(i-1)*sellspace*tick;
				if(i == pb.getShortsegment()){
					h = highpri;
					str_sell = str_sell+";"+df.format(l)+";"+df.format(h);
				} else{
					h = segmentprice + i*sellspace*tick;
					str_sell = str_sell+";"+df.format(l)+";"+df.format(h)+",";
				}
			 }
			//均值做多高-->低
			for(int i=1;i<=pb.getLongsegment();i++){
				double h = 0.0;//高价
				double l = 0.0;//低价
				str_buy = str_buy+i;
				h = segmentprice - (i-1)*buyspace*tick;
				if(i == pb.getLongsegment()){
					l = lowpri;
					str_buy = str_buy+";"+df.format(h)+";"+df.format(l);
				} else{
					l = segmentprice - i*buyspace*tick;
					str_buy = str_buy+";"+df.format(h)+";"+df.format(l)+",";
				}
			}
		} else{
			double shortSeg = 0.0;//空分段数
			double longSeg = 0.0;//多分段数
			for(int i = 1;i<=pb.getShortsegment();i++){
				shortSeg +=i;
			}
			for(int i = 1;i<=pb.getLongsegment();i++){
				longSeg +=i;
			}
			sellspace = (int) ((highpri-segmentprice)/tick)/shortSeg;//（最高价-分割价）/最小变动价位）/空分档
			buyspace = (int) ((segmentprice-lowpri)/tick)/longSeg;//（分割价-最低价）/最小变动价位）/多分档
			//值均做空低-->高
			for(int i=1;i<=shortSeg;i++){
				int num = 0-i;
				double h = 0.0;//高价
				double l = 0.0;//低价
				str_sell = str_sell+num;
				l = segmentprice+(i-1)*sellspace*tick;
				if(i == shortSeg){
					h = highpri;
					str_sell = str_sell+";"+df.format(l)+";"+df.format(h);
				} else{
					h = segmentprice + i*sellspace*tick;
					str_sell = str_sell+";"+df.format(l)+";"+df.format(h)+",";
				}
			 }
			//均值做多高-->低
			for(int i=1;i<=longSeg;i++){
				double h = 0.0;//高价
				double l = 0.0;//低价
				str_buy = str_buy+i;
				h = segmentprice - (i-1)*buyspace*tick;
				if(i == longSeg){
					l = lowpri;
					str_buy = str_buy+";"+df.format(h)+";"+df.format(l);
				} else{
					l = segmentprice - i*buyspace*tick;
					str_buy = str_buy+";"+df.format(h)+";"+df.format(l)+",";
				}
			}
		}
		map.put("sell", str_sell);
		map.put("buy", str_buy);
		map.put("segmentprice", segmentprice+"");
		return map;
	}

	@Override
	public String BelongStalls(double nowprice, Map<String, String> map)
			throws Exception {
		double high = 0.0;//高价
		double low = 0.0;//低价
		boolean b = true;
		int stall = 0;
		String[] sell = map.get("sell").trim().split(",");
		String[] buy = map.get("buy").trim().split(",");
		if(nowprice == Double.parseDouble(sell[0].split(";")[1])){
			return stall+"";
		}
		//循环做空
		for (String str_sell : sell) {
			low = Double.parseDouble(str_sell.split(";")[1]);
			high = Double.parseDouble(str_sell.split(";")[2]);
			if(nowprice>low && nowprice<=high){
				stall = Integer.parseInt(str_sell.split(";")[0]);
				b = false;
				break;
			}
		}
		//循环做多
		if(b){
			for (String str_buy : buy) {
				low = Double.parseDouble(str_buy.split(";")[2]);
				high = Double.parseDouble(str_buy.split(";")[1]);
				if(nowprice<high && nowprice>=low){
					stall = Integer.parseInt(str_buy.split(";")[0]);
					break;
				}
			}
		}
		if(stall == 0){
			String[] sell_new = sell[sell.length-1].split(";");
			String[] buy_new = buy[buy.length-1].split(";");
			if(nowprice >= Double.parseDouble(sell_new[sell_new.length-1])){
				stall = Integer.parseInt(sell_new[0]);
			}else if(nowprice <= Double.parseDouble(buy_new[buy_new.length-1])){
				stall = Integer.parseInt(buy_new[0]);
			}
		}
		return stall+"";
	}

	@Override
	public double GetNowPrice() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
}
