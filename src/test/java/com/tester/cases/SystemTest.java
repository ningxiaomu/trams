package com.tester.cases;

import com.tester.domain.Case;
import com.tester.utils.BaseClient;
import com.tester.utils.DatabaseUtil;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemTest {
    //主要测试后台系统
    private boolean flag;
    private CookieStore cookieStore;
    @Test
    public void login() throws IOException {
        String result = null;
        //建立session连接
        SqlSession session = DatabaseUtil.getSqlSession();
        //获取用例
        Case caseInfo = session.selectOne("testOne","a0f5032787cf11eaa51100163e0d8570");
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
    public void test_findAllProject() throws IOException {
        //查找项目接口
        flag= BaseClient.NeedLoginClient("testOne","139000f5889c11eaa51100163e0d8570",this.cookieStore);
        System.out.println("Thread Id:"+Thread.currentThread().getId());
        Assert.assertTrue(flag);
    }



    @Test(dependsOnMethods = "login")
    public void test_upload() {
        DefaultHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = new DefaultHttpClient();
            httpClient.setCookieStore(cookieStore);

            // 把一个普通参数和文件上传给下面这个地址 是一个servlet
            HttpPost httpPost = new HttpPost("http://39.101.203.223:8080/file/upload");
//            httpPost.setHeader("Content-type","multipart/form-data");

            // 把文件转换成流对象FileBody
            FileBody bin = new FileBody(new File("C:\\Users\\Administrator\\IdeaProjects\\trams\\src\\test\\resources\\testFiles\\test.jpeg"));
            System.out.println("bin:"+bin);



//            StringBody password = new StringBody("123456", ContentType.create(
//                    "text/plain", Consts.UTF_8));

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

            System.out.println("The response value of token:" + response.getFirstHeader("token"));

            // 获取响应对象
            HttpEntity resEntity = response.getEntity();
            System.out.println("resEntity:"+resEntity);
            if (resEntity != null) {
                // 打印响应长度
                System.out.println("Response content length: " + resEntity.getContentLength());
                // 打印响应内容
                System.out.println(EntityUtils.toString(resEntity, Charset.forName("UTF-8")));
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

    @Test(dependsOnMethods = "login")
    public void test_json() throws IOException {
        String url = "http://39.101.203.223:8080/project/saveProject";
        //{"projectName":"t01","domain":"t02","projectDesc":"","status":"1"}
        //{"result":true}
        //Content-Type: application/json;charset=utf-8
        //设置请求超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(60000)
                .setConnectTimeout(60000)
                .setConnectionRequestTimeout(60000)
                .build();
        String response = null;
        //建立session连接
        SqlSession session = DatabaseUtil.getSqlSession();
        //获取用例
        Case caseInfo = session.selectOne("testOne","cacb36f1894111eaa51100163e0d8570");
        String par = caseInfo.getParameter();
        System.out.println(par);
        JSONObject jsonObject = new JSONObject();
        try{
            DefaultHttpClient httpClient = new DefaultHttpClient();
            httpClient.setCookieStore(cookieStore);
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            if(par!=null){
                String[] result1 = par.split(";");
                for (int j = 0; j <result1.length ; j++) {
                    int index = result1[j].indexOf("=");
                    jsonObject.put(result1[j].substring(0,index),result1[j].substring(index+1));
                }
            }
            System.out.println(jsonObject);


            StringEntity entity = new StringEntity(jsonObject.toString(),"utf-8");
            System.out.println("entity:"+entity);
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            System.out.println();

            HttpResponse response1 = httpClient.execute(httpPost);
            System.out.println(response1.getStatusLine().getStatusCode());
            if(response1.getStatusLine().getStatusCode()==200||response1.getStatusLine().getStatusCode()==302){
                HttpEntity he = response1.getEntity();
                response = EntityUtils.toString(he,"UTF-8");

            }


        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(response);

    }

    @Test(dependsOnMethods = "login")
    public void test_addProject() throws IOException {
        flag = BaseClient.NeedLoginClient("testOne","cacb36f1894111eaa51100163e0d8570",this.cookieStore);
        Assert.assertTrue(flag);
    }


}
