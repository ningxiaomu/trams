package com.tester.cases;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.*;

public class test {
//    public static void main(String[] args) {
////        StringBuffer sb = new StringBuffer();
////
////        List<String> list = new ArrayList<String>();
////        list.add("1");
////        list.add("2");
////        list.add("2");
////        // 通过去重之后的HashSet长度来判断原list是否包含重复元素
////        boolean isRepeat = list.size() != new HashSet<String>(list).size();
////        System.out.println("list中包含重复元素：" + isRepeat);
////
////        if(isRepeat){
////            System.out.println("11");
////        }else {
////            System.out.println("22");
////        }
//
//        HashMap<String, List<String>> map1 = new HashMap<String, List<String>>(2);
//        map1.put("a", Arrays.asList("id1", "id2"));
//        map1.put("b", Arrays.asList("d3", "d4"));
//
////
//        HashMap<String, String> map2 = new HashMap<String, String>(2);
//        map2.put("a", "http://baidu.com");
//        map2.put("b", "http://qq.com");
//
////
//        HashMap<String, String> map3 = new HashMap<String, String>(4);
//        map3.put("id2", "/lottery/types");
//        map3.put("d3", "/idcard/index");
//        map3.put("id1", "/xhzd/query");
//        map3.put("d4", "/dream/category");
//
//        ArrayList<String> urlList = new ArrayList<String>();
//        map1.forEach((k, v) -> {
//            for (String path : v) {
//                urlList.add(String.format("%s%s", map2.get(k), map3.get(path)));
//            }
//        });
//        System.out.println(urlList);
//
//
//        ArrayList<Object> list = new ArrayList<Object>();
//        Object obj;
//        for (int i = 10; i > 0; i--) {
//            obj = new Object();
//            // obj = anotherObj;
//            list.add(obj);
//        }
//
//
//
//        ArrayList<String> list1 = new ArrayList<String>();
//        String a;
//        for (int i = 10; i > 0; i--) {
//
//            // obj = anotherObj;
//            list1.add(String.valueOf(i));
//        }
//        System.out.println(list1);
//
//
//
//    }
//    @Test
//    public void test1() throws IOException {
//        BaseClient baseClient = new BaseClient();
//        String result=baseClient.ClientByGet("getCaseList");
//        System.out.println(result);
//    }
//    @Test
//    public void test2() throws IOException {
//        BaseClient baseClient = new BaseClient();
//        String result=baseClient.ClientByPost("getCaseList");
//        System.out.println(result);
//    }
public static void main(String[] args) {
    List<NameValuePair> list = new ArrayList<NameValuePair>();
    String data = "username=admin;password=e10adc3949ba59abbe56e057f20f883e;refer=/zentao/";
    Map<String,String> map =new HashMap();
    if(data !=null){
        String[] result = data.split(";");
        for (int i = 0; i <result.length ; i++) {
            int index = result[i].indexOf("=");
            map.put(result[i].substring(0,index),result[i].substring(index+1));
        }
    }
    System.out.println(map);

    for(Map.Entry<String,String> entry:map.entrySet()){
        String key = entry.getKey();
        String value = entry.getValue();
        System.out.println(key+":"+value);
        BasicNameValuePair basicNameValuePair = new BasicNameValuePair(key, value);
        list.add(basicNameValuePair);
    }
    System.out.println(list);
}



}
