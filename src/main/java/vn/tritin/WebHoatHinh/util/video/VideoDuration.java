package vn.tritin.WebHoatHinh.util.video;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.io.FileChannelWrapper;
import org.jcodec.common.io.NIOUtils;

public class VideoDuration {
	public static String getDuration(File file) {
		try {
			FileChannelWrapper wrapper = NIOUtils.readableChannel(file);
			FrameGrab frameGrab = FrameGrab.createFrameGrab(wrapper);
			long duration = (long) frameGrab.getVideoTrack().getMeta().getTotalDuration();
			short hour = 0;
			short minuties = 0;
			short seconds = 0;
			if (duration > 3600) {
				hour = (short) (duration / 3600);
				duration %= 3600;
			}
			if (duration > 60) {
				minuties = (short) (duration / 60);
				duration %= 60;
			}
			seconds = (short) duration;
			return String.format("%02d:%02d:%02d", hour, minuties, seconds);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JCodecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
