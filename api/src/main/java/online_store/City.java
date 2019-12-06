package online_store;

import java.io.Serializable;

public class City {

    /*
    深圳(City.SZ), 中山(City.ZS),
    广州(City.GZ), 杭州(City.HZ),
    上海(City.SH);

     */

    // 定义一个 private 修饰的实例变量
    private String city;

    public static final String 深圳="深圳";
    public static final String 中山="中山";
    public static final String 广州="广州";
    public static final String 杭州="杭州";
    public static final String 上海="上海";

    // 定义一个带参数的构造器，枚举类的构造器只能使用 private 修饰
    private City(String city) {
        this.city = city;
    }
}
