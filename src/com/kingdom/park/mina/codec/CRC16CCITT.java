package com.kingdom.park.mina.codec;

/** 
 * 类说明 
 * @author 作者:yaozq 
 * @version 创建时间：2016-11-7 上午09:38:04 
 * 
  	************************************************************************* 
	 *  Compilation:  javac CRC16CCITT.java 
	 *  Execution:    java CRC16CCITT s 
	 *  Dependencies:  
	 *   
	 *  Reads in a sequence of bytes and prints out its 16 bit 
	 *  Cylcic Redundancy Check (CRC-CCIIT 0xFFFF). 
	 * 
	 *  1 + x + x^5 + x^12 + x^16 is irreducible polynomial. 
	 * 
	 *  % java CRC16-CCITT 123456789 
	 *  CRC16-CCITT = 29b1 
	 * 
	 *************************************************************************
 * 
 */

	public class CRC16CCITT {   
	    
		/**
		 * 字符串转CRC校验码
		 * @param crcStr 要编码的字符串
		 * @return 两个字节的长度范围的值
		 */
	    public static int codeString(String crcStr) {   
	        int crc = 0xFFFF;          // initial value  
	        int polynomial = 0x1021;   // 0001 0000 0010 0001  (0, 5, 12)   
	  
	        // byte[] testBytes = "123456789".getBytes("ASCII");  
	  
	        byte[] bytes = crcStr.getBytes();  
	  
	        for (byte b : bytes) {  
	            for (int i = 0; i < 8; i++) {  
	                boolean bit = ((b   >> (7-i) & 1) == 1);  
	                boolean c15 = ((crc >> 15    & 1) == 1);  
	                crc <<= 1;  
	                if (c15 ^ bit) crc ^= polynomial;  
	             }  
	        }  
	  
	        crc &= 0xffff;  
	        System.out.println("CRC16-CCITT-String = " + Integer.toHexString(crc));  
	        return crc;
	    }  

		/**
		 * 字节码转CRC校验码
		 * @param crcStr 字节码
		 * @return 两个字节的长度范围的值
		 */
	    public static int codeBytes(byte[] bytes) {   
	        int crc = 0xFFFF;          // initial value  
	        int polynomial = 0x1021;   // 0001 0000 0010 0001  (0, 5, 12)   
	  
	        // byte[] testBytes = "123456789".getBytes("ASCII");  
	        for (byte b : bytes) {  
	            for (int i = 0; i < 8; i++) {  
	                boolean bit = ((b   >> (7-i) & 1) == 1);  
	                boolean c15 = ((crc >> 15    & 1) == 1);  
	                crc <<= 1;  
	                if (c15 ^ bit) crc ^= polynomial;  
	             }  
	        }  
	  
	        crc &= 0xffff;  
	        System.out.println("CRC16-CCITT-Bytes = " + Integer.toHexString(crc));  
	        return crc;
	    } 

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String hex="24 24 11 00 00 00 A0 34 32 30 31 30 36 32 30 30 30 31 37 32 30 31 36 31 32 33 31 32 33 35 39 35 39 39 30 36 31 34 31 00 00 00 71 7B 22 76 65 72 73 69 6F 6E 22 3A 20 22 31 2E 34 22 2C 22 75 70 74 69 6D 65 22 3A 20 22 32 30 31 36 31 31 30 37 31 31 30 31 30 31 22 2C 22 66 72 65 65 63 6F 75 6E 74 22 3A 20 22 31 34 37 39 22 2C 22 6C 69 63 65 6E 73 65 6B 65 79 22 3A 22 34 32 30 31 30 36 32 30 30 30 31 37 32 30 31 36 31 32 33 31 32 33 35 39 35 39 39 30 36 31 34 31 22 7D";
		byte[] bs=Coding.hexStr2Bytes(hex.replace(" ", ""));
		codeBytes(bs);

	}

}
