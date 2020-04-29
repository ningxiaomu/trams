package com.tester.cases;

import org.testng.Assert;
import org.testng.annotations.Test;

public class testReTry {
    @Test
    public void success(){
        System.out.println(1/1);
    }
    @Test(retryAnalyzer = com.tester.utils.OverrodeIReTry.class)
    public void fail(){
        boolean f=false;
        Assert.assertTrue(f);
    }
}
