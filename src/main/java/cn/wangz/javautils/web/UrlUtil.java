package cn.wangz.javautils.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/10/16.
 */
public class UrlUtil {

    /**
     * @作用 使用urlconnection
     * @param url
     * @param Params
     * @return
     * @throws IOException
     */
    public static String sendPost(String url,String Params) throws IOException {
        OutputStreamWriter out = null;
        BufferedReader reader = null;
        String response="";
        try {
            URL httpUrl = null; //HTTP URL类 用这个类来创建连接
            //创建URL
            httpUrl = new URL(url);
            //建立连接
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("connection", "keep-alive");
            conn.setUseCaches(false);//设置不要缓存
            conn.setInstanceFollowRedirects(true);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            //POST请求
            out = new OutputStreamWriter(
                    conn.getOutputStream());
            out.write(Params);
            out.flush();
            //读取响应
            reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String lines;
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                response+=lines;
            }
            reader.close();
            // 断开连接
            conn.disconnect();

        } finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(reader!=null){
                    reader.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }

        return response;
    }

    public static void main(String[] args) throws Exception {

//        String s = "[{\"endpoint\": \"datanode12\",\"metric\": \"MiniBaiLogSourceMetric\",\"timestamp\": "+ (1508747683361L + 60) +",\"step\": 60,\"value\": 1,\"counterType\": \"GAUGE\",\"tags\": \"item=test\"}]";
//        System.out.println(s);
//
//        UrlUtil.sendPost("http://datanode12:1988/v1/push", s);
    }
}
