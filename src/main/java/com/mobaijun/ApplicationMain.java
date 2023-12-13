package com.mobaijun;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.StrBuilder;
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
        String username = "mobaijun";
        StrBuilder sb = new StrBuilder();
        // 标题
        sb.append("# ").append(username).append(" Starred Repositories\n\n");
        List<Repository> repositoryList = Github.getGithubStarList(username);
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
     * 写入 html 文件
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
                "<meta content='width=device-width initial-scale=1' name='viewport'><title>Repo</title></head><body>" +
                "<h1 id='mobaijun-starred-repositories'>mobaijun Starred Repositories</h1>");

        groupedByLanguage.forEach((language, repositories) -> {
            htmlContent.append("<h2>").append(language).append("</h2><ul>");
            repositories.forEach(repository -> {
                htmlContent.append("<li>")
                        .append("<a href=\"").append(repository.getHtmlUrl()).append("\" target=\"_blank\">")
                        .append(repository.getName()).append("</a>")
                        .append(" [").append(repository.getDescription()).append("]")
                        .append("</li>");
            });
            htmlContent.append("</ul>");
        });

        htmlContent.append("</body></html>");

        // 写入 HTML 文件
        try {
            Files.writeString(Path.of("docs/index.html"), htmlContent);
            System.out.println("HTML file created successfully.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error writing HTML file", e);
        }
    }
}
