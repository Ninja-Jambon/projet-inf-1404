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
	private int rows;
	private int cols;
	private Color[][] colors;
	private MouseAdapter mouseListener;

	public Grid(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		this.setPreferredSize(new Dimension(400, 400));
		this.setBackground(Color.WHITE);

		colors = new Color[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				colors[i][j] = Color.WHITE;
			}
		}

		mouseListener = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				int cellWidth = getWidth() / cols;
				int cellHeight = getHeight() / rows;
				int col = evt.getX() / cellWidth;
				int row = evt.getY() / cellHeight;

				if (row >= 0 && row < rows && col >= 0 && col < cols) {
					if (colors[row][col] == Color.WHITE) {
						colors[row][col] = Color.GRAY;
					} else {
						colors[row][col] = Color.WHITE;
					}
					repaint();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				try {
					// Charger l'image pour le curseur personnalisé
					URL url = getClass().getResource("../images/blade.png");
					BufferedImage cursorImage = ImageIO.read(new File(url.getPath()));
					// Créer le curseur personnalisé
					Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0), "customCursor");
					// Définir le curseur personnalisé
					setCursor(customCursor);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(Cursor.getDefaultCursor()); // Rétablir l'icône de la souris par défaut quand elle quitte le composant
			}
		};

		this.addMouseListener(this.mouseListener);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int cellWidth = this.getWidth() / this.cols;
		int cellHeight = this.getHeight() / this.rows;

		// Dessiner les cases avec les couleurs stockées
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				g.setColor(colors[i][j]);
				g.fillRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
			}
		}

		// Dessiner les lignes de la grille
		g.setColor(Color.BLACK);
		for (int i = 0; i <= rows; i++) {
			g.drawLine(0, i * cellHeight, getWidth(), i * cellHeight);
		}
		for (int j = 0; j <= cols; j++) {
			g.drawLine(j * cellWidth, 0, j * cellWidth, getHeight());
		}
	}

	public void changeDim(int width, int height) {
		this.rows = height;
		this.cols = width;
		this.setPreferredSize(new Dimension(700, 400));
		this.setBackground(Color.WHITE);

		this.colors = new Color[height][width];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				colors[i][j] = Color.WHITE;
			}
		}

		this.removeMouseListener(this.mouseListener);

		this.mouseListener = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				int cellWidth = getWidth() / cols;
				int cellHeight = getHeight() / rows;
				int col = evt.getX() / cellWidth;
				int row = evt.getY() / cellHeight;

				if (row >= 0 && row < rows && col >= 0 && col < cols) {
					if (colors[row][col] == Color.WHITE) {
						colors[row][col] = Color.GRAY;
					} else {
						colors[row][col] = Color.WHITE;
					}
					repaint();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				try {
					// Charger l'image pour le curseur personnalisé
					URL url = getClass().getResource("../images/blade.png");
					BufferedImage cursorImage = ImageIO.read(new File(url.getPath()));
					// Créer le curseur personnalisé
					Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0), "customCursor");
					// Définir le curseur personnalisé
					setCursor(customCursor);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(Cursor.getDefaultCursor()); // Rétablir l'icône de la souris par défaut quand elle quitte le composant
			}
		};

		this.addMouseListener(this.mouseListener);
	}
}