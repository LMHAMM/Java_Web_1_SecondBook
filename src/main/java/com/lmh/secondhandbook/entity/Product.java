package com.lmh.secondhandbook.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author mh
 * @since 2021-08-08
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 主键
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 名称
     */
      private String name;

      /**
     * 描述
     */
      private String description;

      /**
     * 价格
     */
      private Float price;

      /**
     * 库存
     */
      private Integer stock;

      /**
     * 一级分类
     */
      private Integer categoryleveloneId;

      /**
     * 二级分类
     */
      private Integer categoryleveltwoId;

      /**
     * 三级分类
     */
      private Integer categorylevelthreeId;

      /**
     * 图片名称
     */
      private String fileName;


}
