package com.lmh.secondhandbook.controller;


import com.lmh.secondhandbook.entity.Product;
import com.lmh.secondhandbook.entity.User;
import com.lmh.secondhandbook.service.CartService;
import com.lmh.secondhandbook.service.ProductCategoryService;
import com.lmh.secondhandbook.service.ProductService;
import com.lmh.secondhandbook.vo.EditBookVO;
import com.lmh.secondhandbook.vo.NewBookVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
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
@RequestMapping("//product")
public class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private CartService service1;

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/booklist/{type}/{id}")
    public ModelAndView booklist(@PathVariable("type") String type, @PathVariable("id") Integer id, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("productList");
        modelAndView.addObject("productList",service.findByCateId(type,id));
        modelAndView.addObject("booklist",productCategoryService.getAllProductCategoryVO());
        //购物车
        User user = (User) session.getAttribute("user");
        if(user == null){
            modelAndView.addObject("cartbooklist",new ArrayList<>());
        }else {
            modelAndView.addObject("cartbooklist",service1.findAllCartVOByUserId(user.getId()));
        }
        return modelAndView;
    }

    @GetMapping("/findById/{bookid}")
    public ModelAndView findById(@PathVariable("bookid") Integer bookid,HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("productDetail");
        modelAndView.addObject("product",service.getById(bookid));
        modelAndView.addObject("booklist",productCategoryService.getAllProductCategoryVO());
        //购物车
        User user = (User) session.getAttribute("user");
        if(user == null){
            modelAndView.addObject("cartbooklist",new ArrayList<>());
        }else {
            modelAndView.addObject("cartbooklist",service1.findAllCartVOByUserId(user.getId()));
        }
        return modelAndView;
    }

    @PostMapping("/AddBook")
    public String AddBook(NewBookVO newBookVO){
        Product product = new Product();
        product.setName(newBookVO.getName());
        product.setDescription(newBookVO.getDescription());
        product.setPrice(newBookVO.getPrice());
        product.setStock(newBookVO.getStock());
        product.setFileName("NoPic.jpg");
        product.setCategorylevelthreeId(productCategoryService.getCategorylevelThree(newBookVO.getCategory()));
        product.setCategoryleveltwoId(productCategoryService.getCategorylevelTwo(newBookVO.getCategory()));
        product.setCategoryleveloneId(productCategoryService.getCategorylevelOne(newBookVO.getCategory()));

        boolean result = false;
        try {
            result =  service.save(product);
        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.printf("error");
            return "AddBook";
        }

        if(result){
            return "redirect:/Admin";
        }else{
            return "redirect:/Admin";
        }
    }

    @RequestMapping(value = "/EditBook",method = RequestMethod.GET)
    public String EditBook(HttpServletRequest request) throws Exception{

        String id = request.getParameter("id");
        String description = request.getParameter("description");
        String price = request.getParameter("price");
        String stock = request.getParameter("stock");
        Integer BookId = new Integer(id);
        Float BookPrice = new Float(price);
        Integer BookStock = new Integer(stock);
        EditBookVO editBookVO = new EditBookVO(BookId,description,BookPrice,BookStock);

        service.UpdateBookById(editBookVO);

        return "redirect:/Admin";
    }



    @RequestMapping(value = "/DeleteBook",method = RequestMethod.GET)
    public String DeleteBook(HttpServletRequest request) throws Exception{

        String id = request.getParameter("id");
        Integer BookId = new Integer(id);
        service.DeleteBookById(BookId);

        return "redirect:/Admin";
     }




    @PostMapping("/findByName")
    public String findByName(String name, HttpServletResponse response) throws IOException {
        Product product = new Product();
        PrintWriter out = response.getWriter();

        response.setContentType("text/html;charset=UTF-8");
        product.setId(service.findByName(name));
        if(product.getId() == null){
            String a = URLEncoder.encode("查找失败，请重新输入", "UTF-8");
            out.print("<script language=\"javascript\">alert(decodeURIComponent('"+a+"'));window.location.href='/productCategory/booklist'</script>");
//            return "redirect:/productCategory/booklist";
            return null;
        }else {
            return "redirect:/product/findById/"+product.getId();
        }
    }


}

