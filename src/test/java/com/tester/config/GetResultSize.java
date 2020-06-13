package com.tester.config;

import com.tester.utils.DatabaseUtil;
import org.apache.ibatis.session.SqlSession;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.xml.XmlSuite;
import com.tester.domain.ResultSize;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GetResultSize implements IReporter {
    //将测试结果的数量插入数据库
    //建立session连接
    SqlSession session;

    {
        try {
            session = DatabaseUtil.getSqlSession();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        String Passed = null; //成功的数量
        String Failed = null; //失败的数量
        String Skipped = null; //跳过的数量

        for (ISuite suite : suites) {

            //Following code gets the suite name
            String suiteName = suite.getName();

            //Getting the results for the said suite
            Map<String, ISuiteResult> suiteResults = suite.getResults();
            for (ISuiteResult sr : suiteResults.values()) {
                ITestContext tc = sr.getTestContext();
                Passed = String.valueOf(tc.getPassedTests().getAllResults().size());
                Failed = String.valueOf(tc.getFailedTests().getAllResults().size());
                Skipped = String.valueOf(tc.getSkippedTests().getAllResults().size());

            }
        }
        System.out.println("成功的数量："+Passed+";失败的数量："+Failed+";跳过的数量："+Skipped);
        ResultSize result = new ResultSize();
        result.setPassed(Passed);
        result.setFailed(Failed);
        result.setSkipped(Skipped);

        session.insert("insertResult",result);
        session.commit();

    }
}
