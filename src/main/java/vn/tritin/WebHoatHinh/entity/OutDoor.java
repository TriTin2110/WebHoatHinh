package vn.tritin.WebHoatHinh.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class OutDoor {
	@Id
	private String name;
	private int totalMovie;

	public OutDoor() {
	}

	public OutDoor(String name) {
		this.name = name;
	}

	public OutDoor(String name, int totalMovie) {
		this.name = name;
		this.totalMovie = totalMovie;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTotalMovie() {
		return totalMovie;
	}

	public void setTotalMovie(int totalMovie) {
		this.totalMovie = totalMovie;
	}

}
