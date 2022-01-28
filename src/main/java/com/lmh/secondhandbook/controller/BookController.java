package com.lmh.secondhandbook.controller;

import com.lmh.secondhandbook.service.ProductService;
import com.lmh.secondhandbook.service.UserAddressService;
import com.lmh.secondhandbook.vo.DataVO;
import com.lmh.secondhandbook.vo.pieVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
public class BookController {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserAddressService userAddressService;

    @RequestMapping("/datalist")
    @ResponseBody
    public DataVO datalist(Integer page,Integer limit){
        return productService.findData(page,limit);
    }



    @RequestMapping("/addresslist")
    @ResponseBody
    public DataVO addresslist(Integer page,Integer limit){
        return userAddressService.findAddress(page, limit);
    }


    @RequestMapping("/pieVO")
    @ResponseBody
    public List<pieVO> getPieVO(){
        return productService.getPieVO();
    }

}
