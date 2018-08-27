package Demo;

import Crypt.AESCipher;

/**
 * @Author: Donlin
 * @Date: Created in 15:34 2018/7/3
 * @Version: 1.0
 * @Description: AES加密demo
 */
public class AESCipherDemo {

    // 测试用
    public static void main(String[] args) {
        AESCipher aesCipher = AESCipher.getInstance();
        String plaintext = "hello worldashdkjashfkjasfhajshfjasf";
        System.out.println("*************************");
        System.out.println(plaintext);
        System.out.println("*************************");
        byte[] ciphertext = aesCipher.encrypt(plaintext.getBytes());
        System.out.println(new String(ciphertext));
        System.out.println("*************************");
        plaintext = new String(aesCipher.decrypt(ciphertext));
        System.out.println(plaintext);
        System.out.println("*************************");
        System.out.println(new String(aesCipher.getAESKey()));
    }

}
