package com.lmh.secondhandbook.controller;


import com.lmh.secondhandbook.entity.User;
import com.lmh.secondhandbook.service.CartService;
import com.lmh.secondhandbook.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mh
 * @since 2021-08-08
 */
@Controller
@RequestMapping("/productCategory")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService  productCategoryService;

    @Autowired
    private CartService service;

    //  "/list"
    @GetMapping("/booklist")
    public ModelAndView Booklist(HttpSession session){
        //list()
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main");
        //list
        modelAndView.addObject("booklist",productCategoryService.getAllProductCategoryVO());

        //购物车,没登入就设为null
        User user = (User) session.getAttribute("user");
        if(user == null){
            modelAndView.addObject("cartbooklist",new ArrayList<>());
        }else {
            modelAndView.addObject("cartbooklist",service.findAllCartVOByUserId(user.getId()));
        }

        return modelAndView;
    }

}

