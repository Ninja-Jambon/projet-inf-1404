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
import universe.Stack;
import universe.Situation;

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

				this.mat[i][j].setPreferredSize(new Dimension(Math.max(50, 600 / this.height), Math.max(50, (this.width * 600 / this.height) / this.width)));

				final int coord_i = i;
				final int coord_j = j;

				this.mat[i][j].addActionListener(e -> {
					switch (this.universe.getGrid()[coord_i + 1][coord_j + 1]) {
						case 0:
							if (this.selected == 1) {
								this.universe.changeUniverseStart(coord_i + 1, coord_j + 1, 10);
								break;
							}

							this.universe.addObstacle(coord_i + 1, coord_j + 1);
							break;
						case -1:
							this.universe.removeObstacle(coord_i + 1, coord_j + 1);
							break;
						case 10:
							if (this.selected == 1) {
								this.universe.changeUniverseStart(coord_i + 1, coord_j + 1, 11);
								break;
							}
							break;
						case 11:
							if (this.selected == 1) {
								this.universe.changeUniverseStart(coord_i + 1, coord_j + 1, 12);
								break;
							}
							break;
						case 12:
							if (this.selected == 1) {
								this.universe.changeUniverseStart(coord_i + 1, coord_j + 1, 13);
								break;
							}
							break;
						case 13:
							if (this.selected == 1) {
								this.universe.changeUniverseStart(coord_i + 1, coord_j + 1, 10);
								break;
							}
							break;
					}

					this.printUniverse();
				});

				super.add(this.mat[i][j]);
			}
		}

		this.printUniverse();
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}

	public void setButtonsEnabled(boolean enabled) {
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j ++) {
				this.mat[i][j].setEnabled(enabled);
			}
		}
	}

	public void printUniverse() {
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j ++) {
				Image photo;
				switch (this.universe.getGrid()[i + 1][j + 1]) {
					case -1:
						photo = new ImageIcon(this.getClass().getResource("../images/wall.png")).getImage();
						this.mat[i][j].setIcon(new ImageIcon(photo));
						break;
					case 0:
						this.mat[i][j].setBackground(Color.WHITE);
						this.mat[i][j].setIcon(null);
						break;
					case 1: 
						photo = new ImageIcon(this.getClass().getResource("../images/verticalLaser.png")).getImage();
						this.mat[i][j].setIcon(new ImageIcon(photo));
						break;
					case 2: 
						photo = new ImageIcon(this.getClass().getResource("../images/horizontalLaser.png")).getImage();
						this.mat[i][j].setIcon(new ImageIcon(photo));
						break;
					case 3:
						photo = new ImageIcon(this.getClass().getResource("../images/wall.png")).getImage();
						this.mat[i][j].setIcon(new ImageIcon(photo));
						break;
					case 4: 
						photo = new ImageIcon(this.getClass().getResource("../images/wall.png")).getImage();
						this.mat[i][j].setIcon(new ImageIcon(photo));
						break;
					case 5: 
						photo = new ImageIcon(this.getClass().getResource("../images/wall.png")).getImage();
						this.mat[i][j].setIcon(new ImageIcon(photo));
						break;
					case 10:
						photo = new ImageIcon(this.getClass().getResource("../images/startUp.png")).getImage();
						this.mat[i][j].setIcon(new ImageIcon(photo));
						break;
					case 11:
						photo = new ImageIcon(this.getClass().getResource("../images/startBot.png")).getImage();
						this.mat[i][j].setIcon(new ImageIcon(photo));
						break;
					case 12:
						photo = new ImageIcon(this.getClass().getResource("../images/startRight.png")).getImage();
						this.mat[i][j].setIcon(new ImageIcon(photo));
						break;
					case 13:
						photo = new ImageIcon(this.getClass().getResource("../images/startLeft.png")).getImage();
						this.mat[i][j].setIcon(new ImageIcon(photo));
						break;
				}
			}
		}
	}

	public void reset() {
		this.universe.resetUniverse();
		this.printUniverse();
	}

	public void solve() {
		this.setButtonsEnabled(false);
		this.universe.resetUniverse();
		final Universe universe = this.universe;

		Thread t1 = new Thread(new Runnable() {
		    @Override
		    public void run() {
		    	int [] startCoords = universe.getStartCoords();

				int firstState_i = startCoords[0];
				int firstState_j = startCoords[1];
				int start_dir = universe.getGrid()[startCoords[0]][startCoords[1]];

				if      (start_dir == 10) firstState_i = firstState_i - 1;
				else if (start_dir == 11) firstState_i = firstState_i + 1;
				else if (start_dir == 12) firstState_j = firstState_j + 1;
				else if (start_dir == 13) firstState_j = firstState_j - 1;

				Stack <Situation> stack = new Stack <Situation>();
				Situation currentState = new Situation(firstState_i, firstState_j, start_dir, 0);

				int [][] bestGrid = universe.copyGrid();
				int best_filled_boxes = 0;
				int best_nb_mirrors = 0;

				do {
					if (universe.canEvolve(currentState)) {
						stack.push(currentState.copy(universe.possibleChoices(currentState)));
						currentState = universe.evolve(currentState);

						if ((universe.getFilledBoxes() > best_filled_boxes) || (universe.getFilledBoxes() == best_filled_boxes && universe.getNbMirrors() < best_nb_mirrors)) {
							bestGrid = universe.copyGrid();
							best_filled_boxes = universe.getFilledBoxes();
							best_nb_mirrors = universe.getNbMirrors();

							printUniverse();
						}
					}
					else if (stack.size() > 0) {
						currentState = stack.pop();
						universe.reset(currentState);
					} else {
						break;
					}
				} while (stack.size() != 0);

		    }
		});  
		t1.start();

		this.setButtonsEnabled(true);
	}
}