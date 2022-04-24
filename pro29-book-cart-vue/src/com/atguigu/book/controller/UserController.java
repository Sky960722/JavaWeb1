package com.atguigu.book.controller;

import com.atguigu.book.pojo.Cart;
import com.atguigu.book.pojo.User;
import com.atguigu.book.service.BookService;
import com.atguigu.book.service.CartItemService;
import com.atguigu.book.service.UserService;
import com.sun.org.apache.bcel.internal.generic.RET;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class UserController {

    private UserService userService;
    private CartItemService cartItemService;
    //private BookService bookService;

    public String login(String uname, String pwd, HttpSession session) {

        User user = userService.login(uname, pwd);
        if (user != null) {
            Cart cart = cartItemService.getCart(user);
            user.setCart(cart);
            session.setAttribute("currUser", user);
            return "redirect:book.do";
        }
        return "user/login";
    }

    public String regist(String verifyCode, String uname, String pwd, String email, HttpSession session, HttpServletResponse response) throws IOException {
        Object kaptchaCodeObj = session.getAttribute("KAPTCHA_SESSION_KEY");
        if (kaptchaCodeObj == null || !verifyCode.equals(kaptchaCodeObj)) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html/;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script language='javascript'>alert('验证码不正确！');</script>");
            return "user/regist";
        } else {
            if (verifyCode.equals(kaptchaCodeObj)) {
                userService.regist(new User(uname, pwd, email, 0));
                return "user/login";
            }
        }
        return "user/login";
    }

    public String ckUname(String uname) {
        User user = userService.getUser(uname);
        if (user != null) {
            return "json:{'uname':'1'}";
        } else {
            return "json:{'uname':'0'}";
        }
    }
}
