package cn.zzu.googleplaytest.base;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.zzu.googleplaytest.conf.Constants;
import cn.zzu.googleplaytest.utils.FileUtils;
import cn.zzu.googleplaytest.utils.HttpUtils;
import cn.zzu.googleplaytest.utils.IOUtils;
import cn.zzu.googleplaytest.utils.LogUtils;
import cn.zzu.googleplaytest.utils.UIUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yangg on 2017/7/12.
 */

public abstract class BaseProtocol<T> {


    private File cacheFile;

    public T loadData(int index) throws Exception {
        T result = null;
         result = loadDataFromMem(index);
        if (result != null) {
            //本地有数据
            LogUtils.i("从内存加载数据-->" + getCacheFile(index));
            return result;
        }

        result = loadDataFromLocal(index);

        if (result!= null){
            LogUtils.i("从本地加载数据-->" + getCacheFile(index).getAbsolutePath());

            return result;
        }

        //从网络加载数据
        return loadDataFromNet(index);

    }

    //从内存加载数据
    private T loadDataFromMem(int index) {

        T result = null;

        LogUtils.i("----------从内存加载数据------------>");
        //从内存-->返回
        //找到存储结构
       MyApplication application = (MyApplication) UIUtils.getContext();Map<String, String> memProtocolCacheMap = application.getMemProtocolCacheMap();

        //判断是否有缓存
        String key = generateKey(index);

        if (memProtocolCacheMap.containsKey(key)){
            String memCacheJsonString = memProtocolCacheMap.get(key);

            result = parseJson(memCacheJsonString);

            if (result!= null){
                return result;
            }
        }
        return null;
    }

    /**
     * 从本地加载数据
     *
     * @param index
     * @return
     */
    protected T loadDataFromLocal(int index) {

        BufferedReader reader = null;
        try {
            //找到缓存文件
            File cacheFile = getCacheFile(index);

            if (cacheFile.exists()) {
                //可能有有效的缓存文件
                 reader = new BufferedReader(new FileReader(cacheFile));
                //读取缓存的生成时间
                String fistLine = reader.readLine();
                long cachelnsertTime = Long.parseLong(fistLine);

                //判断是否过期
                if ((System.currentTimeMillis()-cachelnsertTime)<Constants.PROTOCOLTIMEOUT){
                    //缓存存在的时间<定好的过期时常,,说明还没有过期
                    String diskCacheJsonString = reader.readLine();

                    /**
                     * 程序执行的流程,,硬盘中有数据,,然后保存到内存中
                     */
                    /**---------保存数据到内存------------*/
                    MyApplication application  = (MyApplication) UIUtils.getContext();
                    Map<String, String> memProtocolCacheMap = application.getMemProtocolCacheMap();
                    memProtocolCacheMap.put(generateKey(index),diskCacheJsonString);

                    LogUtils.s("保存磁盘数据到内存中-->"+generateKey(index));

                    return parseJson(diskCacheJsonString);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            IOUtils.close(reader);
        }

        return null;

    }


    /**
     * 负责解析网络请求回来的额jsonString
     * 一共是哪种解析情况(节点解析,Bean解析,泛型解析)
     *
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
     *
     * @return
     */
    public abstract String getInterfaceKey();

    /**
     * 从网络加载数据,,存本地
     *
     * @param index
     * @return
     * @throws IOException
     */
    private T loadDataFromNet(int index) throws IOException {
        //SystemClock.sleep(500);//模拟耗时操作

        //创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2,拼接url
        //http://localhost:8080/GooglePlayServer/home

//        String url = Constants.URLS.BASEURL + "/home";
        String url = Constants.URLS.BASEURL + getInterfaceKey();




        //定义参数对应的map
        /*Map<String, Object> params = new HashMap<>();
        params.put("index", index);//暂时考虑分页*/

        /**----------------修改过来的参数-实现不同的请求参数--------------------*/
        Map<String ,Object> parmas=  getParamsMap(int index);


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

            /**
             *   做内存缓存
             *   保存数据到内存
             */

            MyApplication application = (MyApplication) UIUtils.getContext();

            Map<String, String> memProtocolCacheMap = application.getMemProtocolCacheMap();
            memProtocolCacheMap.put(generateKey(index),resJsonString);

            LogUtils.s("保存数据到内存中-->"+generateKey(index));


            /** -----------保存数据到本地-------------*/

            BufferedWriter writer = null;
            try {

                File cacheFile = getCacheFile(index);
                writer = new BufferedWriter(new FileWriter(cacheFile));
                //写第一行
                writer.write(System.currentTimeMillis() + "");
                //换行
                writer.newLine();
                //写第二行
                writer.write(resJsonString);

            } catch (IOException e) {
                e.printStackTrace();

            } finally {
                IOUtils.close(writer);
            }
            LogUtils.i("网络加载数据后,保存数据到内存-->" + getCacheFile(index).getAbsolutePath());


            /** -----------完成json解析---返回----------*/

            return parseJson(resJsonString);

        } else {
            //没有获取到homebean  返回一个空
            return null;
        }

    }


    /**
     * 得到请求参数对应的HashMap
     * 子类可以复写该方法
     * @param index
     * @return
     */
    private Map<String, Object> getParamsMap(Object index) {
        return null;
    }

    //得到本地的路径    缓存文件
    public File getCacheFile(int index) {
        //有限保存到外置sc卡中.应用程序的缓存目录
        //(sccard/Android/data/包目录/json)
        String dir = FileUtils.getDir("json");

        //唯一的命中的问题 interfaceKey+"."+index;
        String fileName = generateKey(index);

        return new File(dir, fileName);
    }

    /**
     * 生成缓存的的唯一的key
     * @param index
     * @return
     */
    @NonNull
    private String generateKey(int index) {
        return getInterfaceKey() + "." + index;
    }


}
