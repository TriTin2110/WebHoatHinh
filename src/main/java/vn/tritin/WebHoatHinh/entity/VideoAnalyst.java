package vn.tritin.WebHoatHinh.entity;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.Expose;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToOne;

@Entity
public class VideoAnalyst {
	@Id
	@Expose // chỉ convert những trường được đánh dấu sang JSON
	private String id;
	@ElementCollection
	@CollectionTable(name = "total_time_per_user_watch", joinColumns = @JoinColumn(name = "video_analyst_id"))
	@MapKeyColumn(name = "user_id")
	@Column(name = "time_watch")
	private Map<String, Long> totalTimePerUserWatch;
	@Expose // chỉ convert những trường được đánh dấu sang JSON
	private long totalTimeWatch;
	@Expose
	private int totalComment;
	@Expose
	private int totalView;
	private Date dateUploaded;

	@OneToOne
	@JoinColumn(name = "video_id")
	private Video video;

	public VideoAnalyst() {
	}

	public VideoAnalyst(String id, Video video) {
		this.id = id;
		totalTimePerUserWatch = new HashMap<String, Long>();
		totalTimeWatch = 0;
		totalView = 0;
		totalComment = 0;
		this.dateUploaded = video.getDateUploaded();
		this.video = video;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, Long> getTotalTimePerUserWatch() {
		return totalTimePerUserWatch;
	}

	public void setTotalTimePerUserWatch(Map<String, Long> totalTimePerUserWatch) {
		this.totalTimePerUserWatch = totalTimePerUserWatch;
	}

	public long getTotalTimeWatch() {
		return totalTimeWatch;
	}

	public void setTotalTimeWatch(long totalTimeWatch) {
		this.totalTimeWatch = totalTimeWatch;
	}

	public int getTotalView() {
		return totalView;
	}

	public void setTotalView(int totalView) {
		this.totalView = totalView;
	}

	public int getTotalComment() {
		return totalComment;
	}

	public void setTotalComment(int totalComment) {
		this.totalComment = totalComment;
	}

	public Date getDateUploaded() {
		return dateUploaded;
	}

	public void setDateUploaded(Date dateUploaded) {
		this.dateUploaded = dateUploaded;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	@Override
	public String toString() {
		return "VideoAnalyst [id=" + id + ", totalTimePerUserWatch=" + totalTimePerUserWatch + ", totalTimeWatch="
				+ totalTimeWatch + ", totalView=" + totalView + ", totalComment=" + totalComment + ", dateUploaded="
				+ dateUploaded + ", video=" + video + "]";
	}

}
