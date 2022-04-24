package com.atguigu.book.service.impl;

import com.atguigu.book.dao.CartItemDAO;
import com.atguigu.book.dao.OrderDAO;
import com.atguigu.book.dao.OrderItemDAO;
import com.atguigu.book.pojo.CartItem;
import com.atguigu.book.pojo.OrderBean;
import com.atguigu.book.pojo.OrderItem;
import com.atguigu.book.pojo.User;
import com.atguigu.book.service.OrderService;

import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService {

    private OrderDAO orderDAO;
    private OrderItemDAO orderItemDAO;
    private CartItemDAO cartItemDAO;
    private Integer getOrderTotalBookCount;

    @Override
    public void addOrderBean(OrderBean orderBean) {

        //第一步:
        orderDAO.addOrderBean(orderBean);

        //第二步：
        //orderBean中的orderItem用用户的购物车转换
        User currUser = orderBean.getOrderUser();
        Map<Integer, CartItem> cartItemMap = currUser.getCart().getCartItemMap();
        for(CartItem cartItem : cartItemMap.values()){
            OrderItem orderItem = new OrderItem();
            orderItem.setBook(cartItem.getBook());
            orderItem.setBuyCount(cartItem.getBuyCount());
            orderItem.setOrderBean(orderBean);
            orderItemDAO.addOrderItem(orderItem);
        }

        //第三步：

        for(CartItem cartItem : cartItemMap.values()){
            cartItemDAO.delCartItem(cartItem);
        }
    }

    @Override
    public List<OrderBean> getOrdeList(User user) {
      List<OrderBean> orderBeanList = orderDAO.getOrderList(user);

      for(OrderBean orderBean : orderBeanList){
          Integer totalBookCount = orderDAO.getOrderTotalBookCount(orderBean);
          orderBean.setTotalBookCount(totalBookCount);
      }
      return orderBeanList;
    }
}
