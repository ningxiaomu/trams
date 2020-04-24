package com.tester.cases;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class MyProjectLoginTest {

    public static void main(String[] args) throws IOException {
        String result=null;

        CookieStore cookieStore;
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://127.0.0.1/zentao/user-login-L3plbnRhby8=.html");
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        BasicNameValuePair basicNameValuePair = new BasicNameValuePair("account", "admin");
        BasicNameValuePair basicNameValuePair2 = new BasicNameValuePair("password", "e10adc3949ba59abbe56e057f20f883e");
        BasicNameValuePair basicNameValuePair3 = new BasicNameValuePair("referer", "/zentao/");
        list.add(basicNameValuePair);
        list.add(basicNameValuePair2);
        list.add(basicNameValuePair3);

        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list);

        httpPost.setEntity(formEntity);


        HttpResponse response = client.execute(httpPost);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);

        cookieStore=client.getCookieStore();
        List<Cookie> cookieList =cookieStore.getCookies();
        for(Cookie cookie:cookieList){
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println(name+":"+value);
        }

//        HttpClientContext context = HttpClientContext.create();
//        CookieStore cookieStore = new BasicCookieStore();
//        context.setCookieStore(cookieStore);
//        System.out.println("新的cookie-----------");
//        for(Cookie c:context.getCookieStore().getCookies()){
//            System.out.println(c.getName()+":"+c.getValue());
//        }

//        String username="admin1";
//        String password ="1";
//        //全局请求设置
//        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
//        //创建cookie store的本地实例
//        CookieStore cookieStore = new BasicCookieStore();
//        //创建HTTPClient上下文
//        HttpClientContext context = HttpClientContext.create();
//        context.setCookieStore(cookieStore);
//
//        //创建一个HttpClient
//        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig)
//                .setDefaultCookieStore(cookieStore).build();
//
//        CloseableHttpResponse res = null;
//
//        //创建本地的HTTP内容
//
//        HttpGet get = new HttpGet("http://127.0.0.1/zentao/user-login-L3plbnRhby8=.html");
//        res = httpClient.execute(get);
//        //获取常用cookie,在发送请求后
//        System.out.println("获取常规cookie--------------");
//        for(Cookie c:cookieStore.getCookies()){
//            System.out.println(c.getName()+":"+c.getValue());
//        }
//        res.close();
//        //构造post数据
//        List<NameValuePair> valuePairs = new LinkedList<NameValuePair>();
//        valuePairs.add(new BasicNameValuePair("account", "admin"));
//        valuePairs.add(new BasicNameValuePair("password", "e10adc3949ba59abbe56e057f20f883e"));
//        valuePairs.add(new BasicNameValuePair("referer", "/zentao/"));
//        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(valuePairs,Consts.UTF_8);
//        entity.setContentType("application/x-www-form-urlencoded");
//        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(valuePairs);
//
//        //创建一个post请求
//        HttpPost post = new HttpPost("http://127.0.0.1/zentao/user-login-L3plbnRhby8=.html");
//        post.setEntity(formEntity);
//        //注入post数据
//        //post.setEntity(entity);
//        HttpResponse response = httpClient.execute(post);
//        String result = EntityUtils.toString(response.getEntity(),"utf-8");
//        System.out.println(result);
////        res = httpClient.execute(post);
////        //打印响应信息
////        System.out.println(res);
////        res.close();
//        //放在post请求之后，获取的是响应头的set-Cookie
//
//        System.out.println("新的cookie-----------");
//        for(Cookie c:context.getCookieStore().getCookies()){
//            System.out.println(c.getName()+":"+c.getValue());
//        }

        //构造一个新的get请求，用来测试登录是否成功
//        CloseableHttpClient httpClient1 = HttpClients.createDefault();
//        HttpGet httpGet = new HttpGet("http://127.0.0.1/zentao/company-browse.html");
//        CloseableHttpResponse response1 = httpClient1.execute(httpGet);
//        String content=EntityUtils.toString(response.getEntity(),"utf-8");
//        System.out.println(content);
//        System.out.println("登录成功后的页面--------------");
//        System.out.println(content);
//        res.close();
    }
}
