# spring-boot-starter-fmsql
## 在xml里面用freemarker里面写sql

1.下载
2.mvn install
3.引用
pom.xml
```
<dependency>
    <groupId>top.threadlocal</groupId>
    <artifactId>spring-boot-starter-fmsql</artifactId>
    <version>0.0.1</version>
</dependency>
```
4.引用的fmsql的主类上面加上
```
@ComponentScan({"你自己的包","top.threadlocal.fmsql"})
```
