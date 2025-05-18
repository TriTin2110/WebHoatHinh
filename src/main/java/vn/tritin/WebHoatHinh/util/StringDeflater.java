package vn.tritin.WebHoatHinh.util;

import java.io.UnsupportedEncodingException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class StringDeflater {
	private byte[] output;
	private int length;
	private String inflaterString;

	public void deflaterString(String description) {
		int length = 0;
		byte[] output = new byte[100];
		try {
			byte[] descriptionByte = description.getBytes("UTF-8");
			Deflater deflater = new Deflater();
			deflater.setInput(descriptionByte);
			deflater.finish();
			length = deflater.deflate(output);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setOutput(output);
		this.setLength(length);
	}

	public void inflaterString(int length, byte[] input) {
		byte[] temp = new byte[1024];
		Inflater inflater = new Inflater();
		inflater.setInput(input, 0, length);
		int output = 0;
		try {
			output = inflater.inflate(temp);
		} catch (DataFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		inflater.end();
		String result = new String(temp, 0, output);
		this.setInflaterString(result);
	}

	public byte[] getOutput() {
		return output;
	}

	public void setOutput(byte[] output) {
		this.output = output;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getInflaterString() {
		return inflaterString;
	}

	public void setInflaterString(String inflaterString) {
		this.inflaterString = inflaterString;
	}

}
