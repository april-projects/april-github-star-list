# Github Star 列表生成工具

<div align="center">
  <!-- 徽章代码 -->
  <img src="https://img.shields.io/github/stars/april-projects/april-github-star-list.svg" alt="stars"/>
  <img src="https://img.shields.io/github/forks/april-projects/april-github-star-list.svg" alt="forks"/>
  <img src="https://img.shields.io/github/last-commit/april-projects/april-github-star-list.svg" alt="最新提交"/>
</div>

这是一个 Java 程序，可以从 Github 获取指定用户的 Star 仓库列表，并将其输出为 Markdown 格式的文件，使用 GitHub Action 自动运行，作为备份。

## 使用方法

1. 克隆此仓库：

   ```bash
   $ git clone https://github.com/april-projects/april-github-star-list.git
   ```

2. 在 `ApplicationMain.java` 文件中修改 `username` 变量，将其设置为你要获取 Star 列表的 Github 用户名：

   ```java
   String username = "your-username";
   ```

3. 运行程序：

   ```bash
   $ mvn package && java -jar target/april-github-star-list.jar
   ```

   运行成功后，你将在项目根目录下看到一个名为 `Repo.md` 的 Markdown 文件，其中包含了你所选用户的所有 Star 仓库列表。

## 鸣谢

- [Github API](https://docs.github.com/en/rest)
- [Hutool](https://github.com/dromara/hutool) - 一个实用的 Java 工具包，使 Java 开发更加简单。