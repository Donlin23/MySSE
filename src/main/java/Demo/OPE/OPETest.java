package Demo.OPE;

/**
 * @Author: Donlin
 * @Date: Created in 15:21 2018/10/30
 * @Version: 1.0
 * @Description: Construction a scenario to use ope client and ope server.
 */
public class OPETest {
    public static void main(String[] args) {
        // generate a client
        OPEClient opeClient = new OPEClient();
        // generate a server
        OPEServer opeServer = new OPEServer();

        // todo: 模拟client与server交互insert的过程
        // 1.首先从client中产生一个随机数
        int a = opeClient.nextInt();
        // 2.通过client中加密
        String currentCipher = opeClient.encrypted(String.valueOf(a));
        // 3.send到server上，如果server中已经存在这个密文，返回true，不存在返回false
        if (opeServer.contains(currentCipher)){
            // 5.调用server的一个方法，返回该密文的opeEncode，进入下一阶段

        }else{
            // 4.触发insert过程
            /**
             * insert具体的过程(Algorithm 1)：
             *      1.client调用server的getRoot函数，获取root的cipher
             *      2.client解密cipher，并判断与a比大小，cipher>a 调用 getLeft，cipher<a 调用 getRight
             *      3.返回null的时候，将a加密成新的cipher，调用server的insert，并返回true，insert成功的同时会产生ope encode，这个时候存入opetable
             *
             *      insert的这个过程，触发的前提是opetable中没有a这个值
             *      所以server应该是getRoot函数，每次指向root，然后需要一个记录当前节点的[path]的数据结构
             */
            String local = opeServer.getRoot();
            while(local != null){

            }
            opeServer.insert(currentCipher);
        }

    }
}
