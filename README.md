# 简介
该项目是一个关于[美图录](https://www.meitulu.com)的图片下载器<br/>
通过命令行命令来下载图片<br/>
项目基于maven构建, java sdk版本11, 需要安装它们才能运行本项目<br/>
// TODO 后期会打包windows平台下exe文件以便于不需要安装基本环境下快速启动

# 功能
1. 下载套图, 例如:[套图](https://www.meitulu.com/item/19754.html)
2. 下载套图集合, 例如[套图集合](https://www.meitulu.com/t/1148/)
3. 若下载目录下若曾经下载过套图则下一次下载认为本地已经缓存过对应的套图将不会下载

# 快速启动
使用命令行进入项目目录 执行 `mvn install -DskipTests` <br/>
项目构建完成 在命令行执行 `mvn spring-boot:run`<br/>
成功运行项目后, 会出现一个shell命令行终端, 可以使用help来查看当前有哪些命令<br/>
使用tab可以提示补全命令, 如果terminal支持的话<br/>
```jshelllanguage
AVAILABLE COMMANDS

Built-In Commands
        clear: Clear the shell screen.
        exit, quit: Exit the shell.
        help: Display help about available commands.
        history: Display or save the history of previously run commands
        script: Read and execute commands from a file.
        stacktrace: Display the full stacktrace of the last error.

Dispatcher Shell
        download-combination: 下载套图集合
        download-journal: 下载套图
        set-home: 设置下载目录, 若下载目录不存在将创建
        show-home: 查看下载目录
        show-version: 查看下载器版本

```
执行下载命令下载想要下载的套图<br/>
套图下载完成后会在资源管理器中打开对应下载的套图文件夹

# 命令介绍
1. `download-combination 套图集合索引`<br/>
```jshelllanguage
// 示例: download-combination nvshen
// 说明: 下载一个套图集合, 下载套图集合后会自动在资源管理器中展示
```
2. `download-journal 套图索引`<br/>
```jshelllanguage
// 示例: download-journal 19721
// 说明: 下载一个套图, 下载完成后会在资源管理器中展示
```

3. `show-home`<br/>
查看下载目录<br/>

4. `set-home 下载的套图保存目录`<br/>
```jshelllanguage
// 示例: set-home /.meitulu-downloader/journals
// 说明: 将套图的保存目录设置为/.meitulu-downloader/journals
```

# FAQ
1. 如何找到套图集合的索引?<br/>
首先进入任意一个套图集合页面<br/>
以`https://www.meitulu.com/t/1148/`此套图集合页面为例, `1148`为其索引<br/>
套图集合可以是模特[套图集合](https://www.meitulu.com/t/1148/), 或者是[机构](https://www.meitulu.com/t/xiuren/), 又或者是[图集分类](https://www.meitulu.com/t/nvshen/)等等<br/>
只要页面url满足上述url格式都可以下载

2. 如何找到套图的索引?<br/>
以`https://www.meitulu.com/item/19754.html`为例, `19754`为其索引<br/>