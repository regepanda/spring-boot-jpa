package com.jpa.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 接受参数的手转化时间
 * @author lili
 *
 */
@ControllerAdvice
public class ConverHandle {
	private class Str2DateConverter implements Converter<String, Date> {
		
		private List<DateFormat> dateFormats = new ArrayList<DateFormat>();
		
		public Str2DateConverter() {
			dateFormats.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
			dateFormats.add(new SimpleDateFormat("yyyy-MM-dd"));
			dateFormats.add(new SimpleDateFormat("yyyy/MM/dd"));
		}

		@Override
		public Date convert(String source) {
			for (DateFormat dateFormat : this.dateFormats) {
				try {
					return dateFormat.parse(source);
				} catch (ParseException e) {
					continue;
				}
			}
			return null;
		}
		
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		GenericConversionService genericConversionService = (GenericConversionService) binder.getConversionService();
		if (genericConversionService != null) {
			genericConversionService.addConverter(new Str2DateConverter());;
		}
	}
}
