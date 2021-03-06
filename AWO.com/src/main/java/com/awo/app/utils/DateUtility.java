package com.awo.app.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class DateUtility {
	protected static Logger logger = LoggerFactory.getLogger(DateUtility.class);
	public static final String DATE_FORMAT_DDMMYYYY = "ddMMyyyy";
	public static final String DATE_FORMAT_DD_MMM_YYYY = "dd-MMM-yyyy";
	public static final String DATE_FORMAT_DD_MM_YYYY = "dd/MM/yyyy";
	public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	
	private DateUtility() {

	}
	
	public static String getDateByStringFormat(Date date, String dateFromat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);
		try {
			return sdf.format(date);
		} catch (Exception e) {
			logger.error("getDateByStringFormat:Error:", e);
		}
		return "";
	}
}
