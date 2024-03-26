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

public class Grid extends JPanel {
	private int rows;
	private int cols;
	private Color[][] colors;

	public Grid(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		setPreferredSize(new Dimension(400, 400));
		setBackground(Color.WHITE);

		colors = new Color[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				colors[i][j] = Color.WHITE;
			}
		}

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				int cellWidth = getWidth() / cols;
				int cellHeight = getHeight() / rows;
				int col = evt.getX() / cellWidth;
				int row = evt.getY() / cellHeight;

				if (row >= 0 && row < rows && col >= 0 && col < cols) {
					if (colors[row][col] == Color.WHITE) {
						colors[row][col] = Color.RED;
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
					BufferedImage cursorImage = ImageIO.read(new File("h:\\Mes documents\\INF1404\\projet\\picture\\sabre.png"));
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
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int cellWidth = getWidth() / cols;
		int cellHeight = getHeight() / rows;

		// Dessiner les cases avec les couleurs stockées
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
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
}