package com.rhy.demo.util;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtil {
    public static void SendEmail(String title, String content) {
        // 收件人邮箱
        String to = "songliang_zhu@rhyssc.com";
        // 发件人邮箱
        String from = "songliang_zhu@rhyssc.com";
        // 发件人邮箱密码
        String password = "123456789R!";
        // host
        String host = "smtp.mxhichina.com";
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
