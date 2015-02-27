package xuxu.autotest;

import java.util.ArrayList;
import java.util.regex.Pattern;

import xuxu.autotest.utils.ReUtils;
import xuxu.autotest.utils.ShellUtils;
import xuxu.autotest.element.Element;

/**
 * 
 * @author xuxu, 274925460@qq.com
 * 
 */
public class AdbDevice {
	public AdbDevice() {
		// ShellUtils.cmd("wait-for-device");
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
	 * 发送一个按键事件
	 * 
	 * @param keycode
	 *            键值
	 */
	public void sendKeyEvent(int keycode) {
		ShellUtils.suShell("input keyevent " + keycode);
		sleep(500);
	}

	/**
	 * 长按物理键，需要android 4.4以上
	 * 
	 * @param keycode
	 *            键值
	 */
	public void longPressKey(int keycode) {
		ShellUtils.suShell("input keyevent --longpress " + keycode);
		sleep(500);
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
	 *            获取到的文本框中的text
	 */
	public void clearText(String text) {
		int length = text.length();
		for (int i = length; i > 0; i--) {
			sendKeyEvent(AndroidKeyCode.BACKSPACE);
		}
	}

	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
