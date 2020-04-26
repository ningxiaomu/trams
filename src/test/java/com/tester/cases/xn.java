package com.tester.cases;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.github.houbb.junitperf.core.rule.JunitPerfRule;
import org.junit.Rule;
import org.testng.annotations.Test;


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

}
