package com.itdr.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itdr.common.ServerResponse;
import com.itdr.config.ConstCode;
import com.itdr.mapper.CategoryMapper;
import com.itdr.mapper.ProductMapper;
import com.itdr.pojo.Category;
import com.itdr.pojo.Product;
import com.itdr.pojo.vo.ProductVo;
import com.itdr.service.ProductService;
import com.itdr.utils.ObjectToVoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryMapper categoryMapper;

    /**
     * 根据fid获取商品分类
     * @param pid
     * @return
     */
    @Override
    public ServerResponse<Category> basecategory(Integer pid) {
        //参数合法判断
        if(pid==null ||pid<0){
            return ServerResponse.defeatedRS(
                    ConstCode.ProductEnum.UNLAWFULNESS_PARAM.getCode(),
                    ConstCode.ProductEnum.UNLAWFULNESS_PARAM.getDesc());
        }
        //根据父id查找
        List<Category> li=categoryMapper.selectByPid(pid);
        return ServerResponse.successRS(li);
    }

    @Override
    public ServerResponse<Product> detail(Integer productId) {
        //参数合法判断
        if(productId==null ||productId<0){
            return ServerResponse.defeatedRS(
                    ConstCode.ProductEnum.UNLAWFULNESS_PARAM.getCode(),
                    ConstCode.ProductEnum.UNLAWFULNESS_PARAM.getDesc());
        }
        Product product=productMapper.selectByProductId(productId);
        if (product==null || product.getStatus()!=1){
            return ServerResponse.defeatedRS(
                    ConstCode.ProductEnum.INEXISTENCE_PRODUCT.getCode(),
                    ConstCode.ProductEnum.INEXISTENCE_PRODUCT.getDesc());
        }
        //封装ProductVo
        ProductVo productVo=ObjectToVoUtil.productToProductVo(product);
        return ServerResponse.successRS(productVo);
    }

    @Override
    public ServerResponse<ProductVo> list(String keyWord,Integer pageNum, Integer pageSize, String orderBy) {
        //空参判断
        if(StringUtils.isEmpty(keyWord)){
            return ServerResponse.defeatedRS(
                    ConstCode.ProductEnum.EMPTY_PARAM.getCode(),
                    ConstCode.ProductEnum.EMPTY_PARAM.getDesc());
        }
        String keywords="%"+keyWord+"%";
        //排序参数处理
        String spli[]=new String[2];
        if(!StringUtils.isEmpty(orderBy)){
             spli=orderBy.split("_");
            PageHelper.startPage(pageNum,pageSize,spli[0]+" "+spli[1]);
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Product> li=productMapper.selectByKeyword(keywords);
        PageInfo pf = new PageInfo(li);
        List<ProductVo> productVos=new ArrayList<>();
        for(Product product:li){
            ProductVo productVo=ObjectToVoUtil.productToProductVo(product);
            productVos.add(productVo);
        }
        pf.setList(productVos);
        return ServerResponse.successRS(pf);
    }
}
