package com.itdr.controller;

import com.itdr.common.ServerResponse;
import com.itdr.config.ConstCode;
import com.itdr.pojo.User;
import com.itdr.pojo.bo.UserVo;
import com.itdr.service.UserService;
import com.itdr.utils.ObjectToVoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@ResponseBody
@RequestMapping("/portal/user/")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 用户登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping("login.do")
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse sr=userService.login(username,password);
        //登录成功后保存信息
        if(sr.isSuccess()){
            session.setAttribute("user",sr.getData());
        }
        return sr;
    }

    /**
     *用户注册
     * @param u
     * @return
     */
    @RequestMapping("register.do")
    public ServerResponse<User> register(User u){
        return  userService.register(u);
    }

    /**
     * 检查用户名或邮箱是否有效
     * @param str
     * @param type
     * @return
     */
    @RequestMapping("check_valid.do")
    public ServerResponse<User> checkValid(String str,String type,HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user==null){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.FORCE_EXIT.getCode(),
                    ConstCode.UserEnum.FORCE_EXIT.getDesc()
            );
        }
        return  userService.checkValid(str,type);
    }

    /**
     * 获取登录用户信息
     * @param session
     * @return
     */
    @RequestMapping("get_user_info.do")
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user==null){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.FORCE_EXIT.getCode(),
                    ConstCode.UserEnum.FORCE_EXIT.getDesc()
                    );
        }
        UserVo userVo=ObjectToVoUtil.userToUserVo(user);
        return ServerResponse.successRS(userVo);
    }

    /**
     * 获取当前登录用户的详细信息
     * @param session
     * @return
     */
    @RequestMapping("get_inforamtion.do")
    public ServerResponse<User> getInforamtion(HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user==null){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.FORCE_EXIT.getCode(),
                    ConstCode.UserEnum.FORCE_EXIT.getDesc()
            );
        }
        return ServerResponse.successRS(user);
    }

    /**
     * 登录状态更新个人信息
     * @param email
     * @param phone
     * @param question
     * @param answer
     * @param session
     * @return
     */
    @RequestMapping("update_information.do")
    public ServerResponse<User> updateInformation(String email, String phone, String question, String answer,HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user==null){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.FORCE_EXIT.getCode(),
                    ConstCode.UserEnum.FORCE_EXIT.getDesc()
            );
        }
        return userService.updateInformation(email,phone,question,answer,user);
    }


    /**
     * 退出登录
     * @param session
     * @return
     */
    @RequestMapping("logout.do")
    public ServerResponse<User> logout(HttpSession session){
        session.removeAttribute("user");
        return ServerResponse.successRS(ConstCode.UserEnum.LOGOUT.getDesc());
    }


    /**
     * 忘记密码
     * @param username
     * @return
     */
    @RequestMapping("forget_get_question.do")
    public ServerResponse<User> forgetGetQuestion(String username){

        return userService.forgetGetQuestion(username);
    }

    /**
     * 提交问题答案
     * @param username
     * @param question
     * @param answer
     * @return
     */
    @RequestMapping("forget_check_answer.do")
    public ServerResponse<User> forgetCheckAnswer(String username, String question, String answer){

        return userService.forgetCheckAnswer(username,question,answer);
    }

    /**
     * 忘记密码的重设密码
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @param session
     * @return
     */
    @RequestMapping("forget_reset_password.do")
    public ServerResponse<User> forgetResetPassword(String username, String passwordNew, String forgetToken,HttpSession session){
        ServerResponse<User> userServerResponse = userService.forgetResetPassword(username, passwordNew, forgetToken);
        if(userServerResponse.isSuccess()){
            session.removeAttribute("user");
        }
        return userServerResponse;
    }
    @RequestMapping("reset_password.do")
    public ServerResponse<User> resetPassword(String passwordOld, String passwordNew,HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user==null){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.FORCE_EXIT.getCode(),
                    ConstCode.UserEnum.FORCE_EXIT.getDesc()
            );
        }
        return userService.resetPassword(user,passwordOld,passwordNew);
    }
}
