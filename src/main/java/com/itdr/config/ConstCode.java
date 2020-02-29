package com.itdr.config;

public class ConstCode {
    public final static int DEFAULT_SUCCESS=200;
    public final static int DEFAULT_FAIL=100;
    public final static String UNLAWFULNESS_PARAM="非法参数";
    public enum UserEnum {
        //状态信息
        ERROR_PASSWORD(1, "密码错误"),
        EMPTY_USERNAME(2, "用户名不能为空"),
        EMPTY_PASSWORD(3, "密码不能为空"),
        EMPTY_QUESTION(4, "问题不能为空"),
        EMPTY_ANSWER(5, "答案不能为空"),
        INEXISTENCE_USER(6, "用户名不存在"),
        EXIST_USER(7, "用户名已存在"),
        EXIST_USEROREMAIL(8, "用户名或邮箱已注册"),
        EMPTY_PARAM(9, "注册信息不能为空"),
        EMPTY_PARAM2(10, "更新信息不能为空"),
        SUCCESS_USER(11, "用户注册成功"),
        SUCCESS_MSG(12, "校验成功"),
        NO_LOGIN(13, "用户未登录"),
        NO_QUESTION(14, "该用户未设置找回密码问题"),
        ERROR_ANSWER(15, "问题答案错误"),
        LOSE_EFFICACY(16, "token已经失效"),
        UNLAWFULNESS_TOKEN(17, "非法的token"),
        DEFEACTED_PASSWORDNEW(18, "修改密码操作失败"),
        SUCCESS_PASSWORDNEW(19, "修改密码操作成功"),
        ERROR_PASSWORDOLD(20, "旧密码输入错误"),
        SUCCESS_USERMSG(21, "更新个人信息成功"),
        FORCE_EXIT(22, "用户未登录，无法获取当前用户信息"),
        LOGOUT(23, "退出成功"),
        FAIL_LOGIN(24,"登录失败"),
        FAIL_REGISTER(25,"用户注册失败"),
        EMPTY_PARAMETER(26,"参数不能为空"),
        FAIL_PARAMETER(27,"参数不合法"),
        FAIL_USERMSG(28, "更新个人信息失败"),
        ;

        private int code;
        private String desc;

        private UserEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
    public enum ProductEnum {
        //状态信息
        UNLAWFULNESS_PARAM(1,"参数不合法"),
        INEXISTENCE_PRODUCT(2,"商品不存在"),
        EMPTY_PARAM(3,"参数不能为空"),
        BEYOND_STOCK(4,"超出库存")
        ;

        private int code;
        private String desc;

        private ProductEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
    public enum CartEnum {
        //状态信息
        EMPTY_CART(1,"购物车为空"),
        FAIL_ADD(2,"添加失败"),
        FAIL_DELETE(3,"移除失败"),
        FAIL_CHECKED(4,"选择状态更新失败"),
        EMPTY_CHECKED(5,"没有被选中的商品")
        ;

        private int code;
        private String desc;

        private CartEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
    public enum OrderEnum {
        //状态信息
        EMPTY_ORDER(1,"订单不存在"),
        FAIL_ORDER(2,"订单不匹配")
        ;

        private int code;
        private String desc;

        private OrderEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
