package com.rhy.demo.util;

import com.rhy.demo.po.TextMessage;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageUtil {

    public static final String MESSAGE_TEXT = "text";
    public static final String MESSAGE_IMAGE = "image";
    public static final String MESSAGE_VOICE = "voice";
    public static final String MESSAGE_VIDEO = "video";
    public static final String MESSAGE_LINK = "link";
    public static final String MESSAGE_LOCATION = "location";
    public static final String MESSAGE_EVENT = "event";
    public static final String MESSAGE_SUBSCRIBE = "subscribe";
    public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
    public static final String MESSAGE_CLICK = "CLICK";
    public static final String MESSAGE_VIEW = "VIEW";

    /**
     * xml2Map
     *
     * @param request
     * @return
     * @throws DocumentException
     * @throws IOException
     */
    public static Map<String, String> xmlToMap(HttpServletRequest request) throws DocumentException, IOException {
        Map<String, String> map = new HashMap<String, String>();

        SAXReader reader = new SAXReader();

        InputStream ins = request.getInputStream();
        Document doc = reader.read(ins);

        Element root = doc.getRootElement();

        List<Element> list = root.elements();

        for (Element e : list) {
            map.put(e.getName(), e.getText());
        }
        ins.close();
        return map;
    }

    public static String textMessageToXml(TextMessage textMessage) {
        XStream xStream = new XStream();
        xStream.alias("xml", textMessage.getClass());
        return xStream.toXML(textMessage);
    }

    /**
     * 把文本内容转换为微信可识别的xml格式
     *
     * @param toUserName
     * @param fromUserName
     * @param content      消息主题
     * @return
     */
    public static String initText(String toUserName, String fromUserName, String content) {
        TextMessage text = new TextMessage();
        text.setFromUserName(toUserName);
        text.setToUserName(fromUserName);
        text.setMsgType(MessageUtil.MESSAGE_TEXT);
        text.setCreateTime(new Date().getTime());
        text.setContent(content);
        return textMessageToXml(text);
    }

    /**
     * 主菜单
     *
     * @return
     */
    public static String menuText() {
        StringBuffer sb = new StringBuffer();
        sb.append("欢迎您的关注，请按照菜单提示进行操作：\n\n");
        sb.append("1、企业简介\n");
        sb.append("2、服务内容\n");
        sb.append("3、财税咨询");
        return sb.toString();
    }

    /**
     * 第一次订阅要发送的消息
     *
     * @return
     */
    public static String subscribe() {
        StringBuffer sb = new StringBuffer();
        sb.append("感谢您的订阅");
        return sb.toString();
    }

    public static String echoWord(String content) {
        StringBuffer sb = new StringBuffer();
        sb.append("您发送的消息是：");
        sb.append(content);
        return sb.toString();
    }

    public static String firstMenu() {
        StringBuffer sb = new StringBuffer();
        sb.append("您回复了1");
        return sb.toString();
    }

    public static String secondMenu() {
        StringBuffer sb = new StringBuffer();
        sb.append("您回复了2");
        return sb.toString();
    }

}
