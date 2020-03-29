package com.tester.utils;

import com.google.gson.Gson;
import com.tester.dao.IUserDao;
import com.tester.domain.Case;
import com.tester.domain.Project;
import com.tester.domain.ProjectCase;
import com.tester.model.user;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class BaseClient {

    
    public String myMethod( String testUrl) throws IOException{
        HttpGet get = new HttpGet(testUrl);
        HttpPost post = new HttpPost(testUrl);
        String result = null;

        DefaultHttpClient client = new DefaultHttpClient();


        SqlSession session = DatabaseUtil.getSqlSession();
        IUserDao userDao = session.getMapper(IUserDao.class);
        List<user> users = userDao.findAll();

        for(int i =0;i<users.size();i++){
            String method = users.get(i).getMethod();
            String pars = users.get(i).getParams();
            String ContentType = users.get(i).getContentType();
            if(method==null){
                System.out.println("数据库里第 "+i+" 行的method参数为空，请检查~~~~~~~~~");
                assert false;
            }else if(method.equals("post")&&ContentType.equals("application/x-www-form-urlencoded")){
                //如果method是post,且contentType为 application/x-www-form-urlencoded的时候
                try{
                    Gson gson = new Gson();
                    Map<String,String> map = new HashMap<String, String>();
                    map = gson.fromJson(pars,map.getClass());
                    List<NameValuePair> list = new ArrayList<NameValuePair>();
                    Set<Map.Entry<String,String>> entries = map.entrySet();
                    for(Map.Entry<String,String> entry:entries){
                        String key = entry.getKey();
                        String value = entry.getValue();

                        BasicNameValuePair basicNameValuePair = new BasicNameValuePair(key,value);
                        list.add(basicNameValuePair);
                    }
                    System.out.println("method为post且ContentType为application/x-www-form-urlencoded时添加pars后的list："+list);
                    post.setHeader("Content-Type","application/x-www-form-urlencoded");
                    HttpResponse response = client.execute(post);
                    result = EntityUtils.toString(response.getEntity(),"utf-8");
                }catch (Exception e){
                    e.printStackTrace();
                }

            }else if(method.equals("post")&&ContentType.equals("application/json")){
                //如果method是post,且contentType为 application/json的时候
                try{
                    JSONObject param = new JSONObject();
                    Gson gson = new Gson();
                    Map<String,String> map = new HashMap<String, String>();
                    map = gson.fromJson(pars,map.getClass());
                    Set<Map.Entry<String,String>> entries = map.entrySet();
                    for(Map.Entry<String,String> entry:entries){
                        String key = entry.getKey();
                        String value = entry.getValue();
                        param.put(key,value);
                    }

                    post.setHeader("content-type","application/json");
                    StringEntity entity = new StringEntity(param.toString(),"utf-8");
                    post.setEntity(entity);
                    HttpResponse response = client.execute(post);
                    result = EntityUtils.toString(response.getEntity(),"utf-8");
                }catch (Exception e){
                    e.printStackTrace();
                }


            }else if(method.equals("post")&&ContentType.equals("multipart/form-data")){
                //如果method是post,且contentType为 multipart/form-data的时候
                //主要用于提交表单
                try{
                    MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
                    Gson gson = new Gson();
                    Map<String,String> map = new HashMap<String, String>();
                    map = gson.fromJson(pars,map.getClass());
                    Set<Map.Entry<String,String>> entries = map.entrySet();
                    for(Map.Entry<String,String> entry:entries){
                        String key = entry.getKey();
                        String value = entry.getValue();
                        entityBuilder.addTextBody(key,value);
                    }
                    System.out.println("method为post且ContentType为multipart/form-data时添加pars后的entityBuilder："+entityBuilder);
                    post.setEntity(entityBuilder.build());
                    HttpResponse response = client.execute(post);
                    HttpEntity entity = response.getEntity();
                    result = EntityUtils.toString(entity,"UTF-8");
                }catch (Exception e){
                    e.printStackTrace();
                }

            }else if(method.equals("get")&&ContentType==null){
                //如果method是get的时候
                try {
                    HttpResponse response = client.execute(get);
                    result = EntityUtils.toString(response.getEntity(), "utf-8");
                }catch (Exception e){
                    e.printStackTrace();
                }

            }else if(method.equals("post")&&ContentType==null){
                //method是Post，并且无参数
                try {
                    HttpResponse response = client.execute(post);
                    result = EntityUtils.toString(response.getEntity(), "utf-8");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }



        return result;
    }

    /**
     * 返回字符串
     * @return
     */
    public String getResponseResult() throws IOException {
        //重数据库里获得各项参数
        SqlSession session = DatabaseUtil.getSqlSession();
        String projectCase_PID= null;
        String projectCase_CID= null;
        String projecturi = null;
        String projectId=null;
        String caseuri = null;
        String caseId = null;
        String par = null;
        String testuri = null;
        String result=null;

        List<Project> projectList = session.selectList("getProjectList");
        List<Case> caseList = session.selectList("getCaseList");
        List<ProjectCase> projectCaseList = session.selectList("ProjectCaseList");

        for(int k=0;k<projectCaseList.size();k++){
            projectCase_PID = projectCaseList.get(k).getProjectId();
            projectCase_CID = projectCaseList.get(k).getCaseId();
        }

        for(int j=0;j<projectList.size();j++){
            projectId = projectCaseList.get(j).getProjectId();
            projecturi = projectList.get(j).getDomain();
        }

        for (int i = 0; i <caseList.size() ; i++) {
            caseId = projectCaseList.get(i).getCaseId();
            caseuri=caseList.get(i).getRequestAddress();
        }



        //建立get和Post连接
        HttpGet httpGet = new HttpGet(testuri);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(httpGet);
        result = EntityUtils.toString(response.getEntity(),"utf-8");

        return result;

    }

}
