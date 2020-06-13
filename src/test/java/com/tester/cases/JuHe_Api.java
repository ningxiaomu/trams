package com.tester.cases;

import com.tester.utils.BaseClient;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import org.apache.log4j.Logger;

public class JuHe_Api {
    private static final Logger log = Logger.getLogger(JuHe_Api.class);
    private boolean flag;

    @Test
    public void LotteryResultsQuery() throws IOException {
        log.info("执行JuHe_Api开始");
        //彩票开奖结果查询
        flag = BaseClient.NoNeedLoginClient("testOne","f670be0982ea11eaa51100163e0d8570");
        System.out.println("Thread Id:"+Thread.currentThread().getId());
        Assert.assertTrue(flag);
        log.info("执行JuHe_Api结束");
    }

    @Test
    public void IDCardQuery() throws IOException{
        //身份证查询
        flag = BaseClient.NoNeedLoginClient("testOne","2350ae9f86fb11eaa51100163e0d8570");
        System.out.println("Thread Id:"+Thread.currentThread().getId());
        Assert.assertTrue(flag);
    }



}
