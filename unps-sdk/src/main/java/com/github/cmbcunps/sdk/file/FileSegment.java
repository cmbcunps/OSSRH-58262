package com.github.cmbcunps.sdk.file;

public class FileSegment {
	private long fileSize;
	private long segmentCount;
	private int segmentIndex;
	private int segmentLength;
	private String endFlag;

	public FileSegment() {

	}

	public FileSegment(long fileSize, long segmentCount, int segmentIndex) {
		this.fileSize = fileSize;
		this.segmentCount = segmentCount;
		this.segmentIndex = segmentIndex;
		if (segmentCount == segmentIndex + 1) {
			this.segmentLength = (int) (fileSize - segmentIndex * FileSegmentUtil.getSegmentSize());
			this.endFlag = "Y";
		} else {
			this.segmentLength = FileSegmentUtil.getSegmentSize();
			this.endFlag = "N";
		}
	}

	public int getSegmentIndex() {
		return segmentIndex;
	}

	public void setSegmentIndex(int segmentIndex) {
		this.segmentIndex = segmentIndex;
	}

	public int getSegmentLength() {
		return segmentLength;
	}

	public void setSegmentLength(int segmentLength) {
		this.segmentLength = segmentLength;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public long getSegmentCount() {
		return segmentCount;
	}

	public void setSegmentCount(long segmentCount) {
		this.segmentCount = segmentCount;
	}

	public String getEndFlag() {
		return endFlag;
	}

	public void setEndFlag(String endFlag) {
		this.endFlag = endFlag;
	}

	@Override
	public String toString() {
		return "FileSegment [fileSize=" + fileSize + ", segmentCount=" + segmentCount + ", segmentIndex=" + segmentIndex + ", segmentLength=" + segmentLength + ", endFlag=" + endFlag + "]";
	}

}
