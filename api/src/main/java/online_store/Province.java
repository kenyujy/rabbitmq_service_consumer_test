package online_store;

import java.io.Serializable;

public class Province {

    /*
    广东(Province.GD), 福建(Province.FJ),
    湖南(Province.HN), 浙江(Province.ZJ),
    上海(Province.SH);

     */

    // 定义一个 private 修饰的实例变量
    private String province;

    public static final String 广东="广东";
    private static final String 福建="福建";
    private static final String 湖南="湖南";
    private static final String 浙江="浙江";
    private static final String 上海="上海";


    // 定义一个带参数的构造器，枚举类的构造器只能使用 private 修饰
    private Province(String province) {
        this.province = province;
    }
}
