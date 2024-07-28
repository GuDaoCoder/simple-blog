package com.github.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * Json工具类
 *
 * @author Gudao
 * @since 2024/7/28
 */
public class JacksonUtil {

    public static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        // 对象所有字段全部列入
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        // 取消默认转换timestamps格式
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
        // 取消空Bean转换错误
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 日期格式统一
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));
        // 忽略对象中不存在的属性
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 对象转json格式字符串
     * @param object Object
     * @return java.lang.String
     */
    public static String toJsonString(Object object) {
        if (Objects.isNull(object)) {
            return null;
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * json格式字符串转对象
     * @param text String
     * @param clazz Class<T>
     * @return T
     */
    public static <T> T parseObject(String text, Class<T> clazz) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(text, clazz);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
