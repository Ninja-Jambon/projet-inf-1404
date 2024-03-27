package userInterface;

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

public class Window extends JFrame {

	private JPanel panel;
	private JMenuBar menuBar;
	private Grid grid;
	private Universe universe;

	public Window(Universe universe) {
		super("Laser Finder");

		this.universe = universe;

		panel = new JPanel();
		panel.setPreferredSize(new Dimension(1000,800));
		panel.setBackground(Color.decode("#EBEBD3"));

		menuBar = new JMenuBar();
		
		JMenu fichierMenu = new JMenu("File");
		JMenu aideMenu = new JMenu("Help");
		JMenu toolsMenu = new JMenu("Tools");

		URL newUrl = getClass().getResource("../images/new.png");
		URL openUrl = getClass().getResource("../images/open.png");
		URL saveUrl = getClass().getResource("../images/save.png");
		
		ImageIcon nouveauIcon = new ImageIcon(newUrl.getPath());
		ImageIcon openIcon = new ImageIcon(openUrl.getPath());
		ImageIcon saveIcon = new ImageIcon(saveUrl.getPath());

		JMenuItem nouveauItem = new JMenuItem("Nouveau", nouveauIcon);
		JMenuItem ouvrirItem = new JMenuItem("Ouvrir", openIcon);
		JMenuItem enregistrerItem = new JMenuItem("Enregistrer", saveIcon);
		
		JMenuItem apropos = new JMenuItem("About");
		JMenuItem regles = new JMenuItem("Rules");

		JRadioButtonMenuItem radioWall = new JRadioButtonMenuItem("Wall");
		JRadioButtonMenuItem radioStart = new JRadioButtonMenuItem("Start");

		radioWall.setSelected(true);
		
		ButtonGroup buttonGroup = new ButtonGroup();

		buttonGroup.add(radioWall);
		buttonGroup.add(radioStart);

		JMenuItem changeSize = new JMenuItem("Change Size"); 
		JMenuItem solve = new JMenuItem("Solve"); 
		
		fichierMenu.add(nouveauItem);
		fichierMenu.add(ouvrirItem);
		fichierMenu.add(enregistrerItem);
		
		aideMenu.add(apropos);
		aideMenu.add(regles);

		toolsMenu.add(radioWall);
		toolsMenu.add(radioStart);
		toolsMenu.addSeparator();
		toolsMenu.add(changeSize);
		toolsMenu.addSeparator();
		toolsMenu.add(solve);

		menuBar.add(fichierMenu);
		menuBar.add(aideMenu);
		menuBar.add(toolsMenu);
		
		regles.addActionListener(e -> {
			JOptionPane.showMessageDialog(this, "Définissez la taille du plateau ainsi que l'orientation du laser, enfin ajoutez des obstacles et laissez le programme trouver le bon chemin !");
		});

		changeSize.addActionListener(e -> {
			int width = Integer.valueOf(JOptionPane.showInputDialog("Choose the width"));
			int height = Integer.valueOf(JOptionPane.showInputDialog("Choose the height"));

			this.universe.changeUniverseDim(width + 2, height + 2);
			this.panel.remove(this.grid);
			this.grid = new Grid(width, height, this.universe);
			this.panel.add(this.grid);
			super.pack();
			super.repaint();
		});

		radioWall.addActionListener(e -> {
			this.grid.setSelected(1);
		});

		radioStart.addActionListener(e -> {
			this.grid.setSelected(0);
		});
		
		this.grid = new Grid(this.universe.getHeight() - 2, this.universe.getWidth() - 2, this.universe); 
		this.panel.add(grid, BorderLayout.CENTER);

		super.setJMenuBar(menuBar);
		super.setContentPane(this.panel);

		super.setLocationRelativeTo(null);		
		super.setLocation(1000, 400);
		super.pack();
		super.setVisible(true);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}