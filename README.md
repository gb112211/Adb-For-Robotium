# adb-For-Robotium
修改 Adb-For-Test 项目，在 Robotium 中实现跨进程操作

模拟器上无效果，需要真机并且 root。因为使用 uiautomaor 命令获取 uidump.xml 时需要 root 权限，chomd 时也需要 root 权限。
例子可参考 CrossProcessDemo_Robotium.zip 中的例子<br>
demo 提供者 [http://my.csdn.net/qingchunjun](http://my.csdn.net/qingchunjun)

原理、使用方法及常见问题见后文。

## 2015.12.13
*	修复一些已发现的问题
*	增减部分方法，增加长按、缩放、截屏等方法
*	修改某些方法的实现方式，具体请阅读源码
*	增加 readme 内容

## 2015.06.08
*	修改 `ByText` 方法定位元素，修改后支持中文。jar包 自行用 jdk1.7 编译，如果出现 jdk 版本兼容问题，麻烦请自行重新编译下！！
*	有部分方法可能无法使用，后续会抽时间完善，也可自行修改源码，该项目主要传达的是一个跨进程的方法！欢迎交流

## 原理
简单的说，就是让被测应用执行 `su` 命令进行提权，被测应用在拥有 root 权限后就能执行 Android 系统中的 adb shell 命令对设备进行操作，如 input、uiautomator 等命令，因此就可在跨进程之后，调用 adb shell 命令完成类似点击、长按、缩放、截屏、元素定位等操作，最终实现跨进程操作。

## 使用方法
*	克隆该项目至本地，或者 DownloadZip
```
git clone https://github.com/gb112211/Adb-For-Robotium.git
```

*	在 Eclipse 中导入该项目，或者在测试项目中引入 jar 目录中的 jar 包
*	调用方法及部分方法使用示例,以下图手机桌面为例，场景为在被测应用界面按下Home键返回到桌面，此时已不能通过 Solo 去控制设备

	![launcher image](/image/launcher.png)
初始化：
```
AdbDevice adb = new AdbDevice();
```
需要依靠元素定位时：
```
Position position = new Position();
```
按下物理按键
```
//按下Home键
adb.sendKeyEvent(AndroidKeyCode.HOME);
//按下菜单键
adb.sendKeyEvent(AndroidKeyCode.MENU);
//按下返回键
adb.sendKeyEvent(AndroidKeyCode.BACK);
//按下电源键
adb.sendKeyEvent(AndroidKeyCode.POWER);
//长按Home键盘
adb.longPressKey(AndroidKeyCode.HOME);
//长按电源键
adb.longPressKey(AndroidKeyCode.POWER);
```
点击某个位置
```
//通过坐标点击相机图标
adb.tap(160, 1060)
//通过相机图标的名称这个元素进行点击（还可通过class、resource id 等属性定位）
adb.tap(position.findElementByText("相机"))
//长按相机图标1.5s
adb.longPress(160, 1060, 1500);
//或者
adb.longPress(position.findElementByText("相机"))
```
滑动屏幕
```
//左滑屏幕，持续500ms
adb.swipe(900, 1060, 160, 1060, 500);
或者，从“个性主题”图标位置滑动至“相机图标位置”
adb.swipe(position.findElementByText("个性主题"), position.findElementByText("相机"), 500)
```
缩放屏幕
```
//第一个起始点的位置(500, 700),该店滑动至(800,400)
//第二个起始点的位置(500,1200),该店滑动至(200,1600)
//持续1s
adb.pinchZoom(500,700,800,400,500,1200,200,1600,1000);
```
启动其它应用
```
//启动相机
adb.startActivity("com.android.camera/.Camera");
```
截屏
```
//截屏并保存至sd卡
adb.getSceenshot().writeToFile("sdcard");
```
剩余方法请查看源码。

## FAQ
*	`java.lang.NoClassDefFoundError: com.robotium.solo.Solo`
解决方法：
右键项目 > Build Path > Config Build Path > Order and Export,
选中 robotium 的 jar 包并勾选，点击 Top 移动到顶部。
![robotium-bulid-path](/image/robotium_build_path.png)

*	`java.lang.NoClassDefFoundError: xuxu.autotest.element.Position`
解决方法：
与上面的解决方法相同。

*	`xuxu.autotest.TestException: 未在当前界面找到元素`
解决方法：
	*	确定是使用真机，并且使用第三方 root 软件 root，并且在 root 权限管理中对被测应用进行授权
	*	确定已跳转至目标界面，使用 sdk 自带工具 uiautomatorviewer，确认需要定位的元素的属性是否有误
	![uiautomatorviewer](/image/uiautomatorviewer.png)
	*	在进入目标页面进行定位之前，增加延时。
	![throttle](/image/throttle.png)
*	`Test run failed: Instrumentation run failed due to 'Process crashed.'`
解决方法：

	```
	F:\git-project>adb shell ps | grep com.example.crossprocessdemo
	u0_a464   10875 246   888732 45320 ffffffff 400468b8 S com.example.crossprocessdemo

	F:\git-project>adb shell kill 10875

	```
Windows 如果没有 grep 命令，用 findstr 命令，杀掉未正常结束掉的被测应用进程。
*	Eclipse 查看源码出现乱码
解决方法：
更改 Eclips 的编码格式为 utf-8.

