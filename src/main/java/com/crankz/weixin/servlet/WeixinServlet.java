package com.crankz.weixin.servlet;

import com.crankz.weixin.util.CheckUtil;
import com.crankz.weixin.util.MessageUtil;
import org.dom4j.DocumentException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet("/wx.do")
public class WeixinServlet extends HttpServlet {
    /**
     * 微信接口验证
     * @param req
     * @param resp
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String signature = req.getParameter("signature"); // 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
        String timestamp = req.getParameter("timestamp"); // 时间戳
        String nonce = req.getParameter("nonce"); // 随机数
        String echostr = req.getParameter("echostr"); //随机字符串

        PrintWriter out = resp.getWriter();
        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }
    }

    /**
     * 消息或事件的回复
     * @param req
     * @param resp
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        try {
            Map<String, String> map = MessageUtil.xmlToMap(req);
            String fromUserName = map.get("FromUserName");
            String toUserName = map.get("ToUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");

            String message = null;
            //消息的接收与回复
            if (MessageUtil.MESSAGE_TEXT.equals(msgType)) {
                if ("1".equals(content)) {
                    message = MessageUtil.initNewsMessage(toUserName, fromUserName);
                } else if ("2".equals(content)) {
                    message = MessageUtil.initNewsMessage(toUserName, fromUserName);
                } else if ("?".equals(content) || "？".equals(content)) {
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
                } else {
                    // 简单的echo回复，接收什么回复什么
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.echoWord(content));
                }
            } else if (MessageUtil.MESSAGE_EVENT.equals(msgType)) {
                String eventType = map.get("Event");
                int eventKey = Integer.parseInt(map.get("EventKey"));
                System.out.println("eventType:" + eventType);
                // 订阅事件
                if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.subscribe());
                }
                // 按钮单击事件
                else if (MessageUtil.MESSAGE_CLICK.equals(eventType)) {
                    String reply = null;
                    if (eventKey == 33) {
                        reply = "名称：苏州XX科技有限公司\n" +
                                "单位地址：苏州工业园区XX号XX栋XX室\n" +
                                "电话号码：0512XXXXX";
                    }
                    message = MessageUtil.initText(toUserName, fromUserName, reply);
                } else if (MessageUtil.MESSAGE_VIEW.equals(eventType)) {
                    String url = map.get("EventKey");
                    message = MessageUtil.initText(toUserName, fromUserName, url);
                }
            }
            System.out.println(message);
            out.print(message);
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
}