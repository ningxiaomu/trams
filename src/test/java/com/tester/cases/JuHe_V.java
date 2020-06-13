package com.tester.cases;

import com.tester.utils.BaseClient;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class JuHe_V {
    private boolean flag;
    @Test
    public void JokeQuery() throws IOException {
        //笑话接口查询
        flag = BaseClient.NoNeedLoginClient("testOne","9eaec1f186fb11eaa51100163e0d8570");
        System.out.println("Thread Id:"+Thread.currentThread().getId());
        Assert.assertTrue(flag);
    }

    @Test
    public void XinhuaDictionaryy() throws IOException {
        //新华字典
        flag = BaseClient.NoNeedLoginClient("testOne","19726eb786fc11eaa51100163e0d8570");
        System.out.println("Thread Id:"+Thread.currentThread().getId());
        Assert.assertTrue(flag);
    }

    @Test
    public void BookClassification() throws IOException {
        //笑话接口查询
        flag = BaseClient.NoNeedLoginClient("testOne","46cedc5c86fe11eaa51100163e0d8570");
        System.out.println("Thread Id:"+Thread.currentThread().getId());
        Assert.assertTrue(flag);
    }

}
