package com.tester.utils;

import com.tester.domain.Case;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class BaseClient {

    private static final Logger log = Logger.getLogger(BaseClient.class);


    /**
     * id -->传你在sqlMapper里的select id
     * caseid --> 你测试用例的id
     * @param id
     * @return
     * @throws IOException
     *
     */

    public static boolean NoNeedLoginClient(String id,String caseid) throws IOException {
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(60000)
                .setConnectTimeout(60000)
                .setConnectionRequestTimeout(60000)
                .build();
        boolean flag;
        String result = null;
        //建立session连接
        SqlSession session = DatabaseUtil.getSqlSession();
        //获取用例
        Case caseInfo = session.selectOne(id,caseid);
        try{
            //不需要依赖登录的情况 -> get请求+有参
            if(caseInfo.getNeed_login().equals("否")&&caseInfo.getMethod().equals("get")&&caseInfo.getParameter()!=null){
                //获取接口地址
                String testuri = caseInfo.getDomain()+caseInfo.getRequestAddress()+"?"+caseInfo.getParameter();
                //建立client对象
                DefaultHttpClient client = new DefaultHttpClient();
                //创建get请求
                HttpGet httpGet = new HttpGet(testuri);
                HttpResponse response = null;
                try{
                    response = client.execute(httpGet);
                    //判断响应状态码是200或者302
                    if(response.getStatusLine().getStatusCode()==200 ||response.getStatusLine().getStatusCode()==302){
                        result = EntityUtils.toString(response.getEntity(),"UTF-8");
                        log.info(caseid+"的result:"+result);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    //释放连接
                    client.close();
                }
            }
            //不需要依赖登录的情况 -> get请求+无参
            else if(caseInfo.getNeed_login().equals("否")&&caseInfo.getMethod().equals("get")&&caseInfo.getParameter()==null){
                //获取接口地址
                String testuri = caseInfo.getDomain()+caseInfo.getRequestAddress();
                //创建client对象
                DefaultHttpClient client = new DefaultHttpClient();
                //创建get请求
                HttpGet httpGet = new HttpGet(testuri);
                HttpResponse response = null;
                try{
                    response = client.execute(httpGet);
                    //判断响应状态码是200或者302
                    if(response.getStatusLine().getStatusCode()==200||response.getStatusLine().getStatusCode()==302){
                        result = EntityUtils.toString(response.getEntity(),"UTF-8");
                        log.info(caseid+"的result:"+result);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    //释放连接
                    client.close();
                }
            }
            //不需要依赖登录的情况 ->post请求+无参
            else if(caseInfo.getNeed_login().equals("否")&&caseInfo.getMethod().equals("post")&&caseInfo.getParameter()==null){
                //获取接口地址
                String testuri = caseInfo.getDomain()+caseInfo.getRequestAddress();
                //创建client对象
                DefaultHttpClient client = new DefaultHttpClient();
                //创建post请求
                HttpPost httpPost = new HttpPost(testuri);
                HttpResponse response = null;
                try{
                    response = client.execute(httpPost);
                    //判断响应状态码是200或者302
                    if(response.getStatusLine().getStatusCode()==200||response.getStatusLine().getStatusCode()==302){
                        result = EntityUtils.toString(response.getEntity(),"UTF-8");
                        log.info(caseid+"的result:"+result);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    //释放连接
                    client.close();
                }
            }
            //不需要依赖登录的情况 ->post请求+有参【表单提交】
            else if(caseInfo.getNeed_login().equals("否")&&caseInfo.getMethod().equals("post")&&caseInfo.getParameter()!=null){
                //获取接口地址
                String testuri = caseInfo.getDomain()+caseInfo.getRequestAddress();
                //创建client对象
                DefaultHttpClient client = new DefaultHttpClient();
                //创建post请求
                HttpPost httpPost = new HttpPost(testuri);
                httpPost.setConfig(requestConfig);
                HttpResponse response = null;
                //获取请求的参数
                String par = caseInfo.getParameter();
                List<NameValuePair> list = new ArrayList<NameValuePair>();
                Map<String,String> map = new HashMap<String,String>();
                if(par!=null){
                    String[] result1 = par.split(";");
                    for (int j = 0; j <result1.length ; j++) {
                        int index = result1[j].indexOf("=");
                        map.put(result1[j].substring(0,index),result1[j].substring(index+1));
                    }
                }
                //将参数封装到list集合中去
                for(Map.Entry<String,String> entry:map.entrySet()){
                    String key = entry.getKey();
                    String value = entry.getValue();
                    BasicNameValuePair basicNameValuePair = new BasicNameValuePair(key, value);
                    list.add(basicNameValuePair);
                }

                //创建表单Entity对象
                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list,"UTF-8");
                //设置表单Entity对象到POST请求中去
                httpPost.setEntity(formEntity);
                try{
                    response = client.execute(httpPost);
                    //判断返回的状态码
                    if(response.getStatusLine().getStatusCode()==200||response.getStatusLine().getStatusCode()==302){
                        result = EntityUtils.toString(response.getEntity(),"UTF-8");
                        log.info(caseid+"的result:"+result);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    //关闭连接
                    client.close();
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        //判断caseinfo里的预期结果是否在result里存在
        String exresult = caseInfo.getExResult();
        flag = result.contains(exresult);
        return flag;
    }


    //需要依赖登录的接口
    public static boolean NeedLoginClient(String id,String caseid,CookieStore cookieStore) throws IOException {
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(60000)
                .setConnectTimeout(60000)
                .setConnectionRequestTimeout(60000)
                .build();

        boolean flag;
        String result = null;
        //建立session连接
        SqlSession session = DatabaseUtil.getSqlSession();
        //获取用例
        Case caseInfo = session.selectOne(id,caseid);
        try{
            //get请求+无参
            if((caseInfo.getNeed_login().equals("是")&&caseInfo.getMethod().equals("get")&&caseInfo.getParameter().equals(""))){
                //获取接口地址
                String testuri = caseInfo.getDomain()+caseInfo.getRequestAddress();
                //创建client对象
                DefaultHttpClient client = new DefaultHttpClient();
                //设置cookie
                client.setCookieStore(cookieStore);
                //创建get请求
                HttpGet httpGet = new HttpGet(testuri);
                HttpResponse response = null;
                try{
                    response = client.execute(httpGet);
                    //判断响应状态码是200或者302
                    if(response.getStatusLine().getStatusCode()==200||response.getStatusLine().getStatusCode()==302){
                        result = EntityUtils.toString(response.getEntity(),"UTF-8");
                        log.info(caseid+"的result:"+result);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    //释放连接
                    client.close();
                }

            }

            //需要依赖登录->get请求+有参
            else if((caseInfo.getNeed_login().equals("是")&&caseInfo.getMethod().equals("get")&&caseInfo.getParameter()!=null)){
                //获取接口地址
                String testuri = caseInfo.getDomain()+caseInfo.getRequestAddress()+"?"+caseInfo.getParameter();
                //建立client对象
                DefaultHttpClient client = new DefaultHttpClient();
                client.setCookieStore(cookieStore);
                //创建get请求
                HttpGet httpGet = new HttpGet(testuri);
                HttpResponse response = null;
                try{
                    response = client.execute(httpGet);
                    //判断响应状态码是200或者302
                    if(response.getStatusLine().getStatusCode()==200 ||response.getStatusLine().getStatusCode()==302){
                        result = EntityUtils.toString(response.getEntity(),"UTF-8");
                        log.info(caseid+"的result:"+result);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    //释放连接
                    client.close();
                }
            }
            //需要依赖登录的情况 ->post请求+无参
            else if(caseInfo.getNeed_login().equals("是")&&caseInfo.getMethod().equals("post")&&caseInfo.getParameter().equals("")){
                System.out.println("需要依赖登录的情况 ->post请求+无参");
                //获取接口地址
                String testuri = caseInfo.getDomain()+caseInfo.getRequestAddress();
                //创建client对象
                DefaultHttpClient client = new DefaultHttpClient();
                client.setCookieStore(cookieStore);
                //创建post请求
                HttpPost httpPost = new HttpPost(testuri);
                HttpResponse response = null;
                try{
                    response = client.execute(httpPost);
                    //判断响应状态码是200或者302
                    if(response.getStatusLine().getStatusCode()==200||response.getStatusLine().getStatusCode()==302){
                        result = EntityUtils.toString(response.getEntity(),"UTF-8");
                        log.info(caseid+"的result:"+result);
                    }else if(response.getStatusLine().getStatusCode()==500){
                        log.info("状态码为500");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    //释放连接
                    client.close();
                }
            }
            //需要依赖登录的情况 ->post请求+有参【普通表单提交】
            else if(caseInfo.getNeed_login().equals("是")&&caseInfo.getMethod().equals("post")&&caseInfo.getParameter()!=null&&caseInfo.getContentType().equals("application/x-www-form-urlencoded")){
                //获取接口地址
                String testuri = caseInfo.getDomain()+caseInfo.getRequestAddress();
                //创建client对象
                DefaultHttpClient client = new DefaultHttpClient();
                client.setCookieStore(cookieStore);
                //创建post请求
                HttpPost httpPost = new HttpPost(testuri);
                httpPost.setConfig(requestConfig);
                HttpResponse response = null;
                //获取请求的参数
                String par = caseInfo.getParameter();
                List<NameValuePair> list = new ArrayList<NameValuePair>();
                Map<String,String> map = new HashMap<String,String>();
                System.out.println("par:"+par);
                if(par!=null){
                    String[] result1 = par.split(";");
                    for (int j = 0; j <result1.length ; j++) {
                        int index = result1[j].indexOf("=");
                        map.put(result1[j].substring(0,index),result1[j].substring(index+1));
                    }
                }
                //将参数封装到list集合中去
                for(Map.Entry<String,String> entry:map.entrySet()){
                    String key = entry.getKey();
                    String value = entry.getValue();
                    BasicNameValuePair basicNameValuePair = new BasicNameValuePair(key, value);
                    list.add(basicNameValuePair);
                }

                //创建表单Entity对象
                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list,"UTF-8");
                //设置表单Entity对象到POST请求中去
                httpPost.setEntity(formEntity);
                try{
                    response = client.execute(httpPost);
                    //判断返回的状态码
                    if(response.getStatusLine().getStatusCode()==200||response.getStatusLine().getStatusCode()==302){
                        result = EntityUtils.toString(response.getEntity(),"UTF-8");
                        log.info(caseid+"的result:"+result);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    //关闭连接
                    client.close();
                }

            }
            //需要依赖登录的情况 ->post请求+有参【json格式】
            else if(caseInfo.getNeed_login().equals("是")&&caseInfo.getMethod().equals("post")&&caseInfo.getParameter()!=null&&caseInfo.getContentType().equals("application/json")){
                //获取接口地址
                String testuri = caseInfo.getDomain()+caseInfo.getRequestAddress();
                //获取参数
                String par = caseInfo.getParameter();
                //创建client对象
                DefaultHttpClient client = new DefaultHttpClient();
                client.setCookieStore(cookieStore);
                JSONObject jsonObject = new JSONObject();
                String val = null;
                //获取时间戳
                long time = new Date().getTime();
                String Time = String.valueOf(time);
                //创建post请求
                HttpPost httpPost = new HttpPost(testuri);
                httpPost.setConfig(requestConfig);
                HttpResponse response = null;
                System.out.println("par:"+par);
                //json方式
                try{
                    if(par!=null){
                        String[] result1 = par.split(";");
                        for (int j = 0; j <result1.length ; j++) {
                            int index = result1[j].indexOf("=");
                            if(!(result1[j].substring(index+1).equals("1")||result1[j].substring(index+1).equals("0"))){
                                System.out.println(result1[j].substring(index+1)+Time);
                                val = result1[j].substring(index+1)+Time;
                            }else {
                                val = result1[j].substring(index+1);
                            }
                            jsonObject.put(result1[j].substring(0,index),val);
                        }
                    }
                    System.out.println("json:"+jsonObject);
                    StringEntity entity = new StringEntity(jsonObject.toString(),"utf-8");
                    System.out.println("entity:"+entity);
                    entity.setContentEncoding("UTF-8");
                    entity.setContentType("application/json");
                    httpPost.setEntity(entity);
                    System.out.println();

                     response = client.execute(httpPost);
                    System.out.println(response.getStatusLine().getStatusCode());
                    if(response.getStatusLine().getStatusCode()==200||response.getStatusLine().getStatusCode()==302){
                        HttpEntity he = response.getEntity();
//                        System.out.println("he:"+he);
                        result = EntityUtils.toString(he,"UTF-8");
                        log.info(caseid+"的result:"+result);
                    }else if(response.getStatusLine().getStatusCode()==500){
                        System.out.println("状态码为：500；测试失败");
                        HttpEntity he = response.getEntity();
                        System.out.println("he:"+he);
                        result = EntityUtils.toString(he,"UTF-8");
                        log.info(caseid+"的result:"+result);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    client.close();
                }

            }
            //需要依赖登录的情况 ->post请求+有参【multipart/form-data】 主要是文件上传的表单形式
            else if(caseInfo.getNeed_login().equals("是")&&caseInfo.getMethod().equals("post")&&caseInfo.getParameter()!=null&&caseInfo.getContentType().equals("application/x-www-form-urlencoded")){

                //获取接口地址
                String testuri = caseInfo.getDomain()+caseInfo.getRequestAddress();
                DefaultHttpClient httpClient = null;
                CloseableHttpResponse response = null;
                try {
                    httpClient = new DefaultHttpClient();
                    httpClient.setCookieStore(cookieStore);

                    // 把一个普通参数和文件上传给下面这个地址 是一个servlet
                    HttpPost httpPost = new HttpPost(testuri);

//                    String filePath = this.getClass().getClassLoader().getResource("testFiles/2.txt").getPath();
                    String filePath="";
                    // 把文件转换成流对象FileBody
                   // FileBody bin = new FileBody(new File("D:\\TSBrowserDownloads\\login.txt"));
                    FileBody bin = new FileBody(new File(filePath));
                    System.out.println("bin:"+bin);



                    HttpEntity reqEntity = MultipartEntityBuilder.create()
                            // 相当于<input type="file" name="upload"/>
                            .addPart("upload", bin)

                            // 相当于<input type="text" name="userName" value=userName>
//                    .addPart("userName", userName)
//                    .addPart("pass", password)
                            .build();

                    httpPost.setEntity(reqEntity);

                    // 发起请求 并返回请求的响应
                    response = httpClient.execute(httpPost);


                    // 获取响应对象
                    HttpEntity resEntity = response.getEntity();
                    System.out.println("resEntity:"+resEntity);
                    if (resEntity != null) {
                        // 打印响应长度
                        System.out.println("Response content length: " + resEntity.getContentLength());
                        // 打印响应内容
                        System.out.println(EntityUtils.toString(resEntity, Charset.forName("UTF-8")));
                        log.info(caseid+"的result:"+EntityUtils.toString(resEntity, Charset.forName("UTF-8")));
                    }

                    // 销毁
//            EntityUtils.consume(resEntity);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    try {
                        if(response != null){
                            response.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        if(httpClient != null){
                            httpClient.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        //判断caseinfo里的预期结果是否在result里存在
        String exresult = caseInfo.getExResult();
        System.out.println("exresult:"+exresult);
        flag = result.contains(exresult);
        return flag;
    }



}
