package com.lmh.secondhandbook.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmh.secondhandbook.entity.Product;
import com.lmh.secondhandbook.entity.ProductCategory;
import com.lmh.secondhandbook.mapper.ProductCategoryMapper;
import com.lmh.secondhandbook.mapper.ProductMapper;
import com.lmh.secondhandbook.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmh.secondhandbook.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mh
 * @since 2021-08-08
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;


    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Override
    public List<Product> findByCateId(String type,Integer CateId) {
        Map<String,Object> map = new HashMap<>();
        map.put("categorylevel"+type+"_id",CateId);
        return productMapper.selectByMap(map);
    }

    @Override
    public DataVO<Product2VO> findData(Integer page,Integer limit) {
        DataVO dataVO = new DataVO();
        dataVO.setCode(0);
        dataVO.setMsg("");

        IPage<Product> productIPage = new Page<>(page,limit);
        IPage<Product> result = productMapper.selectPage(productIPage,null);

        dataVO.setCount(result.getTotal());

        List<Product> productList = result.getRecords();
        List<Product2VO> product2VOList = new ArrayList<>();
        for (Product product: productList) {
            Product2VO product2VO = new Product2VO();
            //BeanUtils的方法
            BeanUtils.copyProperties(product,product2VO);
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("id",product.getCategoryleveloneId());
            ProductCategory productCategory = productCategoryMapper.selectOne(wrapper);
            if(productCategory!=null){
                product2VO.setCategorylevelone(productCategory.getName());
            }


            wrapper = new QueryWrapper();
            wrapper.eq("id",product.getCategoryleveltwoId());
            productCategory = productCategoryMapper.selectOne(wrapper);
            if(productCategory!=null){
                product2VO.setCategoryleveltwo(productCategory.getName());
            }

            wrapper = new QueryWrapper();
            wrapper.eq("id",product.getCategorylevelthreeId());
            productCategory = productCategoryMapper.selectOne(wrapper);
            if(productCategory!=null){
                product2VO.setCategorylevelthree(productCategory.getName());
            }
            product2VOList.add(product2VO);
        }

        dataVO.setData(product2VOList);

        return dataVO;
    }

    @Override
    public List<pieVO> getPieVO() {
        List<BookpieVO> list = productMapper.findAllBookPieVO();
        List<pieVO> pieVOList = list.stream().map(e -> new pieVO(e.getCount(),e.getName())).collect(Collectors.toList());
        return pieVOList;
    }

    @Override
    public Integer findByName(String name) {

        List<Product> productList = productMapper.selectList(null);
        boolean exitorno = true;
        Integer bookId = null;
        for (Product product:productList) {
            if (product.getName().contains(name)){
                exitorno=false;
                bookId = product.getId();
            }
        }
        if(exitorno){
            return null;
        }else {
            return bookId;
        }
    }

    @Override
    public void DeleteBookById(int ids) {
        productMapper.deleteById(ids);
    }

    @Override
    public void UpdateBookById(EditBookVO editBookVO) {
        Product product = new Product();
        Product product1 = productMapper.selectById(editBookVO.getId());

        product.setName(product1.getName());
        product.setCategoryleveloneId(product1.getCategoryleveloneId());
        product.setCategoryleveltwoId(product1.getCategoryleveltwoId());
        product.setCategorylevelthreeId(product1.getCategorylevelthreeId());
        product.setFileName(product1.getFileName());


        product.setId(editBookVO.getId());
        product.setPrice(editBookVO.getPrice());
        product.setDescription(editBookVO.getDescription());
        product.setStock(editBookVO.getStock());

        productMapper.updateById(product);
    }
}
