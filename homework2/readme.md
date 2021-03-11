# 课堂记录

## Fragment - 碎片

Acitvity - 页面

页面中的碎片布局

### 静态添加

- 定义布局文件

- 定义类
  - 需要继承 Fragment 基类
  - 然后重写 onCreateView 的函数

- 在 activity 中嵌入 fragment

静态添加，写死

### 动态添加/删除

布局文件和类是一样的

但是不一样的是需要在 acitvity中写一个 fragment_layout 的布局文件

## Animation - 动画

# Homework

## 1 - lottie 尝试

<img src="https://typoraim.oss-cn-shanghai.aliyuncs.com/image/lottie.gif" alt="lottie" style="zoom:50%;" />

首先修改 lottie 的属性

![image-20210307145730102](https://typoraim.oss-cn-shanghai.aliyuncs.com/image/image-20210307145730102.png)

然后向java文件中添加修改部分

关键是涉及seekBar的监听部分

其实不需要再写 Listener 监听器，针对SeekBar已经有了它改变时的监听器，所以里面直接进行 lottie 动画的进度修改就可以了，也就是`setProgress`

![image-20210307155543337](https://typoraim.oss-cn-shanghai.aliyuncs.com/image/image-20210307155543337.png)

但是修改为多少是个问题：

- seekBar 的范围是 0-100，官网：https://developer.android.google.cn/reference/android/widget/SeekBar.OnSeekBarChangeListener.html#onProgressChanged

  ![image-20210307155925377](https://typoraim.oss-cn-shanghai.aliyuncs.com/image/image-20210307155925377.png)

- lottieAnimatorView 的范围是 0f-1f，浮点数，官网：https://airbnb.io/lottie/#/android?id=sample-app

  ![image-20210307155910825](https://typoraim.oss-cn-shanghai.aliyuncs.com/image/image-20210307155910825.png)

所以在赋值的过程中需要一个修改：

（float）progress/100，首先要把原值改成 float，然后转换成 0到1之间的数值

## 2 - 属性动画尝试

<img src="https://typoraim.oss-cn-shanghai.aliyuncs.com/image/animator.gif" alt="animator" style="zoom:50%;" />

比较套路

```java
ObjectAnimator scaleX_animator = ObjectAnimator.ofFloat(target, "scaleX", 1, 2);
scaleX_animator.setRepeatCount(ValueAnimator.INFINITE);
scaleX_animator.setRepeatMode(ValueAnimator.REVERSE);       scaleX_animator.setDuration(Integer.parseInt((durationSelector.getText().toString())));
```

基于类似的语言就可以实现在 java脚本中添加动画的操作

最后需要对多个动画进行合并：

```java
animatorSet = new AnimatorSet();
animatorSet.playTogether(animator1, scaleX_animator, scaleY_animator, alpha_animator);
animatorSet.start();
```

`setDuration()` 可以设置动画的时长

`setVisible()` 可以设置控件可见或者不可见

## 3. ViewPager + Fragment

<img src="https://typoraim.oss-cn-shanghai.aliyuncs.com/image/viewpager.gif" alt="viewpager" style="zoom:50%;" />

首先是设置 viewpager 页面，并不难，可以直接进行设置，包括设置tabbar，也可以按照教程来进行设置。

但是接下来出现两个问题：

1. tabbar怎么样出现点击的效果，并且跳转到相应的界面？
2. 接下来需要在 每个界面上加载一个 lottie 的动画，然后过 5s 之后消失，再出现一个表单

在 placeholder.xml 文件中添加 lottie 动画，其实可以设置自动循环和播放

再添加一个 属性动画，就可以实现动画出现或者消失的操作

### 如何让两个控件重叠

**framelayout 本身就是重叠的**

如果是多个framelayout 嵌套，怎么样调整布局？

- 在 RelativeLayout 中可以调整两个块之间的布局
- 然后设置 FrameLayout 的位置：`layout_below="@id/tab_layout"` 就可以调整好布局了

或者使用 relativeLayout，可以调整布局

> 参考：[如何让安卓的控件重叠在一起](https://zhidao.baidu.com/question/688331054973098564.html)

### 添加列表页面

添加一个recyclerView的页面

按照recyclerView 的设置方法，依次编辑：

- fragment 中添加 recyclerView 的布局
- item 布局，写清每一行的格式
- ViewHolder  找到 item 布局
- Adapter 调用 item 完成布局，并且调用布局数量，进行更改等等

### 过渡动画

lottie 动画直接设置了自动循环

再给 lottie 动画控件添加了属性动画，0.5s淡出

给 recyclerView 的控件添加了属性动画，0.5s 淡入

同时给 recyclerView 的控件添加了关于 visible 的设置，使得刚开始不可见，随后可见

> 加入recyclerView 之后，tabbar 就可以进行点击了，奇怪
>
> 仍然存在的一个问题是：
>
> 点击第一个页面和第三个页面，会重新刷新 lottie 动画，但是 第二个页面始终不会刷新该动画

# log

[Android笔记 | Android Studio日志工具Log](https://www.jianshu.com/p/b48f3967f66a)