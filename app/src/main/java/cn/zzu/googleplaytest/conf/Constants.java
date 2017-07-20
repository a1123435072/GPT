package cn.zzu.googleplaytest.conf;

import cn.zzu.googleplaytest.utils.LogUtils;

/**
 * Created by yangg on 2017/7/8.
 * 声明应用程序中的常量
 * 描述:TODO
 */

public class Constants {

    /**
     * LogUtils.LEVEL_ALL:打开日志(显示所有的日志输出)
     * LogUTils.LEVEL_OFF:关闭日志(屏蔽所有的日志输出)
     */
    public static final int DEBUGLEVEL = LogUtils.LEVEL_ALL;
    public static final long PROTOCOLTIMEOUT = 5 * 60 * 1000;//缓存的时效时间  5分钟
    //public static final String URSSLS ="http://10.0.1.1:8080/GooglePlayServer";


    public class URLS {
        public static final String BASEURL ="http://10.0.2.2:8080/GooglePlayServer/";
        public static final String IMGBASEURL =BASEURL+"image?name=";

    }
}
