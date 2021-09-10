package life.youshi.studynotes.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * [实用工具] JSON响应实用工具类
 *
 * 用来构建JSON响应数据。
 */
public class JsonResponseUtils {
    // 状态码
    // 正常：0
    // 异常：非0
    private Integer code = 0;
    // 消息
    private String message = "OK";
    // 数据
    private Map<String, Object> data = new LinkedHashMap<>();

    public JsonResponseUtils() {
    }

    public JsonResponseUtils(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public JsonResponseUtils(Integer code, String message, Map<String, Object> data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    /**
     * 设置数据
     *
     * @param key   键
     * @param value 值
     * @return      当前实例，以便链式调用
     */
    public JsonResponseUtils putData(String key, Object value) {
        data.put(key, value);
        return this;
    }

    /**
     * 获取JSON字符串
     *
     * @return      JSON字符串
     */
    public String toJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
