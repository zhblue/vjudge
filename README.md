#Virtual-Judge#

  a fork of the hust-virtual-judge system early released source code



基础代码来自
https://github.com/hnshhslsh/virtual-judge

做了一些小的调整和改动，让部署更容易

整理了所有的jar依赖，修订了路径和配置，增加了与最新版hustoj配合的本地判题功能。
工程整理为eclipse工程，不需要破解myeclipse

Ubuntu 14/16 LTS install script 执行完访问http://服务器IP:8080/vjudge/ 即可使用vjudge
```
wget https://github.com/zhblue/vjudge/raw/master/install.sh
sudo bash install.sh
```


基本操作：
* 安装 jdk7+
* 安装 mysql5+
* 安装启动tomcat7+
* 将下载到的vjudge.war文件放入webapps目录
* 等待tomcat自动解压缩得到vjudge目录
* 启动mysql,建库，用vjudge/WEB-INF/classes/vhoj_20141109.sql 建表。
* 编辑vjudge/WEB-INF/classes/config.properties设置数据库账号
* 编辑vjudge/WEB-INF/classes/remote_accounts.json设置宿主OJ账号
* 编辑vjudge/WEB-INF/classes/http_client.json设置代理服务器（可选）
* 重启tomcat
* 浏览器访问http://服务器地址:8080/vjudge/
* 如需去掉端口、子目录，可用nginx做反向代理。

只需少量调整就可以跟最新版hustoj配合，自己的vjudge抓自己oj的题。

  相关类judge.remote.provider.local.*
  配置域名和路径
  https://github.com/zhblue/vjudge/blob/master/vjudge2016/src/config.properties#L34

