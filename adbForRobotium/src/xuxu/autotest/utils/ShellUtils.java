package xuxu.autotest.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ShellUtils {
//	public static Process cmd(String command) {
//		return process("adb " + command);
//	}

	public static Process shell(String command) {
//		return process("adb shell " + command);
		return process(command);
	}
	
	public static BufferedReader shellOut(Process ps) {
		BufferedInputStream in = new BufferedInputStream(ps.getInputStream());
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		return br;
	}

	public static String getShellOut(Process ps) {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = shellOut(ps);
		String line;

		try {
			while ((line = br.readLine()) != null) {
//				sb.append(line);
			sb.append(line + System.getProperty("line.separator"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

	private static Process process(String command) {
		Process ps = null;
		try {
			ps = Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ps;
	}
	
	//需要root权限执行命令时使用该方法
	public static void suShell(String cmd) {
		Process ps = null;
		DataOutputStream os;
		
		try {
			ps = Runtime.getRuntime().exec("su");
			os = new DataOutputStream(ps.getOutputStream());
			
			os.writeBytes(cmd + "\n");
			os.writeBytes("exit\n");
			os.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static InputStream StringTOInputStream(String in) throws Exception {
		ByteArrayInputStream is = new ByteArrayInputStream(in.getBytes("ISO-8859-1"));
		return is;
	}
}
