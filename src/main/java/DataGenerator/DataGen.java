package DataGenerator;

import java.io.*;
import java.util.*;

/**
 * @Author: Donlin
 * @Date: Created in 20:03 2019/1/15
 * @Version: 1.0
 * @Description:
 */
public class DataGen {

    public static String prefixFileName = "F:\\MySSE\\src\\main\\resources\\";
    public static Map<String, String[]> classifer = new HashMap<String, String[]>();
    public static Map<Integer, String> keywordSet = new HashMap();
    public static String filename = prefixFileName + "classify.txt";
    public static int N = 1000;                 // 总共生成数据的规模大小
    public static int n = 10;                   // 每天服务内容包含的关键字个数
    public static Random random = new Random();

    static {
        File file = new File(filename);
        try {
            Reader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            String s = null;
            while ((s = reader.readLine()) != null){
                System.out.println(s);
                String[] ss = s.split(":");
                String[] sss = ss[1].split("、");
                classifer.put(ss[0], sss);
            }

            reader.close();
            fileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File keywordFile = new File(prefixFileName + "keyword.txt");
        try {
            Writer w = new FileWriter(keywordFile);
            BufferedWriter keywordWriter = new BufferedWriter(w);

            // 取出hashmap的所有元素组成hashset
            int i = 0;
            for (String key : classifer.keySet()) {
                //sum = sum + classifer.get(key).length;
                //System.out.printf("%s of length is %d\n", key, classifer.get(key).length);
                keywordSet.put(i, key);
                i++;
                for (String word : classifer.get(key)){
                    keywordSet.put(i, word);
                    i++;
                }
            }
            for (int key: keywordSet.keySet()) {
                keywordWriter.write(keywordSet.get(key) + "\n");
            }
            keywordWriter.flush();

            keywordWriter.close();
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println(keywordSet.size());
    }

    public static void main(String[] args) {
        // 打开一个文件输出流
        String dataFile = String.format("F:\\MySSE\\src\\main\\resources\\LBSData_%d.csv", N);
        try {
            Writer dataWriter = new FileWriter(dataFile);
            BufferedWriter bufDataWriter = new BufferedWriter(dataWriter);
//            String title = "\"PID\",\"latitude\",\"longitude\",\"keywords of content\"\n";
//            bufDataWriter.write(title);

            // LBS数据集形式：
            // csv格式：PID,latitude,longitude,content
            // PID-递增用户ID，latitude-纬度，longitude-经度，content-10个服务内容的关键字
            for (int i = 0; i < N; i++) {
                // todo: 生成递增内容PID
                int PID = i;

                // todo: 读取已经生成好的经纬度数据
                String latitude = LocalGen.getLatitude(random.nextDouble());
                String longitude = LocalGen.getLongitude(random.nextDouble());

                // todo: 选取服务内容的关键字，形成真正的LBS数据集，这个要符合一定的概率分布，或者关键字我重新组织新的内容关键字
                StringBuffer content = new StringBuffer();
                for (int j = 0; j < n; j++) {
                    if (j == n -1 ){
                        content.append(keywordSet.get((int)(Math.random() *keywordSet.size())));
                    }else {
                        content.append(keywordSet.get((int)(Math.random() *keywordSet.size())) + ",");
                    }
                }
                String contents = content.toString();
                // todo: 组合成一条LBS数据并写入data.txt文件
                String lbs = String.format("%d,%s,%s,%s\n", PID, latitude, longitude, contents);
                bufDataWriter.write(lbs);
            }
            bufDataWriter.flush();

            bufDataWriter.close();
            dataWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
