package com.lmh.secondhandbook.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmh.secondhandbook.entity.Cart;
import com.lmh.secondhandbook.entity.Product;
import com.lmh.secondhandbook.entity.User;
import com.lmh.secondhandbook.mapper.ProductMapper;
import com.lmh.secondhandbook.service.CartService;
import com.lmh.secondhandbook.service.ProductService;
import com.lmh.secondhandbook.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService service;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserAddressService userAddressService;

    @GetMapping("/add/{bookid}/{price}/{quantity}")
    public String addCart(@PathVariable("bookid") Integer bookid, @PathVariable("price") Float price, @PathVariable("quantity") Integer quantity, HttpSession session){
        Cart cart = new Cart();
        cart.setProductId(bookid);
        cart.setQuantity(quantity);
        cart.setCost(price*quantity);
        User user = (User) session.getAttribute("user");
        cart.setUserId(user.getId());
        try {
            if(service.save(cart)){
                return "redirect:/cart/findAllBook";
            }
        } catch (Exception e){
            return "redirect:/productCategory/booklist";
        }
        return null;
    }

    @GetMapping("/findAllBook")
    public ModelAndView findAllBook(HttpSession session){

        User user = (User) session.getAttribute("user");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("settlement1");
        modelAndView.addObject("booklist",service.findAllCartVOByUserId(user.getId()));

        //购物车
        if(user == null){
            modelAndView.addObject("cartbooklist",new ArrayList<>());
        }else {
            modelAndView.addObject("cartbooklist",service.findAllCartVOByUserId(user.getId()));
        }
        return modelAndView;

    }

    @GetMapping("deleteById/{id}")
    public String deleteById(@PathVariable("id") Integer id){
        service.removeById(id);
//        QueryWrapper queryWrapper = new QueryWrapper();
//        queryWrapper.eq("id",id);
//        Product product = productService.getOne(queryWrapper);
//        product.setStock(product.getStock()+1);
//        productService.saveOrUpdate(product);
        return "redirect:/cart/findAllBook";

    }

    @GetMapping("/settlement2")
    public ModelAndView settlement2(HttpSession session){

        User user = (User) session.getAttribute("user");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("settlement2");
        modelAndView.addObject("booklist",service.findAllCartVOByUserId(user.getId()));

        //购物车
        if(user == null){
            modelAndView.addObject("cartbooklist",new ArrayList<>());
        }else {
            modelAndView.addObject("cartbooklist",service.findAllCartVOByUserId(user.getId()));
        }
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",user.getId());
        modelAndView.addObject("addressList",userAddressService.list(wrapper));
        return modelAndView;

    }

    @PostMapping("/updatebook/{id}/{quantity}/{cost}")
    @ResponseBody
    public String upadteCart(@PathVariable("id") Integer id,@PathVariable("quantity") Integer quantity,@PathVariable("cost") Float cost){
        Cart cart = service.getById(id);
        cart.setQuantity(quantity);
        cart.setCost(cost);
        if(service.updateById(cart)){
            return "success";
        }else {
            return "unsuccess";
        }


    }


}

