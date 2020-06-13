package com.tester.cases;

import org.testng.Assert;
import org.testng.annotations.Test;

public class testReTry {
    @Test
    public void success(){
        System.out.println(1/1);
        System.out.println("Thread Id:"+Thread.currentThread().getId());
    }
    @Test(retryAnalyzer = com.tester.utils.OverrodeIReTry.class)
    public void fail(){
        boolean f=false;
        System.out.println("Thread Id:"+Thread.currentThread().getId());
        Assert.assertTrue(f);
    }
}
