package com.tester.cases;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.tester.domain.Case;
import com.tester.utils.BaseClient;
import com.tester.utils.DatabaseUtil;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;


public class ZenDao {
    private CookieStore cookieStore;
    private boolean flag;

    //这条用例是用来登陆的，某些接口会依赖登陆
    //取登录用例的case
    @Test
    public void login() throws IOException {
        System.out.println("Thread Id:"+Thread.currentThread().getId());
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
        System.out.println("Thread Id:"+Thread.currentThread().getId());
        Assert.assertTrue(flag);
    }
    @Test(dependsOnMethods = "login")
    public void test02() throws IOException{
        flag = BaseClient.NeedLoginClient("testOne","37b3c06c83e611eaa51100163e0d8570",this.cookieStore);
        System.out.println("Thread Id:"+Thread.currentThread().getId());
        Assert.assertTrue(flag);
    }


    @Test(dependsOnMethods = "login")
    public void test_addBug(){
        //提交bug 并添加附件
        System.out.println("Thread Id:"+Thread.currentThread().getId());
        DefaultHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        long time = new Date().getTime();
        String Time = String.valueOf(time);
        String result1="";

        try {
            httpClient = new DefaultHttpClient();
            httpClient.setCookieStore(cookieStore);

            // 把一个普通参数和文件上传给下面这个地址 是一个servlet
            HttpPost httpPost = new HttpPost("http://39.108.184.169:8082/zentao/bug-create-1-0-moduleID=0.html");
//            httpPost.setHeader("Content-type","multipart/form-data");
//            String Path = SystemTest.class.getClassLoader().getResource("").toString()+"testFiles/test.jpeg";

            // 把文件转换成流对象FileBody
            FileBody bin = new FileBody(new File("/usr/local/TestFile/1.jpg"));
            System.out.println("bin:"+bin);

            //开始
            StringBody product = new StringBody("1", ContentType.create("text/plain", Consts.UTF_8));
            StringBody module = new StringBody("0", ContentType.create("text/plain", Consts.UTF_8));
            StringBody project = new StringBody("", ContentType.create("text/plain", Consts.UTF_8));
            StringBody openedBuild = new StringBody("trunk", ContentType.create("text/plain", Consts.UTF_8));
            StringBody assignedTo = new StringBody("luo", ContentType.create("text/plain", Consts.UTF_8));
            StringBody deadline = new StringBody("", ContentType.create("text/plain", Consts.UTF_8));
            StringBody type = new StringBody("codeerror", ContentType.create("text/plain", Consts.UTF_8));
            StringBody os = new StringBody("", ContentType.create("text/plain", Consts.UTF_8));
            StringBody browser = new StringBody("", ContentType.create("text/plain", Consts.UTF_8));
            StringBody title = new StringBody(Time, ContentType.create("text/plain", Consts.UTF_8));
            StringBody color = new StringBody("", ContentType.create("text/plain", Consts.UTF_8));
            StringBody severity = new StringBody("3", ContentType.create("text/plain", Consts.UTF_8));
            StringBody pri = new StringBody("3", ContentType.create("text/plain", Consts.UTF_8));
            StringBody steps = new StringBody("<p>内容</p>", ContentType.create("text/plain", Consts.UTF_8));
            StringBody story = new StringBody("0", ContentType.create("text/plain", Consts.UTF_8));
            StringBody task = new StringBody("0", ContentType.create("text/plain", Consts.UTF_8));
            StringBody oldTaskID = new StringBody("0", ContentType.create("text/plain", Consts.UTF_8));
            StringBody mailto = new StringBody("", ContentType.create("text/plain", Consts.UTF_8));
            StringBody keywords = new StringBody("", ContentType.create("text/plain", Consts.UTF_8));
            StringBody status = new StringBody("active", ContentType.create("text/plain", Consts.UTF_8));
            StringBody uid = new StringBody("5ea67c4ada7ff", ContentType.create("text/plain", Consts.UTF_8));
            StringBody result = new StringBody("0", ContentType.create("text/plain", Consts.UTF_8));
            StringBody caseVersion = new StringBody("0", ContentType.create("text/plain", Consts.UTF_8));
            StringBody testtask = new StringBody("0", ContentType.create("text/plain", Consts.UTF_8));
            StringBody case1 = new StringBody("0", ContentType.create("text/plain", Consts.UTF_8));

            //结束



            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    // 相当于<input type="file" name="upload"/>
                    .addPart("files[]", bin)
                    .addPart("product",product)
                    .addPart("module",module)
                    .addPart("project",project)
                    .addPart("openedBuild[]",openedBuild)
                    .addPart("assignedTo",assignedTo)
                    .addPart("deadline",deadline)
                    .addPart("type",type)
                    .addPart("os",os)
                    .addPart("browser",browser)
                    .addPart("title",title)
                    .addPart("color",color)
                    .addPart("severity",severity)
                    .addPart("pri",pri)
                    .addPart("steps",steps)
                    .addPart("story",story)
                    .addPart("task",task)
                    .addPart("oldTaskID",oldTaskID)
                    .addPart("mailto[]",mailto)
                    .addPart("keywords",keywords)
                    .addPart("status",status)
                    .addPart("uid",uid)
                    .addPart("case",case1)
                    .addPart("caseVersion",caseVersion)
                    .addPart("result",result)
                    .addPart("testtask",testtask)

                    // 相当于<input type="text" name="userName" value=userName>
//                    .addPart("userName", userName)
//                    .addPart("pass", password)
                    .build();

            httpPost.setEntity(reqEntity);

            // 发起请求 并返回请求的响应
            response = httpClient.execute(httpPost);

//            System.out.println("The response value of token:" + response.getFirstHeader("token"));

            // 获取响应对象
            HttpEntity resEntity = response.getEntity();
            System.out.println("resEntity:"+resEntity);
            if (resEntity != null) {
                // 打印响应长度
                System.out.println("Response content length: " + resEntity.getContentLength());
                // 打印响应内容
                result1 = EntityUtils.toString(resEntity, Charset.forName("UTF-8"));
//                System.out.println(EntityUtils.toString(resEntity, Charset.forName("UTF-8")));
            }

            if(result1.contains("保存成功")){
                flag = true;
            }else {
                flag = false;
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

        Assert.assertTrue(flag);
    }
}

