package cn.hbmz.password.util;
import cn.hbmz.password.controller.PasswordController;
import cn.hbmz.password.entiy.Role;
import cn.hbmz.password.entiy.Users;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.util.Base64;

/**
 * `ALGORITHM_NAME`：表示SM4加密算法的名称
 * `ALGORITHM_NAME_PADDING`：表示使用CBC模式和PKCS7填充模式的SM4加密算法的名称
 * `DEFAULT_IV`：表示默认的初始化向量（IV），用于CBC模式下的加密和解密操作
 * `CHARSET_NAME`：表示明文和密文所使用的字符编码名称，这里使用的是UTF-8
 */
@Component
public class Password {
    private static final String ALGORITHM_NAME = "SM4";
    private static final String ALGORITHM_NAME_PADDING = "SM4/CBC/PKCS7Padding";
    private static final byte[] DEFAULT_IV = new byte[]{0x12, 0x34, 0x56, 0x78, (byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF, 0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF};
    private static final String CHARSET_NAME = StandardCharsets.UTF_8.name();

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * SM4加密
     *
     * @param key       加密秘钥，长度必须是16个字节
     * @param plaintext 明文
     * @return 返回Base64编码后的密文
     */
    public static String encrypt(byte[] key, String plaintext) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM_NAME_PADDING);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, ALGORITHM_NAME);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(DEFAULT_IV);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("SM4加密失败！", e);
        }
    }

    /**
     * SM4解密
     *
     * @param key         加密秘钥，长度必须是16个字节
     * @param ciphertext  密文，必须是Base64编码格式
     * @return 返回解密后的明文
     */
    public static String decrypt(byte[] key, String ciphertext) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM_NAME_PADDING);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, ALGORITHM_NAME);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(DEFAULT_IV);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("SM4解密失败！", e);
        }
    }

    /**
     * 将用户解密返回
     * @param user
     * @return
     */
    public static Users decryptUser(Users user,byte[] key){
        Users newUser = new Users();
        newUser.setUserName(decrypt(key,user.getUserName()));
        newUser.setPassword(decrypt(key,user.getPassword()));
        newUser.setWebsite(user.getWebsite());
        return newUser;
    }
    /**
     * 将角色解密返回
     * @param user
     * @return
     */
    public static Role decryptRole(Role user, byte[] key){
        Role newUser = new Role();
        newUser.setUserName(decrypt(key,user.getUserName()));
        newUser.setPassword(decrypt(key,user.getPassword()));
        return newUser;
    }
}