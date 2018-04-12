package com.ylxx.qt.utils;

/**
 * @desc  : 阿里大于短信服务 接入配置
 *  初始化ascClient需要的几个参数
 *  
 * @author: suimanman
 * @date : 2018年02月11日 下午14:06:06
 */
public class Env {
    //1.产品名称:云通信短信API产品,开发者无需替换
    public static final String PRODUCT = "Dysmsapi";
    //短信API产品域名（接口地址固定，无需修改）
    public static final String DOMAIN = "dysmsapi.aliyuncs.com";
    
    //2.短信签名和模板,替换成自己的
    public static final String SIGN_NAME  = "易宽交易";
    public static final String TEMPLATE_CODE  = "SMS_125022511";
    
    //3.替换成你的AK:你的accessKeyId,参考本文档步骤2
    public static final String ACCESSKEY_ID = "LTAIxyyUXB0jHcmM";
    //你的accessKeySecret，参考本文档步骤2
    public static final String ACCESSKEY_SECRET = "hckL5X61eFFO1tNdGwqcthUul3uGhq";

}