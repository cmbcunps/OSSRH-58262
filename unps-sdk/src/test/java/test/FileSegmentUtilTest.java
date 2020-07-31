package test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

import com.cmbc.unps.sdk.file.FileSegment;
import com.cmbc.unps.sdk.file.FileSegmentUtil;

public class FileSegmentUtilTest {
	public static void main(String[] args) throws IOException {
		File file = new File("/mskj/unps-sdk/tmp/2019-06-20.zip");
		List<FileSegment> list = FileSegmentUtil.getSegmentInfo(file);
		System.out.println(list);
		RandomAccessFile inputRandomFile = new RandomAccessFile(file, "r");
		for (FileSegment segment : list) {
			System.out.println(FileSegmentUtil.getBase64String(inputRandomFile, segment.getSegmentIndex(), segment.getSegmentLength()));
		}
	}
}
