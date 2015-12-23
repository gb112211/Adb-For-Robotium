package xuxu.autotest;

import java.util.ArrayList;

import xuxu.autotest.utils.ShellUtils;
import xuxu.autotest.element.Element;

/**
 * 
 * @author xuxu, 274925460@qq.com
 * 
 */
public class AdbDevice {
	public AdbDevice() {
	}

	/**
	 * 获取设备的id号
	 * 
	 * @return 返回设备id号
	 */
	public String getDeviceId() {
		Process ps = ShellUtils.shell("getprop ro.boot.serialno");
		String serialno = ShellUtils.getShellOut(ps);

		return serialno;
	}

	/**
	 * 获取设备中Android的版本号，如4.4.2
	 * 
	 * @return 返回Android版本号
	 */
	public String getAndroidVersion() {
		Process ps = ShellUtils.shell("getprop ro.build.version.release");
		String androidVersion = ShellUtils.getShellOut(ps);

		return androidVersion;
	}

	/**
	 * 获取设备中SDK的版本号
	 * 
	 * @return 返回SDK版本号
	 */
	public int getSdkVersion() {
		Process ps = ShellUtils.shell("getprop ro.build.version.sdk");
		String sdkVersion = ShellUtils.getShellOut(ps).trim();

		return Integer.parseInt(sdkVersion);
	}

	/**
	 * 截屏
	 * 
	 * @return
	 */
	public XuImage getSceenshot() {
		return XuImage.getXuImage().screenShot();
	}

	/**
	 * 执行shell命令
	 * 
	 * @param command
	 *            shell命令
	 */
	public void shell(String command) {
		ShellUtils.suShell(command);
	}

	/**
	 * 发送一个按键事件
	 * 
	 * @param keycode
	 *            键值 ok
	 */
	public void sendKeyEvent(int keycode) {
		ShellUtils.suShell("input keyevent " + keycode);
		sleep(500);
	}

	/**
	 * 长按物理键
	 * 
	 * @param keycode
	 *            键值
	 */
	public void longPressKey(int keycode) {
		String param1 = "DispatchKey(0,0,0," + keycode + ",0,0,0,0)";
		String param2 = "UserWait(1500)";
		String param3 = "DispatchKey(0,0,1," + keycode + ",0,0,0,0)";
		String param = param1 + "\n" + param2 + "\n" + param3;
		writeMonkeyScriptFile(param);
		runMonkey();
		sleep(500);
	}

	/**
	 * 长按某个坐标位置
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 * @param ms
	 *            持续时间
	 */
	public void longPress(int x, int y, long ms) {
		String param = "PressAndHold(" + x + "," + y + "," + ms + ")";
		writeMonkeyScriptFile(param);
		runMonkey();
	}

	/**
	 * 长按某个元素
	 * 
	 * @param e
	 *            元素
	 * @param ms
	 *            持续时间
	 */
	public void longPress(Element e, long ms) {
		String param = "PressAndHold(" + e.getX() + "," + e.getY() + "," + ms + ")";
		writeMonkeyScriptFile(param);
		runMonkey();
	}

	/**
	 * 发送一个点击事件
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 */
	public void tap(int x, int y) {
		ShellUtils.suShell("input tap " + x + " " + y);
		sleep(500);
	}

	/**
	 * 发送一个点击事件
	 * 
	 * @param e
	 *            元素对象
	 */
	public void tap(Element e) {
		ShellUtils.suShell("input tap " + e.getX() + " " + e.getY());
		sleep(500);
	}

	/**
	 * 发送一个滑动事件
	 * 
	 * @param startX
	 *            起始x坐标
	 * @param startY
	 *            起始y坐标
	 * @param endX
	 *            结束x坐标
	 * @param endY
	 *            结束y坐标
	 * @param ms
	 *            持续时间
	 */
	public void swipe(int startX, int startY, int endX, int endY, long ms) {
		String param = startX + "," + startY + "," + endX + "," + endY + "," + ms;
		writeMonkeyScriptFile("PinchZoom(" + param + ")");
		runMonkey();
		sleep(500);
	}

	/**
	 * 发送一个滑动事件
	 * 
	 * @param e1
	 *            起始元素
	 * @param e2
	 *            终点元素
	 * @param ms
	 *            持续时间
	 */
	public void swipe(Element e1, Element e2, long ms) {
		String param = e1.getX() + "," + e1.getY() + "," + e2.getX() + "," + e2.getY() + "," + ms;
		writeMonkeyScriptFile("PinchZoom(" + param + ")");
		runMonkey();
		sleep(500);
	}

	/**
	 * 缩放事件
	 * 
	 * @param startX1
	 *            第一起始点x坐标
	 * @param startY1
	 *            第一起始点y坐标
	 * @param endX1
	 *            第一终点x坐标
	 * @param endY1
	 *            第一终点y坐标
	 * @param startX2
	 *            第二起始点x坐标
	 * @param startY2
	 *            第二起始点y坐标
	 * @param endX2
	 *            第二终点x坐标
	 * @param endY2
	 *            第二终点y坐标
	 * @param ms
	 *            持续时间
	 */
	public void pinchZoom(int startX1, int startY1, int endX1, int endY1, int startX2, int startY2, int endX2,
			int endY2, long ms) {
		String param = startX1 + "," + startY1 + "," + endX1 + "," + endY1 + "," + startX2 + "," + startY2 + "," + endX2
				+ "," + endY2 + "," + ms;
		writeMonkeyScriptFile("PinchZoom(" + param + ")");
		runMonkey();
	}

	/**
	 * 发送一段文本，只支持英文，多个空格视为一个空格
	 * 
	 * @param text
	 *            英文文本
	 */
	public void sendText(String text) {
		String[] str = text.split(" ");
		ArrayList<String> out = new ArrayList<String>();
		for (String string : str) {
			if (!string.equals("")) {
				out.add(string);
			}
		}

		int length = out.size();
		for (int i = 0; i < length; i++) {
			ShellUtils.suShell("input text " + out.get(i));
			sleep(100);
			if (i != length - 1) {
				sendKeyEvent(AndroidKeyCode.SPACE);
			}
		}
	}

	/**
	 * 清除文本
	 * 
	 * @param text
	 *            清除文本框中的text
	 */
	public void clearText(String text) {
		int length = text.length();
		for (int i = length; i > 0; i--) {
			sendKeyEvent(AndroidKeyCode.BACKSPACE);
		}
	}

	/**
	 * 启动一个应用
	 * 
	 * @param component
	 *            应用包名加主类名，packageName/Activity ok
	 */
	public void startActivity(String component) {
		ShellUtils.suShell("am start -n " + component);
	}

	/**
	 * 使用默认浏览器打开一个网页
	 * 
	 * @param url
	 *            网页地址,http://xuxu1988.com ok
	 */
	public void startWebpage(String url) {
		ShellUtils.suShell("am start -a android.intent.action.VIEW -d " + url);
	}

	/**
	 * 使用拨号器拨打号码
	 * 
	 * @param number
	 *            电话号码
	 */
	public void callPhone(int number) {
		ShellUtils.suShell("am start -a android.intent.action.CALL -d tel:" + number);
	}

	private void writeMonkeyScriptFile(String cmd) {
		shell("rm /data/local/tmp/monkey.txt");
		shell("touch /data/local/tmp/monkey.txt");
		shell("echo \"count= 1\nspeed= 1.0\nstart data >>\n\" > /data/local/tmp/monkey.txt");
		shell("echo \"" + cmd + "\" >> /data/local/tmp/monkey.txt");
	}

	private void runMonkey() {
		shell("monkey -f /data/local/tmp/monkey.txt 1");
	}

	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
