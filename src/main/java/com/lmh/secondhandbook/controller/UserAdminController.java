package com.lmh.secondhandbook.controller;


import com.lmh.secondhandbook.service.ProductService;
import com.lmh.secondhandbook.service.UserService;
import com.lmh.secondhandbook.vo.DataVO;
import com.lmh.secondhandbook.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
public class UserAdminController {
    @Autowired
    private UserService userService;

    @RequestMapping("/userlist")
    @ResponseBody
    public UserVO datalist(Integer page, Integer limit){
        return userService.findUserVO(page,limit);
    }
}
