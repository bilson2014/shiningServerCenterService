package com.panfeng.shining.utils;


public class Coding {
	public static String bin2hex(String bin) {
		char[] digital = "0123456789ABCDEF".toCharArray();
		StringBuffer sb = new StringBuffer("");
		byte[] bs = bin.getBytes();

		for (int i = 0; i < bs.length; ++i) {
			int bit = (bs[i] & 0xF0) >> 4;
			sb.append(digital[bit]);
			bit = bs[i] & 0xF;
			sb.append(digital[bit]);
		}
		return sb.toString();
	}

	public static String hex2bin(String hex) {
		String digital = "0123456789ABCDEF";
		char[] hex2char = hex.toCharArray();
		byte[] bytes = new byte[hex.length() / 2];

		for (int i = 0; i < bytes.length; ++i) {
			int temp = digital.indexOf(hex2char[(2 * i)]) * 16;
			temp += digital.indexOf(hex2char[(2 * i + 1)]);
			bytes[i] = (byte) (temp & 0xFF);
		}
		return new String(bytes);
	}

	public static String byte2hex(byte[] b) {
		String hs = "";
		String tmp = "";
		for (int n = 0; n < b.length; ++n) {
			tmp = Integer.toHexString(b[n] & 0xFF);
			if (tmp.length() == 1)
				hs = hs + "0" + tmp;
			else {
				hs = hs + tmp;
			}
		}
		tmp = null;
		return hs.toUpperCase();
	}

	public static byte[] hex2byte(byte[] b) {
		if (b.length % 2 != 0) {
			throw new IllegalArgumentException("长度不是偶数");
		}
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);

			b2[(n / 2)] = (byte) Integer.parseInt(item, 16);
		}
		b = (byte[]) null;
		return b2;
	}

	public static void main(String[] args) {
		String content = "技术性问题EDF%&^%#_|~";
		System.out.println(bin2hex(content));
		System.out.println(hex2bin(bin2hex(content)));
	}
}