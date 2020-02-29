package com.itdr.service.impl;

import com.itdr.common.ServerResponse;
import com.itdr.config.ConstCode;
import com.itdr.mapper.CartMapper;
import com.itdr.mapper.ProductMapper;
import com.itdr.pojo.Cart;
import com.itdr.pojo.Product;
import com.itdr.pojo.User;
import com.itdr.pojo.vo.CartProductVo;
import com.itdr.pojo.vo.CartVo;
import com.itdr.service.CartService;
import com.itdr.utils.BigDecimalUtil;
import com.itdr.utils.ObjectToVoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartMapper cartMapper;
    @Autowired
    ProductMapper productMapper;
    //获取购物车全部信息
    private ServerResponse<List<Cart>> getCartList(User user){
        List<Cart> carts=cartMapper.selectByUserId(user.getId());
        if (carts.size()==0){
            return ServerResponse.defeatedRS(
                    ConstCode.CartEnum.EMPTY_CART.getCode(),
                    ConstCode.CartEnum.EMPTY_CART.getDesc());
        }
        return ServerResponse.successRS(carts);
    }
    //根据购物车获取购物车商品和是否全选以及总价的封装类
    protected CartVo getCartVO(List<Cart> carts){
        //根据购物车获取商品
        List<CartProductVo> cartProductVos=new ArrayList<>();
        boolean flag=true;
        BigDecimal cartTotalPrice=new BigDecimal("0");
        for(Cart cart: carts){
            Product product=productMapper.selectByProductId(cart.getProductId());
            if(product!=null){
                //购物车与商品进行封装
                CartProductVo cartProductVo=ObjectToVoUtil.cartProductVoToVo(cart,product);
                cartProductVos.add(cartProductVo);
                if(cart.getChecked()==1){
                    cartTotalPrice=BigDecimalUtil.add(cartTotalPrice.doubleValue(),cartProductVo.getProductTotalPrice().doubleValue());
                }

            }
            if(cart.getChecked()==0){
                flag=false;
            }
        }
        CartVo cartVo = ObjectToVoUtil.toCartVo(cartProductVos,flag,cartTotalPrice);
        return cartVo;
    }
    //商品是否在售
    private ServerResponse<Product> productIsExit(Integer productId){
        Product product=productMapper.selectByProductId(productId);
        if(product==null || product.getStatus()!=1){
            return ServerResponse.defeatedRS(
                    ConstCode.ProductEnum.INEXISTENCE_PRODUCT.getCode(),
                    ConstCode.ProductEnum.INEXISTENCE_PRODUCT.getDesc());
        }
        return ServerResponse.successRS(product);
    }
    //根据不同类型更新商品
    private ServerResponse updateByType(Integer type,Cart cart,Integer count){
        if(type==0){
            cart.setQuantity(count+cart.getQuantity());
        }else if(type==1){
            cart.setQuantity(count);
        }
        int i = cartMapper.updateByPrimaryKey(cart);
        if(i<=0){
            return ServerResponse.defeatedRS(
                    ConstCode.CartEnum.FAIL_ADD.getCode(),
                    ConstCode.CartEnum.FAIL_ADD.getDesc());
        }
        return ServerResponse.successRS(i);
    }
    /**
     * 获取购物车信息
     * @param user
     * @return
     */
    @Override
    public ServerResponse list(User user) {
        //获取购物车信息
        ServerResponse<List<Cart>> cartList = getCartList(user);
        if(!cartList.isSuccess()){
            return cartList;
        }
        List<Cart> carts= cartList.getData();
        CartVo cartVo=getCartVO(carts);
        return ServerResponse.successRS(cartVo);
    }

    /**
     * 添加购物车
     * @param productId
     * @param count
     * @param type
     * @param user
     * @return
     */
    @Override
    public ServerResponse add(Integer productId, Integer count, Integer type,User user) {
        //参数合法判断
        if(productId==null || productId < 0){
            return ServerResponse.defeatedRS(
                    ConstCode.ProductEnum.EMPTY_PARAM.getCode(),
                    ConstCode.ProductEnum.EMPTY_PARAM.getDesc());
        }
        if(count==null || count <=0){
            return ServerResponse.defeatedRS(
                    ConstCode.ProductEnum.EMPTY_PARAM.getCode(),
                    ConstCode.ProductEnum.EMPTY_PARAM.getDesc());
        }
        //商品是否在售
        ServerResponse<Product> product=productIsExit(productId);
        if(!product.isSuccess()){
            return product;
        }
        //是否超出库存
        if(count>product.getData().getStock()){
            return ServerResponse.defeatedRS(
                    ConstCode.ProductEnum.BEYOND_STOCK.getCode(),
                    ConstCode.ProductEnum.BEYOND_STOCK.getDesc());
        }
        Cart c=new Cart();
        c.setUserId(user.getId());
        c.setProductId(productId);
        c.setQuantity(count);
        Cart cart=cartMapper.selectByUserIdAndPriductId(user.getId(),productId);
        if(cart==null){
            int i=cartMapper.insert(c);
            if(i!=1){
                return ServerResponse.defeatedRS(
                        ConstCode.CartEnum.FAIL_ADD.getCode(),
                        ConstCode.CartEnum.FAIL_ADD.getDesc());
            }
        }else{
            //根据不同类型更新商品
            ServerResponse serverResponse = updateByType(type, cart, count);
            if(!serverResponse.isSuccess()){
                return serverResponse;
            }
        }
        //获取购物车信息
        return list(user);
    }

    /**
     * 更新购物车某个产品数量
     * @param productId
     * @param count
     * @param type
     * @param user
     * @return
     */
    @Override
    public ServerResponse update(Integer productId, Integer count, Integer type, User user) {
        //用户在购物车页面使用加减器更新
        //参数合法判断
        if(productId==null || productId < 0){
            return ServerResponse.defeatedRS(
                    ConstCode.ProductEnum.EMPTY_PARAM.getCode(),
                    ConstCode.ProductEnum.EMPTY_PARAM.getDesc());
        }
        if(count==null || count <=0){
            return ServerResponse.defeatedRS(
                    ConstCode.ProductEnum.EMPTY_PARAM.getCode(),
                    ConstCode.ProductEnum.EMPTY_PARAM.getDesc());
        }
        //商品是否在售
        ServerResponse<Product> product=productIsExit(productId);
        if(!product.isSuccess()){
            return product;
        }
        //根据用户ID和商品ID获得购物车
        Cart cart=cartMapper.selectByUserIdAndPriductId(user.getId(),productId);
        //根据不同类型更新商品
        ServerResponse serverResponse = updateByType(type, cart, count);
        if(!serverResponse.isSuccess()){
            return serverResponse;
        }
        //获取购物车信息
        return list(user);
    }

    /**
     * 移除购物车某个产品
     * @param productId
     * @param user
     * @return
     */
    @Override
    public ServerResponse delete(Integer productId, User user) {
        //参数判断
        if(productId!=null && productId<0){
            return ServerResponse.defeatedRS(
                    ConstCode.ProductEnum.UNLAWFULNESS_PARAM.getCode(),
                    ConstCode.ProductEnum.UNLAWFULNESS_PARAM.getDesc());
        }

        if(productId!=null){
            int i=cartMapper.deleteByUserIdAndProductId(user.getId(),productId);
            if(i<=0){
                return ServerResponse.defeatedRS(
                        ConstCode.CartEnum.FAIL_DELETE.getCode(),
                        ConstCode.CartEnum.FAIL_DELETE.getDesc());
            }
        }else{
            int i=cartMapper.deleteByUserIdAndCheck(user.getId());
            if(i<=0){
                return ServerResponse.defeatedRS(
                        ConstCode.CartEnum.FAIL_DELETE.getCode(),
                        ConstCode.CartEnum.FAIL_DELETE.getDesc());
            }
        }

        return list(user);
    }

    /**
     *  购物车商品数量
     * @param user
     * @return
     */
    @Override
    public ServerResponse getCartProductCount(User user) {
        List<Cart> carts = cartMapper.selectByUserId(user.getId());
        if (carts.size()==0){
            return ServerResponse.defeatedRS(
                    ConstCode.CartEnum.EMPTY_CART.getCode(),
                    ConstCode.CartEnum.EMPTY_CART.getDesc());
        }
        int sum=0;
        for (Cart cart:carts){
            sum+=cart.getQuantity();
        }
        return ServerResponse.successRS(sum);
    }

    /**
     * 购物车选择状态
     * @param productId
     * @param type
     * @param user
     * @return
     */
    @Override
    public ServerResponse checked(Integer productId, Integer type, User user) {
        //参数判断
        if(productId!=null&&productId<0){
            return ServerResponse.defeatedRS(
                    ConstCode.ProductEnum.UNLAWFULNESS_PARAM.getCode(),
                    ConstCode.ProductEnum.UNLAWFULNESS_PARAM.getDesc());
        }
        int i=cartMapper.updateByUserIdAndProduct(user.getId(),productId,type);
        if(i<=0){
            return ServerResponse.defeatedRS(
                    ConstCode.CartEnum.FAIL_CHECKED.getCode(),
                    ConstCode.CartEnum.FAIL_CHECKED.getDesc());
        }
        return list(user);
    }

    @Override
    public ServerResponse over(User user) {
        List<Cart> carts=cartMapper.selectByUserId(user.getId());
        if(carts.size()==0){
            return ServerResponse.defeatedRS(
                    ConstCode.CartEnum.EMPTY_CART.getCode(),
                    ConstCode.CartEnum.EMPTY_CART.getDesc());
        }
        boolean bol=false;
        for(Cart cart:carts){
            if(cart.getChecked()==1){
                bol=true;
            }
        }
        if(!bol){
            return ServerResponse.defeatedRS(
                    ConstCode.CartEnum.EMPTY_CHECKED.getCode(),
                    ConstCode.CartEnum.EMPTY_CHECKED.getDesc());
        }
        return ServerResponse.successRS(true);
    }
}
