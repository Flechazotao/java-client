package com.teach.javafx.request;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.gson.Gson;
import com.teach.javafx.AppStore;
import com.teach.javafx.models.DO.User;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.useless.teach.util.CommonMethod;
import com.teach.javafx.utils.JsonUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.UUID;

/**
 * HttpRequestUtil 后台请求实例程序，主要实践向后台发送请求的方法
 *  static boolean isLocal 业务处理程序实现方式 false java-server实现 前端程序通过下面的方法把数据发送后台程序，后台返回前端需要的数据，true 本地方式 业务处理 在SQLiteJDBC 实现
 *  String serverUrl = "http://localhost:9090" 后台服务的机器地址和端口号
 */
public class HttpRequestUtil {
    public static boolean isLocal = false;
    private static Gson gson = new Gson();
    private static HttpClient client = HttpClient.newHttpClient();
    public static String serverUrl = "http://localhost:9090";
//    public static String serverUrl = "http://202.194.14.120:9090";

    /**
     *  应用关闭是需要做关闭处理
     */
    public static void close(){
    }

    /**
     * String login(LoginRequest request)  用户登录请求实现
     * @param  username 登录账号
     * @param  password 登录密码
     * @return  返回null 登录成功 AppStore注册登录账号信息 非空，登录错误信息
     */

    public static String login(String username,String password,String url){
        DataRequest request = new DataRequest();
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        request.add("user",user);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + url))
                .POST(HttpRequest.BodyPublishers.ofString(JSON.toJSONString(request)))
                .headers("Content-Type", "application/json")
                .build();//
        try {
            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            DataResponse dataResponse = JSONObject.parseObject(response.body(), DataResponse.class);
            System.out.println("url=" + url +"    response.statusCode="+response.statusCode());
            if (dataResponse.getCode() == 200) {
                JwtResponse jwt = gson.fromJson(response.body(), JwtResponse.class);
                AppStore.setJwt(jwt);
                return null;
            } else if (dataResponse.getCode() == 404) {
                return "用户名或密码不存在！";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "登录失败";
    }

    /**
     * DataResponse request(String url,DataRequest request) 一般数据请求业务的实现
     * @param url  Web请求的Url 对用后的 RequestMapping
     * @param request 请求参数对象
     * @return DataResponse 返回后台返回数据
     */
    public static DataResponse request(String url, DataRequest request){
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + url))
                .POST(HttpRequest.BodyPublishers.ofString(JSON.toJSONString(request)))
                .headers("Content-Type", "application/json")
                .headers("Authorization", "Bearer " + AppStore.getJwt().getAccessToken())
                .build();
        request.add("username",AppStore.getJwt().getUsername());
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("url=" + url +"    response.statusCode="+response.statusCode());
            if (response.statusCode() == 200) {
                DataResponse dataResponse = JsonUtil.parse(JSON.parse(response.body()), DataResponse.class);
                return dataResponse;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  byte[] requestByteData(String url, DataRequest request) 获取byte[] 对象 下载数据文件等
     * @param url  Web请求的Url 对用后的 RequestMapping
     * @param request 请求参数对象
     * @return List<OptionItem> 返回后台返回数据
     */
    public static byte[] requestByteData(String url, DataRequest request){
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + url))
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(request)))
                .headers("Content-Type", "application/json")
                .headers("Authorization", "Bearer "+AppStore.getJwt().getAccessToken())
                .build();
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<byte[]>  response = client.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());
            if(response.statusCode() == 200) {
                return response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return 上传操作信息
     */
    public static DataResponse uploadFile(String uri,Path file,String source)  {
        try {
//            Path file = Path.of(filePath);
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(serverUrl+uri+"?"+"fileName="+URLEncoder.encode(String.valueOf(source+file.getFileName()),StandardCharsets.UTF_8)))
                    .headers("Content-Type", "application/octet-stream")
                    .POST(HttpRequest.BodyPublishers.ofByteArray(Files.readAllBytes(file)))
                    .build();
            HttpResponse<String>  response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 200) {
                DataResponse dataResponse = gson.fromJson(response.body(), DataResponse.class);
                return dataResponse;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * DataResponse importData(String url, String fileName, String paras) 导入数据文件
     * @param url  Web请求的Url 对用后的 RequestMapping
     * @param fileName 本地文件名
     * @param paras  上传参数
     * @return 导入结果信息
     */
    public static DataResponse importData(String url, String fileName, String paras)  {
        try {
            Path file = Path.of(fileName);
            String urlStr = serverUrl+url+"?uploader=HttpTestApp&fileName=" + file.getFileName() ;
            if(paras != null && paras.length() > 0)
                urlStr += "&"+paras;
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlStr))
                    .POST(HttpRequest.BodyPublishers.ofFile(file))
                    .build();
            HttpResponse<String>  response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 200) {
                DataResponse dataResponse = gson.fromJson(response.body(), DataResponse.class);
                return dataResponse;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * DataResponse int uploadHtmlString(String html) 加密上传html模板字符串，用于生成htmp网页和PDF文件
     * @param html 上传的HTML字符串
     * @return html 序列号
     */

    public static int uploadHtmlString(String html)  {
            DataRequest req = new DataRequest();
            String str = new String(Base64.getEncoder().encode(html.getBytes(StandardCharsets.UTF_8)));
            req.add("html", str);
            DataResponse res =request("/api/base/uploadHtmlString",req);
            return CommonMethod.getIntegerFromObject(res.getData());
    }

}
