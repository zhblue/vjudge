# vjudge
Just a preservation snap of the latest opensource version of virtual-judge

基础代码来自
https://github.com/hnshhslsh/virtual-judge

做了一些小的调整和改动，让部署更容易。

基本操作：
	× 安装 jdk7+
	* 安装 mysql5+
	* 安装启动tomcat7+
	* 将下载到的vjudge.war文件放入webapps目录
	* 等待tomcat自动解压缩得到vjudge目录
	× 启动mysql,建库，用vjudge/WEB-INF/classes/vhoj_20141109.sql 建库。
	* 编辑vjudge/WEB-INF/classes/config.properties设置数据库账号
	* 编辑vjudge/WEB-INF/classes/remote_accounts.json设置宿主OJ账号
	* 编辑vjudge/WEB-INF/classes/http_client.json设置代理服务器（可选）
	* 重启tomcat
	* 浏览器访问http://服务器地址:8080/vjudge/
	* 如需去掉端口、子目录，可用nginx做反向代理。
