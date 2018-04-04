package com.zf.utils;

import com.zf.callback.ApiCallBack;

import org.xutils.common.Callback.Cancelable;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * 
 * Xutils的封装
 */
public class HttpUtil {
	
	public static HttpUtil instence;
	public static HttpUtil getInstence(){
		
		return instence;
	}

	/**
	 * 无参get请求
	 * @param url
	 * @param callBack
	 */
	public static void doApiRequest(String url,final ApiCallBack callBack){
		RequestParams params = new RequestParams(url);
		params.setCacheMaxAge(1000*30);//超时时间设置为30s
		params.setMultipart(true);
		x.http().get(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				// TODO Auto-generated method stub
				callBack.onFailure(arg0.toString());
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(String arg0) {
				callBack.onSuccess(arg0);
			}
		});
	}
	
	/**
	 * post网络请求(带文件)
	 * @param url
	 * @param map
	 * @param files
	 * @param callBack
	 */
	public  static void doApiRequest(String url,HashMap<String, Object> map,HashMap<String, InputStream> files ,final ApiCallBack callBack)
	{
		RequestParams params = new RequestParams(url);
		params.setCacheMaxAge(1000*30);//超时时间设置为30s
		params.setMultipart(true);
		if(files != null){ //上传文件,文件不参与签名
			    Object[] filekeys =  files.keySet().toArray();    
				for(int i = 0; i<filekeys.length; i++)  
				{   
					 String key = (String) filekeys[i];
				     InputStream inputStream = files.get(key);
				     try {
				    	 
				    	  params.addBodyParameter(key, inputStream, inputStream.available()+"");
					} catch (Exception e) {
						// TODO: handle exception
					}
				   
				}
		}
		if(null!=map){
			for(Map.Entry<String, Object> entry : map.entrySet()){
//				params.addParameter(entry.getKey(), entry.getValue());
				params.addBodyParameter(entry.getKey(), entry.getValue(),null);
			}
		}
		//加密方式,先对key排序,在拼接字符串,在md5
//		String signStr = "";
//		if(map != null){
//			
//			Object[] keys =  map.keySet().toArray();    
//			Arrays.sort(keys);//
//		
//			for(int i = 0; i<keys.length; i++)  
//			{   
//				 String key = (String) keys[i];
//			     Object value = map.get(key);
//			     if(value == null ){
//			    	 	continue;
//			     }
//			     params.addParameter(key, value);
////			     params.addBodyParameter(key, value,null);
//			     signStr += key+"="+value+"&";
//			}  
//			if(signStr!=""){
//				signStr = signStr.substring(0,signStr.length()-1);
//
//			}
//		}
		x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				callBack.onFailure("网络出错了");
			}

			@Override
			public void onFinished() {
			}

			@Override
			public void onSuccess(String arg0) {
				callBack.onSuccess(arg0);
			}

		});
		
		
	}
	/**
	 * post网络请求(不带文件)
	 * @param url
	 * @param map
	 * @param callBack
	 */
	public static void  doApiRequest(String url,HashMap<String, Object> map,ApiCallBack callBack)
	{
		doApiRequest(url, map, null, callBack);
	}
	/**
	 * post上传文件
	 * @param 
	 */
	public static  Cancelable UpLoadFile(String url,Map<String,Object> map,final ApiCallBack callBack){
		RequestParams params=new RequestParams(url);
		if(null!=map){
			for(Map.Entry<String, Object> entry : map.entrySet()){
				params.addParameter(entry.getKey(), entry.getValue());
			}
		}
		params.setMultipart(true);
		Cancelable cancelable = x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				callBack.onFailure(arg0.toString());
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(String arg0) {
				callBack.onSuccess(arg0);
			}
		});
		return cancelable;
	}
	/**
	 * post下载文件
	 * @param 
	 */
	public static   Cancelable downLoadFile(String url,String filepath,final ApiCallBack callBack){
		final RequestParams params=new RequestParams(url);
		//设置断点续传
		params.setAutoResume(true);
		params.setSaveFilePath(filepath);
		
		Cancelable cancelable = x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				callBack.onFailure(arg0.toString());
				
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub
			}

			@Override
			public void onSuccess(String arg0) {
				String saveFilePath = params.getSaveFilePath();
				callBack.onSuccess(arg0);
				
			}
		});
		return cancelable;
	}
	
	
}
