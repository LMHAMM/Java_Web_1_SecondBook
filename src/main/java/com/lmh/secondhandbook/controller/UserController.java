package com.lmh.secondhandbook.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmh.secondhandbook.entity.User;
import com.lmh.secondhandbook.service.CartService;
import com.lmh.secondhandbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mh
 * @since 2021-08-08
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService service;

     /*
    注册功能
     */
    @PostMapping("/register")
    public String register(User user, Model model){
        boolean result = false;
        try {
            result =  userService.save(user);
        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.printf("error");
            model.addAttribute("error",user.getLoginName()+"已存在!");
            return "register";
        }
        if(result){
            return "login";
        }else{
            return "register";
        }
    }




    /*
    登入功能
     */
    @PostMapping("/login")
    public String login(String loginName, String password, HttpSession session){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("login_name",loginName);
        wrapper.eq("password",password);
        User user = userService.getOne(wrapper);

        if(user == null){
//            System.out.printf("数据库没有此人----------------------");
            return "login";
        }else{
            session.setAttribute("user",user);
//            System.out.printf("数据库中有这个人************************");
            return "redirect:/productCategory/booklist";
        }


    }
    /*
    后台管理员登入
     */
    @PostMapping("/AdminLogin")
    public String AdminLogin(String loginName,String password,HttpSession session){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("login_name",loginName);
        wrapper.eq("password",password);

        User user =userService.getOne(wrapper);

        if(user==null){
            return "redirect:/register";
        }else if(user!=null & user.getLoginName().contains("admin")){
            session.setAttribute("user",user);
            return "redirect:/Admin";
        }else{
            return "redirect:/login";
        }

    }

    /*
    注销功能
     */
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }


    /**
     * 用户信息
     */
    @GetMapping("/userInfo")
    public ModelAndView userInfo(HttpSession session){
        User user = (User) session.getAttribute("user");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userInfo");
        modelAndView.addObject("cartbooklist",service.findAllCartVOByUserId(user.getId()));
        return modelAndView;
    }


}

