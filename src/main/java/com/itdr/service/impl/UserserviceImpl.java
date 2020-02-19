package com.itdr.service.impl;

import com.itdr.common.ServerResponse;
import com.itdr.config.ConstCode;
import com.itdr.config.TokenCache;
import com.itdr.mapper.UserMapper;
import com.itdr.pojo.User;
import com.itdr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Service
public class UserserviceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public ServerResponse login(String username, String password) {
        //参数非空判断
        if(StringUtils.isEmpty(username)){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.EMPTY_USERNAME.getCode(),
                    ConstCode.UserEnum.EMPTY_USERNAME.getDesc());
            }
        if(StringUtils.isEmpty(password)){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.EMPTY_PASSWORD.getCode(),
                    ConstCode.UserEnum.EMPTY_PASSWORD.getDesc());
        }
        User u=userMapper.selectByUsernameAndPassword(username,password);
        if(u==null){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.FAIL_LOGIN.getCode(),
                    ConstCode.UserEnum.FAIL_LOGIN.getDesc());
        }
        return ServerResponse.successRS(u);
    }

    /**
     * 注册
     * @param u
     * @return
     */
    @Override
    public ServerResponse<User> register(User u) {
        //参数非空判断
        if(StringUtils.isEmpty(u.getUsername())){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.EMPTY_USERNAME.getCode(),
                    ConstCode.UserEnum.EMPTY_USERNAME.getDesc());
        }
        if(StringUtils.isEmpty(u.getPassword())){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.EMPTY_PASSWORD.getCode(),
                    ConstCode.UserEnum.EMPTY_PASSWORD.getDesc());
        }
        if(StringUtils.isEmpty(u.getQuestion())){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.EMPTY_QUESTION.getCode(),
                    ConstCode.UserEnum.EMPTY_QUESTION.getDesc());
        }
        if(StringUtils.isEmpty(u.getAnswer())){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.EMPTY_ANSWER.getCode(),
                    ConstCode.UserEnum.EMPTY_ANSWER.getDesc());
        }
        //查找用户名是否存在
        ServerResponse<User> username = checkValid(u.getUsername(), "username");
        //查找邮箱是否才能在
        ServerResponse<User> email = checkValid(u.getEmail(), "email");
        if(!username.isSuccess() || !email.isSuccess()){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.EXIST_USEROREMAIL.getCode(),
                    ConstCode.UserEnum.EXIST_USEROREMAIL.getDesc());
        }
        int insert=userMapper.insert(u);
        if(insert<=0){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.FAIL_REGISTER.getCode(),
                    ConstCode.UserEnum.FAIL_REGISTER.getDesc());
        }
        return ServerResponse.successRS(
                ConstCode.UserEnum.SUCCESS_USER.getDesc());
    }

    /**
     * 检查用户名或邮箱是否有效
     * @param str
     * @param type
     * @return
     */
    @Override
    public ServerResponse<User> checkValid(String str, String type) {
        if(StringUtils.isEmpty(str)||StringUtils.isEmpty(type)){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.EMPTY_PARAMETER.getCode(),
                    ConstCode.UserEnum.EMPTY_PARAMETER.getDesc());
        }
        //查找用户名或邮箱是否存在
        int i=userMapper.selectByUserNameOrEmail(str,type);
        if(i>0){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.EXIST_USEROREMAIL.getCode(),
                    ConstCode.UserEnum.EXIST_USEROREMAIL.getDesc());
        }
        return ServerResponse.successRS(
                ConstCode.UserEnum.SUCCESS_MSG.getDesc());
    }

    /**
     * 登录状态更新个人信息
     * @param email
     * @param phone
     * @param question
     * @param answer
     * @param user
     * @return
     */
    @Override
    public ServerResponse<User> updateInformation(String email, String phone, String question, String answer, User user) {
        User u=new User();
        u.setId(user.getId());
        u.setEmail(email);
        u.setPhone(phone);
        u.setQuestion(question);
        u.setAnswer(answer);
        int i = userMapper.updateByPrimaryKeySelective(u);
        if(i<=0){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.FAIL_USERMSG.getCode(),
                    ConstCode.UserEnum.FAIL_USERMSG.getDesc()
                    );
        }
        return ServerResponse.successRS(ConstCode.UserEnum.SUCCESS_USERMSG.getDesc());
    }

    @Override
    public ServerResponse<User> forgetGetQuestion(String username) {
        //参数非空判断
        if(StringUtils.isEmpty(username)){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.EMPTY_USERNAME.getCode(),
                    ConstCode.UserEnum.EMPTY_USERNAME.getDesc());
        }
        //该用户是否存在可以在前端通过ajax判断
        User u=userMapper.selectByusername(username);
        if(u==null){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.INEXISTENCE_USER.getCode(),
                    ConstCode.UserEnum.INEXISTENCE_USER.getDesc());
        }
        //查找用户密保问题不能为空
        String question = u.getQuestion();
        if(StringUtils.isEmpty(question)){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.NO_QUESTION.getCode(),
                    ConstCode.UserEnum.NO_QUESTION.getDesc());
        }
        return ServerResponse.successRS(ConstCode.DEFAULT_SUCCESS,question);
    }

    @Override
    public ServerResponse<User> forgetCheckAnswer(String username, String question, String answer) {
        //参数非空判断
        if(StringUtils.isEmpty(username)){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.EMPTY_USERNAME.getCode(),
                    ConstCode.UserEnum.EMPTY_USERNAME.getDesc());
        }
        if(StringUtils.isEmpty(question)){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.EMPTY_QUESTION.getCode(),
                    ConstCode.UserEnum.EMPTY_QUESTION.getDesc());
        }
        if(StringUtils.isEmpty(answer)){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.EMPTY_ANSWER.getCode(),
                    ConstCode.UserEnum.EMPTY_ANSWER.getDesc());
        }
        int i=userMapper.selectByUsernameAndQuestionAndAnswer(username,question,answer);
        if(i<=0){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.ERROR_ANSWER.getCode(),
                    ConstCode.UserEnum.ERROR_ANSWER.getDesc());
        }
        //返回随机令牌
        String s = UUID.randomUUID().toString();
        TokenCache.set("token_" + username, s);
        return ServerResponse.successRS(ConstCode.DEFAULT_SUCCESS,s);
    }

    @Override
    public ServerResponse<User> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        //参数非空判断
        if(StringUtils.isEmpty(username)){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.EMPTY_USERNAME.getCode(),
                    ConstCode.UserEnum.EMPTY_USERNAME.getDesc());
        }
        if(StringUtils.isEmpty(passwordNew)){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.EMPTY_PASSWORD.getCode(),
                    ConstCode.UserEnum.EMPTY_PASSWORD.getDesc());
        }
        if(StringUtils.isEmpty(forgetToken)){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.UNLAWFULNESS_TOKEN.getCode(),
                    ConstCode.UserEnum.UNLAWFULNESS_TOKEN.getDesc());
        }
        String token=TokenCache.get("token_"+username);
        if(token==null || token.equals("")){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.LOSE_EFFICACY.getCode(),
                    ConstCode.UserEnum.LOSE_EFFICACY.getDesc());
        }
        if(!token.equals(forgetToken)){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.UNLAWFULNESS_TOKEN.getCode(),
                    ConstCode.UserEnum.UNLAWFULNESS_TOKEN.getDesc());
        }
        int i=userMapper.updateByUsernameAndPasswordNew(username,passwordNew);
        if(i<=0){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.DEFEACTED_PASSWORDNEW.getCode(),
                    ConstCode.UserEnum.DEFEACTED_PASSWORDNEW.getDesc());
        }
        return ServerResponse.successRS(
                ConstCode.UserEnum.SUCCESS_PASSWORDNEW.getCode(),
                ConstCode.UserEnum.SUCCESS_PASSWORDNEW.getDesc());
    }

    @Override
    public ServerResponse<User> resetPassword(User user, String passwordOld, String passwordNew) {
        //参数非空判断
        if(StringUtils.isEmpty(passwordOld)){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.EMPTY_PASSWORD.getCode(),
                    ConstCode.UserEnum.EMPTY_PASSWORD.getDesc());
        }
        if(StringUtils.isEmpty(passwordNew)){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.EMPTY_PASSWORD.getCode(),
                    ConstCode.UserEnum.EMPTY_PASSWORD.getDesc());
        }
        int i=userMapper.updateByUsernameAndPasswordOldAndPasswordNew(user.getUsername(),passwordOld,passwordNew);
        if(i<=0){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.DEFEACTED_PASSWORDNEW.getCode(),
                    ConstCode.UserEnum.DEFEACTED_PASSWORDNEW.getDesc());
        }
        return ServerResponse.successRS(
                ConstCode.UserEnum.SUCCESS_PASSWORDNEW.getCode(),
                ConstCode.UserEnum.SUCCESS_PASSWORDNEW.getDesc());
    }

}
