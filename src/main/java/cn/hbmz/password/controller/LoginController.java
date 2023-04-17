package cn.hbmz.password.controller;

import cn.hbmz.password.entiy.ResultComment;
import cn.hbmz.password.entiy.Role;
import cn.hbmz.password.service.UserLoginService;
import cn.hbmz.password.util.Password;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

//我规定1为处理成功，0为处理失败
@Slf4j
@RestController
public class LoginController {
    private static final int SUCCESS = 1;
    private static final int ERROR = 0;
    private static final byte[] passwordEncodingKey = {102, 43, 58, -24, 91, -124, -65, -55, 12, -92, 26, -96, 62, 126, -124, 56};

    @Autowired
    UserLoginService userService;
    /**
     * 登录，登录成功返回1 ，将该用户的id存到session中
     * @param request
     * @param role
     */
    @PostMapping("/login")
    public ResultComment login(HttpServletRequest request, @RequestBody Role role){
        ResultComment resultComment = new ResultComment();
        //产看是否为空
        if(role == null ) {
            resultComment.setState(ERROR);
            resultComment.setMessage("您输入的信息是空的");
            return resultComment;
        }
        //对传递过来的数据进行md5加密
        Role newrole = new Role();
        newrole.setUserName(Password.encrypt(passwordEncodingKey,role.getUserName()));
        newrole.setPassword(Password.encrypt(passwordEncodingKey,role.getPassword()));
///fdfsd
        QueryWrapper<Role> query = new QueryWrapper<>();
        query.eq("user_name",newrole.getUserName());
        Role role1 = userService.getOne(query);
        if(role1 == null){
            resultComment.setState(ERROR);
            resultComment.setMessage("没有找到账户信息");
            return resultComment;
        }
        if(!role1.getPassword().equals(newrole.getPassword())){
            resultComment.setState(ERROR);
            resultComment.setMessage("密码错误");
            return resultComment;
        }
        request.getSession().setAttribute("role_id",role1.getId());
        resultComment.setState(SUCCESS);
        return resultComment;
    }
//test
    /**
     * 注册账户，注册成功返回 1
     * @param role
     * @return
     */
    @PostMapping("/regist")
    public ResultComment regist(@RequestBody Role role){
        log.warn(Arrays.toString(passwordEncodingKey));
        Role user = new Role();
        ResultComment resultComment = new ResultComment();
        try {
            user.setUserName(Password.encrypt(passwordEncodingKey,role.getUserName()));
            user.setPassword(Password.encrypt(passwordEncodingKey,role.getPassword()));
            user.setMail(role.getMail());
            log.info(role.toString());
            userService.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            resultComment.setState(ERROR);
            resultComment.setMessage("保存用户信息出错");
        }
        log.info("insert success");
        resultComment.setState(SUCCESS);
        resultComment.setMessage("注册成功");
        return resultComment;
    }

    /**
     * 检查用户名有无重复，无重复返回 1
     * @param userName
     * @return
     */
    @GetMapping("/getUser")
    public ResultComment checkUser(@RequestParam("userName") String userName){
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        ResultComment resultComment = new ResultComment();
//        System.out.println(userName);
        userName = Password.encrypt(passwordEncodingKey,userName);
        roleQueryWrapper.eq("user_name",userName);
        Role one = userService.getOne(roleQueryWrapper);
        if(one != null){
            resultComment.setState(ERROR);
            resultComment.setMessage("已经存在该账户了");
            return resultComment;
        }
        resultComment.setState(SUCCESS);
        resultComment.setMessage("成功！");
        return resultComment;
    }
    /**
     * 将session中的数据进行删除，同时回到登录页面，浏览器端将state从浏览器存储中删除
     * 退出登录
     */
    @GetMapping("/loginOut")
    public void loginOut(){
        //将账户从session中删除
    }
}
