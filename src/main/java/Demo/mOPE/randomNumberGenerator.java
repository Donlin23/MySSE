package Demo.mOPE;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * @Author: Donlin
 * @Date: Created in 12:30 2018/12/5
 * @Version: 1.0
 * @Description: 生成一个随机数文件.txt
 */
public class randomNumberGenerator {

    public static String path = "F:\\mycryptdb\\src\\main\\resources\\random_text.txt";
    public static int num = 10000;

    // todo: 实现一个生成均匀分布的随机数的程序，生成在当前目录下,格式为txt文件
    // 已完成
    public static void main(String[] args) {
        // 创建一个随机数生成器
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        // 创建一个文件random_text.txt，保存生成的随机数
        File file = new File(path);
        try {
            if (!file.exists()){
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            for (int i = 0; i < num ; i++) {
                String stringNumber = String.valueOf(random.nextInt(1000000)) + "\n";
                outputStream.write(stringNumber.getBytes());
            }
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
