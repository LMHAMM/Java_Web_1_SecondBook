package com.lmh.secondhandbook.controller;


import com.lmh.secondhandbook.entity.Orders;
import com.lmh.secondhandbook.entity.User;
import com.lmh.secondhandbook.service.CartService;
import com.lmh.secondhandbook.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Random;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mh
 * @since 2021-08-08
 */
@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService service;

//    @PostMapping
    @PostMapping("/settlement3")
    public ModelAndView settlement2(Orders orders, HttpSession session,String address,String remark){
        //简化了，业务让OrderService去做了（重写了个save方法）
        User user = (User) session.getAttribute("user");

        orderService.save(orders,user,address,remark);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("settlement3");
        modelAndView.addObject("cartbooklist",service.findAllCartVOByUserId(user.getId()));
        modelAndView.addObject("orders",orders);
        return modelAndView;
    }

    @GetMapping("/list")
    public ModelAndView list(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("orderList");
        User user = (User) session.getAttribute("user");
        modelAndView.addObject("list",orderService.findAllOrederVOByUserId(user.getId()));
        modelAndView.addObject("cartbooklist",service.findAllCartVOByUserId(user.getId()));
        return modelAndView;
    }

}

