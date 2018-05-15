package com.crankz.weixin.util;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 邮件发送工具类
 */
public class EmailUtil {
    public static void SendEmail(String title, String content,String sort) {
        // 收件人邮箱

        String to = "default@gmail.com";
        // 根据选择的不同的类型，发送到不同人的邮箱
        if (sort.equals("外资咨询")) {
            to = "a@gmail.com";
        } else if (sort.equals("内资咨询")) {
            to = "b@gmail.com";
        } else if (sort.equals("资本事业部")) {
            to = "c@gmail.com";
        }
        // 发件人邮箱
        String from = "xx@xx.com";
        // 发件人邮箱密码
        String password = "123x";
        // host
        String host = "smtp.xx.com";
        // 设置邮箱服务器
        Properties props = new Properties();

        // 开启debug调试
        props.setProperty("mail.debug", "true");
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        // 设置邮件服务器主机名
        props.setProperty("mail.host", host);
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");

        try {
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.socketFactory", sf);

            Session session = Session.getInstance(props);

            Message msg = new MimeMessage(session);
            // 这是标题
            msg.setSubject(title);
            // 这是主要内容
            msg.setText(content);
            msg.setFrom(new InternetAddress(from));

            Transport transport = session.getTransport();
            transport.connect(host, from, password);

            transport.sendMessage(msg, new Address[]{new InternetAddress(to)});
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
