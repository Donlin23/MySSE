package Crypt;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

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

    private AESCipher(String IV){
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            AESKey = keyGenerator.generateKey();
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            this.IV = IV;
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

    public byte[] getAESKey() {
        return AESKey.getEncoded();
    }
}
