# 说明

本项目为字节-安卓开发课程，第一次作业内容，最终效果展示如下：

![homework1](https://typoraim.oss-cn-shanghai.aliyuncs.com/image/homework1.gif)

## 简介

1. 增加开始页面
   1. 更改背景，增加信息
   2. 使用了 LayoutConstraint 布局
2. **使用了Fragment 方式，两个Fragment页面，在一个activity下进行跳转，使用 navigate 页面进行跳转**
3. RecyclerView页面
   1. 添加了搜索功能
   2. 当点击 editView 框时，**会监听并修改 TextView的内容：从“搜索”改为“取消”**；会监听并修改 RecyclerView 的内容
   3. **RecyclerView 的功能在 Fragment 页面中进行实现**
   4. **点击空白区域会返回开始页面**
4. 搜索框布局
   1. 使用了 LinearLayout 嵌套的方式，通过颜色体现出了层次

# 作业笔记

## 环境配置问题

即使软件安装在C盘以外的位置，SDk和gradle的下载还是会在C盘（具体位置是C盘用户名下的local-> AppData中），从而导致C盘空间不足，会导致模拟器启动失败（具体表现就是会闪退）。

为了解决上述问题，首先可以将SDK迁移到其他位置（此时，需要修改软件中SDK的位置），这一步有可能会导致模拟器的文件丢失，但是不要紧，这里的关键是**修改了SDK的位置**。

以上步骤做完之后，多半还是会出错。

因此，接下来还是先卸载软件，然后删除全部和android、gradle相关的文件，SDK可以不删除，也可以删除。

再重新安装软件，这时之前的配置会留下来，所以直接打开之后，问题就是找不到SDK包（因为可能删除了），那么再下载到需要的位置就可以了（这里关键的一点是不会自动下载SDK包了）。

最后再尝试gradle本地化等等，就可以实现模拟器的调出了。

## 文件结构

首先需要把界面调整到 projects，而不是 android 页面

drawable - 存放图片文件

layout - 存放布局文件

- 也就是再这里创建不同页面的具体布局
- 布局页面主要使用xml进行编写
- 可以直接在这里将文字写上去，但是更好的方法应该是在java文件中进行添加

menu - 有物理菜单按钮的收集用的比较多，和菜单项相关的xml可以在这里进行编写

values - 存放各种资源的文件

- 比如 string.xml 存放的是字符串的资源，在layout中使用的字符串来自这里，可以使用 `android:text="@string/hello_world"` 进行调用，但是需要注意的是在string.xml中需要提前定义好名字为 hello_world 的字符串
- demens.xml 尺寸资源
- styles.xml 样式资源
- arrays.xml 数组资源
- attrs.xml 自定义空间属性

raw 文件，存放音频、视频等原生资源：可以通过`openRawResource(int id)` 来获得资源的二进制流

animator、anim 存放动画

> res目录下的所有资源文件都会在 R.java 文件下生成对应的资源id
>
> R文件可以理解为字典，res下的每个资源都会在这里生成一个唯一的id

这些资源可以在java代码中使用

也可以在xml代码中使用（也就是layout中使用）

## 三大文件

MainActivity.java 文件

main.xml 布局文件

AndroidManifest.xml 配置文件

## 从0起步

一个线性布局的诞生

![一个线性布局的诞生](https://www.runoob.com/wp-content/uploads/2015/06/22328602.jpg)

其中：

- xmlns：提示
- id：设置id，添加id

## Fragment 和 Activity的关系

Fragment 可以有很多个

它和Activity 是从属的关系，可以有好几个Fragment对应一个Activity

终于成功地显示出了一个新的页面！

<img src="https://typoraim.oss-cn-shanghai.aliyuncs.com/image/firstPage.gif" alt="firstPage" style="zoom:33%;" />

这样看 Fragment 是 android app的一个分离布局开发的一个界面，可以好好利用一下

具体使用方法：

首先在Activity中需要使用NavHost 来托管 Navigation

默认使用NavHostFragment 来进行托管，ta实现了NavHost的接口

> Fragment 有二分属性：
>
> app:navGraph   针对navigation 文件
>
> app:defaultNavHost  和返回键相关  

对于Fragment 通过 API进行控制

- `Navigation.findNavController(params).navigateUp()`
- `Navigation.findNavController(params).navigate(actionid)`

等方法可以获取到 `NavController` ，就可以使用 `navigate()` 方法发起页面跳转

首先需要在 Activity 的 layout 文件中提前添加 NavGraphFragment，用于导航并展示其他的 Fragment

## Fragment 中 实现 recyclerView 窗口

这一部分有一定的固有思路和步骤：

- 首先查询了 Fragment
- 然后查询了 Fragment 中使用 RecyclerView 的方法
- 在 RecyclerView 中还会涉及到调用 recyclerView 的窗口，设置 adapter，设置显示效果等问题
  - 可以参考：[Fragment中RecyclerView的使用解析，以及监听事件处理](https://blog.csdn.net/zhuchenglin830/article/details/82286109)
  - 其中关于 LinearLayoutManager() 部分，并没有简单地用 this 带过，而是对内容进行了读取，使用了`new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false)`，这样获取了内容
- 其中，设置显示效果这一项，使用的是 LinearLayoutManager() 函数
  - 可以参考：[RecyclerView详解（四）：LayoutManager布局管理器](https://www.jianshu.com/p/501e10bc31f1)

### Adapter 问题

在进行到 搜索框一部分的时候，遇到一个问题就是 SearchAdapter 的问题，后来发现比如一个通用的 `RecyclerView`教程中常用的一个 `fruit` 案例中，就是使用 Adapter 继承了一个 `fruitAdapter` 的类，用来定义 `ViewHolder` 等属性或者方法的

那么这个 `SearchAdapter` 应该就是为了这个搜索的功能而设计的一个 类 了

### Edit 监听 和 修改 recyclerview 内容的问题

要实现的效果就是 修改 edit 中的内容

监听其中的内容，然后对recyclerview中的内容进行修改

其实主要还是使用了老师给的demo中的代码，关于 searchLayout 的部分，源代码中应该是对 单独的 layout 进行了修改

但是我使用了 Fragment，所以就直接在 Fragment 中进行监听，并修改 recyclerview 的值

> 参考：[Fragment的一个简单实现+EditText的监听事件](https://blog.csdn.net/kazu_rika/article/details/78141626)

如何在 fragment 中修改 textview

查看了这个文章：[android – 在Fragment中更改TextView](http://www.voidcn.com/article/p-tthequud-bth.html)

但是能给出的建议相对比较复杂

## 字符串

主要的一句代码是

`String.valueof(i)`

这应该是 java 中的类型转换，将基本数据类型转换为 string

那么如果我这样写呢：

“敌军还有xx秒到达战场”

也就是是用规范式

> 这里使用到了 Java 的格式化输出方法
>
> 可以参考[Java的格式化输出 ](https://www.cnblogs.com/kunlbc/p/4518977.html)，里面使用了 `String.format("%d", 10)` 这样的形式将数值添加到字符串当中
>
> 在[JAVA-String类-菜鸟教程](https://www.runoob.com/java/java-string.html)中，也讲了，可以通过 String 相加的方式实现效果

## 主题

`android:theme="@style/AppTheme.PopupOverlay"`

`android:theme="@style/AppTheme.AppBarOverlay"`

可以通过修改 theme 文件，color文件等对主题进行简单的修改

如果想要使用圆角

那么首先需要在 drawable 文件夹中新建圆角图形

然后在 xml 的 background 选项中选择使用圆角图形作为背景即可

## Layout Constraint 布局

> 参考：[约束布局ConstraintLayout看这一篇就够了](https://www.jianshu.com/p/17ec9bd6ca8a)

为了解决布局嵌套过多的问题【确实，目前我的布局页面就嵌套了很多的 LinearLayout】

ConstraintLayout 比 RelativeLayout 更加灵活，可以按照比例约束控件位置和尺寸，还可以适配屏幕大小不同的机型

首先需要相对定位

`app:layout_constraintLeft_toRightOf="@+id/TextView1"` 类似这样的写法

> 下面来看看相对定位的常用属性：
>  layout_constraintLeft_toLeftOf
>  layout_constraintLeft_toRightOf
>  layout_constraintRight_toLeftOf
>  layout_constraintRight_toRightOf
>  layout_constraintTop_toTopOf
>  layout_constraintTop_toBottomOf
>  layout_constraintBottom_toTopOf
>  layout_constraintBottom_toBottomOf
>  layout_constraintBaseline_toBaselineOf：可以在两个文本高度不一致的情况下，将两个文本对齐
>  layout_constraintStart_toEndOf
>  layout_constraintStart_toStartOf
>  layout_constraintEnd_toStartOf
>  layout_constraintEnd_toEndOf

缺点可能是各个控件之间是强关联的，不改则已，一改爆炸

# 网页资料

主要教程

- 菜鸟教程：[1.0 Android基础入门教程 | 菜鸟教程 (runoob.com)](https://www.runoob.com/w3cnote/android-tutorial-intro.html)
- android 官方文档：[开发者指南  | Android 开发者  | Android Developers](https://developer.android.com/guide?hl=zh-cn)

辅助资料：

- [键盘快捷键  | Android 开发者  | Android Developers](https://developer.android.com/studio/intro/keyboard-shortcuts?hl=zh-cn)
- Navigation架构：[Android架构组件-Navigation的使用(一) - 简书 (jianshu.com)](https://www.jianshu.com/p/729375b932fe)
- [Android官方架构组件Navigation：大巧不工的Fragment管理框架 - 简书 (jianshu.com)](https://www.jianshu.com/p/ad040aab0e66)
- 圆角设计：[ Android 布局圆角方案总结_进击的小怪兽-CSDN博客_android 圆角](https://blog.csdn.net/ldld1717/article/details/106652831)
- Fragment:https://www.jianshu.com/p/a4c51309bc19

参考作业：

- [使用SearchView+RecyclerView做搜索框_技术无他唯手熟尔-CSDN博客](https://blog.csdn.net/xx244488877/article/details/69486330)