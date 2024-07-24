package com.pay.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @TITLE ImageBinaryUtil.java
 * @NAME 生成图片
 * @DATE 2016-9-21
 */
public class ImageBinaryUtil {

	public static BASE64Encoder encoder = new BASE64Encoder();
	public static BASE64Decoder decoder = new BASE64Decoder();

	public static String getImageBinary(File f) {
		try {
			BufferedImage bi = ImageIO.read(f);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bi, "jpg", baos);
			byte[] bytes = baos.toByteArray();
			String encodeBuffer = encoder.encodeBuffer(bytes);
			System.out.println("ImageBinary:===>" + encodeBuffer.replaceAll("\n", "").replaceAll("\t", "").replaceAll("\r", ""));
			return encodeBuffer.replaceAll("\n", "").replaceAll("\t", "").replaceAll("\r", "");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getImageBinary(byte[] bytes) {
		String encodeBuffer = encoder.encodeBuffer(bytes);
		System.out.println("ImageBinary:===>" + encodeBuffer);
		return encodeBuffer;
	}

	/**
	 * 可以是jpg,png,gif格式
	 */
	public static void base64StringToImage(String base64String, File outFile) {
		try {
			byte[] bytes1 = decoder.decodeBuffer(base64String);
			// ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
			// BufferedImage bi1 = ImageIO.read(bais);
			// ImageIO.write(bi1, "jpg", outFile);// 不管输出什么格式图片，此处不需改动
			FileImageOutputStream imageOutput = new FileImageOutputStream(outFile);// 打开输入流
			imageOutput.write(bytes1, 0, bytes1.length);// 将byte写入硬盘
			imageOutput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}