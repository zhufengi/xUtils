package com.zf.callback;

public abstract  class ApiCallBack {

	/**
	 * 失败回调方法
	 * @param error 错误信息
	 */
	public  abstract  void onFailure(String error);
	
	/**
	 * 成功回调方法
	 * @param result
	 */
	public abstract  void onSuccess(String result);
}
