package com.teach.javafx.models.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * <p>DataRequest 请求参数数据类
 * <p>Map data 保存前端请求参数的Map集合
 */
@Getter
@Setter
public class DataRequest {
    private Map<String, Object> data;

    public DataRequest() {
        data = new HashMap<>();
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public void add(String key, Object obj){
        // 对参数值进行字符过滤
        if (obj instanceof String) {
            obj = filterHtmlSpecialChars((String)obj);
        }
        data.put(key,obj);
    }

    public Object get(String key){
        // 对参数值进行字符过滤
        Object obj = data.get(key);
        if (obj instanceof String) {
            obj = filterHtmlSpecialChars((String)obj);
        }
        return obj;
    }

    public String getString(String key){
        // 对参数值进行字符过滤
        Object obj = data.get(key);
        if(obj == null)
            return null;
        if(obj instanceof String) {
            return filterHtmlSpecialChars((String)obj);
        }
        return obj.toString();
    }

    public Boolean getBoolean(String key){
        Object obj = data.get(key);
        if(obj == null)
            return false;
        if(obj instanceof Boolean)
            return (Boolean)obj;
        if("true".equals(obj.toString()))
            return true;
        else
            return false;
    }

    public List getList(String key){
        Object obj = data.get(key);
        if(obj == null)
            return new ArrayList<>();
        if(obj instanceof List)
            return (List)obj;
        else
            return new ArrayList<>();
    }

    public Map<String, Object> getMap(String key){
        if(data == null)
            return new HashMap<>();
        Object obj = data.get(key);
        if(obj == null)
            return new HashMap<>();
        if(obj instanceof Map)
            return (Map<String, Object>)obj;
        else
            return new HashMap<>();
    }

    public Integer getInteger(String key) {
        if(data == null)
            return null;
        Object obj = data.get(key);
        if(obj == null)
            return null;
        if(obj instanceof Integer)
            return (Integer)obj;
        String str = obj.toString();
        try {
            return (int)Double.parseDouble(str);
        }catch(Exception e) {
            return null;
        }
    }

    public Long getLong(String key) {
        if(data == null)
            return null;
        Object obj = data.get(key);
        if(obj == null)
            return null;
        if(obj instanceof Long)
            return (Long)obj;
        String str = obj.toString();
        try {
            return Long.parseLong(str);
        }catch(Exception e) {
            return null;
        }
    }

    public Double getDouble(String key) {
        if(data == null)
            return null;
        Object obj = data.get(key);
        if(obj == null)
            return null;
        if(obj instanceof Double)
            return (Double)obj;
        String str = obj.toString();
        try {
            return Double.parseDouble(str);
        }catch(Exception e) {
            return null;
        }
    }

    public Date getDate(String key) {
        return null;
    }

    public Date getTime(String key) {
        return null;
    }

    /**
     * 对字符串中的 < 和 > 字符进行过滤
     */
    private String filterHtmlSpecialChars(String str) {
        return str
                .replaceAll("<", "&lt")
                .replaceAll(">", "&gt;")
                .replaceAll(";", " ")
                .replaceAll("'", " ")
                .replaceAll("'", " ")
                .replaceAll("\"", " ")
//                .replaceAll("\\\\", " ")
                .replaceAll("--", " ")
                .replaceAll("%", " ")
                .replaceAll("!", " ");
//                .replaceAll("/* */", " ");
    }
    //过滤 < 和 > 字符可以防止 XSS 攻击，因为这些字符是 HTML 中的标签符号，
    //攻击者可以通过在网站中注入恶意的脚本和标签来执行 XSS 攻击。
    //攻击者可以将恶意代码埋藏在表单、输入框、URL 参数等位置中，
    //并利用这些标记字符绕过前端或后端的校验，向服务器提交恶意请求，而这些请求会被执行，从而导致安全漏洞的产生。
    //单引号（'）：用于表示字符串，需用两个单引号（''）进行转义。 例如：
    //SELECT * FROM user WHERE name = 'O''Brien';
    //双引号（"）：用于表示列名、表名等标识符，可以使用方括号（[]）进行代替，也可以用两个双引号（""）进行转义。 例如：
    //SELECT "user_id", "user_name" FROM "User";
    //SELECT [user_id], [user_name] FROM [User];
    //SELECT "test""col" FROM "test";
    //百分号（%）：用于表示任意字符，可以使用反斜杠进行转义。 例如：
    //SELECT * FROM user WHERE name LIKE '%\%%';  -- 搜索包含百分号的用户名
    //下划线（_）：用于表示单个字符，可以使用反斜杠进行转义。 例如：
    //SELECT * FROM user WHERE name LIKE '_a%';  -- 搜索以a为第二个字符的用户名
    //斜杠（/）：表示除法运算符，无需转义。 例如：
    //SELECT 10 / 5;  -- 返回结果为2
    //感叹号（!）：表示逻辑非运算符，无需转义。 例如：
    //SELECT * FROM user WHERE name <> 'admin';  -- 搜索除了admin外的用户
    //问号（?）：用于表示占位符，常用于预处理语句中，无需转义。 例如：
    //SELECT * FROM user WHERE name = ?;
    //通过过滤掉这些特殊字符,可以防止sql注入,增加系统的安全性.
}