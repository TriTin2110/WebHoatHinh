package vn.tritin.WebHoatHinh.thread;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class DeleteFileThread extends Thread {
	private File file;

	public DeleteFileThread(File file) {
		this.file = file;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(3000);
			if (file.exists()) {
				while (file.exists()) {
					if (file.canWrite()) {
						Files.delete(file.toPath());
					}
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
