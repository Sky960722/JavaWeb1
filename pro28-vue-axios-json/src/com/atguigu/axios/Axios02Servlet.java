package com.atguigu.axios;

import com.atguigu.pojo.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/axios02.do")
public class Axios02Servlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuffer stringBuffer = new StringBuffer("");
        BufferedReader bufferedReader = req.getReader();
        String str = null;
        while ((str = bufferedReader.readLine())!=null){
            stringBuffer.append(str);
        }
        str = stringBuffer.toString();

        //已知String
        //需要转化成JavaObject

        Gson gson = new Gson();
        User user = gson.fromJson(str, User.class);

        System.out.println(user);

        user.setUname("鸠摩智");
        user.setPwd("123456");

        String userJsonStr = gson.toJson(user);


        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(userJsonStr);


    }
}
