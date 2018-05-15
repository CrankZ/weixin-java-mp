package Util;

import com.alibaba.fastjson.JSONObject;
import com.crankz.demo.po.AccessToken;
import com.crankz.demo.util.WeixinUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class WeixinTest {

    @Test
    public void getToken() throws IOException {
        AccessToken token = WeixinUtil.getAccessToken();
        System.out.println(token.getToken());
        System.out.println(token.getExpiresIn());
    }

    /**
     * 想要修改菜单必须先执行这个
     * @throws IOException
     */
    @Test
    public void menuTest() throws IOException {
        AccessToken token = WeixinUtil.getAccessToken();
        String menu = JSONObject.toJSONString(WeixinUtil.initMenu());
        int result = WeixinUtil.createMenu(token.getToken(), menu);
        if (result == 0) {
            System.out.println("创建菜单成功");
        } else {
            System.out.println("错误码：" + result);
        }
    }

    @Test
    public void queryTest() throws IOException {
        AccessToken token = WeixinUtil.getAccessToken();
        JSONObject jsonObject = WeixinUtil.queryMenu(token.getToken());
        System.out.println(jsonObject);
    }
}
