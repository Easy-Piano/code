package ru.mipt.cs.easypiano.piano;

// Dima

import java.awt.*;

public class CustomGridBagConstraints extends GridBagConstraints {

	public CustomGridBagConstraints(int gridx, int gridy) {
		super();
		this.gridx = gridx;
		this.gridy = gridy;
		this.anchor = GridBagConstraints.FIRST_LINE_START;
	}
}
