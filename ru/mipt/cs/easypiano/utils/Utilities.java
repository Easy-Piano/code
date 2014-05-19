package ru.mipt.cs.easypiano.utils;
//Dima

import java.net.URL;

//Class for utilities, which can help simplify the code

public class Utilities {
	//Concatination of two String Arrays
	public static String[] concatStringArray(String[] a, String[] b) {
		String[] concat = new String[a.length + b.length];
		for (int i = 0; i < a.length; i++)
			concat[i] = a[i];
		for (int i = 0; i < b.length; i++)
			concat[a.length + i] = b[i];
		return concat;
	}

	// Universal method for printing debugging messages.
	public static void showMessage(String message) {
		System.out.println(message);
	}
	
	// Universal method for printing error messages.
	public static void showMessageErr(String message)	{
		System.err.println(message);
	}

	//Returns the resource URL. It is said that by using URL, a resource
	//can be read in Java program of any form: application, applet, JAR, etc.
	public static URL getResourceURL(String fileName) {
		Utilities.showMessage("fileName filtered = " + "/" + fileName.replace('\\', '/'));
		Utilities.showMessage("URL on Utilities = " + Utilities.class.getResource("/" + fileName.replace('\\', '/')));
		return Utilities.class.getResource("/" + fileName.replace('\\', '/'));
	}
}
