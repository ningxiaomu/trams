package com.tester.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

//用例失败重跑
public class OverrodeIReTry implements IRetryAnalyzer {
    public  int initReTryNum=1;
    public  int maxReTryNum=3;
    @Override
    public boolean retry(ITestResult iTestResult) {
        if(initReTryNum<=maxReTryNum){
            String msg = "方法<"+iTestResult.getName()+">执行失败，重试第"+initReTryNum+"次";
            System.out.println(msg);
            Reporter.setCurrentTestResult(iTestResult);
            Reporter.log(msg);
            initReTryNum++;
            return true;
        }
        return false;
    }
}
