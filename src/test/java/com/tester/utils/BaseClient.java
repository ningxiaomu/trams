package com.tester.utils;

import com.tester.domain.Case;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseClient {


    /**
     * id -->传你在sqlMapper里的select id
     * caseid --> 你测试用例的id
     * @param id
     * @return
     * @throws IOException
     */
    public static boolean NoNeedLoginClient(String id,String caseid) throws IOException {
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

        boolean flag;
        String result = null;
        //建立session连接
        SqlSession session = DatabaseUtil.getSqlSession();
        //获取用例
        Case caseInfo = session.selectOne(id,caseid);
        try{
            //get请求+无参
            if((caseInfo.getNeed_login().equals("是")&&caseInfo.getMethod().equals("get")&&caseInfo.getParameter()==null)){
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
                        //System.out.println("result:"+result);
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
                       // System.out.println("result:"+result);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    //释放连接
                    client.close();
                }
            }
            //需要依赖登录的情况 ->post请求+无参
            else if(caseInfo.getNeed_login().equals("是")&&caseInfo.getMethod().equals("post")&&caseInfo.getParameter()==null){
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
                        //System.out.println("result:"+result);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    //释放连接
                    client.close();
                }
            }
            //需要依赖登录的情况 ->post请求+有参【表单提交】
            else if(caseInfo.getNeed_login().equals("是")&&caseInfo.getMethod().equals("post")&&caseInfo.getParameter()!=null){
                //获取接口地址
                String testuri = caseInfo.getDomain()+caseInfo.getRequestAddress();
                //创建client对象
                DefaultHttpClient client = new DefaultHttpClient();
                client.setCookieStore(cookieStore);
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

                //创建表单Entity对象
                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list,"UTF-8");
                //设置表单Entity对象到POST请求中去
                httpPost.setEntity(formEntity);
                try{
                    response = client.execute(httpPost);
                    //判断返回的状态码
                    if(response.getStatusLine().getStatusCode()==200||response.getStatusLine().getStatusCode()==302){
                        result = EntityUtils.toString(response.getEntity(),"UTF-8");
                        //System.out.println("result:"+result);
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
        if(flag=false){
            System.out.println("失败用例的返回结果为："+result);
        }
        return flag;
    }

}
