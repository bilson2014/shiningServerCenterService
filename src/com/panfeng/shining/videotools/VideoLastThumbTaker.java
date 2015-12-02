package com.panfeng.shining.videotools;

import java.io.IOException;

public class VideoLastThumbTaker extends VideoThumbTaker {
	public VideoLastThumbTaker(String ffmpegApp) {
		super(ffmpegApp);
	}

	public void getThumb(String videoFilename, String thumbFilename, int width,
			int height) throws IOException, InterruptedException {
		VideoInfo videoInfo = new VideoInfo(this.ffmpegApp);
		videoInfo.getInfo(videoFilename);
		super.getThumb(videoFilename, thumbFilename, width, height,
				videoInfo.getHours(), videoInfo.getMinutes(),
				videoInfo.getSeconds() - 0.2F);
	}
}
