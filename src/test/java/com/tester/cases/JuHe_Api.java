package com.tester.cases;

import com.tester.utils.BaseClient;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class JuHe_Api {
    private boolean flag;

    @Test
    public void LotteryResultsQuery() throws IOException {
        //彩票开奖结果查询
        flag = BaseClient.NoNeedLoginClient("testOne","f670be0982ea11eaa51100163e0d8570");
        Assert.assertTrue(flag);
    }

    @Test
    public void IDCardQuery() throws IOException{
        //身份证查询
        flag = BaseClient.NoNeedLoginClient("testOne","2350ae9f86fb11eaa51100163e0d8570");
        Assert.assertTrue(flag);
    }

}
