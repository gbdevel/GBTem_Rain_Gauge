package com.gbsoft.rainfallcollector.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gbsoft.rainfallcollector.exception.DateFormatInvalidException;

public class DateUtil {

	private final static String BASIC_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	private final static String BASIC_DATE = "yyyy-MM-dd";

	public static LocalDateTime stringToLocalDateTime(String datetime) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(BASIC_DATE_FORMAT);

		return LocalDateTime.parse(datetime, formatter);
	}
	
	public static LocalDate stringToLocalDate(String datetime) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(BASIC_DATE);

		return LocalDate.parse(datetime.substring(0, 10), formatter);
	}

	public static String localDateTimeToString(LocalDateTime datetime) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(BASIC_DATE_FORMAT);

		return datetime.format(formatter);
	}

	public static boolean isValidDateTimeFormat(String dateTimeString) {

		SimpleDateFormat sdf = new SimpleDateFormat(BASIC_DATE_FORMAT);
		sdf.setLenient(false);

		Pattern regexPattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
		Matcher matcher = regexPattern.matcher(dateTimeString);

		return matcher.matches() && isValidDateTime(dateTimeString);
	}

	public static boolean isValidDateTime(String dateTimeString) {

		SimpleDateFormat sdf = new SimpleDateFormat(BASIC_DATE_FORMAT);
		sdf.setLenient(false);

		try {
			sdf.parse(dateTimeString);
			return true;
		} catch (ParseException e) {
			throw new DateFormatInvalidException();
		}
	}

	public static Date parseDateTime(String dateTimeString) {

		SimpleDateFormat sdf = new SimpleDateFormat(BASIC_DATE_FORMAT);
		sdf.setLenient(false);

		try {
			return sdf.parse(dateTimeString);
		} catch (ParseException e) {
			throw new DateFormatInvalidException();
		}
	}

}
