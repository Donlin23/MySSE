package Demo.mOPE;

import java.io.*;
import java.util.ArrayList;

/**
 * @Author: Donlin
 * @Date: Created in 14:58 2018/12/5
 * @Version: 1.0
 * @Description:
 */
public class mOPEClient {

    public static ArrayList<Integer> plaintexts = new ArrayList<>();
    public static String path = randomNumberGenerator.path;

    // todo: 读取数据集，模拟作为client给server发送均匀分布的数据
    public static void main(String[] args) {
        // 读取数据集到内存中
        readDataInMem(path);

        for (int i = 0; i < 1000; i++) {
            System.out.println(plaintexts.get(i));
        }

        // 将数据集发送到server上，单机版:直接调用server的accept方法，网络版:调用socket方法


    }

    // todo: 读取数据集
    public static void readDataInMem(String path){
        File file = new File(path);
        if (!file.exists()){
            System.err.printf("The file %s is not exists.\n", path);
            return ;
        }
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = fileReader.readLine()) != null){
                plaintexts.add(Integer.valueOf(tempString));
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 网络版
    // todo: 将plaintext中的数据传送到server，开一个socket发送过去

}
