#####1 将该moule直接导入项目工程
#####2 在Application中添加 x.Ext.init(this);
#####3 在AndroidManifest.xml中添加网络权限
```
<uses-permission android:name="android.permission.INTERNET" />
```
#####4 使用方法列如get请求
```
HashMap<String,Object> map = new HashMap<>();
                map.put("username","admin");
                map.put("userpass","admin");
                HttpUtil.doApiRequest(url, map, new ApiCallBack() {
                    @Override
                    public void onFailure(String error) {
                        showToast("==错误信息=="+error.toString());
                    }

                    @Override
                    public void onSuccess(String result) {
                        showToast("==正确=="+result);
                    }
                });
```
