package vn.tritin.WebHoatHinh;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Snippet {
	public static void main(String[] args) {
		List<String> names = Arrays.asList("Name 1", "Name 2", "Name 3", "Name 4", "Name 5", "Name 6", "Name 7");
		String name = "name 1";

		names.stream().filter(o -> {
			Pattern pattern = Pattern.compile(name, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(o);
			return matcher.find();
		}).forEach(System.out::println);

	}
}
