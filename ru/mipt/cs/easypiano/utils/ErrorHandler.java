package ru.mipt.cs.easypiano.utils;
//Dima

import javax.swing.JOptionPane;

public class ErrorHandler {
	//Display an error message
	public static void display(String message) {
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
