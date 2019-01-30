package LBSSSE.User;

import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @Author: Donlin
 * @Date: Created in 19:19 2019/1/19
 * @Version: 1.0
 * @Description:
 */
public class User {

    private static String serverHost = "localhost";
    private static int serverPort = 8040;
    // todo: 提供一个接受控制台输入关键字的入口

    // todo: 获取KMC的相关密钥参数

    // todo: 通过密钥相关参数构建查询的特征向量

    // todo: 加密并处理查询特征向量

    // todo: 处理用户查询的位置信息

    // todo: 组装整个Query，并以json格式向cloud发起查询请求

    // todo: 解密查询结果集并逐条验证查询结果，返回给控制台
    public static void main(String[] args) {
        // todo: 先写一个client连接的程序，接受并发送相应的json数据 bingo
        try {
            Socket userClient = new Socket(serverHost, serverPort);

            DataOutputStream out = new DataOutputStream(userClient.getOutputStream());
            // json 数据组装并发送
            for (int i = 0; i < 10; i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("from", "user");
                jsonObject.put("qid",i);
                out.write(jsonObject.toString().getBytes());
                out.writeChar('\t');
            }
            // json数据发送结束
            userClient.shutdownOutput();

            // 接收cloud反馈
            DataInputStream in = new DataInputStream(userClient.getInputStream());
            System.out.println("Server Response:"+in.readUTF());

            // 关闭socket
            userClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
