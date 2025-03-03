package vn.tritin.WebHoatHinh;

import java.util.Calendar;
import java.util.Date;

public class Snippet {
	public static void main(String[] args) {
		int age = Calendar.getInstance().get(Calendar.YEAR) - (new Date(2003, 10, 21).getYear());
		System.out.println(age);

	}
}
