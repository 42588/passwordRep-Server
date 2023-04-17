package cn.hbmz.password.util;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.util.Arrays;

@Slf4j
public class RandomPassword {
    /**
     *  生成一个16字节长的随机密钥
     * @return 返回生成的密钥字节数组
     */
    public static byte[] generateRandomKey() {
        byte[] key = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(key);
        log.info("当前的密钥 ：" + Arrays.toString(key));
        return key;
    }
}
