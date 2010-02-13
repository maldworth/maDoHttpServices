package com.maldworth.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Helpers
{
	public static final boolean isNullOrEmptyOrBlank(String str)
	{
		return str == null || "".equals(str.trim());
	}
	
	public static final boolean isNullOrEmpty(String str)
	{
		return str == null || "".equals(str);
	}
	
	public static final String firstCharToUpper(String str)
	{
		return str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase();
	}
	
	public static final String convertToHex(byte[] data)
	{
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++)
		{
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;
			do
			{
				if ((0 <= halfbyte) && (halfbyte <= 9))
					buf.append((char) ('0' + halfbyte));
				else
					buf.append((char) ('a' + (halfbyte - 10)));
				halfbyte = data[i] & 0x0F;
			} while(two_halfs++ < 1);
		}
		return buf.toString();
	}
	
	public static final String md5(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] md5hash = new byte[32];
		md.update(text.getBytes("UTF-8"), 0, text.length());
		md5hash = md.digest();
		return convertToHex(md5hash);
	}
	
	public static final String encode(String normal)
	{
		return normal.replace("&", "%26").replace(";", "%3B");
	}
}
