package Demo.Json;

import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @Author: Donlin
 * @Date: Created in 14:52 2019/1/12
 * @Version: 1.0
 * @Description: 一个实现发送json数据的网络客户端
 */
public class jsonClient {
    public static void main(String[] args) {
        // todo: 组织json数据格式
        JSONObject jsonObject = jsonDemo.JSONDemo();
        byte[] jsonBytes = jsonObject.toString().getBytes();

        // todo: 向8040端口建立一个TCP连接
        try {
            Socket socket = new Socket("localhost",8040);
            System.out.println("*** Client has linked the port 8040");
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());

            outputStream.write(jsonBytes);
            outputStream.flush();

            socket.shutdownOutput();
            outputStream.close();



            // todo: 连接server并发送json数据

            // todo: 打印server的反馈




        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
