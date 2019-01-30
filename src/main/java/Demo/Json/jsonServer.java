package Demo.Json;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: Donlin
 * @Date: Created in 14:53 2019/1/12
 * @Version: 1.0
 * @Description: 一个实现接收json数据并存储到Redis数据库的服务端
 */
public class jsonServer {
    public static void main(String[] args) {

        try {
            // 作为服务器绑定端口8040，接收客户端的json数据
            ServerSocket serverSocket = new ServerSocket(8040);
            System.out.println("******** Server has opened. ********");
            Socket socket = serverSocket.accept();  //用这个socket接收客户端发送的json数据
            int count = 0;

            DataInputStream inputStream = null;     // 数据输入流
            DataOutputStream outputStream = null;   // 数据输出流
            String strInputStream = "";

            inputStream = new DataInputStream(socket.getInputStream());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] bytes = new byte[2048];
            int n;
            while ((n = inputStream.read(bytes)) != -1){
                baos.write(bytes, 0, n);        // readline()将会把json数据格式破坏掉
            }

            strInputStream = new String(baos.toByteArray());
            socket.shutdownInput();
            baos.close();

            // todo: 解析数据并存到Redis

            // todo: 反馈给客户端处理结果

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
