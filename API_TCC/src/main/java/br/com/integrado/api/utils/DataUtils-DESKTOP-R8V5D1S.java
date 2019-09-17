package br.com.integrado.api.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtils {
	
	private SimpleDateFormat formatter;
	
	public String DataHoraParaStringSpring(Date data) {
		try {
			this.formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			return this.formatter.format(data);
		} catch (Exception e) {
			System.out.println("Data Inválida - " + e.getMessage());
			return null;
		}
	}

	public Date StringParaDataHoraSpring(String string) {
		try {
			string.replace('-', '/');
			this.formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			return this.formatter.parse(string);
		} catch (Exception e) {
			System.out.println("Data Inválida - " + e.getMessage());
			return null;
		}
	}
	
	public String DataParaStringSpring(Date data) {
		try {
			this.formatter = new SimpleDateFormat("yyyy/MM/dd");
			return this.formatter.format(data);
		} catch (Exception e) {
			System.out.println("Data Inválida - " + e.getMessage());
			return null;
		}
	}

	public Date StringParaDataSpring(String string) {
		try {
			string = string.replace('-', '/');
			this.formatter = new SimpleDateFormat("yyyy/MM/dd");
			return this.formatter.parse(string);
		} catch (Exception e) {
			System.out.println("Data Inválida - " + e.getMessage());
			return null;
		}
	}
		
}
