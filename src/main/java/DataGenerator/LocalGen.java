package DataGenerator;

import java.io.*;
import java.util.Random;

/**
 * @Author: Donlin
 * @Date: Created in 10:07 2019/1/14
 * @Version: 1.0
 * @Description: 生成相当规模的经纬度数据
 */
public class LocalGen {

    public static int dataScale = 10000;   // 生成经纬度数据的个数
    public static String filename = "F:\\MySSE\\src\\main\\resources\\localtion_lat_lon.txt";

    public static String latPrefix = "23.";
    public static String lonPrefix = "113.3";


    public static String getLatitude(double seed) {
        int flag = 0;
        if (seed > 0.5){
            flag = 1;
        }
        String result = latPrefix + flag + String.valueOf((1+seed) * Math.pow(10, 10)).substring(2, 11);
        return result;
    }

    public static String getLongitude(double seed) {
        String result = lonPrefix + String.valueOf((1+seed) * Math.pow(10, 9)).substring(2, 11);
        return result;
    }

    public static void main(String[] args) {
        // 生成文件
        File file = new File(filename);
        try {
            if (!file.exists()){
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // 打开一个输出流
            Writer writer = new FileWriter(file);

            // todo: 根据广州地理位置随机生成一些经纬度
            // 参考URL：http://www.gpsspg.com/maps.htm
            // 广州地区经纬度示例：(23.0613537423,113.3994538899) (23.0591468546,113.3891752100) (23.0880192798,113.3448119351) (23.1173023001,113.3225933266)
            // 规律：(latitude,longitude)(纬度，经度)-(23.0000000000-23.1999999999,113.3000000000-113.3999999999)

            Random random = new Random();
            for (int i = 0; i < dataScale; i++) {
                String localtion = getLatitude(random.nextDouble()) + "," + getLongitude(random.nextDouble()) + "\n";
                writer.write(localtion);
            }
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
