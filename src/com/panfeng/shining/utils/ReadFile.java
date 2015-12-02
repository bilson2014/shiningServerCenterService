package com.panfeng.shining.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {

	// 读取指定路径文本文件
	public static List<String> read(String filePath) {
		StringBuilder str = new StringBuilder();
		BufferedReader in = null;
		List<String> data = null;
		try {
			File file = new File(filePath);
			if (file.exists())
				data = new ArrayList<String>();
			else
				return data;
			BufferedReader fr = new BufferedReader(new FileReader(file));
			String line = "";
			while ((line = fr.readLine()) != null) {
				if (!line.equals("")) {
					data.add(line.trim());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	// 写入指定的文本文件，append为true表示追加，false表示重头开始写，
	// text是要写入的文本字符串，text为null时直接返回
	public static void write(String filePath, boolean append, String text) {
		if (text == null)
			return;
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(filePath,
					append));
			try {
				out.write(text);
			} finally {
				out.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
