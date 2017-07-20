package cn.zzu.googleplaytest.protocol;

import com.google.gson.Gson;

import cn.zzu.googleplaytest.base.BaseProtocol;
import cn.zzu.googleplaytest.bean.HomeBean;

/**
 * Created by yangg on 2017/7/12.
 */

public class HomeProtocol extends BaseProtocol<HomeBean> {

    /**
     * 决定洗衣的关键字
     */

    @Override
    public String getInterfaceKey() {
        return "home";
    }


    /**
     * 完成网络请求回来jsonString 的解析
     *
     * @param resJsonString
     * @return
     */
    @Override
    protected HomeBean parseJson(String resJsonString) {
        Gson gson = new Gson();

        return gson.fromJson(resJsonString, HomeBean.class);
    }

    /**
     * 有加载更多,重写加载更多的开关
     */
    /**
     * 具体完成加载更多的过程
     */
}
