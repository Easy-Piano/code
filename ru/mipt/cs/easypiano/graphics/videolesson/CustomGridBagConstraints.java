package ru.mipt.cs.easypiano.graphics.videolesson;

import java.awt.*;


public class CustomGridBagConstraints extends GridBagConstraints {

    public CustomGridBagConstraints() {
        super();
    }

    public CustomGridBagConstraints(int gridx, int gridy) {
        super();
        this.gridx = gridx;
        this.gridy = gridy;
        this.anchor = GridBagConstraints.FIRST_LINE_START;
    }

    public CustomGridBagConstraints(int gridx, int gridy, int anchor) {
        this(gridx, gridy);
        this.anchor = anchor;
    }

    public CustomGridBagConstraints(int gridx, int gridy, int gridwidth,
                                    int gridheight) {
        this(gridx, gridy);
        this.gridwidth = gridwidth;
        this.gridheight = gridheight;
    }

    public CustomGridBagConstraints(int gridx, int gridy, int gridwidth,
                                    int gridheight, int anchor) {
        this(gridx, gridy, gridwidth, gridheight);
        this.anchor = anchor;
    }

    public CustomGridBagConstraints(int gridx, int gridy, int gridwidth,
                                    int gridheight, double weightx, double weighty, int anchor,
                                    int fill, Insets insets, int ipadx, int ipady) {
        super(gridx, gridy, gridwidth, gridheight, weightx, weighty, anchor,
                fill, insets, ipadx, ipady);
    }

}