package com.pay.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;
import java.util.Map;

/**
 * @TITLE JsonUtils.java
 * @NAME JSON工具类
 * @DATE 2017-1-19
 */
public class JsonUtils {

    /**
     * 功能描述：转换为JSON字符串
     *
     * @param obj 数据对象
     */
    public static String getJson(Object obj) throws Exception {
        return JSON.toJSONString(obj);
    }

    public static String toJSONString(Object obj) throws Exception {
        return JSON.toJSONString(obj, true);
    }

    /**
     * 功能描述：把JSON数据转换成普通字符串列表
     *
     * @param jsonData JSON数据
     */
    public static List<String> getStringList(String jsonData) throws Exception {
        return JSON.parseArray(jsonData, String.class);
    }

    /**
     * 功能描述：把JSON数据转换成指定的java对象
     *
     * @param jsonData JSON数据
     * @param clazz    指定的java对象
     */
    public static <T> T getSingleBean(String jsonData, Class<T> clazz) throws Exception {
        return JSON.parseObject(jsonData, clazz);
    }

    /**
     * 功能描述：把JSON数据转换成指定的java对象列表
     *
     * @param jsonData JSON数据
     * @param clazz    指定的java对象
     */
    public static <T> List<T> getBeanList(String jsonData, Class<T> clazz) throws Exception {
        return JSON.parseArray(jsonData, clazz);
    }

    /**
     * 功能描述：把JSON数据转换成较为复杂的java对象列表
     *
     * @param jsonData JSON数据
     */
    public static List<Map<String, Object>> getBeanMapList(String jsonData) throws Exception {
        return JSON.parseObject(jsonData, new TypeReference<List<Map<String, Object>>>() {
        });
    }

    /**
     * 功能描述：把JSON数据转换成较为复杂的java对象列表
     *
     * @param jsonData JSON数据
     */
    public static Map<String, String> getBeanMap(String jsonData) throws Exception {
        return JSON.parseObject(jsonData, new TypeReference<Map<String, String>>() {
        });
    }

    /**
     * 将网络请求下来的数据用fastjson处理空的情况，并将时间戳转化为标准时间格式
     *
     * @param result
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String dealResponseResult(String result) {
        result = JSON.toJSONString(result, SerializerFeature.WriteClassName, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullBooleanAsFalse, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteEnumUsingToString, SerializerFeature.WriteSlashAsSpecial, SerializerFeature.WriteTabAsSpecial);
        return result;
    }
}
