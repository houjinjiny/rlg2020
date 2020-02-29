package com.itdr.service.impl;

import com.itdr.common.ServerResponse;
import com.itdr.config.ConstCode;
import com.itdr.mapper.*;
import com.itdr.pojo.*;
import com.itdr.pojo.vo.CartVo;
import com.itdr.pojo.vo.OrderItemVo;
import com.itdr.pojo.vo.OrderVo;
import com.itdr.pojo.vo.ShippingVo;
import com.itdr.service.CartService;
import com.itdr.service.OrderService;
import com.itdr.utils.BigDecimalUtil;
import com.itdr.utils.ObjectToVoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    CartMapper cartMapper;
    @Autowired
    CartService cartService;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderItemMapper orderItemMapper;
    @Autowired
    ShippingMapper shippingMapper;
    private Long getOrderNo(){
        long round = Math.round(Math.random() * 100);
        Long o=System.currentTimeMillis()+round;
        return o;
    }
    @Override
    public ServerResponse create(User user, Integer shippingId) {
        //参数判断
        if(shippingId==null || shippingId<0){
            return ServerResponse.defeatedRS(ConstCode.UNLAWFULNESS_PARAM);
        }
        //查看购物车里是否有已选中的订单
        ServerResponse over = cartService.over(user);
        if(!over.isSuccess()){
            return ServerResponse.defeatedRS(
                    ConstCode.CartEnum.EMPTY_CHECKED.getCode(),
                    ConstCode.CartEnum.EMPTY_CHECKED.getDesc());
        }
        //获取随机订单号
        Long orderNo = getOrderNo();
        //获取应付价格
        List<Cart> carts = cartMapper.selectByUserId(user.getId());
        CartVo cartVO = ((CartServiceImpl) cartService).getCartVO(carts);
        //创建一个订单
        Order order=new Order();
        order.setUserId(user.getId());
        order.setOrderNo(orderNo);
        order.setShippingId(shippingId);
        order.setPayment(cartVO.getCartTotalPrice());
        order.setPaymentType(1);
        order.setPostage(0);
        order.setStatus(10);
        //把订单信息放入数据库
        int i=orderMapper.insert(order);
        if(i<=0){
            return ServerResponse.defeatedRS("订单添加失败");
        }
        //创建订单详情
        List<OrderItemVo> orderItemVoList=new ArrayList<>();
        for (Cart cart:carts){
            OrderItem orderItem=new OrderItem();
            orderItem.setUserId(user.getId());
            orderItem.setOrderNo(orderNo);
            if(cart.getChecked()==1){
                Product product=productMapper.selectByProductId(cart.getProductId());
                if(product.getStatus()==1 && product.getStock()>=cart.getQuantity()){
                    orderItem.setProductId(product.getId());
                    orderItem.setProductName(product.getName());
                    orderItem.setProductImage(product.getMainImage());
                    orderItem.setCurrentUnitPrice(product.getPrice());
                    orderItem.setQuantity(cart.getQuantity());
                    orderItem.setTotalPrice(BigDecimalUtil.mul(cart.getQuantity(),product.getPrice().doubleValue()));
                }
                //把订单详情信息放入数据库
                int i2=orderItemMapper.insert(orderItem);
                if(i2<=0){
                    return ServerResponse.defeatedRS("订单详情添加失败");
                }
                OrderItemVo orderItemVo = ObjectToVoUtil.orderItemToOrderItemVo(orderItem);
                orderItemVoList.add(orderItemVo);
            }
        }
        //清空购物车
        int i3=cartMapper.deleteByUserIdAndCheck(user.getId());
        if(i3<=0){
            return ServerResponse.defeatedRS("购物车删除失败");
        }
        Shipping shipping = shippingMapper.selectByPrimaryKey(shippingId);
        if(shipping==null){
            return ServerResponse.defeatedRS("地址不存在");
        }
        ShippingVo shippingVo = ObjectToVoUtil.shippingToShippingVo(shipping);
        OrderVo orderVo = ObjectToVoUtil.toOrderVo(order, orderItemVoList, shippingId, shippingVo);
        return ServerResponse.successRS(orderVo);
    }
}
