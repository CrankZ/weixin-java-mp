package com.crankz.demo.util;

import com.crankz.demo.po.News;
import com.crankz.demo.po.NewsMessage;
import com.crankz.demo.po.TextMessage;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class MessageUtil {

    public static final String MESSAGE_TEXT = "text";
    public static final String MESSAGE_NEWS = "news";
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
     * 图文消息转为xml
     *
     * @param newsMessage
     * @return
     */
    public static String newsMessageToXml(NewsMessage newsMessage) {
        XStream xStream = new XStream();
        xStream.alias("xml", newsMessage.getClass());
        xStream.alias("item", new News().getClass());
        return xStream.toXML(newsMessage);
    }

    /**
     * 图文消息的组装
     * @param toUserName
     * @param fromUserName
     * @return
     */
    public static String initNewsMessage(String toUserName, String fromUserName) {
        String message = null;
        List<News> newsList = new ArrayList<News>();
        NewsMessage newsMessage = new NewsMessage();

        News news = new News();
        news.setTitle("图文消息标题");
        news.setDescription("图文消息描述");
        news.setPicUrl("https://cdn0.tnwcdn.com/wp-content/blogs.dir/1/files/2018/03/GitHub-brave-hed-796x418.jpg");
        news.setUrl("https://github.com/");

        newsList.add(news);

        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MESSAGE_NEWS);
        newsMessage.setArticles(newsList);
        newsMessage.setArticleCount(newsList.size());
        message = newsMessageToXml(newsMessage);
        return message;
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
     * 初次订阅事件
     *
     * @return
     */
    public static String subscribe() {
        StringBuffer sb = new StringBuffer();
        sb.append("感谢您的订阅");
        return sb.toString();
    }

    /**
     * 简单的回复相同的内容
     *
     * @param content
     * @return
     */
    public static String echoWord(String content) {
        StringBuffer sb = new StringBuffer();
        sb.append("您发送的消息是：");
        sb.append(content);
        return sb.toString();
    }
}
