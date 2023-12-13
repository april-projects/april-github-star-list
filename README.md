# Github Star 列表生成工具

<div align="center">
  <!-- 徽章代码 -->
  <img src="https://img.shields.io/github/stars/mobaijun/github-star-list.svg" alt="stars"/>
  <img src="https://img.shields.io/github/forks/mobaijun/github-star-list.svg" alt="forks"/>
  <img src="https://img.shields.io/github/last-commit/mobaijun/github-star-list.svg" alt="最新提交"/>
</div>

这是一个 Java 程序，可以从 Github 获取指定用户的 Star 仓库列表，并将其输出为 Markdown 格式的文件，使用 GitHub Action 自动运行，作为备份。

## 使用 Vercel 部署

## 开始使用

1. 点击右侧按钮开始部署： [![Deploy with Vercel](https://camo.githubusercontent.com/5e471e99e8e022cf454693e38ec843036ec6301e27ee1e1fa10325b1cb720584/68747470733a2f2f76657263656c2e636f6d2f627574746f6e)](https://vercel.com/new/clone?repository-url=https://github.com/mobaijun/github-star-list&project-name=github-star-list&repository-name=github-star-list) ，直接使用 Github 账号登录即可；
2. 部署完毕后，即可开始使用；
3. （可选）[绑定自定义域名](https://vercel.com/docs/concepts/projects/domains/add-a-domain)：Vercel 分配的域名 DNS 在某些区域被污染了，绑定自定义域名即可直连。

## 普通使用方法

1. 克隆此仓库：

   ```bash
   $ git clone https://github.com/mobaijun/github-star-list.git
   ```

2. 在 `ApplicationMain.java` 文件中修改 `username` 变量，将其设置为你要获取 Star 列表的 Github 用户名：

   ```java
   String username = "your-username";
   ```

3. 运行程序：

   ```bash
   $ mvn package && java -jar target/github-star-list.jar
   ```

   运行成功后，你将在项目根目录下看到一个名为 `Repo.md` 的 Markdown 文件，其中包含了你所选用户的所有 Star 仓库列表。

## 鸣谢

- [Github API](https://docs.github.com/en/rest)
- [Hutool](https://github.com/dromara/hutool) - 一个实用的 Java 工具包，使 Java 开发更加简单。