package com.tester.cases;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.tester.domain.Case;
import com.tester.utils.BaseClient;
import com.tester.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ZenDao {
    private CookieStore cookieStore;
    private boolean flag;

    //这条用例是用来登陆的，某些接口会依赖登陆
    //取登录用例的case
    @Test
    public void login() throws IOException {
        String result = null;
        //建立session连接
        SqlSession session = DatabaseUtil.getSqlSession();
        //获取用例
        Case caseInfo = session.selectOne("testOne","e5e468b986f311eaa51100163e0d8570");
        try{
            //获取登录接口的地址
            String testuri = caseInfo.getDomain() + caseInfo.getRequestAddress();
            //建立client 连接
            DefaultHttpClient client = new DefaultHttpClient();
            //创建post请求
            HttpPost httpPost = new HttpPost(testuri);
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
            System.out.println("list:"+list);
            //创建表单Entity对象
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list,"UTF-8");
            //设置表单Entity对象到POST请求中去
            httpPost.setEntity(formEntity);
            try{
                response = client.execute(httpPost);
                //判断返回的状态码
                if(response.getStatusLine().getStatusCode()==200||response.getStatusLine().getStatusCode()==302){
                    result = EntityUtils.toString(response.getEntity(),"UTF-8");
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                //关闭连接
                client.close();
            }
            this.cookieStore=client.getCookieStore();
            List<Cookie> cookieList =cookieStore.getCookies();
            for(Cookie cookie:cookieList){
                String name = cookie.getName();
                String value = cookie.getValue();
                System.out.println(name+":"+value);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("登录结果:"+result);
    }

    @Test(dependsOnMethods = "login")
    public void test01() throws IOException {
        flag= BaseClient.NeedLoginClient("testOne","ca5cde5183b111eaa51100163e0d8570",this.cookieStore);
        Assert.assertTrue(flag);
    }
    @Test(dependsOnMethods = "login")
    public void test02() throws IOException{
        flag = BaseClient.NeedLoginClient("testOne","37b3c06c83e611eaa51100163e0d8570",this.cookieStore);
        Assert.assertTrue(flag);
    }
}

