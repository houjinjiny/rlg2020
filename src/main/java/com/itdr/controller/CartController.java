package com.itdr.controller;

import com.itdr.common.ServerResponse;
import com.itdr.config.ConstCode;
import com.itdr.pojo.User;
import com.itdr.service.CartService;
import com.sun.xml.internal.fastinfoset.util.ValueArrayResourceException;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
@Controller
@ResponseBody
@RequestMapping("/portal/cart/")
public class CartController {
    @Autowired
    CartService cartService;

    /**
     * 购物车List列表
     * @param session
     * @return
     */
    @RequestMapping("list.do")
    public ServerResponse list(HttpSession session){
        User user= (User) session.getAttribute("user");
        if(user==null){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.NO_LOGIN.getCode(),
                    ConstCode.UserEnum.NO_LOGIN.getDesc());
        }
        return cartService.list(user);
    }

    /**
     * 添加购物车
     * @param session
     * @param productId
     * @param count
     * @param type
     * @return
     */
    @RequestMapping("add.do")
    public ServerResponse add(HttpSession session,
                              Integer productId,
                              @RequestParam(value = "count",required = false,defaultValue = "1") Integer count,
                              @RequestParam(value = "type",required = false,defaultValue = "1") Integer type){
        User user= (User) session.getAttribute("user");
        if(user==null){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.NO_LOGIN.getCode(),
                    ConstCode.UserEnum.NO_LOGIN.getDesc());
        }
        return cartService.add(productId,count,type,user);
    }

    /**
     * 更新购物车某个产品数量
     * @param session
     * @param productId
     * @param count
     * @param type
     * @return
     */
    @RequestMapping("update.do")
    public ServerResponse update(HttpSession session,
                              Integer productId,
                              @RequestParam(value = "count",required = false,defaultValue = "1") Integer count,
                              @RequestParam(value = "type",required = false,defaultValue = "0") Integer type){
        User user= (User) session.getAttribute("user");
        if(user==null){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.NO_LOGIN.getCode(),
                    ConstCode.UserEnum.NO_LOGIN.getDesc());
        }
        return cartService.update(productId,count,type,user);
    }

    /**
     *移除商品
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping("delete.do")
    public ServerResponse delete(HttpSession session,
                                 Integer productId){
        User user= (User) session.getAttribute("user");
        if(user==null){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.NO_LOGIN.getCode(),
                    ConstCode.UserEnum.NO_LOGIN.getDesc());
        }
        return cartService.delete(productId,user);
    }

    /**
     * 查询在购物车里的产品数量
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping("get_cart_product_count.do")
    public ServerResponse getCartProductCount(HttpSession session,
                                 Integer productId){
        User user= (User) session.getAttribute("user");
        if(user==null){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.NO_LOGIN.getCode(),
                    ConstCode.UserEnum.NO_LOGIN.getDesc());
        }
        return cartService.getCartProductCount(user);
    }
    @RequestMapping("checked.do")
    public ServerResponse checked(HttpSession session,
                                  Integer productId,
                                  @RequestParam(value = "type",required = false,defaultValue = "0") Integer type){
        User user= (User) session.getAttribute("user");
        if(user==null){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.NO_LOGIN.getCode(),
                    ConstCode.UserEnum.NO_LOGIN.getDesc());
        }
        return cartService.checked(productId,type,user);
    }
    @RequestMapping("over.do")
    public ServerResponse over(Integer shippingId, HttpSession session){
        User user= (User) session.getAttribute("user");
        if(user==null){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.NO_LOGIN.getCode(),
                    ConstCode.UserEnum.NO_LOGIN.getDesc());
        }
        return cartService.over(user);
    }

}
