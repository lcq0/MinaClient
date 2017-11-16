package com.kingdom.park.mina.codec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kingdom.park.mina.util.LoggerUtil;

/**
 * 字节转换工具
 *
 * @author cdd
 *
 */
public abstract class BinaryUtils {
	public static final Logger logger = LoggerFactory
			.getLogger(BinaryUtils.class);

	/**
	 * 输入流转换为byte数组
	 *
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static byte[] inputStreamToByte(InputStream is) {
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		int ch;
		byte byteData[] = new byte[4906];
		try {
			while ((ch = is.read()) != -1) {
				bytestream.write(ch);
			}
			byteData = bytestream.toByteArray();
		} catch (IOException e1) {
			LoggerUtil.error(BinaryUtils.class, e1, "读取输入流失败！！");
		} finally {
			try {
				bytestream.close();
			} catch (IOException e) {
				LoggerUtil.error(BinaryUtils.class, e, "关闭输入流失败！！");
			}
		}

		return byteData;
	}

	/**
	 * 文件转换成字节数组
	 *
	 * @param file
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static byte[] fileToByte(File file) {
		byte[] b = new byte[0];
		if (file != null && file.exists()) {
			try {
				b = inputStreamToByte(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				com.kingdom.park.mina.util.LoggerUtil.error(BinaryUtils.class, e, "文件未找到！！");
			}
		}
		return b;
	}

	/**
	 * 字节数组转换为流
	 *
	 * @param b
	 * @return
	 */
	public static InputStream byteToInputStream(byte[] b) {
		return new ByteArrayInputStream(b);

	}

}
