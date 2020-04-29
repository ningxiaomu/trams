package com.tester.cases;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.github.houbb.junitperf.core.rule.JunitPerfRule;
import org.json.JSONObject;
import org.junit.Rule;
import org.testng.annotations.Test;

import java.util.Date;


public class xn {
    @Test(invocationCount=1000,threadPoolSize=500)
    public void testMethod() throws Exception{

        long start = System.currentTimeMillis();

        int i = 0;
        while(i < 100){
            System.out.println(i++);
            Thread.sleep(100);
        }

        long end = System.currentTimeMillis();

        long duringTime = end - start;
        System.out.println("duringTime:"+duringTime);

    }

    @Test
    public void test(){
        String par = "projectName=123;domain=123;status=1";
        long time = new Date().getTime();
        String Time = String.valueOf(time);
        System.out.println("Time:"+Time);
        JSONObject jsonObject = new JSONObject();
        String val = null;
        if(par!=null){
            String[] result1 = par.split(";");
            for (int j = 0; j <result1.length ; j++) {
                int index = result1[j].indexOf("=");
                if(!(result1[j].substring(index+1).equals("1")||result1[j].substring(index+1).equals("0"))){
                    System.out.println(result1[j].substring(index+1)+Time);
                    val = result1[j].substring(index+1)+Time;
                }else {
                    val = result1[j].substring(index+1);
                }

                jsonObject.put(result1[j].substring(0,index),val);
            }
        }
        System.out.println(jsonObject);
    }

}
