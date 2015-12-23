package xuxu.autotest;

import java.io.File;

import xuxu.autotest.utils.DateUtils;
import xuxu.autotest.utils.ShellUtils;

public class XuImage {
	private String tempFile = System.getProperty("java.io.tmpdir");
	private String temp = new File(tempFile + "/screenshot").getAbsolutePath();
	
	private static XuImage image = null;
	
	public static XuImage getXuImage() {
		if (image == null) {
			image = new XuImage();
		}
		
		return image;
	}

	private XuImage() {
		File dumpFile = new File(temp);
		if (!dumpFile.exists()) {
			dumpFile.mkdir();
		}
	}

	/**
	 * 截取屏幕
	 */
	public XuImage screenShot() {
		ShellUtils.suShell("screencap -p " + temp + "/temp.png");
		return this;
	}

	/**
	 * 将截屏文件写入目标路径，以当前系统时间戳命名，格式为PNG
	 * 
	 * @param path
	 *            存储路径
	 */
	public void writeToFile(String path) {
		ShellUtils.suShell("cp "+temp+"/temp.png"+" "+path+"/"+DateUtils.getCurrentSystemTime()+".png");
	}

}
