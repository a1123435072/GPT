package cn.zzu.googleplaytest.base;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import cn.zzu.googleplaytest.conf.Constants;
import cn.zzu.googleplaytest.utils.HttpUtils;
import cn.zzu.googleplaytest.utils.LogUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yangg on 2017/7/12.
 *
 */

public abstract class BaseProtocolNew<T> {

    private static String interfaceKey;

    public  T loadData(int index) throws Exception {

        //SystemClock.sleep(500);//模拟耗时操作

        //创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2,拼接url
        //http://localhost:8080/GooglePlayServer/home

//        String url = Constants.URLS.BASEURL + "/home";
        String url = Constants.URLS.BASEURL + getInterfaceKey();

        //定义参数对应的map
        Map<String, Object> params = new HashMap<>();
        params.put("index", index);//暂时考虑分页
        //拼接参数信息?index =0 把map中的转换成一个字符串
        String urlParamsByMap = HttpUtils.getUrlParamsByMap(params);

        //url凭借参数与对应的字符串信息:http://localhost:8080/GooglePlayServer/home?index=0
        url = url + "?" + urlParamsByMap;
        LogUtils.i("url------------------->" + url);
        //创建请求对象
        Request request = new Request.Builder().get().url(url).build();

        //3,发起情i去-->同步请求
        //因为线程池中已经使用了一一异步-->这里使用同步的就星了

        Response response = okHttpClient.newCall(request).execute();
        //4,解析相应的结果
        if (response.isSuccessful()) {
            //取出响应的内容
            String resJsonString = response.body().string();
            LogUtils.i("resJsonString" + resJsonString);

            Gson gson = new Gson();
            //HomeBean homeBean = gson.fromJson(resJsonString, HomeBean.class);

            //LogUtils.i("resJsonString"+resJsonString);


            return parseJson(resJsonString);

        } else {
            //没有获取到homebean  返回一个空
            return null;
        }


    }

    /**
     * 负责解析网络请求回来的额jsonString
     * 一共是哪种解析情况(节点解析,Bean解析,泛型解析)
     * @param resJsonString
     * @return
     */
    protected abstract T parseJson(String resJsonString);

    /**
     * 得到协议的关键字
     * 在BaseFragment中,不知道协议关键字具体是什么,交给子类
     * 子类是必须实现,所以定义为抽象方法,,,,交给子类去实现,因为key是固定的,
     * 而loadData()方法可能会执行对此,用这种方法传递参数,我们,在使用这个框架的时候,就不用,
     * 每次都传参数了
     * @return
     */
    public abstract String getInterfaceKey() ;


}
