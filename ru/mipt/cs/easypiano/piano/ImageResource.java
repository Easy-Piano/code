package ru.mipt.cs.easypiano.piano;

// Dima
// Encapsulates an image.  Contains a factory getter method to get an image resource.
// Implements factory pattern.

import ru.mipt.cs.easypiano.utils.*;

import javax.swing.*;
import java.awt.*;

public class ImageResource {
	// identifier for stored resources
	public static final int PEDAL_UP = 0;
	public static final int PEDAL_DOWN = 1;
    public static final int BLACK_KEY_UP = 2;
    public static final int BLACK_KEY_DOWN = 3;
    public static final int WHITE_KEY_CENTRAL_UP = 4;
    public static final int WHITE_KEY_CENTRAL_DOWN = 5;
    public static final int WHITE_KEY_RIGHT_UP = 6;
    public static final int WHITE_KEY_RIGHT_DOWN = 7;
    public static final int WHITE_KEY_LEFT_UP = 8;
    public static final int WHITE_KEY_LEFT_DOWN = 9;

	// factory
	private static ImageResource[] imageResources;
	
	private String fileName;
	private Image image;
	private int width;
	private int height;

    // constructor
    private ImageResource(String fileName) {
        this.fileName = fileName;
        this.image = new ImageIcon(Utilities.getResourceURL(fileName)).getImage();
        populateDimension();
    }

    // initialize factory
    public static void initFactory() {
        imageResources = new ImageResource[] {
                new ImageResource("ru/mipt/cs/easypiano/resources/images/pedal_up.gif"),
                new ImageResource("ru/mipt/cs/easypiano/resources/images/pedal_down.gif"),
                new ImageResource("ru/mipt/cs/easypiano/resources/images/black_key_up.png"),
                new ImageResource("ru/mipt/cs/easypiano/resources/images/black_key_down.png"),
                new ImageResource("ru/mipt/cs/easypiano/resources/images/white_key_central_up.png"),
                new ImageResource("ru/mipt/cs/easypiano/resources/images/white_key_central_down.png"),
                new ImageResource("ru/mipt/cs/easypiano/resources/images/white_key_right_up.png"),
                new ImageResource("ru/mipt/cs/easypiano/resources/images/white_key_right_down.png"),
                new ImageResource("ru/mipt/cs/easypiano/resources/images/white_key_left_up.png"),
                new ImageResource("ru/mipt/cs/easypiano/resources/images/white_key_left_down.png"),
        };
    }
	
	// returns ImageResource resource according to id
	public static ImageResource getInstance(int id) {
		// TODO: assumed no array index error
		return ImageResource.imageResources[id];		
	}

	public static Image getImage(int id) {
		ImageResource ir = ImageResource.getInstance(id);
		return ir == null ? null : ir.getImage();
	}

	private void populateDimension() {
		width = image.getWidth(null);
		height = image.getHeight(null);
	}

	// Getters

	public Image getImage() {
		return image;
	}

	public String getFileName() {
		return fileName;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
