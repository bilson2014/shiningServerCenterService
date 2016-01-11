package com.panfeng.shining.utils.videotools;

import java.io.IOException;

public class VideoTranscoding {

	public static void videotranscoding(String input, String output) {
		try {
			// ffmpeg -i 1435057883570.mp4 -vcodec h264 -vtag xvid -strict -2
			// testxvid.mp4
			ProcessBuilder processBuilder = new ProcessBuilder(new String[] {
					"ffmpeg", "-i", input, "-vcodec", "h264", "-vtag", "xvid",
					"-strict", "-2", output });
			Process process = processBuilder.start();
			process.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
