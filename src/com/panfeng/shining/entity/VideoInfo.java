package com.panfeng.shining.entity;

public class VideoInfo {
	private String videoName;
	private long size;

	@Override
	public boolean equals(Object obj) {
		boolean res = false;
		if (obj != null && obj instanceof VideoInfo) {
			String name = ((VideoInfo) obj).getVideoName();
			long sizes = ((VideoInfo) obj).getSize();
			if (name.equals(videoName) && sizes == size)
				res = true;
		} else
			res = false;
		return res;
	}

	@Override
	public String toString() {
		return videoName + "%%" + size;
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

	public VideoInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VideoInfo(String videoName, long size) {
		super();
		this.videoName = videoName;
		this.size = size;
	}

}
