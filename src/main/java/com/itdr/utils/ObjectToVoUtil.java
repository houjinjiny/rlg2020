package com.itdr.utils;

import com.alipay.api.domain.ExtendParams;
import com.alipay.api.domain.GoodsDetail;
import com.itdr.config.pay.BizContent;
import com.itdr.config.pay.Configs;
import com.itdr.config.pay.PGoodsDetail;
import com.itdr.pojo.*;
import com.itdr.pojo.vo.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ObjectToVoUtil {
    /**
     * 用户信息封装
     * @param u
     * @return
     */
    public static UserVo userToUserVo(User u){
        UserVo uv=new UserVo();
        uv.setId(u.getId());
        uv.setUsername(u.getUsername());
        uv.setUname(u.getUname());
        uv.setSex(u.getSex());
        uv.setEmail(u.getEmail());
        uv.setPhone(u.getPhone());
        uv.setCreateTime(u.getCreateTime());
        uv.setUpdateTime(u.getUpdateTime());
        return uv;
    }

    /**
     * 商品类封装
     * @param p
     * @return
     */
    public static ProductVo productToProductVo(Product p){
        ProductVo pv=new ProductVo();
        pv.setId(p.getId());
        pv.setCategoryId(p.getCategoryId());
        pv.setName(p.getName());
        pv.setSubtitle(p.getSubtitle());
        pv.setMainImage(p.getMainImage());
        pv.setSubImages(p.getSubImages());
        pv.setDetail(p.getDetail());
        pv.setPrice(p.getPrice());
        pv.setStock(p.getStock());
        pv.setStatus(p.getStatus());
        pv.setNew(p.getNew());
        pv.setHot(p.getHot());
        pv.setBanner(p.getBanner());
        pv.setCreateTime(p.getCreateTime());
        pv.setUpdateTime(p.getUpdateTime());
        pv.setImagesHost(PropertiesUtil.getValue("ImageHost"));
        return pv;
    }

    /**
     * 购物车与商品的封装
     * @param t
     * @param p
     * @return
     */
    public static CartProductVo cartProductVoToVo(Cart t,Product p){
        CartProductVo cpv=new CartProductVo();
        cpv.setId(t.getId());
        cpv.setUserId(t.getUserId());
        cpv.setProductId(cpv.getProductId());
        cpv.setQuantity(t.getQuantity());
        cpv.setProductName(p.getName());
        cpv.setProductSubtitle(p.getSubtitle());
        cpv.setProductMainImage(p.getMainImage());
        cpv.setProductPrice(p.getPrice());
        cpv.setProductStatus(p.getStatus());
        BigDecimal bd=BigDecimalUtil.mul(t.getQuantity(),p.getPrice().doubleValue());
        cpv.setProductTotalPrice(bd);
        cpv.setProductStock(p.getStock());
        cpv.setProductChecked(t.getChecked());
        String limitQuantity="LIMIT_NUM_SUCCESS";
        if(t.getQuantity()>p.getStock()){
            limitQuantity="LIMIT_NUM_FAIL";
        }
        cpv.setLimitQuantity(limitQuantity);
        return cpv;
    }

    /**
     * 购物车的封装
     * @param li
     * @param flage
     * @param cartTotalPrice
     * @return
     */
    public static CartVo toCartVo(List<CartProductVo> li,boolean flage,BigDecimal cartTotalPrice){
        CartVo cv=new CartVo();
        cv.setCartProductVos(li);
        cv.setAllChecked(flage);
        cv.setCartTotalPrice(cartTotalPrice);
        return cv;
    }
    /*商品详情和支付宝商品类转换*/
    public static PGoodsDetail getNewPay(OrderItem orderItem){
        PGoodsDetail info = new PGoodsDetail();
        info.setGoods_id(orderItem.getProductId().toString());
        info.setGoods_name(orderItem.getProductName());
        info.setPrice(orderItem.getCurrentUnitPrice().toString());
        info.setQuantity(orderItem.getQuantity().longValue());
        return info;
    }

    /*获取一个BizContent对象*/
    public static BizContent getBizContent(Order order, List<OrderItem> orderItems){
        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = String.valueOf(order.getOrderNo());

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = "睿乐GO在线平台"+order.getPayment();

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = String.valueOf(order.getPayment());

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = "购买商品"+orderItems.size()+"件共"+order.getPayment()+"元";

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "001";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "001";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
        for (OrderItem orderItem : orderItems) {
            // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
            GoodsDetail goods = getNewPay(orderItem);
            // 创建好一个商品后添加至商品明细列表
            goodsDetailList.add(goods);
        }

        BizContent biz = new BizContent();
        biz.setSubject(subject);
        biz.setTotal_amount(totalAmount);
        biz.setOut_trade_no(outTradeNo);
        biz.setUndiscountable_amount(undiscountableAmount);
        biz.setSeller_id(sellerId);
        biz.setBody(body);
        biz.setOperator_id(operatorId);
        biz.setStore_id(storeId);
        biz.setExtend_params(extendParams);
        biz.setTimeout_express(timeoutExpress);
        //支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
        biz.setNotify_url(Configs.getNotifyUrl_test());
        biz.setGoods_detail(goodsDetailList);

        return biz;
    }

    /**
     * 商品详情封装
     * @param orderItem
     * @return
     */
    public static OrderItemVo orderItemToOrderItemVo(OrderItem orderItem){
        OrderItemVo orderItemVo=new OrderItemVo();
        orderItemVo.setOrderNo(orderItem.getOrderNo());
        orderItemVo.setProductId(orderItem.getProductId());
        orderItemVo.setProductName(orderItem.getProductName());
        orderItemVo.setProductImage(orderItem.getProductImage());
        orderItemVo.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
        orderItemVo.setQuantity(orderItem.getQuantity());
        orderItemVo.setTotalPrice(orderItem.getTotalPrice());
        orderItemVo.setCreateTime(orderItem.getCreateTime());
        return orderItemVo;
    }

    /**
     * 地址的封装
     * @param shipping
     * @return
     */
    public static ShippingVo shippingToShippingVo(Shipping shipping){
        ShippingVo shippingVo=new ShippingVo();
        shippingVo.setReceiverName(shipping.getReceiverName());
        shippingVo.setReceiverPhone(shipping.getReceiverPhone());
        shippingVo.setReceiverMobile(shipping.getReceiverMobile());
        shippingVo.setReceiverProvince(shipping.getReceiverProvince());
        shippingVo.setReceiverCity(shipping.getReceiverCity());
        shippingVo.setReceiverDistrict(shipping.getReceiverDistrict());
        shippingVo.setReceiverAddress(shipping.getReceiverAddress());
        shippingVo.setReceiverZip(shipping.getReceiverZip());
        return shippingVo;
    }
    public static OrderVo toOrderVo(Order order,List<OrderItemVo> orderItemVoList,Integer shippingId,ShippingVo shipping){
        OrderVo orderVo=new OrderVo();
        orderVo.setOrderNo(order.getOrderNo());
        orderVo.setPayment(order.getPayment());
        orderVo.setPaymentType(order.getPaymentType());
        orderVo.setPostage(order.getPostage());
        orderVo.setStatus(order.getStatus());
        orderVo.setPaymentTime(order.getPaymentTime());
        orderVo.setSendTime(order.getSendTime());
        orderVo.setEndTime(order.getEndTime());
        orderVo.setCloseTime(order.getCloseTime());
        orderVo.setCreateTime(order.getCreateTime());
        orderVo.setOrderItemVoList(orderItemVoList);
        orderVo.setShippingId(shippingId);
        orderVo.setShippingVo(shipping);
        orderVo.setImageHost(PropertiesUtil.getValue("ImageHost"));
        return orderVo;
    }
}
