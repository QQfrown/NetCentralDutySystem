package edu.gpnu.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * properties工具类
 * 对指定路径的properties文件获取和修改
 */
public class PropertiesUtil {

    private static Properties prop;

    /**
     *
     * @param path 项目文件的绝对路径，获取方法：Thread.currentThread().getContextClassLoader().getResource("").getPath();
     *             当properties文件在resource的properties目录下时，在项目路径后面拼接"properties/xx.properties";
     * @throws IOException io异常
     */
    public static void load(String path) throws IOException {
        prop = new Properties(); // 属性集合对象
        FileInputStream fis = new FileInputStream(path);
        prop.load(fis);
        fis.close(); // 关闭流
    }

    /**
     * 修改properties中的参数
     * @param path 要修改的文件路径
     * @param key 要修改的属性名
     * @param value 要修改的属性值
     * @return 是否修改成功
     * @throws IOException io异常
     */
    public static Boolean updatePro(String path, String key, String value) throws IOException {
        load(path);
        prop.setProperty(key, value);
        // 文件输出流
        FileOutputStream fos = new FileOutputStream(path);
        // 将Properties集合保存到流中
        prop.store(fos, "update [key:" + key + ", value:" + value + "]");
        fos.close();
        return true;
    }

    /**
     * 根据key获取properties中的value
     * @param path 文件路径
     * @param key 要获取的属性名
     * @return 属性值
     * @throws IOException io异常
     */
    public static String getPro(String path, String key) throws IOException {
        load(path);
        return prop.getProperty(key);
    }
}

