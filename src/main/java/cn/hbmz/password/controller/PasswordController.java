package cn.hbmz.password.controller;

import cn.hbmz.password.entiy.Users;
import cn.hbmz.password.mapper.PasswordMapper;
import cn.hbmz.password.util.Password;
import cn.hbmz.password.util.RandomPassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
@Slf4j
@RestController
public class PasswordController {
    //随机生成一个密钥，静态代码仅执行一次
    private static final byte[] passwordEncodingKey = {102, 43, 58, -24, 91, -124, -65, -55, 12, -92, 26, -96, 62, 126, -124, 56};
    static {
//        passwordEncodingKey = RandomPassword.generateRandomKey();这里随机生成
    }
    @Autowired
    PasswordMapper mapper;
    @Autowired
    Password passwordEncoding;
    @GetMapping("/userdata")
    public List<Users> querry(){
        List<Users> passwords = mapper.selectList(null);
        for(int i = 0;i < passwords.size();i++){
            passwords.set(i,Password.decryptUser(passwords.get(i), passwordEncodingKey));//将解密的用户传进去
            log.info("解密后：" + passwords.get(i));
        }
        return passwords;
    }
    @PostMapping("/save")
    public void save(@RequestBody Users users){
        log.warn(Arrays.toString(passwordEncodingKey));
        Users user = new Users();
        user.setUserName(passwordEncoding.encrypt(passwordEncodingKey,users.getUserName()));
        user.setPassword(passwordEncoding.encrypt(passwordEncodingKey,users.getPassword()));
        user.setWebsite(users.getWebsite());
        log.info(users.toString());
        int insert = mapper.insert(user);
        log.info("insert"+ insert);
    }

}
