package com.rhy.demo.util;

import com.alibaba.fastjson.JSONObject;
import com.rhy.demo.menu.Button;
import com.rhy.demo.menu.ClickButton;
import com.rhy.demo.menu.Menu;
import com.rhy.demo.menu.ViewButton;
import com.rhy.demo.po.AccessToken;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class WeixinUtil {
    private static final String APPID = "wx6e2722059d43a84b";
    private static final String APPSECRET = "b0487ddf8a505125a8e8caec58d25f06";
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private static final String UPLOAD_URL = "";
    private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    private static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";

    public static JSONObject doGetStr(String url) throws IOException {
        HttpClientBuilder client = HttpClientBuilder.create();

        HttpGet get = new HttpGet(url);
        JSONObject jsonObject = null;
        HttpResponse response = client.build().execute(get);

        HttpEntity entity = response.getEntity();
        if (entity != null) {
            String result = EntityUtils.toString(entity, "UTF-8");
            jsonObject = JSONObject.parseObject(result);
        }
        return jsonObject;
    }

    public static JSONObject doPostStr(String url, String outStr) throws IOException {
        HttpClientBuilder client = HttpClientBuilder.create();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(outStr, "UTF-8"));
        HttpResponse response = client.build().execute(httpPost);
        String result = EntityUtils.toString(response.getEntity(), "UTF-8");
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject;
    }

    public static Menu initMenu() {
        Menu menu = new Menu();

        // 菜单1
        ClickButton button11 = new ClickButton();
        button11.setName("外资咨询");
        button11.setType("click");
        button11.setKey("11");

        ClickButton button12 = new ClickButton();
        button12.setName("内资咨询");
        button12.setType("click");
        button12.setKey("12");

        ClickButton button13 = new ClickButton();
        button13.setName("资本事业部");
        button13.setType("click");
        button13.setKey("13");

        Button button1 = new Button();
        button1.setName("财税咨询");
        button1.setSub_button(new Button[]{button11, button12, button13});


        // 菜单2
        ViewButton button21 = new ViewButton();
        button21.setName("服务内容");
        button21.setType("view");
        button21.setUrl("https://mp.weixin.qq.com/s/reMIe-ffdHgOortA7s7VEg");

        ClickButton button22 = new ClickButton();
        button22.setName("服务案例");
        button22.setType("click");
        button22.setKey("22");

        ViewButton button23 = new ViewButton();
        button23.setName("公司招聘");
        button23.setType("view");
        button23.setUrl("http://company.zhaopin.com/CC513024887.htm");

        Button button2 = new Button();
        button2.setName("公司业务");
        button2.setSub_button(new Button[]{button21, button22, button23});

        // 菜单3
        ViewButton button31 = new ViewButton();
        button31.setName("公司简介");
        button31.setType("view");
        button31.setUrl("https://mp.weixin.qq.com/s/4k0rBjvAgJHoG-8Gy2hpwg");

        ViewButton button32 = new ViewButton();
        button32.setName("团队架构");
        button32.setType("view");
        button32.setUrl("https://mp.weixin.qq.com/s/MJIFkohhyRyvjyPPf_LfzA");

        ClickButton button33 = new ClickButton();
        button33.setName("联系我们");
        button33.setType("click");
        button33.setKey("33");

        Button button3 = new Button();
        button3.setName("公司介绍");
        button3.setSub_button(new Button[]{button31, button32, button33});

        menu.setButton(new Button[]{button1, button2, button3});
        return menu;
    }


    public static AccessToken getAccessToken() throws IOException {
        AccessToken token = new AccessToken();
        String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
        JSONObject jsonObject = doGetStr(url);
        if (jsonObject != null) {
            token.setToken(jsonObject.getString("access_token"));
            token.setExpiresIn(jsonObject.getInteger("expires_in"));
        }
        return token;
    }

    public static int createMenu(String token, String menu) throws IOException {
        int result = 0;
        String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = doPostStr(url, menu);
        if (jsonObject != null) {
            result = jsonObject.getInteger("errcode");
        }
        return result;
    }

    public static JSONObject queryMenu(String token) throws IOException {
        String url = QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = doGetStr(url);
        return jsonObject;
    }
}
