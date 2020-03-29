package com.tester.cases;

import com.tester.domain.Case;
import com.tester.domain.Project;
import com.tester.domain.ProjectCase;
import com.tester.utils.DatabaseUtil;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.*;

public class GetCaseList {
//    @Test(dependsOnGroups = "loginTrue",description = "添加用户接口接口")
//    @Test(description = "获取用例列表")
    public void getCaseList() throws IOException, InterruptedException {
        SqlSession session=DatabaseUtil.getSqlSession();
//        GetCaseListCase getCaseListCasecase=session.selectOne("getCaseList",1);
//        Thread.sleep(1000);
//
//        String testuri=getCaseListCasecase.getDomain()+getCaseListCasecase.getCaseAddress()+"?"+getCaseListCasecase.getParamter();
//        System.out.println(testuri);
//        HttpGet get = new HttpGet(testuri);
//        HttpClient client = new DefaultHttpClient();
//        HttpResponse response = client.execute(get);
//        String testresult;
//        testresult = EntityUtils.toString(response.getEntity(),"utf-8");
//        System.out.println(testresult);

        //上面的是可以的
//        List<GetCaseListCase> caseList = session.selectList("getCaseList");
//        System.out.println(caseList.get(0).getCaseId());
        String projectCase_PID= null;
        String projectCase_CID;
        String projecturi = null;
        String projectId=null;
        String caseuri = null;
        String caseId = null;
        String par = null;
        String testuri = null;
        String result=null;
        Collection<String> val = null;
        List<String> listVal = null;
        Map<String,String> mapProject=new HashMap<String,String>();
        Map<String,String> mapCase=new HashMap<String,String>();
        MultiValuedMap<String, String> mapProjectCase = new ArrayListValuedHashMap<String,String>();

        List<Project> projectList = session.selectList("getProjectList");
        List<Case> caseList = session.selectList("getCaseList");
        List<ProjectCase> projectCaseList = session.selectList("ProjectCaseList");
        HashMap<String, List<String>> map1 = new HashMap<String, List<String>>();
        List<String> list = new ArrayList<String>();

        for(int k=0;k<projectCaseList.size();k++){
            projectCase_PID = projectCaseList.get(k).getProjectId();
            projectCase_CID = projectCaseList.get(k).getCaseId();
            System.out.println("ProjectCaseList:"+projectCaseList);
            System.out.println("projectCase_CID:"+projectCase_CID);
            list.add(projectCase_CID);
            System.out.println("list:"+list);
            mapProjectCase.put(projectCase_PID,projectCase_CID);
            map1.put(projectCase_PID,list);
        }

        System.out.println("map1:"+map1);
        System.out.println("mapProjectCase:"+mapProjectCase);
        List listProjectCase = new ArrayList(mapProjectCase.keySet());
        System.out.println("listProjectCase:"+listProjectCase);
        System.out.println("----------------------------------");
        for(int j=0;j<projectList.size();j++){
            projectId = projectCaseList.get(j).getProjectId();
            projecturi = projectList.get(j).getDomain();
            mapProject.put(projectId,projecturi);
        }
        System.out.println("mapProject:"+mapProject);
        List listProject = new ArrayList(mapProject.keySet());
        List listProjectUri = new ArrayList();
        for(String key : mapProject.keySet()){
            String value = mapProject.get(key);
            listProjectUri.add(value);
        }
        System.out.println("listProject:"+listProject);
        System.out.println("----------------------------------");

        for (int i = 0; i <caseList.size() ; i++) {
            caseId = projectCaseList.get(i).getCaseId();
            caseuri=caseList.get(i).getRequestAddress();
            mapCase.put(caseId,caseuri);
        }
        System.out.println("mapCase:"+mapCase);
        List listCase = new ArrayList(mapCase.keySet());
        List listCaseUri = new ArrayList();
        for(String key : mapCase.keySet()){
            String value = mapCase.get(key);
            listCaseUri.add(value);
        }
        System.out.println("listCase:"+listCase);
        System.out.println("----------------------------------");
        ArrayList<String> urlList = new ArrayList<String>();
//        map1.forEach((k, v) -> {
//            for (String path : v) {
//                urlList.add(String.format("%s%s", mapProject.get(k), mapCase.get(path)));
//            }
//        });
        System.out.println(urlList);
//        map1.forEach((k, v) -> {
//            for (String path : v) {
//                urlList.add(String.format("%s%s", map2.get(k), map3.get(path)));
//            }
//        });

//
//        List listResult= new ArrayList();
//        for (int i = 0; i <listProjectCase.size() ; i++) {
//            for(int j=0;j<listProjectUri.size();j++){
//                for(int h=0;h<listCaseUri.size();h++){
//                    for (String key:mapProjectCase.keySet()) {
//                        val=mapProjectCase.get(key);
////                        System.out.println("listProject:"+listProject.get(j));
////                        String resultUri=key+"-"+val;
////                        System.out.println("key:"+key);
////                        System.out.println("val:"+val);
//                        listVal = (List) val;
//
//                        String pu=null;
//                        if(key==listProject.get(j)){
//                            pu = (String) listProjectUri.get(j);
//                            System.out.println("最终的ProjectUri:"+pu);
//                        }
//                        String caseUri=null;
//                        if(listCase.get(h)==listVal.get(i)){
//                            caseUri = (String) listCaseUri.get(h);
//                            System.out.println("最终的CaseUri:"+caseUri);
//                        }
//
//                        if(pu!=null&&caseUri!=null){
//                            String res = pu + caseUri;
//                            System.out.println("res:"+res);
//                        }
//身份查询：http://apis.juhe.cn/idcard/index   http://v.juhe.cn/idcard/index     http://apis.juhe.cn/idcard/index
//新华字典:http://v.juhe.cn/xhzd/query         http://apis.juhe.cn/xhzd/query    http://v.juhe.cn/xhzd/query
//周公解梦:http://v.juhe.cn/dream/category     http://v.juhe.cn/dream/category   http://apis.juhe.cn/dream/category
//彩票开奖:http://apis.juhe.cn/lottery/types   http://apis.juhe.cn/lottery/types http://v.juhe.cn/lottery/types
//                        listResult.add(res);
//                        boolean isRepeat = listResult.size() != new HashSet<String>(listResult).size();
//                        if(isRepeat){
//                            break;
//                        }
//                        System.out.println("最终拼接的结果是:"+listResult);




//                        listResult.add(resultUri);
//                        boolean isRepeat = listResult.size() != new HashSet<String>(listResult).size();
//                        if(isRepeat){
//                            break;
//                        }
//                        System.out.println("最终拼接的结果是:"+resultUri);

//                    }
//                }
//            }
        }

//        @MapKey("ProjectId")
//        @Test(description = "转换为map测试")
//        public void getAllMap() throws IOException {
//            SqlSession session=DatabaseUtil.getSqlSession();
//            MultiValuedMap<String, String> mapProjectCase = new ArrayListValuedHashMap<String,String>();
//            HashMap<String, List<String>> map1 = new HashMap<String, List<String>>();
//            Map<String,Map<String,String>> map= session.selectMap("ToMap","CaseId");
//            System.out.println(map);
//            List<String> list = new ArrayList<>();
//            List<String> list1 = new ArrayList<>();
//            LinkedHashSet<String> list2 = new LinkedHashSet<>();
//            ArrayList<String> listWithoutDuplicates = new ArrayList<>(list2);
//
//            for(Map<String, String> v:map.values()){
//                for(Map.Entry<String, String> entry : v.entrySet()){
//                    String mapKey = entry.getKey();
//                    String mapValue = entry.getValue();
//                    if(mapKey.equals("ProjectId")){
//                        list2.add(mapValue);
//                    }else if(mapKey.equals("CaseId")){
//                        list1.add(mapValue);
//                    }
//                    System.out.println(mapKey+":"+mapValue);
//                    map1.put(mapValue,list);
//                }
//            }
//            System.out.println(list2);
//            System.out.println(list1);
//
//
//            System.out.println("---------------------------------");
//            List<String> list4 = new ArrayList<>();
//            Map<String,Map<String,String>> mapCase=session.selectMap("getCaseMap","caseId");
//            System.out.println(mapCase);
//            Map<String,String> map11 = new HashMap<>();
////            for(Map<String, String> v:mapCase.values()){
////                for(Map.Entry<String, String> entry : v.entrySet()){
////                    String mapKey = entry.getKey();
////                    String mapValue = entry.getValue();
////                    System.out.println(mapKey+":"+mapValue);
////                    if(mapKey.equals("requestAddress")){
////                        list4.add(mapValue);
////                    }
////
////                }
////            }
//            System.out.println("list4:"+list4);
//            System.out.println("---------------------------------");
//            Map<String,Map<String,String>> mapProject = session.selectMap("getProjectMap","projectId");
//            System.out.println(mapProject);
//
//            List<String> list3 = new ArrayList<>();
//            for(Map<String, String> v:mapProject.values()){
//                for(Map.Entry<String, String> entry : v.entrySet()){
//                    String mapKey = entry.getKey();
//                    String mapValue = entry.getValue();
//                    System.out.println(mapKey+":"+mapValue);
//                    if(mapKey.equals("domain")){
//                        list3.add(mapValue);
//                    }
//                }
//            }
//            System.out.println("list3:"+list3);
//
//            String testurl;
//            for(String key : map.keySet()){
//                for(String keyCase:mapCase.keySet()){
//                    if(key.equals(keyCase)){
//                        System.out.println(key);
//                        for(Map<String, String> v:mapCase.values()){
//                            for(Map.Entry<String, String> entry : v.entrySet()){
//                                String mapKey = entry.getKey();
//                                String mapValue = entry.getValue();
//                                if(mapKey.equals("requestAddress")){
//                                    testurl = mapValue;
//                                    System.out.println("testurl:"+testurl);
//                                }
//                            }
//                        }
//
//                    }
//                }
//            }
//
//        }

        @Test
    public void test1() throws IOException {
        String testuri;
        String projecturi;
        String caseuri;
        String method;
        String result=null;
        String par;
        String exresult;

            SqlSession session=DatabaseUtil.getSqlSession();
            List<Case> caseList = session.selectList("getCaseList");
            for (int i = 0; i <caseList.size() ; i++) {
                projecturi = caseList.get(i).getDomain();
                caseuri = caseList.get(i).getRequestAddress();
                testuri = projecturi + caseuri;
                method = caseList.get(i).getMethod();
                par = caseList.get(i).getParameter();
                exresult = caseList.get(i).getExResult();
                boolean flag = true;
                if(method.equals("get") &&par==null){
                    HttpGet httpGet = new HttpGet(testuri);
                    HttpClient client = new DefaultHttpClient();
                    HttpResponse response = client.execute(httpGet);
                    result = EntityUtils.toString(response.getEntity(),"utf-8");
                    System.out.println(result);
                    System.out.println(result.contains(exresult));
                    Assert.assertTrue(result.contains(exresult));

                }else if(method.equals("get") &&par!=null){
                    testuri = projecturi + caseuri + "?" +par;
                    HttpGet httpGet = new HttpGet(testuri);
                    HttpClient client = new DefaultHttpClient();
                    HttpResponse response = client.execute(httpGet);
                    result = EntityUtils.toString(response.getEntity(),"utf-8");
                    System.out.println(result);
                    System.out.println(result.contains(exresult));
                    Assert.assertTrue(result.contains(exresult));
                }
                else if(method.equals("post")&&par==null){
                    testuri = projecturi + caseuri;
                    HttpPost httpPost = new HttpPost(testuri);
                    HttpClient client = new DefaultHttpClient();
                    HttpResponse response = client.execute(httpPost);
                    result = EntityUtils.toString(response.getEntity(),"utf-8");
                    System.out.println(result);
                    System.out.println(result.contains(exresult));
                    Assert.assertTrue(result.contains(exresult));
                }else if(method.equals("post") && par!= null){
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
    }

//    public void addUser() throws IOException, InterruptedException {
//
//        SqlSession session = DatabaseUtil.getSqlSession();
//        AddUserCase addUserCase = session.selectOne("addUserCase",1);
//        System.out.println(addUserCase.toString());
//        System.out.println(TestConfig.addUserUrl);

//        //下边的代码为写完接口的测试代码
//        String result = getResult(addUserCase);
//
//        /**
//         * 可以先讲
//         */
//        //查询用户看是否添加成功
//        Thread.sleep(2000);
//        User user = session.selectOne("addUser",addUserCase);
//        System.out.println(user.toString());
//
//
//        //处理结果，就是判断返回结果是否符合预期
//        Assert.assertEquals(addUserCase.getExpected(),result);
//
//
//    }


