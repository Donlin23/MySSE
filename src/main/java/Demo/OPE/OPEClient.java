package Demo.OPE;

import Demo.Crypt.AESCipher;

import java.util.Random;

/**
 * @Author: Donlin
 * @Date: Created in 15:17 2018/10/30
 * @Version: 1.0
 * @Description: Demo.OPE client means a client that interact with Demo.OPE server. ps:这个client只负责产生随机数，然后向server发送要加密的数值型数据
 */
public class OPEClient {

    private Random random ;             // 不断产生随机数的随机数产生器
    private AESCipher aesCipher;        // 一个加密器，已经封装好了可以直接加解密

    public OPEClient(){
        this.random = new Random(System.currentTimeMillis());
        this.aesCipher = AESCipher.getInstance();
    }

    public int nextInt(){
        return random.nextInt();
    }

    // todo: 封装一个AES直接调用的加密模块
    /**
     * 传入String类型的数值，返回一个String类型的AES密文
     * @param num
     * @return
     */
    public String encrypted(String num){
        return aesCipher.encryptWithBase64(num);
    }

    // todo: 封装一个AES直接调用的解密模块
    /**
     * 传入Base64编码的String类型AES密文，返回String类型的数值型明文
     * @param ciph
     * @return
     */
    public String decrypted(String ciph){
        return aesCipher.decryptWithBase64(ciph);
    }

}
