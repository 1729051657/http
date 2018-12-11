package com.http;

import com.google.gson.JsonObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GetData {
    /**
     * 发起http请求并获取结果
     * @param requestUrl 请求地址
     */
    public static String getRequest(String requestUrl){
        String res="";
//        JsonObject object = null;
        StringBuffer buffer = new StringBuffer();
        try{
            URL url = new URL(requestUrl);
            //打开连接
            HttpURLConnection urlCon= (HttpURLConnection)url.openConnection();
            if(200==urlCon.getResponseCode()){//连接正常，获取输入流
                InputStream is = urlCon.getInputStream();
                InputStreamReader isr = new InputStreamReader(is,"GBK");
                BufferedReader br = new BufferedReader(isr);

                String str = null;
                while((str = br.readLine())!=null){
                    buffer.append(str);
                }
                //关闭流
                br.close();
                isr.close();
                is.close();
                res = buffer.toString();
                //如果是json数据可以这样解析然后返回JsonObject类型的对象
//                JsonParser parse =new JsonParser();
//                JsonObject res2 = (JsonObject)parse.parse(buffer.toString());
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return res;
    }

    public static String postDownloadRes(String path,String post){
        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// Post请求
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            // POST需设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数(post请求的参数一般可以从浏览器里查请求的时候看到参数是哪些)
            printWriter.write(post);//post的参数 形式为xx=xx&yy=yy

            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len;
            byte[] arr = new byte[1024];
            while((len=bis.read(arr))!= -1){
                bos.write(arr,0,len);
                bos.flush();
            }
            bos.close();
            //如果是json数据可以这样解析然后返回JsonObject类型的对象
//          JsonParser parse =new JsonParser();
//          JsonObject res2 = (JsonObject)parse.parse(bos.toString("utf-8"));
            return bos.toString("utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //测试
    public static void main(String args [] ) {
//        JsonObject res = null;
        String responseStr = null;
        JsonObject res2 = null;
//        for (int k=1;k<=631;k++) {
//           //获取某一页的数据可以根据“nextPageNo=*”来指定，就是字符串拼接下，把1换成n
        System.out.println(postDownloadRes("https://try.jgc99.com/lottery/last?tephra-session-id=BQb5FJp3AP4fSz5p7VFN6VOG1544501890392&type=zypk10&size=200", ""));

    }
}
