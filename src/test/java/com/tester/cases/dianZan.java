package com.tester.cases;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class dianZan {

    @Test
    public static void main(String[] args)throws IOException {
        //get请求
//    String result;
//    HttpGet get = new HttpGet("https://www.baidu.com");
//
//    HttpClient client = new DefaultHttpClient();
//
//    HttpResponse response = client.execute(get);
//
//    result = EntityUtils.toString(response.getEntity(),"utf-8");
//    System.out.println(result);
//    int a = result.indexOf("登陆");
//    if(result.indexOf("更多产品")!=-1) {
//      assert true;
//      System.out.println("检验成功");
//    }else {
//      System.out.println("校验失败");
//      assert false;
//    }
        // post 请求

        HttpClient client = new DefaultHttpClient();
//    CloseableHttpClient httpclient = HttpClients.createDefault();
        // 通过HttpPost来发送post请求
        HttpPost httpPost = new HttpPost("http://127.0.0.1/zentao/user-login-L3plbnRhby8=.html");
        /*
         * post带参数开始
         */
        // 第三步：构造list集合，往里面丢数据
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        BasicNameValuePair basicNameValuePair = new BasicNameValuePair("account", "admin");
        BasicNameValuePair basicNameValuePair2 = new BasicNameValuePair("password", "e10adc3949ba59abbe56e057f20f883e");
        BasicNameValuePair basicNameValuePair3 = new BasicNameValuePair("referer", "/zentao/");
        list.add(basicNameValuePair);
        list.add(basicNameValuePair2);
        list.add(basicNameValuePair3);

        //设置关闭后面关闭连接
        httpPost.setHeader("Connection","close");
        // 第二步：我们发现Entity是一个接口，所以只能找实现类，发现实现类又需要一个集合，集合的泛型是NameValuePair类型
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list);
        // 第一步：通过setEntity 将我们的entity对象传递过去
        httpPost.setEntity(formEntity);
        /*
         * post带参数结束
         */
        // HttpEntity
        // 是一个中间的桥梁，在httpClient里面，是连接我们的请求与响应的一个中间桥梁，所有的请求参数都是通过HttpEntity携带过去的
        // 通过client来执行请求，获取一个响应结果
        HttpResponse response = client.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String str = EntityUtils.toString(entity, "UTF-8");

        BasicCookieStore cookieStore = new BasicCookieStore();

        List<Cookie> cookies = cookieStore.getCookies();
        System.out.println(cookies);
        for(Cookie cookie:cookies){
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println("cookies: key= "+ name + "  value= " + value);
        }
        System.out.println(str);
        // 关闭

        String testGetKuid = "http://127.0.0.1:81/zentao/bug-create-1-0-moduleID=0.html";
        String resultKuid;
//    HttpGet get = new HttpGet("https://www.baidu.com");

   // HttpClient client = new DefaultHttpClient();

//    HttpResponse response2 = client.execute(get);
//
//    resultKuid = EntityUtils.toString(response2.getEntity(),"utf-8");
//    System.out.println(resultKuid);




    }
}
