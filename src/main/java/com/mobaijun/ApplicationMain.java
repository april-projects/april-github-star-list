package com.mobaijun;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.StrBuilder;
import com.mobaijun.github.Github;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 主程序
 *
 * @author mobai
 */
public class ApplicationMain {

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
        Github.getGithubStarList(username)
                .forEach(item -> {
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
}
