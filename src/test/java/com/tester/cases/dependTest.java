package com.tester.cases;

import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class dependTest {
    private CookieStore cookieStore;
    @Test
    public void login() throws IOException {
        String result=null;
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://127.0.0.1/zentao/user-login-L3plbnRhby8=.html");
                                               //http://127.0.0.1/zentao/user-login-L3plbnRhby8=.html
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        BasicNameValuePair basicNameValuePair = new BasicNameValuePair("account", "admin");
        BasicNameValuePair basicNameValuePair2 = new BasicNameValuePair("password", "e10adc3949ba59abbe56e057f20f883e");
        BasicNameValuePair basicNameValuePair3 = new BasicNameValuePair("referer", "/zentao/");
        list.add(basicNameValuePair);
        list.add(basicNameValuePair2);
        list.add(basicNameValuePair3);
        System.out.println("list:"+list);

        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list);

        httpPost.setEntity(formEntity);


        HttpResponse response = client.execute(httpPost);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);

        this.cookieStore=client.getCookieStore();
        List<Cookie> cookieList =cookieStore.getCookies();
        for(Cookie cookie:cookieList){
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println(name+":"+value);
        }
    }
    @Test(dependsOnMethods = "login")
    public void my() throws IOException {
        HttpGet get =new HttpGet("http://127.0.0.1/zentao/company-browse.html");

        DefaultHttpClient client = new DefaultHttpClient();
        //设置cookie
        client.setCookieStore(this.cookieStore);
        HttpResponse response = client.execute(get);

        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);

    }
}
