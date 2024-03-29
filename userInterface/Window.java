package userInterface;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.Collectors;

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
		JMenu solveMenu = new JMenu("Solve");
		JMenu displayMenu = new JMenu("Display");
		JMenu refreshRate = new JMenu("Refresh Rate");

		URL newUrl = getClass().getResource("../images/new.png");
		URL openUrl = getClass().getResource("../images/open.png");
		URL saveUrl = getClass().getResource("../images/save.png");
		
		ImageIcon nouveauIcon = new ImageIcon(newUrl.getPath());
		ImageIcon openIcon = new ImageIcon(openUrl.getPath());
		ImageIcon saveIcon = new ImageIcon(saveUrl.getPath());

		JMenuItem nouveauItem = new JMenuItem("New", nouveauIcon);
		JMenuItem ouvrirItem = new JMenuItem("Open", openIcon);
		JMenuItem enregistrerItem = new JMenuItem("Save", saveIcon);
		
		JMenuItem apropos = new JMenuItem("About");
		JMenuItem regles = new JMenuItem("Rules");

		JRadioButtonMenuItem radioWall = new JRadioButtonMenuItem("Wall");
		JRadioButtonMenuItem radioStart = new JRadioButtonMenuItem("Start");

		radioWall.setSelected(true);
		
		ButtonGroup buttonGroup = new ButtonGroup();

		buttonGroup.add(radioWall);
		buttonGroup.add(radioStart);

		JMenuItem changeSize = new JMenuItem("Change Size");
		JMenuItem reset = new JMenuItem("Reset");

		JMenuItem solve = new JMenuItem("Start");
		JMenuItem stop = new JMenuItem("Stop");

		JRadioButtonMenuItem radio10ms = new JRadioButtonMenuItem("10ms");
		JRadioButtonMenuItem radio200ms = new JRadioButtonMenuItem("200ms");
		JRadioButtonMenuItem radio500ms = new JRadioButtonMenuItem("500ms");
		JRadioButtonMenuItem radio1000ms = new JRadioButtonMenuItem("1000ms");

		radio10ms.setSelected(true);

		ButtonGroup refreshRates = new ButtonGroup();

		refreshRates.add(radio10ms);
		refreshRates.add(radio200ms);
		refreshRates.add(radio500ms);
		refreshRates.add(radio1000ms);

		JMenuItem displayAll = new JMenuItem("Display progress and regress");
		JMenuItem displayProgress = new JMenuItem("Display only progress");
		JMenuItem displayBest = new JMenuItem("Display best grid");
		
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
		toolsMenu.add(reset);

		solveMenu.add(solve);
		solveMenu.add(stop);

		refreshRate.add(radio10ms);
		refreshRate.add(radio200ms);
		refreshRate.add(radio500ms);
		refreshRate.add(radio1000ms);

		displayMenu.add(refreshRate);
		displayMenu.addSeparator();
		displayMenu.add(displayAll);
		displayMenu.add(displayProgress);
		displayMenu.add(displayBest);

		menuBar.add(fichierMenu);
		menuBar.add(aideMenu);
		menuBar.add(toolsMenu);
		menuBar.add(solveMenu);
		menuBar.add(displayMenu);

		nouveauItem.addActionListener(e -> {
			this.universe.changeUniverseStart(1, 1, 11);
			this.universe.changeUniverseDim(5, 5);
			this.universe.resetUniverseObstacles();
			this.panel.remove(this.grid);
			this.grid = new Grid(3, 3, this.universe, 10, 0);
			this.panel.add(this.grid);
			super.pack();
			super.repaint();
		});

		enregistrerItem.addActionListener(e -> {
			String name = JOptionPane.showInputDialog("Choose the universe name");
			this.universe.save(name);
		});

		ouvrirItem.addActionListener(e -> {
			String message = "Choose the universe among those : ";

			Set<String> files = Stream.of(new File("./saves").listFiles()).filter(file -> !file.isDirectory()).map(File::getName).collect(Collectors.toSet());

			for (String element : files) {
				message += "\n- " + element.replace(".txt", "");
			}

			String name = JOptionPane.showInputDialog(message);

			try {
				BufferedReader reader = new BufferedReader(new FileReader("./saves/" + name + ".txt"));
				int universe_height = Integer.valueOf(reader.readLine());
				int universe_width = Integer.valueOf(reader.readLine());
				int start_dir = Integer.valueOf(reader.readLine());
				int start_i = Integer.valueOf(reader.readLine());
				int start_j = Integer.valueOf(reader.readLine());


				this.universe.changeUniverseDim(universe_width + 2, universe_height + 2);
				this.universe.changeUniverseStart(start_i, start_j, start_dir);
				this.universe.resetUniverseObstacles();

				while (true) {
					try {
						int pos1 = Integer.valueOf(reader.readLine());
						int pos2 = Integer.valueOf(reader.readLine());

						this.universe.addObstacle(pos1, pos2);
					} 
					catch (Exception error) {
						break;
					}
				}

				this.panel.remove(this.grid);
				this.grid = new Grid(universe_width, universe_height, this.universe, 10, 0);
				this.panel.add(this.grid);
				super.pack();
				super.repaint();
			}
			catch (Exception error) {}
		});
		
		regles.addActionListener(e -> {
			JOptionPane.showMessageDialog(this, "Définissez la taille du plateau ainsi que l'orientation du laser, enfin ajoutez des obstacles et laissez le programme trouver le bon chemin !");
		});

		changeSize.addActionListener(e -> {
			int width = Integer.valueOf(JOptionPane.showInputDialog("Choose the width"));
			int height = Integer.valueOf(JOptionPane.showInputDialog("Choose the height"));

			this.universe.changeUniverseDim(width + 2, height + 2);
			this.panel.remove(this.grid);
			this.grid = new Grid(width, height, this.universe, 10, 0);
			this.panel.add(this.grid);
			super.pack();
			super.repaint();
		});

		radioWall.addActionListener(e -> {
			this.grid.setSelected(0);
		});

		radioStart.addActionListener(e -> {
			this.grid.setSelected(1);
		});

		solve.addActionListener(e -> {
			this.grid.solve();
		});

		stop.addActionListener(e -> {
			this.grid.setSolving(false);
		});

		reset.addActionListener(e -> {
			this.grid.reset();
		});

		radio10ms.addActionListener(e -> {
			this.grid.setRefreshRate(10);
		});

		radio200ms.addActionListener(e -> {
			this.grid.setRefreshRate(200);
		});

		radio500ms.addActionListener(e -> {
			this.grid.setRefreshRate(500);
		});

		radio1000ms.addActionListener(e -> {
			this.grid.setRefreshRate(1000);
		});

		displayAll.addActionListener(e -> {
			this.grid.setDisplay(0);
		});

		displayProgress.addActionListener(e -> {
			this.grid.setDisplay(1);
		});

		displayBest.addActionListener(e -> {
			this.grid.setDisplay(2);
		});
		
		this.grid = new Grid(this.universe.getHeight() - 2, this.universe.getWidth() - 2, this.universe, 10, 0); 
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