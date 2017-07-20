package cn.zzu.googleplaytest.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import cn.zzu.googleplaytest.base.BaseProtocol;
import cn.zzu.googleplaytest.bean.SubjectBean;

/**
 * Created by yangg on 2017/7/15.
 */

public class SubjectProrocol extends BaseProtocol<List<SubjectBean>> {
    @Override
    protected List<SubjectBean> parseJson(String resJsonString) {
        Gson gson = new Gson();


        return gson.fromJson(resJsonString, new TypeToken<List<SubjectBean>>() {}.getType());

    }

    @Override
    public String getInterfaceKey() {
        return "subject";
    }
}
