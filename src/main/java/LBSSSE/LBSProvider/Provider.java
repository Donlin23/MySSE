package LBSSSE.LBSProvider;

import Util.GeohashUtil;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: Donlin
 * @Date: Created in 11:32 2019/1/18
 * @Version: 1.0
 * @Description: provider主程序
 */
public class Provider {

    private static String prefixFileName = null;
    private static int scaleFactor = 1000;

    private static String serverHost = "localhost";
    private static int serverPort = 8040;

    static {
        Properties props = System.getProperties();
        String systemType = props.getProperty("os.name");
        if (systemType.equals("Windows 10")){
            prefixFileName = "F:\\MySSE\\src\\main\\resources\\";
        }else {
            // todo: 当改程序处在Linux环境下应该切换别的数据路径
        }
    }

    private static Map<Integer, String[]> dataMap = new HashMap<>();    // 存放原始数据的map
    private static GeohashUtil geohashEncoder = new GeohashUtil();

    public Provider() {
    }

    /**
     * 读取数据到内存中
     * @param filename
     */
    public void loadData(String filename){
        try {
            Reader reader = new FileReader(new File(filename));
            BufferedReader  bufReader = new BufferedReader(reader);
            String s = null;
            while ((s = bufReader.readLine()) != null){
                String[] ss = s.split(",");
                dataMap.put(Integer.parseInt(ss[0]), Arrays.copyOfRange(ss,1, ss.length - 1));
            }

            bufReader.close();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 逐条处理数据，一条数据组装一个JSONObject对象
     * @param pid
     * @param contents
     * @return
     */
    public JSONObject dealData(int pid, String[] contents) {
        /**
         * 这里要定一个json的数据格式：
         * {
         *     "from": provider / user,
         *     "pid": pid,
         *     "geohash": geohash,
         *     "m1_vector": m1,
         *     "m2_vector": m2,
         *     "sha1": sha1,
         *     "ciphertext": aes_ciphertext,
         *     "comment": comment
         * }
         */
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("from", "provider");
        jsonObject.put("pid",pid);
        // todo: 处理latitude and longitude，用geohash编码
        double lat = Double.valueOf(contents[0]);
        double lon = Double.valueOf(contents[1]);
        String geohashEncode = geohashEncoder.encode(lat, lon);
        jsonObject.put("geohash", geohashEncode);
        // todo: 处理关键字,先建立该条内容的特征向量，然后对向量做secure KNN加密，得到{m1^T P',m2^T p"}作为加密的特征向量

        // todo: 计算AES密文以及SHA验证值

        // 注释
        jsonObject.put("comment", "这是一个注释");
        return jsonObject;
    }


    public static void main(String[] args) {

        // todo: 实例化一个provider去处理这个程序
        Provider provider = new Provider();

        // todo: 读取LBSData_x.csv数据文件到内存中
        String file = String.format("LBSData_%d.csv", scaleFactor);
        provider.loadData(prefixFileName + file);
        System.out.printf("【info】loadData completed. %s has loaded in memory.", file);

        // todo: 开辟一个socket，将数据传到cloud
        try {
            // 连接到cloud
            Socket providerClient = new Socket(serverHost, serverPort);

            // 获取输出流
            DataOutputStream out = new DataOutputStream(providerClient.getOutputStream());
            // 组装json数据并发送
            for (Integer pid: dataMap.keySet()) {
                // 返回一条组装好的jsonObject，然后将该jsonObject写到网络中
                JSONObject jsonObject = provider.dealData(pid, dataMap.get(pid));
                // todo: 通过输出流json格式上传cloud
                out.write(jsonObject.toString().getBytes());
                out.writeChar('\t');
            }
            providerClient.shutdownOutput();

            // 获取输入流，获得cloud的反馈
            DataInputStream in = new DataInputStream(providerClient.getInputStream());
            System.out.println("Cloud response: " + in.readUTF());

            // 关闭socket
            providerClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
