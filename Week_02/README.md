# 学习笔记

## 常用GC命令

``` bash
# 使用并行GC并打印GC详情
java -XX:+PrintGCDetails -XX:+UseParallelGC GCLogAnalysis

# 打印GC详情至日志文件
java -Xloggc:gc.demo.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis

# 设置小堆内存模拟oom
java -XX:+PrintGCDetails -XX:+UseParallelGC -Xms128m -Xmx128m GCLogAnalysis

# CMS GC
java -XX:+UseConcMarkSweepGC -Xms512m -Xmx512m -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis

# G1 GC
java -XX:+UseG1GC -Xms512m -Xmx512m -XX:+PrintGC -XX:+PrintGCDateStamps GCLogAnalysis
```


