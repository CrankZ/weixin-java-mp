package com.rhy.demo.servlet;

import com.rhy.demo.util.EmailUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/consult")
public class ConsultServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String sort = req.getParameter("sort");
        String phone = req.getParameter("phone");
        String content = req.getParameter("content");
        System.out.println(username);
        System.out.println(sort);
        System.out.println(phone);
        System.out.println(content);

        String title = username + "," + sort + "," + phone;
        content = content + "\n手机号：" + phone;
        EmailUtil.SendEmail(title, content, sort);
        resp.setContentType("text/html;charset=UTF-8");

        PrintWriter out = resp.getWriter();
        // TODO不会自动关闭
        out.println("<body onload='setTimeout(\"mm()\",3000)'>咨询成功，我们会尽快回复您<script>function mm(){window.opener=null;window.close();} </script></body>");
    }
}
