package Demo.Json;

import org.json.JSONObject;

/**
 * @Author: Donlin
 * @Date: Created in 9:31 2019/1/12
 * @Version: 1.0
 * @Description: json数据的网络传输
 * json对象的三种构造方式：JSONObject、Map、JavaBean
 */
public class jsonDemo {
    public static void main(String[] args) {
        // todo: 组装一个json数据
        JSONDemo();

    }

    public static JSONObject JSONDemo() {
        JSONObject wangxiaoer = new JSONObject();
        wangxiaoer.put("name", "王小二");
        wangxiaoer.put("age", 25);
        wangxiaoer.put("school","华南理工大学");
        wangxiaoer.put("address",new String[]{"大学城校区","五山校区"});
        wangxiaoer.put("house", false);
        wangxiaoer.put("comment", "这是一个注释");
        System.out.println(wangxiaoer.toString());
        return wangxiaoer;
    }
}
