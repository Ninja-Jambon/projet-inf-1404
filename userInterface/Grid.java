package userInterface;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import java.net.URL;

public class Grid extends JPanel {
	private int width;
	private int height;
	private JButton[][] mat;
	private int selected;

	public Grid(int width, int height) {
		super(new GridLayout(height, width));
		this.width = width;
		this.height = height;
		this.selected = 0;

		this.mat = new JButton[height][width];

		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				this.mat[i][j] = new JButton();
				this.mat[i][j].setBackground(Color.WHITE);
				this.mat[i][j].setPreferredSize(new Dimension(Math.max(50, 600 / this.height), Math.max(50, (this.width * 600 / this.height) / this.width)));

				final int coord_i = i;
				final int coord_j = j;

				this.mat[i][j].addActionListener(e -> {
					System.out.println(coord_i + " " + coord_j);
				});

				super.add(this.mat[i][j]);
			}
		}
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}
}