package com.mobaijun.github;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.Method;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;

import java.util.List;

/**
 * software：IntelliJ IDEA 2022.2.3<br>
 * class name: Github<br>
 * class description: GitHub操作工具类
 *
 * @author MoBaiJun 2023/3/4 7:28
 */
public class Github {

    /**
     * 获取指定用户所有仓库详情
     *
     * @param username 用户名
     * @return 仓库列表
     */
    public static List<Repository> gitHubRepositoryDetailsList(String username) {
        String apiUrl = "https://api.github.com/users/" + username + "/repos";
        // Parse JSON response
        return JSONUtil.toList(JSONUtil.parseArray(getHttpResponse(apiUrl).body()), Repository.class);
    }

    /**
     * 获取单个仓库详情
     *
     * @param username 用户名
     * @param repo     仓库名称
     * @return 仓库详情
     */
    public static Repository gitHubRepositoryDetails(String username, String repo) {
        String apiUrl = "https://api.github.com/repos/" + username + "/" + repo;
        // Parse JSON response
        return JSONUtil.toBean(JSONUtil.parseObj(getHttpResponse(apiUrl).body()), Repository.class);
    }

    public static List<Repository> getGithubStarList(String username) {
        String apiUrl = "https://api.github.com/users/" + username + "/starred?per_page=1000";
        JSONArray objects = JSONUtil.parseArray(getHttpResponse(apiUrl).body());
        // Parse JSON response
        return JSONUtil.toList(objects, Repository.class);
    }

    /**
     * 通用请求方法
     *
     * @param apiUrl 请求地址
     * @return 返回body
     */
    private static HttpResponse getHttpResponse(String apiUrl) {
        // Send HTTP GET request and get response
        return HttpRequest.get(apiUrl)
                .setMethod(Method.GET)
                .header("User-Agent", "Java client")
                .header("Accept", "application/vnd.github.v3+json")
                .execute();
    }
}
