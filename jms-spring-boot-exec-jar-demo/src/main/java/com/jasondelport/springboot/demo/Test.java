package com.jasondelport.springboot.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.*;

public class Test {

	// Collections.emptyList() is an immutable list
	private List<String> list = Collections.emptyList();

	public Test() {

	}

	public static void main(String[] args) {
		List<String> temp = new ArrayList<>();
		temp.add("hello");
		temp.add("world");
		temp.add("woop");
		temp.add("woop");

		Test test = new Test();
		test.setData(temp);


		int i1 = 9;

		int i2 = i1 / 2;
		System.out.println("i2 -> " + i2);

		System.out.println("i2 rnd -> " + Math.round(i2));


		//'Z' stands for Zulu time, which is also GMT and UTC.
		String DATE_FORMAT1 = "yyyy-MM-dd'T'HH:mm:ss'+00:00'"; // same as "yyyy-MM-dd'T'HH:mm:ss'Z'"
		String DATE_FORMAT2 = "yyyy-MM-dd'T'HH:mm:ssZ"; // RFC 822 time zone
		String DATE_FORMAT3 = "yyyy-MM-dd'T'HH:mm:ssXXX"; // ISO 8601 but doesn't work in older android
		String DATE_FORMAT4 = "yyyy-MM-dd'T'HH:mm:ssZZZZ";

		SimpleDateFormat dateFormatGmt1 = new SimpleDateFormat(DATE_FORMAT1, Locale.UK);
		dateFormatGmt1.setTimeZone(TimeZone.getTimeZone("UTC"));

		// this ends up being wrong because the timezone is hardcoded in the format
		SimpleDateFormat dateFormatGmt11 = new SimpleDateFormat(DATE_FORMAT1, Locale.UK);
		dateFormatGmt11.setTimeZone(TimeZone.getTimeZone("Europe/London"));

		// wrong during BST
		SimpleDateFormat dateFormatGmt2 = new SimpleDateFormat(DATE_FORMAT1, Locale.UK);

		SimpleDateFormat dateFormatGmt3 = new SimpleDateFormat(DATE_FORMAT2, Locale.UK);
		dateFormatGmt3.setTimeZone(TimeZone.getTimeZone("UTC"));

		SimpleDateFormat dateFormatGmt33 = new SimpleDateFormat(DATE_FORMAT2, Locale.UK);
		dateFormatGmt33.setTimeZone(TimeZone.getTimeZone("Europe/London"));

		SimpleDateFormat dateFormatGmt4 = new SimpleDateFormat(DATE_FORMAT2, Locale.UK);

		SimpleDateFormat dateFormatGmt5 = new SimpleDateFormat(DATE_FORMAT3, Locale.UK);
		dateFormatGmt5.setTimeZone(TimeZone.getTimeZone("UTC"));

		SimpleDateFormat dateFormatGmt55 = new SimpleDateFormat(DATE_FORMAT3, Locale.UK);
		dateFormatGmt55.setTimeZone(TimeZone.getTimeZone("Europe/London"));

		SimpleDateFormat dateFormatGmt6 = new SimpleDateFormat(DATE_FORMAT3, Locale.UK);

		SimpleDateFormat dateFormatGmt7 = new SimpleDateFormat(DATE_FORMAT4, Locale.UK);
		dateFormatGmt7.setTimeZone(TimeZone.getTimeZone("UTC"));

		SimpleDateFormat dateFormatGmt77 = new SimpleDateFormat(DATE_FORMAT4, Locale.UK);
		dateFormatGmt77.setTimeZone(TimeZone.getTimeZone("Europe/London"));

		SimpleDateFormat dateFormatGmt8 = new SimpleDateFormat(DATE_FORMAT4, Locale.UK);

		//2018-10-25T12:46:53+00:00

		System.out.println("1 -> " + dateFormatGmt1.format(new Date()));
		System.out.println(dateFormatGmt11.format(new Date()));
		System.out.println(dateFormatGmt2.format(new Date()));
		System.out.println("3 -> " + dateFormatGmt3.format(new Date()));
		System.out.println(dateFormatGmt33.format(new Date()));
		System.out.println(dateFormatGmt4.format(new Date()));
		System.out.println("5 -> " + dateFormatGmt5.format(new Date()));
		System.out.println(dateFormatGmt55.format(new Date()));
		System.out.println(dateFormatGmt6.format(new Date()));
		System.out.println("7 -> " + dateFormatGmt7.format(new Date()));
		System.out.println(dateFormatGmt77.format(new Date()));
		System.out.println(dateFormatGmt8.format(new Date()));

		System.out.println("-----------------");


		ZonedDateTime a = ZonedDateTime.now();
		System.out.println(a.toString());
		System.out.println(a.toOffsetDateTime().toString());

		ZonedDateTime b = ZonedDateTime.parse("2018-10-25T15:34:33Z");
		System.out.println(b.toLocalDateTime().toString());

		ZonedDateTime c = ZonedDateTime.parse("2018-10-25T15:35:26+01:00");
		System.out.println(c.toOffsetDateTime().toString());

		// this breaks
		//ZonedDateTime d = ZonedDateTime.parse("2018-10-25T15:34:33+0100");
		//System.out.println(d.toString());

		System.out.println("-----------------");

		OffsetDateTime offset = OffsetDateTime.parse("2018-10-25T15:34:33Z"); // 16:34 in BST
		OffsetDateTime offset2 = OffsetDateTime.parse("2018-10-25T15:34:33+01:00");
		//OffsetDateTime offset3 = OffsetDateTime.parse("2018-10-25T15:34:33+0100"); // breaks
		System.out.println(offset.toString());
		System.out.println(offset.toInstant().toString()); // Instant is always in UTC.

		System.out.println(Date.from(offset.toInstant()).toString());
		System.out.println(Date.from(offset2.toInstant()).toString());

		System.out.println("---------||--------");

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT3, Locale.UK);
		try {
			Date date = simpleDateFormat.parse("2018-10-25T15:34:33+01:00");
			System.out.println(date.toString());
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			System.out.println(cal.toString());
			System.out.println(new SimpleDateFormat("HH:mm", Locale.UK).format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}



	}

	private void setData(List<String> data) {
		// this assignment disregards the original immutable list
		list = data;
		System.out.println("done -> " + list.size());
		list.add("can add?");
		System.out.println("done -> " + list.size());
	}
}
