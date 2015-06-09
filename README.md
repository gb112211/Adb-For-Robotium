# adb-For-Robotium
修改 Adb-For-Test 项目，在 Robotium 中实现跨进程操作

模拟器上无效果，需要真机并且 root。因为使用 uiautomaor 命令获取 uidump.xml 时需要 root 权限，chomd 时也需要 root 权限。
例子可参考 CrossProcessDemo_Robotium.zip 中的例子<br>
demo 提供者 [http://my.csdn.net/qingchunjun](http://my.csdn.net/qingchunjun)


## 2015.06.08
*	修改 `ByText` 方法定位元素，修改后支持中文。jar 包使用 jdk 1.8 编译，如果出现 jdk 版本兼容问题，麻烦请自行重新编译下！！
*	有部分方法可能无法使用，后续会抽时间完善，也可自行修改源码，该项目主要传达的是一个跨进程的方法！欢迎交流
