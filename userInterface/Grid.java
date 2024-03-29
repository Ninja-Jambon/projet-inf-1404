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

import java.time.Instant;

public class Grid extends JPanel {
	private int width;
	private int height;
	private JButton[][] mat;
	private int selected;
	private Universe universe;
	private int button_width, button_height;
	private int refreshRate;
	private boolean solving;

	public Grid(int width, int height, Universe universe, int refreshRate) {
		super(new GridLayout(height, width));
		this.width = width;
		this.height = height;
		this.selected = 0;
		this.universe = universe;
		this.button_width = 600 / this.height;
		this.button_height = button_width;
		this.refreshRate = refreshRate;
		this.solving = false;

		this.mat = new JButton[height][width];

		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				this.mat[i][j] = new JButton();

				this.mat[i][j].setPreferredSize(new Dimension(this.button_width, this.button_height));

				final int coord_i = i;
				final int coord_j = j;

				this.mat[i][j].addActionListener(e -> {
					if (solving) {
						return;
					}
					
					this.universe.resetUniverse();

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

					this.printUniverseGrid(this.universe.getGrid());
				});

				super.add(this.mat[i][j]);
			}
		}

		this.printUniverseGrid(this.universe.getGrid());
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}

	public void setRefreshRate(int refreshRate) {
		this.refreshRate = refreshRate;
	}

	public void setSolving(boolean solving) {
		this.solving = solving;
	}

	public void printUniverseGrid(int [][] universeGrid) {
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j ++) {
				Image photo;
				switch (universeGrid[i + 1][j + 1]) {
					case -1:
						this.changeButtonState(i, j, "../images/wall.png");
						break;
					case 0:
						this.changeButtonState(i, j, null);
						break;
					case 1: 
						this.changeButtonState(i, j, "../images/verticalLaser.png");
						break;
					case 2: 
						this.changeButtonState(i, j, "../images/horizontalLaser.png");
						break;
					case 3:
						this.changeButtonState(i, j, "../images/crossLaser.png");
						break;
					case 4:
						this.changeButtonState(i, j, "../images/miror1.png");
						break;
					case 5: 
						this.changeButtonState(i, j, "../images/miror2.png");
						break;
					case 6: 
						this.changeButtonState(i, j, "../images/miror3.png");
						break;
					case 7: 
						this.changeButtonState(i, j, "../images/miror4.png");
						break;
					case 10:
						this.changeButtonState(i, j, "../images/startUp.png");
						break;
					case 11:
						this.changeButtonState(i, j, "../images/startBot.png");
						break;
					case 12:
						this.changeButtonState(i, j, "../images/startRight.png");
						break;
					case 13:
						this.changeButtonState(i, j, "../images/startLeft.png");
						break;
				}
			}
		}
	}

	public void changeButtonState(int coord_i, int coord_j, String url) {
		if (url == null) {
			Thread t = new Thread(new Runnable() {
			    @Override
			    public void run() {
					mat[coord_i][coord_j].setBackground(Color.WHITE);
					mat[coord_i][coord_j].setIcon(null);
				}
			});

			t.start();
		} else {
			Thread t = new Thread(new Runnable() {
			    @Override
			    public void run() {
					Image image = new ImageIcon(getClass().getResource(url)).getImage().getScaledInstance(button_width, button_height, Image.SCALE_SMOOTH);
					mat[coord_i][coord_j].setIcon(new ImageIcon(image));
				}
			});

			t.start();
		}
	}

	public void reset() {
		this.universe.resetUniverse();
		this.printUniverseGrid(this.universe.getGrid());
	}

	public void alert(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	public void solve() {
		this.universe.resetUniverse();
		final Universe universe = this.universe;

		this.solving = true;

		Thread computeThread = new Thread(new Runnable() {
			int [][] bestGrid;
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

				this.bestGrid = universe.copyGrid();
				int best_filled_boxes = 0;
				int best_nb_mirrors = 0;

				long start = Instant.now().toEpochMilli();
				long lastRefresh = start;

				do {
					if (universe.canEvolve(currentState)) {
						stack.push(currentState.copy(universe.possibleChoices(currentState)));
						currentState = universe.evolve(currentState);

						if ((universe.getFilledBoxes() > best_filled_boxes) || (universe.getFilledBoxes() == best_filled_boxes && universe.getNbMirrors() < best_nb_mirrors)) {
							this.bestGrid = universe.copyGrid();
							best_filled_boxes = universe.getFilledBoxes();
							best_nb_mirrors = universe.getNbMirrors();
						}
					}
					else if (stack.size() > 0) {
						currentState = stack.pop();
						universe.reset(currentState);
					} else {
						break;
					}
					if (Instant.now().toEpochMilli() - lastRefresh > refreshRate) {
						lastRefresh = Instant.now().toEpochMilli();
						printUniverseGrid(this.bestGrid);
					}
				} while (stack.size() != 0 && solving == true);

				printUniverseGrid(bestGrid);

				solving = false;
				String message = "Solved in  " + ((Instant.now().toEpochMilli() - start)/1000) + "s and " + ((Instant.now().toEpochMilli() - start)%1000) +  "ms \nMirrors : " + best_nb_mirrors + "\nLaser length : " + best_filled_boxes;
				alert(message);
		    }
		});  
		computeThread.start();
	}
}