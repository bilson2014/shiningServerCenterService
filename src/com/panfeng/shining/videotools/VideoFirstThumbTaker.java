package com.panfeng.shining.videotools;

import java.io.IOException;

public class VideoFirstThumbTaker extends VideoThumbTaker {
	public VideoFirstThumbTaker(String ffmpegApp) {
		super(ffmpegApp);
	}

	public void getThumb(String videoFilename, String thumbFilename, int width,
			int height) throws IOException, InterruptedException {
		super.getThumb(videoFilename, thumbFilename, width, height, 0, 0, 1.0F);
	}
	
	public void getThumb_2(String videoFilename, String thumbFilename) throws IOException, InterruptedException {
		super.getThumb_2(videoFilename, thumbFilename, 0, 0, 1.0F);
	}
}
