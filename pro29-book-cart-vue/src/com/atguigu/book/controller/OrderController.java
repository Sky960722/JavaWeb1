package com.atguigu.book.controller;

import com.atguigu.book.pojo.OrderBean;
import com.atguigu.book.pojo.User;
import com.atguigu.book.service.OrderService;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class OrderController {

    private OrderService orderService;

    public String checkout(HttpSession session){

        OrderBean orderBean = new OrderBean();

        Date now = new Date();
        int year = now.getYear();
        int month = now.getMonth();
        int day = now.getDay();
        int hour = now.getHours();
        int min = now.getMinutes();
        int sec = now.getSeconds();
        orderBean.setOrderNo(UUID.randomUUID().toString()+"_"+year+month+day+hour+min+sec);
        orderBean.setOrderDate(now);

        User user = (User) session.getAttribute("currUser");
        orderBean.setOrderUser(user);
        orderBean.setOrderMoney(user.getCart().getTotalMoney());
        orderBean.setOrderStatus(0);
        orderService.addOrderBean(orderBean);

        return "index";
    }

    //查看订单列表
    public String getOrderList(HttpSession session){
        User user = (User) session.getAttribute("currUser");
        List<OrderBean> ordeList = orderService.getOrdeList(user);

        user.setOrderList(ordeList);

        session.setAttribute("currUser",user);

        return "order/order";

    }
}
