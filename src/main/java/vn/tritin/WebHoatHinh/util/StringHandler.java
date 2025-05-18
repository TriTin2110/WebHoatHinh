package vn.tritin.WebHoatHinh.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class StringHandler {
	/*-
	 * We will write description at byte[] value to memory
	 * Then we take it out from memory and encrypt it with Base64
	 * */
	public String encrypt(String description) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		GZIPOutputStream gzip = null;
		try {
			gzip = new GZIPOutputStream(output);
			gzip.write(description.getBytes("UTF-8"));
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (gzip != null) {
				try {
					gzip.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return Base64.getEncoder().encodeToString(output.toByteArray());
	}

	public String decrypt(String encryptedString) {
		StringBuilder result = new StringBuilder();
		byte[] input = Base64.getDecoder().decode(encryptedString.getBytes());
		ByteArrayInputStream byteInput = new ByteArrayInputStream(input);
		GZIPInputStream gzip = null;
		InputStreamReader inputReader = null;
		BufferedReader br = null;
		try {
			gzip = new GZIPInputStream(byteInput);
			inputReader = new InputStreamReader(gzip);
			br = new BufferedReader(inputReader);
			String line = "";
			while ((line = br.readLine()) != null) {
				result.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (inputReader != null) {
				try {
					inputReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (gzip != null) {
				try {
					gzip.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (byteInput != null) {
				try {
					byteInput.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return result.toString();
	}

}
