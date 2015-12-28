package com.panfeng.shining.videotools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class VideoThumbTaker {
	protected String ffmpegApp;

	public VideoThumbTaker(String ffmpegApp) {
		this.ffmpegApp = ffmpegApp;
	}

	public void getThumb(String videoFilename, String thumbFilename, int width,
			int height, int hour, int min, float sec) throws IOException,
			InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder(new String[] {
				this.ffmpegApp, "-y", "-i", videoFilename, "-vframes", "1",
				"-ss", hour + ":" + min + ":" + sec, "-f", "mjpeg", "-s",
				width + "*" + height, "-an", thumbFilename });

		Process process = processBuilder.start();

		InputStream stderr = process.getErrorStream();
		InputStreamReader isr = new InputStreamReader(stderr);
		BufferedReader br = new BufferedReader(isr);
		String line;
		while ((line = br.readLine()) != null)
			;
		process.waitFor();

		if (br != null)
			br.close();
		if (isr != null)
			isr.close();
		if (stderr != null)
			stderr.close();
	}

	public void getThumb_2(String videoFilename, String thumbFilename,
			int hour, int min, float sec) throws IOException,
			InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder(new String[] {
				this.ffmpegApp, "-y", "-i", videoFilename, "-vframes", "1",
				"-ss", hour + ":" + min + ":" + sec, "-f", "mjpeg", "-an",
				thumbFilename });
//		com.panfeng.shining.videotools.VideoFirstThumbTaker.getThumb_2(VideoFirstThumbTaker.java:16)

		Process process = processBuilder.start();

		InputStream stderr = process.getErrorStream();
		InputStreamReader isr = new InputStreamReader(stderr);
		BufferedReader br = new BufferedReader(isr);
		String line;
		while ((line = br.readLine()) != null)
			;
		process.waitFor();

		if (br != null)
			br.close();
		if (isr != null)
			isr.close();
		if (stderr != null)
			stderr.close();
	}

	public static void main(String[] args) {
		VideoThumbTaker videoThumbTaker = new VideoThumbTaker("ffmpeg");
		try {
			videoThumbTaker.getThumb(
					"f:/sun/Diana_Vickers_-_The_Boy_Who_Murdered_Love.mkv",
					"C:\\thumbTest.png", 800, 600, 0, 0, 9.0F);
			System.out.println("over");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
