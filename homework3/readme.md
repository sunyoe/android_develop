# HomeWork 4

## 说明

本周作业内容为实现一个表盘的设计和运行，主要有三个作业考察点：

1. 绘制分针；
2. 绘制表盘上的文字；
3. 更新表盘显示的时间。

其中尤其以第三个问题为关键：

- 不希望更新的过程中产生过多的垃圾，长时间运行过程中可能会导致系统不稳定

## 展示

<img src="https://typoraim.oss-cn-shanghai.aliyuncs.com/image/homework3.gif" alt="homework3" style="zoom:50%;" />

## 绘制

绘制分针使用了 `drawPointer()` 的方法，封装了从起始点指向终点的线段绘制的方法

绘制文字的过程主要考虑以下问题：

- 文字位置，根据中心点坐标进行计算，主要问题是 其使用的角度单位是 弧度，需要进行转换
- 文字对齐方法，设置为中心对齐
- 文字颜色和大小，如果文字太小，很可能看不见（也有可能是文字颜色不对），但是文字颜色的设置似乎没有独立的API

## 更新

表盘的一次绘制在 `OnDraw()` 函数的调用过程中完成

如果需要更新表盘，那就是要销毁当前的表盘 `invalidate()`，然后==会自动调用OnDraw()== 重建页面，所以就完成了刷新

方法有多种：

【第一种】

直接使用 `PostInvalidateDelayed()` 函数直接延迟加循环进行反复调用，实现更新

【第二种】

在 `OnDraw()` 中使用 Handler，不停销毁页面，实现定时更新

【第三种】

在 `OnDraw()` 外部设置 Handler，进行消息传递，并在 `OnDraw` 外部修改页面情况

如何在 本页面之外修改呢？

- 必须要获取页面内容，也就是 view 的信息

即使是手绘内容，在 android 页面也是 view 信息

可以在 activity 中搜索 view 信息，并修改 canvas（直接销毁），即可实现

> 比较简单的方式的方式还是在本页面内进行 handler 的传递

在外部重写 `mHandler` 类，实现更新操作，但是需要进行消息的接收

然后在 `OnDraw` 内部新建 `mHandler`，进行消息的发送

最后操作执行结束之后，在`mHandler`中将消息销毁，完成全部操作

## 参考

1. [Android 异步通信：手把手教你使用Handler消息传递机制（含实例Demo）](https://blog.csdn.net/carson_ho/article/details/80305411)
2. [Android消息传递之Handler消息机制](https://www.cnblogs.com/whoislcj/p/5590615.html)
3. [关于Handler.removemessages方法](https://blog.csdn.net/feelinghappy/article/details/85273451)