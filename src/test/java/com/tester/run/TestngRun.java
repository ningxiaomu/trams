package com.tester.run;

import org.testng.TestNG;
import org.testng.collections.Lists;

import java.util.List;

public class TestngRun {
    public static void main(String[] args){

        TestNG testng = new TestNG();

        List suites = Lists.newArrayList();

        suites.add("C:\\Users\\Administrator\\IdeaProjects\\firstTEST\\src\\main\\resources\\testng.xml");//path to xml..

        testng.setTestSuites(suites);

        testng.run();



    }
}
