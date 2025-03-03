package com.mobaijun;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.json.JSONUtil;
import com.mobaijun.github.Github;
import com.mobaijun.github.Repository;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * 主程序
 * <a href="https://www.mobaijun.com">框架师</a>
 *
 * @author mobai
 */
public class ApplicationMain {

    /**
     * 获取 Logger 实例
     */
    private static final Logger logger = Logger.getLogger(ApplicationMain.class.getName());

    /**
     * 文件地址
     */
    private static final Path PATH;

    static {
        try {
            // 初始化文件
            PATH = Paths.get("Repo.md");
            if (!Files.exists(PATH)) {
                Files.createFile(PATH);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String username = "asayake777";
        StrBuilder sb = new StrBuilder();
        // 标题
        sb.append("# ").append(username).append(" Starred Repositories\n\n");
        List<Repository> repositoryList = Github.getGithubStarList(username);

        // 写入 json api
        FileUtil.writeString(JSONUtil.toJsonStr(repositoryList), FileUtil.newFile("api.json"), StandardCharsets.UTF_8);

        // 写入 html
        writeRepositoriesToHtml(repositoryList);
        // 拼接列表
        repositoryList.forEach(item -> {
            String name = item.getName();
            String htmlUrl = item.getHtmlUrl();
            String description = item.getDescription();
            // 拼接描述信息
            sb.append("- [").append(name).append("](").append(htmlUrl).append(")\t[");
            sb.append(description != null ? description : "No description available.");
            sb.append("]\n");
        });
        // 打印
        FileUtil.writeString(sb.toString(), PATH.toAbsolutePath().toString(), StandardCharsets.UTF_8);
    }

    /**
     * 写入 HTML 文件
     *
     * @param repositoryList 仓库列表
     */
    public static void writeRepositoriesToHtml(List<Repository> repositoryList) {
        // 使用 Stream API 对 repositoryList 进行分组，将语言为空的仓库单独放在一个列表中
        Map<String, List<Repository>> groupedByLanguage = repositoryList.stream()
                .collect(Collectors.groupingBy(repository -> repository.getLanguage() != null ? repository.getLanguage() : "Unknown"));

        // 构建 HTML 字符串
        StringBuilder htmlContent = new StringBuilder("<!doctype html><html lang=\"en\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">" +
                "<title>Starred Repositories</title>" +
                "<style>" +
                "    body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f8f9fa; margin: 0; padding: 0; }" +
                "    h1, h2 { color: #333; text-align: center; font-weight: 600; }" +
                "    h1 { font-size: 2.5rem; margin-top: 50px; color: #495057; }" +
                "    h2 { font-size: 1.75rem; color: #007bff; margin-bottom: 20px; }" +
                "    .container { width: 85%; max-width: 1200px; margin: 0 auto; padding: 40px 20px; background-color: white; border-radius: 12px; box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); }" +
                "    .language-group { margin-bottom: 40px; }" +
                "    .repo-list { list-style: none; padding: 0; margin: 0; }" +
                "    .repo-list li { margin: 20px 0; padding: 10px; border-radius: 8px; background-color: #f1f1f1; transition: transform 0.3s ease, box-shadow 0.3s ease; }" +
                "    .repo-list li:hover { transform: translateY(-5px); box-shadow: 0 6px 18px rgba(0, 0, 0, 0.1); }" +
                "    .repo-list li a { text-decoration: none; color: #007bff; font-size: 1.2rem; font-weight: 500; transition: color 0.3s ease; }" +
                "    .repo-list li a:hover { color: #0056b3; }" +
                "    .repo-description { display: block; font-size: 1rem; color: #6c757d; margin-top: 8px; font-style: italic; }" +
                "    .repo-description::before { content: '\"'; font-size: 1.5rem; }" +
                "    .repo-description::after { content: '\"'; font-size: 1.5rem; }" +
                "    .search-bar { margin: 20px auto; text-align: center; }" +
                "    .search-bar input { width: 100%; max-width: 400px; padding: 10px; font-size: 1rem; border-radius: 5px; border: 1px solid #ccc; }" +
                "    .footer { text-align: center; font-size: 1rem; color: #6c757d; margin-top: 40px; padding: 20px 0; }" +
                "    .footer a { color: #007bff; text-decoration: none; font-weight: 500; }" +
                "    .footer a:hover { color: #0056b3; }" +
                "    @media (max-width: 768px) { .container { width: 90%; padding: 30px 15px; } }" +
                "</style>" +
                "</head>" +
                "<body>");


        htmlContent.append("<div class=\"container\">" +
                "<h1>mobaijun Starred Repositories</h1>");

        // 搜索框
        htmlContent.append("<div class=\"search-bar\">" +
                "<input type=\"text\" id=\"searchInput\" placeholder=\"Search repositories...\" onkeyup=\"filterRepositories()\">" +
                "</div>");

        // 显示所有仓库，按语言分组
        groupedByLanguage.forEach((language, repositories) -> {
            htmlContent.append("<div class=\"language-group\" id=\"group-").append(language).append("\">")
                    .append("<h2>").append(language).append("</h2>")
                    .append("<ul class=\"repo-list\">");

            repositories.forEach(repository -> {
                htmlContent.append("<li>")
                        .append("<a href=\"").append(repository.getHtmlUrl()).append("\" target=\"_blank\">")
                        .append(repository.getName()).append("</a>")
                        .append("<span class=\"repo-description\">").append(repository.getDescription()).append("</span>")
                        .append("</li>");
            });

            htmlContent.append("</ul></div>");
        });

        // 页脚
        htmlContent.append("<div class=\"footer\">" +
                "<p>Created with <span style=\"color: #e25555;\">♥</span> by <a href=\"https://github.com/mobaijun\" target=\"_blank\">mobaijun</a></p>" +
                "</div>");

        htmlContent.append("</div>");

        // 添加搜索功能的 JavaScript
        htmlContent.append("<script>" +
                "    function filterRepositories() {" +
                "        const input = document.getElementById('searchInput').value.toLowerCase();" +
                "        const allRepos = document.querySelectorAll('.repo-list li');" +
                "        const allLanguageGroups = document.querySelectorAll('.language-group');" +
                "        allLanguageGroups.forEach(group => {" +
                "            const repos = group.querySelectorAll('.repo-list li');" +
                "            let hasVisibleRepos = false;" +
                "            repos.forEach(repo => {" +
                "                const name = repo.querySelector('a').textContent.toLowerCase();" +
                "                const description = repo.querySelector('.repo-description').textContent.toLowerCase();" +
                "                if (name.includes(input) || description.includes(input)) {" +
                "                    repo.style.display = 'block';" +
                "                    hasVisibleRepos = true;" +
                "                } else {" +
                "                    repo.style.display = 'none';" +
                "                }" +
                "            });" +
                "            if (hasVisibleRepos) {" +
                "                group.style.display = 'block';" +
                "            } else {" +
                "                group.style.display = 'none';" +
                "            }" +
                "        });" +
                "    }" +
                "</script>");

        // 写入 HTML 文件
        try {
            Files.writeString(Path.of("docs/index.html"), htmlContent);
            System.out.println("HTML file created successfully.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error writing HTML file", e);
        }
    }
}
