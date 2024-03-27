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

import universe.Universe;

public class Grid extends JPanel {
	private int width;
	private int height;
	private JButton[][] mat;
	private int selected;
	private Universe universe;

	public Grid(int width, int height, Universe universe) {
		super(new GridLayout(height, width));
		this.width = width;
		this.height = height;
		this.selected = 0;
		this.universe = universe;

		this.mat = new JButton[height][width];

		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				this.mat[i][j] = new JButton();
				switch (this.universe.getGrid()[i + 1][j + 1]) {
					case -1:
						this.mat[i][j].setBackground(Color.RED);
						break;
					case 0:
						this.mat[i][j].setBackground(Color.WHITE);
						break;
				
				}
				this.mat[i][j].setPreferredSize(new Dimension(Math.max(50, 600 / this.height), Math.max(50, (this.width * 600 / this.height) / this.width)));

				final int coord_i = i;
				final int coord_j = j;
				final int final_selected = this.selected;
				final Universe tempUniverse = this.universe;

				this.mat[i][j].addActionListener(e -> {
					switch (this.universe.getGrid()[coord_i + 1][coord_j + 1]) {
						case 0:
							this.mat[coord_i][coord_j].setBackground(Color.RED);
							this.universe.addObstacle(coord_i + 1, coord_j + 1);
							break;
						case -1:
							this.mat[coord_i][coord_j].setBackground(Color.WHITE);
							this.universe.removeObstacle(coord_i + 1, coord_j + 1);
							break;
					}

					//Universe.print(this.universe.getGrid(), this.width + 2, this.height + 2, 4, 4);
				});

				super.add(this.mat[i][j]);
			}
		}
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}
}