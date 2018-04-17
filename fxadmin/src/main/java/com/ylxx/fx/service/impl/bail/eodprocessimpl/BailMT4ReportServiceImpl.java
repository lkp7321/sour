package com.ylxx.fx.service.impl.bail.eodprocessimpl;

import java.io.File;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.core.mapper.bail.eodprocess.BailMT4ReportMapper;
import com.ylxx.fx.service.bail.eodprocess.IBailMT4ReportService;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ReadFileConfig;
import com.ylxx.fx.utils.ResultDomain;

@Service("bailMT4ReportService")
public class BailMT4ReportServiceImpl implements IBailMT4ReportService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BailMT4ReportServiceImpl.class);
	@Resource
	private BailMT4ReportMapper bailMT4ReportMapper;

	//检查文件是否存在
	public String checkFileExist(String date) throws Exception {
		Boolean[] bls = {false,false,false,false};
		ReadFileConfig readFileConfig = new ReadFileConfig();
		readFileConfig.readProperties("filePath");
		String fileName=readFileConfig.getFilePath();
		LOGGER.info("配置文件中读到的路径是："+fileName);
		File file = new File(fileName);
		if(file.exists()){
			File file1=new File(fileName+File.separator+date);
			if(file1.exists()){
				File file2=new File(fileName+File.separator+date+"/opentrades.fxi.htm");
				if(file2.exists()){
					bls[0]=true;
				}
				File file3=new File(fileName+File.separator+date+"/closedtrades.fxi.htm");
				if(file3.exists()){
					bls[1]=true;
				}
				File file4=new File(fileName+File.separator+date+"/opentrades.fxe.htm");
				if(file4.exists()){
					bls[2]=true;
				}
				File file5=new File(fileName+File.separator+date+"/closedtrades.fxe.htm");
				if(file5.exists()){
					bls[3]=true;
				}
			}
		}
		return ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(bls));
	}

}
