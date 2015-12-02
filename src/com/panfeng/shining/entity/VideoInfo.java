package com.panfeng.shining.entity;

public class VideoInfo {
	private String videoName;
	private long size;

	public VideoInfo() {
		super();
	}

	public VideoInfo(String videoName, long size) {
		super();
		this.videoName = videoName;
		this.size = size;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

}
