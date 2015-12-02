package com.panfeng.shining.videotools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoInfo {
	private String ffmpegApp;
	private int hours;
	private int minutes;
	private float seconds;
	private int width;
	private int heigt;

	public VideoInfo() {
	}

	public VideoInfo(String ffmpegApp) {
		this.ffmpegApp = ffmpegApp;
	}

	public String toString() {
		return "time: " + this.hours + ":" + this.minutes + ":" + this.seconds
				+ ", width = " + this.width + ", height= " + this.heigt;
	}

	public void getInfo(String videoFilename) throws IOException,
			InterruptedException {
		String tmpFile = videoFilename + ".tmp.png";
		ProcessBuilder processBuilder = new ProcessBuilder(new String[] {
				this.ffmpegApp, "-y", "-i", videoFilename, "-vframes", "1",
				"-ss", "0:0:0", "-an", "-vcodec", "png", "-f", "rawvideo",
				"-s", "100*100", tmpFile });

		Process process = processBuilder.start();

		InputStream stderr = process.getErrorStream();
		InputStreamReader isr = new InputStreamReader(stderr);
		BufferedReader br = new BufferedReader(isr);

		StringBuffer sb = new StringBuffer();
		String line;
		while ((line = br.readLine()) != null) {
			// String line;
			sb.append(line);
		}

		new File(tmpFile).delete();

		System.out.println("video info:\n" + sb);
		Pattern pattern = Pattern.compile("Duration: (.*?),");
		Matcher matcher = pattern.matcher(sb);

		if (matcher.find()) {
			String time = matcher.group(1);
			calcTime(time);
		}

		pattern = Pattern.compile("w:\\d+ h:\\d+");
		matcher = pattern.matcher(sb);

		if (matcher.find()) {
			String wh = matcher.group();

			String[] strs = wh.split("\\s+");
			if ((strs != null) && (strs.length == 2)) {
				this.width = Integer.parseInt(strs[0].split(":")[1]);
				this.heigt = Integer.parseInt(strs[1].split(":")[1]);
			}
		}

		process.waitFor();
		if (br != null)
			br.close();
		if (isr != null)
			isr.close();
		if (stderr != null)
			stderr.close();
	}

	private void calcTime(String timeStr) {
		String[] parts = timeStr.split(":");
		this.hours = Integer.parseInt(parts[0]);
		this.minutes = Integer.parseInt(parts[1]);
		this.seconds = Float.parseFloat(parts[2]);
	}

	public String getFfmpegApp() {
		return this.ffmpegApp;
	}

	public void setFfmpegApp(String ffmpegApp) {
		this.ffmpegApp = ffmpegApp;
	}

	public int getHours() {
		return this.hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getMinutes() {
		return this.minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public float getSeconds() {
		return this.seconds;
	}

	public void setSeconds(float seconds) {
		this.seconds = seconds;
	}

	public int getWidth() {
		return this.width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeigt() {
		return this.heigt;
	}

	public void setHeigt(int heigt) {
		this.heigt = heigt;
	}

	public static void main(String[] args) {
		VideoInfo videoInfo = new VideoInfo("c:\\ffmpeg\\bin\\ffmpeg.exe");
		try {
			// videoInfo.getInfo("C:\\\11.mp4");
			System.out.println(videoInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
