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

public class Window extends JFrame {

	JPanel panel;
	JMenuBar menuBar;
	Grid grid;

	public Window() {
		super("Laser Finder");

		panel = new JPanel();
		panel.setPreferredSize(new Dimension(1000,800));
		panel.setBackground(Color.decode("#EBEBD3"));

		
		menuBar = new JMenuBar();
		
		JMenu fichierMenu = new JMenu("Fichier");
		JMenu aideMenu = new JMenu("Aide");
		
		ImageIcon nouveauIcon = new ImageIcon("h:\\Mes documents\\INF1404\\projet\\picture\\nouveau.png");
		ImageIcon openIcon = new ImageIcon("h:\\Mes documents\\INF1404\\projet\\picture\\ouvrir.png");
		ImageIcon saveIcon = new ImageIcon("h:\\Mes documents\\INF1404\\projet\\picture\\sauvegarder.png");

		JMenuItem nouveauItem = new JMenuItem("Nouveau", nouveauIcon);
		JMenuItem ouvrirItem = new JMenuItem("Ouvrir", openIcon);
		JMenuItem enregistrerItem = new JMenuItem("Enregistrer", saveIcon);
		
		JMenuItem apropos = new JMenuItem("à propos");
		JMenuItem regles = new JMenuItem("règles");
		
		fichierMenu.add(nouveauItem);
		fichierMenu.add(ouvrirItem);
		fichierMenu.add(enregistrerItem);
		
		aideMenu.add(apropos);
		aideMenu.add(regles);

		menuBar.add(fichierMenu);
		menuBar.add(aideMenu);
		
		
		regles.addActionListener(e -> {
			JOptionPane.showMessageDialog(this, "Définissez la taille du plateau ainsi que l'orientation du laser, enfin ajoutez des obstacles et laissez le programme trouver le bon chemin !");
		});
		
		grid = new Grid(5, 5); 
		panel.add(grid, BorderLayout.CENTER);

		super.setJMenuBar(menuBar);
		super.setContentPane(this.panel);

		super.setLocationRelativeTo(null);		
		super.setLocation(1000, 400);
		super.pack();
		super.setVisible(true);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}