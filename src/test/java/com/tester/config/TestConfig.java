package com.tester.config;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import lombok.Data;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Data
public class TestConfig implements IReporter {

//    //登陆接口uri
//    public static String loginUrl;
//    //更新用户信息接口uri
//    public static String updateUserInfoUrl;
//    //获取用户列表接口uri
//    public static String getUserListUrl;
//    //获取用户信息接口uri
//    public static String getUserInfoUrl;
//    //添加用户信息接口
//    public static String addUserUrl;
//    //获取用例接口
//    public static String caseurl;
//
//
//    //用来存储cookies信息的变量
//    public static CookieStore store;
//    //声明http客户端
//    public static DefaultHttpClient defaultHttpClient;

    public static void main(String[] args) {

    }

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        List<ITestResult> list = new ArrayList<ITestResult>();
        for (ISuite suite : suites) {
            Map<String, ISuiteResult> suiteResults = suite.getResults();
            for (ISuiteResult suiteResult : suiteResults.values()) {
                ITestContext testContext = suiteResult.getTestContext();
                IResultMap passedTests = testContext.getPassedTests();
                IResultMap failedTests = testContext.getFailedTests();
                IResultMap skippedTests = testContext.getSkippedTests();
                IResultMap failedConfig = testContext.getFailedConfigurations();
                System.out.println(passedTests);
            }
        }
    }
}
