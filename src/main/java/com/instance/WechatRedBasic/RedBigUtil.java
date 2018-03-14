package com.instance.WechatRedBasic;


import com.instance.util.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.security.KeyStore;
import java.util.*;

/**
 * 微信公众号红包
 * @author wxy
 */
public class RedBigUtil {
    /**公众号appid*/
    private static final String APPID = "111111111";
	/** 微信红包所需信息 */
    /**微信商户号macid*/
    private final static String MCH_ID = "1324233434";
    /**微信商户号key*/
    private final static String KEY = "aaaaaasdasdas";
    /**客户端ip*/
    private static final String CLIENT_IP = "112.124.97.40";
	/**发送红包接口*/
	private static final String RED_ENVELOPES_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
	/** CA证书路径 */
    private static final String CERTIFICATE_PATH = "//richest//cert//apiclient_cert.p12";

	/**
	 * 生成微信红包的商户订单号
	 * @return code 订单号
	 */
	private static String getCampaignCode() {
		String code = getRedString(10);
		//任意字符只要不重复
		code += "wxy";
		Date date = new Date();
		code += String.valueOf(date.getTime());
		return code;
	}

	/**
	 * 成红包随机字符串
	 * @param length 随机字符串长度
	 * @return string
	 */
	public static String getRedString(int length) {
		String str = "abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuilder sf = new StringBuilder();
		for (int i = 0; i < length; i++) {
			// 0~61
			int number = random.nextInt(61);
			sf.append(str.charAt(number));
		}
		return sf.toString();
	}

	/**
	 * 红包生成秘钥sign
	 * @param map 信息map
	 * @return sign string
	 */
	private static String createSign(Map<String, Object> map) {
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		for (Map.Entry<String, Object> m : map.entrySet()) {
			packageParams.put(m.getKey(), m.getValue().toString());
		}
		StringBuilder sb = new StringBuilder();
		Set<?> es = packageParams.entrySet();
        for (Object e : es) {
            Map.Entry entry = (Map.Entry) e;
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (v != null && !"".equals(k) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k).append("=").append(v).append("&");
            }
        }
		sb.append("key=").append(map.get("key"));
		return DigestUtils.md5Hex(sb.toString()).toUpperCase();
	}

	/**
	 * 
	 *  发送微信现金红包
	 * @param url 微信红包接口
	 * @param mch_id 商户ID
	 * @param data 请求xml数据
	 * @return string
	 */
	private static String wechatPaymentSSL(String url, String mch_id, String data) {
		StringBuffer message = new StringBuffer();
		try {
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			String certFilePath = "E:/cert/apiclient_cert.p12";
			if ("/".equals(File.separator)) {
				certFilePath = CERTIFICATE_PATH;
			}
			FileInputStream instream = new FileInputStream(new File(certFilePath));
			keyStore.load(instream, mch_id.toCharArray());
			SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mch_id.toCharArray()).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" },
					null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			HttpPost httpost = new HttpPost(url);
			httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			httpost.setEntity(new StringEntity(data, "UTF-8"));
			CloseableHttpResponse response = httpclient.execute(httpost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
					String text;
					while ((text = bufferedReader.readLine()) != null) {
						message.append(text);
					}
				}
				EntityUtils.consume(entity);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				response.close();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return message.toString();
	}

	/**
	 * 
	 *  初始化红包基本信息
	 * @param openid 用户标志
	 * @param total_amount 发送金额(单位：分)
	 * @param wishing 祝福语
	 * @return string
	 */
	private static String initRedBasic(String openid, String total_amount, String wishing,String act_name) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("wxappid", APPID);
		map.put("mch_id", MCH_ID);
		map.put("nonce_str", getRedString(24));
		map.put("mch_billno", getCampaignCode());
		map.put("send_name", "Hello");
		map.put("re_openid", openid);
		map.put("total_amount", total_amount);
		map.put("total_num", 1);
		map.put("wishing", wishing);
		map.put("client_ip", CLIENT_IP);
		map.put("act_name", act_name);
		map.put("remark", "欢迎使用创图房产经纪人");
		map.put("key", KEY);
		String sig = createSign(map);
		map.put("sign", sig);
		return StringUtil.createXML(map);
	}

	/**
	 * 发送红包
	 * @param openid 用户openid
	 * @param money 红包金额
	 * @param wishing 祝福语
	 * @param actName
	 * @return
	 * @throws Exception
	 */
	public static Map<String,String> sendRedBasic(String openid, String money, String wishing, String actName) throws Exception {
		String reddata = RedBigUtil.initRedBasic(openid, money, wishing,actName);
		String msg = RedBigUtil.wechatPaymentSSL(RED_ENVELOPES_URL,MCH_ID, reddata);
		return CommonUtil.doXMLParse(msg);
	}

}
