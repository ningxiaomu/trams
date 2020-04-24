package com.tester.cases;

import com.tester.domain.Case;
import com.tester.utils.BaseClient;
import com.tester.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.*;

public class GetCaseList {

    private CookieStore cookieStore;
    @Test
    public void test6() throws IOException {
        boolean result ;
        result = BaseClient.NoNeedLoginClient("testselectone","7e8c8a18845411eaa51100163e0d8570");
        System.out.println(result);
        Assert.assertTrue(result);
    }
    @Test
    public void test5() throws IOException {
        boolean result ;
        result = BaseClient.NoNeedLoginClient("testselectone","f670be0982ea11eaa51100163e0d8570");
        System.out.println(result);
        Assert.assertTrue(result);
    }
    @Test
    public void test4() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        Case caseList =session.selectOne("testselectone","f670be0982ea11eaa51100163e0d8570");
        System.out.println(caseList);
        System.out.println(caseList.getDomain());
    }
    @Test
    public void login() throws IOException {
        String testuri;
        String projecturi;
        String caseuri;
        String method;
        String result=null;
        String par;
        String exresult;
        String loginaddress;
        String loginpar;
        String need_login = null;
        SqlSession session = DatabaseUtil.getSqlSession();
        List<Case> caseList =session.selectList("getCaseList");

        for (int i = 0; i <caseList.size() ; i++) {
            need_login = caseList.get(i).getNeed_login();
            DefaultHttpClient client = new DefaultHttpClient();
            if(need_login.equals("是")){
                projecturi = caseList.get(i).getDomain();
                loginaddress = caseList.get(i).getLoginaddress();
                testuri = projecturi + loginaddress;
                System.out.println("testuri:"+testuri);
                loginpar = caseList.get(i).getLoginpar();
                System.out.println("loginpar:"+loginpar);
                List<NameValuePair> list = new ArrayList<NameValuePair>();
                HttpPost httpPost = new HttpPost(testuri);
                Map<String,String> map =new HashMap();
                if(loginpar !=null){
                    String[] result1 = loginpar.split(";");
                    for (int j = 0; j <result1.length ; j++) {
                        int index = result1[j].indexOf("=");
                        map.put(result1[j].substring(0,index),result1[j].substring(index+1));
                    }
                }
                System.out.println("map:"+map);

                for(Map.Entry<String,String> entry:map.entrySet()){
                    String key = entry.getKey();
                    String value = entry.getValue();
                    System.out.println(key+":"+value);
                    BasicNameValuePair basicNameValuePair = new BasicNameValuePair(key, value);
                    list.add(basicNameValuePair);
                }
                System.out.println("拼接出来的list:"+list);
                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list);

                httpPost.setEntity(formEntity);

                HttpResponse response = client.execute(httpPost);
                result = EntityUtils.toString(response.getEntity(),"UTF-8");
                System.out.println("result:"+result);
                this.cookieStore=client.getCookieStore();
                List<Cookie> cookieList =cookieStore.getCookies();
                for(Cookie cookie:cookieList){
                    String name = cookie.getName();
                    String value = cookie.getValue();
                    System.out.println(name+":"+value);
                }
            }
        }

    }


    @Test
    public void test1() throws IOException {
        String testuri;
        String projecturi;
        String caseuri;
        String method;
        String result=null;
        String par;
        String exresult;
        String need_login;

            SqlSession session=DatabaseUtil.getSqlSession();
            List<Case> caseList = session.selectList("getCaseList");
            for (int i = 0; i <caseList.size() ; i++) {
                projecturi = caseList.get(i).getDomain();
                caseuri = caseList.get(i).getRequestAddress();
                testuri = projecturi + caseuri;
                method = caseList.get(i).getMethod();
                par = caseList.get(i).getParameter();
                exresult = caseList.get(i).getExResult();
                need_login = caseList.get(i).getNeed_login();
                boolean flag = true;
                //不需要依赖登录的接口
                if(need_login.equals("否")&&method.equals("get") &&par==null){
                    HttpGet httpGet = new HttpGet(testuri);
                    HttpClient client = new DefaultHttpClient();
                    HttpResponse response = client.execute(httpGet);
                    result = EntityUtils.toString(response.getEntity(),"utf-8");
                    System.out.println(result);
                    System.out.println(result.contains(exresult));
                    Assert.assertTrue(result.contains(exresult));

                }else if(need_login.equals("否")&&method.equals("get") &&par!=null){
                    testuri = projecturi + caseuri + "?" +par;
                    //String finalUri = URLEncoder.encode(testuri);
                    testuri = testuri.replaceAll(" ", "%3F");
                    //String finallyuri = new String(testuri);
                    System.out.println(testuri);

                    HttpGet httpGet = new HttpGet(testuri);
                    HttpClient client = new DefaultHttpClient();
                    HttpResponse response = client.execute(httpGet);
                    result = EntityUtils.toString(response.getEntity(),"utf-8");
                    System.out.println(result);
                    System.out.println(result.contains(exresult));
                    Assert.assertTrue(result.contains(exresult));
                }
                else if(need_login.equals("否")&&method.equals("post")&&par==null){
                    testuri = projecturi + caseuri;
                    HttpPost httpPost = new HttpPost(testuri);
                    HttpClient client = new DefaultHttpClient();
                    HttpResponse response = client.execute(httpPost);
                    result = EntityUtils.toString(response.getEntity(),"utf-8");
                    System.out.println(result);
                    System.out.println(result.contains(exresult));
                    Assert.assertTrue(result.contains(exresult));
                }else if(need_login.equals("否")&&method.equals("post") && par!= null){
                    testuri = projecturi + caseuri + "?" +par;
                    HttpPost httpPost = new HttpPost(testuri);
                    HttpClient client = new DefaultHttpClient();
                    HttpResponse response = client.execute(httpPost);
                    result = EntityUtils.toString(response.getEntity(),"utf-8");
                    System.out.println(result);
                    System.out.println(result.contains(exresult));
                    Assert.assertTrue(result.contains(exresult));
                }
            }
        }
        @Test
        public void test2(){
        Assert.assertEquals(1,1);
    }
        @Test(dependsOnMethods = "login")
        public void test3() throws IOException {
            String testuri;
            String projecturi;
            String caseuri;
            String method;
            String result=null;
            String par;
            String exresult;
            String loginaddress;
            String loginpar;
            String need_login = null;
            SqlSession session = DatabaseUtil.getSqlSession();
            List<Case> caseList =session.selectList("getCaseList");
            for (int i = 0; i <caseList.size() ; i++) {
                need_login = caseList.get(i).getNeed_login();
                DefaultHttpClient client = new DefaultHttpClient();
                if(need_login.equals("是")){
                    projecturi = caseList.get(i).getDomain();
                    caseuri = caseList.get(i).getRequestAddress();
                    testuri = projecturi + caseuri;
                    HttpGet httpGet = new HttpGet(testuri);
                    client.setCookieStore(this.cookieStore);
                    HttpResponse response = client.execute(httpGet);
                    result = EntityUtils.toString(response.getEntity(),"UTF-8");
                    System.out.println(result);
                }
            }
        }
    }




