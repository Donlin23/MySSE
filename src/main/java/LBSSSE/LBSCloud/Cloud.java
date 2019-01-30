package LBSSSE.LBSCloud;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: Donlin
 * @Date: Created in 19:28 2019/1/19
 * @Version: 1.0
 * @Description:
 */
public class Cloud {

    private static int serverPort = 8040;

    // todo: 设计一个存储的数据结构，作为加密数据存储方式

    /**
     * 处理data stream
     * @param in
     * @param out
     */
    private static void dealDataStream(InputStream in, OutputStream out) {
        try {
            // 接收数据
            DataInputStream dataIn = new DataInputStream(in);
            byte[] buf = new byte[2048];
            int c;
            StringBuffer sb = new StringBuffer();

            while ((c = dataIn.read(buf)) != -1){
                sb.append(new String(buf, 0, c));
            }
            // todo: 解析json数据，然后分开调用provider的处理和user的处理
            System.out.println(sb.toString());

            // 数据接收完毕，反馈数据
            DataOutputStream dataOut = new DataOutputStream(out);
            dataOut.writeUTF("已接收所有json数据。");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    // todo: provider发来的数据，存到内存的数据结构中，以供查询

    // todo: user线程：接收user发来的查询请求，处理该请求（重点）

    public static void main(String[] args) {
        // todo: 写一个TCP服务器，绑定一个端口，接收client端发来的数据，user、provider都是client
        // 判断的规则是根据一个jsonobject对象的某个字段，判断该数据来自provider还是user
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(serverPort);
            while (true){
                System.out.println("Wait for linking...");
                Socket socket = serverSocket.accept();

                System.out.println("Link has established from address " + socket.getRemoteSocketAddress());
                // 获取输入流、输出流
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
                // 这里写一个方法处理json数据的入口
                dealDataStream(in ,out);

                // 关闭socket
                System.out.println("Disconnect the link " + socket.getRemoteSocketAddress());
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (serverSocket != null){
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
