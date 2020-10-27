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

- Yang GC & Old GC vs Minor GC & Major GC
  - Serial GC -Minor GC: Eden区和S0区的数据回收或转移至S1或Old区
  - Serial GC -Full GC: Old区回收，Eden区和存活区不管
  - Parallel GC -Yang GC: Eden区和S0区的数据回收或转移至S1或Old区
  - Parallel GC -Full GC：Eden区和S0区清除，Old区回收

## JVM 线程模型


## 一个Java对象占用多少内存
G1GC 吞吐量和暂停时间平衡 优先选择 会根据统计数据自动调整启动时间（启发式GC）
并行GC 暂停时间较长，但总体消耗最少
CMSGC 暂停时间较短，但效率较低，暂停时间不可控

## 不同GC比较

- 串行GC 暂停时间最长，而且暂停时间随堆内存增大而增大，因为当内存可用空间较大时，GC触发的频率会降低，但每次需要回收的对象会增大
- 并行GC 默认GC线程数为系统核心数，暂停时间较串行GC短，暂停时间也会随堆内存增大而增大
- CMSGC 只有初始标记阶段和最终标记阶段会暂停，其余阶段与应用线程并行执行，因此STW暂停时间较并行GC短，然而实际运行时间并行GC长，因此消耗的资源也比并行GC多，暂停时间也会随堆内存增大而增大
- G1GC 暂停时间最短，由[GC pause (G1 Evacuation Pause)]触发Yang GC，由[GC pause (G1 Humongous Allocation)]触发mix GC，由[[Full GC (Allocation Failure)]触发Full GC，Full GC时退化为串行GC，暂停时间较长