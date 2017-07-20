package cn.zzu.googleplaytest.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import cn.zzu.googleplaytest.base.BaseProtocol;
import cn.zzu.googleplaytest.bean.HomeBean;
import cn.zzu.googleplaytest.bean.ListBean;

/**
 * Created by yangg on 2017/7/12.
 *
 * List<ListBean>  这里为什么要用这个数据  啊啊啊啊啊啊啊啊啊啊啊啊啊啊
 *
 */

public class AppProtocol extends BaseProtocol<List<ListBean>> {

    /**
     * 决定洗衣的关键字
     */

    @Override
    public String getInterfaceKey() {
        return "app";
    }


    /**
     * 完成网络请求回来jsonString 的解析
     *
     * @param resJsonString
     * @return
     */
    @Override
    protected List<ListBean> parseJson(String resJsonString) {
        Gson gson = new Gson();

        //为什么要用泛型解析
        return gson.fromJson(resJsonString, new TypeToken<List<ListBean>>(){}.getType());
    }

    /**
     * 有加载更多,重写加载更多的开关
     */
    /**
     * 具体完成加载更多的过程
     */
}
