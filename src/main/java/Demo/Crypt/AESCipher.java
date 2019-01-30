package Demo.Crypt;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @Author: Donlin
 * @Date: Created in 15:37 2018/8/27
 * @Version: 1.0
 * @Description: AES Cipher class
 */
public class AESCipher {
    private static SecretKey AESKey = null;     // A aes symmetric key
    private static String IV = "aabbccddeeffgghh";       // Default initialized vector
    private static Cipher cipher = null;        // A cipher to en/decrypt the plaintext

    Base64.Encoder encoder;                     // Base64 encoder
    Base64.Decoder decoder;                     // Base64 decoder

    private AESCipher(String IV){
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            AESKey = keyGenerator.generateKey();
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            this.IV = IV;
            encoder = Base64.getEncoder();
            decoder = Base64.getDecoder();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    public static AESCipher getInstance(){
        return new AESCipher(IV);
    }

    public static AESCipher getInstance(String IV){
        return new AESCipher(IV);
    }

    /**
     * Encrypt the plaintext
     * @param plaintext
     * @return
     */
    public byte[] encrypt(byte[] plaintext){
        byte[] ciphertext = null;
        try {
            cipher.init(Cipher.ENCRYPT_MODE, AESKey, new IvParameterSpec(IV.getBytes()));
            ciphertext = cipher.doFinal(plaintext);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return ciphertext;
    }

    /**
     * 使用Base64编码的String类AES密文
     * @param plaintext
     * @return
     */
    public String encryptWithBase64(String plaintext){
        byte[] plain = new byte[0];
        try {
            plain = plaintext.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] cipher = encrypt(plain);
        return encoder.encodeToString(cipher);
    }

    /**
     * Decrypt the ciphertext
     * @param ciphertext
     * @return
     */
    public byte[] decrypt(byte[] ciphertext){
        byte[] plaintext = null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, AESKey, new IvParameterSpec(IV.getBytes()));
            plaintext = cipher.doFinal(ciphertext);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return plaintext;
    }

    /**
     * 使用Base64解密String类的AES明文
     * @param ciphertext
     * @return
     */
    public String decryptWithBase64(String ciphertext){
        byte[] cipher = decoder.decode(ciphertext);
        byte[] plain = decrypt(cipher);
        String plaintext = null;
        try {
            plaintext = new String(plain, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return plaintext;
    }

    public byte[] getAESKey() {
        return AESKey.getEncoded();
    }

    /**
     * @Author: Donlin
     * @Date: Created in 15:34 2018/7/3
     * @Version: 1.0
     * @Description: AES加密demo test
     */
    public static class AESCipherDemo {

        // 测试用
        public static void main(String[] args) {
            AESCipher aesCipher = getInstance();
            String plaintext = "3";
            System.out.println("*************************");
            System.out.println(plaintext);
            System.out.println("*************************");
            String ciphertext = aesCipher.encryptWithBase64(plaintext);
            System.out.println(ciphertext);
            System.out.println("*************************");
            plaintext = aesCipher.decryptWithBase64(ciphertext);
            System.out.println(plaintext);
            System.out.println("*************************");
            System.out.println(new String(aesCipher.getAESKey()));
        }

    }
}
