package com.lmh.secondhandbook.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmh.secondhandbook.entity.Product;
import com.lmh.secondhandbook.entity.ProductCategory;
import com.lmh.secondhandbook.mapper.ProductCategoryMapper;
import com.lmh.secondhandbook.mapper.ProductMapper;
import com.lmh.secondhandbook.service.ProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmh.secondhandbook.service.ProductService;
import com.lmh.secondhandbook.vo.ProductCategoryVO;
import com.lmh.secondhandbook.vo.ProductVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Autowired
    private ProductMapper productMapper;

    //自定义的方法
    @Override
    public List<ProductCategoryVO> getAllProductCategoryVO() {

        //查询一级分类
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("type",1);//查询一级商品
        List<ProductCategory> One_level = productCategoryMapper.selectList(wrapper);
        //二级分类
        wrapper = new QueryWrapper();
        wrapper.eq("type",2);//查询二级商品
        List<ProductCategory> Two_level = productCategoryMapper.selectList(wrapper);

        //三级分类
        wrapper = new QueryWrapper();
        wrapper.eq("type",3);//查询三级商品
        List<ProductCategory> Three_level = productCategoryMapper.selectList(wrapper);
        /*
        将各级list转为vo
         */
        //一级VO(包含二级三级VO)
        List<ProductCategoryVO> One_Vo = new ArrayList<>();
        for (ProductCategory one_product : One_level) {
            ProductCategoryVO one_vo = new ProductCategoryVO(one_product.getId(),one_product.getName());
            One_Vo.add(one_vo);//装到One_Vo中
        }
        for (int i = 0; i< One_Vo.size();i++){

            wrapper = new QueryWrapper();
            wrapper.eq("categorylevelone_id",One_Vo.get(i).getId());
            productMapper.selectList(wrapper);
            List<Product> productList = productMapper.selectList(wrapper);
            List<ProductVO> productVOList = productList.stream()
                    .map(e->new ProductVO(e.getId(),e.getName(),e.getPrice(),e.getFileName())).collect(Collectors.toList());
            One_Vo.get(i).setProductVOList(productVOList);
        }

        //OneproductCategoryVO一和二之间的
        for (ProductCategoryVO OneproductCategoryVO : One_Vo) {
            wrapper = new QueryWrapper();
            //两个条件满足才行
            wrapper.eq("type",2);
            wrapper.eq("parent_id",OneproductCategoryVO.getId());
            List<ProductCategory> One_levelTwo = productCategoryMapper.selectList(wrapper);
            List<ProductCategoryVO> levelTwoVO =One_levelTwo.stream().map(e->new ProductCategoryVO(e.getId(),e.getName())).collect(Collectors.toList());
            OneproductCategoryVO.setThisChild(levelTwoVO);

            //查找二和三级的
            for (ProductCategoryVO TwoproductcategoryVO : levelTwoVO) {
                wrapper = new QueryWrapper();
                wrapper.eq("type",3);
                wrapper.eq("parent_id",TwoproductcategoryVO.getId());
                List<ProductCategory> Two_levelThree = productCategoryMapper.selectList(wrapper);
                List<ProductCategoryVO> levelThreeVO = Two_levelThree.stream().map(e->new ProductCategoryVO(e.getId(),e.getName())).collect(Collectors.toList());
                TwoproductcategoryVO.setThisChild(levelThreeVO);
            }
            
        }
        //最终封装好的levelOneVO
        return One_Vo;
    }

    @Override
    public Integer getCategorylevelOne(String name) {
        QueryWrapper queryWrapper;
        queryWrapper = new QueryWrapper();
        queryWrapper.eq("name",name);
        ProductCategory productCategory = productCategoryMapper.selectOne(queryWrapper);

        QueryWrapper queryWrapper1;
        queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("id",productCategory.getParentId());
        ProductCategory productCategory1 = productCategoryMapper.selectOne(queryWrapper1);
        return productCategory1.getParentId();
    }

    @Override
    public Integer getCategorylevelTwo(String name) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name",name);
        ProductCategory productCategory = productCategoryMapper.selectOne(queryWrapper);
        return productCategory.getParentId();
    }

    @Override
    public Integer getCategorylevelThree(String name) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name",name);
        ProductCategory productCategory = productCategoryMapper.selectOne(queryWrapper);
        return productCategory.getId();
    }


}
