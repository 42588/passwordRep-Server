package cn.hbmz.password;

import cn.hbmz.password.entiy.Users;
import cn.hbmz.password.mapper.PasswordMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PassWordApplicationTests {
@Autowired
    PasswordMapper mapper;
    @Test
    void contextLoads() {
        List<Users> passwords = mapper.selectList(null);
        for (Users item : passwords) {
            System.out.println(item);
        }
    }
    @Test
    void test01(){
        byte[] passwordEncodingKey = {102, 43, 58, -24, 91, -124, -65, -55, 12, -92, 26, -96, 62, 126, -124, 56};
        for(int i = 0; i<passwordEncodingKey.length ; i++){
            System.out.print(passwordEncodingKey[i]);
        }
    }

}
