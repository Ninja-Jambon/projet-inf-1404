// Antoine CRETUAL, Lukian LEIZOUR, 14/02/2024

package universe;

import java.io.File;
import java.io.FileWriter;

public class Universe {
	// Atributes

	private int[][] grid;
	private int width, height;
	private int start_i, start_j;
	private int filled_boxes;
	private int nb_mirors;
	private String name;

	// Constructors

	public Universe(int width, int height, int i_start, int j_start, int dir_start) {
		this.grid = new int[height][width];
		this.height = height;
		this.width = width;
		this.start_i = i_start;
		this.start_j = j_start;
		this.filled_boxes = 0;
		this.nb_mirors = 0;

		int i, j;
		for (i = 1; i < this.height - 1; i++) {
			for (j = 1; j < this.width - 1; j++) {
				this.grid[i][j] = 0;
			}
		}

		for (i = 0; i < this.height; i++) {
			this.grid[i][0] = -1;
			this.grid[i][width - 1] = -1;
		}

		for (j = 0; j < this.width; j++) {
			this.grid[0][j] = -1;
			this.grid[height - 1][j] = -1;
		}

		this.grid[i_start][j_start] = dir_start;
	}

	// Methods

	public static void print(int [][] tab, int width, int height, int pos_i, int pos_j) {
		int i, j;
		for (i = 0; i < height; i++) {
			for (j = 0; j < width; j++) {
				System.out.print("\033[" + (i + pos_i) + ";" + (j*2 + pos_j) + "H");
				switch (tab[i][j]) {
					case -1: 
						System.out.printf(" X");
						break;

					case 0: 
						System.out.printf("  ");
						break;

					case 1: 
						System.out.printf(" |");
						break;

					case 2: 
						System.out.printf(" -");
						break;

					case 3:
						System.out.printf(" +");
						break;

					case 4: 
						System.out.printf(" /");
						break;

					case 5: 
						System.out.printf(" /");
						break;

					case 6: 
						System.out.printf(" \\");
						break;

					case 7: 
						System.out.printf(" \\");
						break;

					case 10: 
						System.out.printf(" ^");
						break;

					case 11: 
						System.out.printf(" v");
						break;

					case 12: 
						System.out.printf(" >");
						break;

					case 13: 
						System.out.printf(" <");
						break;

					default:
						System.out.printf("%2d", tab[i][j]);
				}
			}
			System.out.print("\n");
		}
	}

	public void resetUniverse() {
		for (int i = 1; i < this.height - 1; i++) {
			for (int j = 1; j < this.width - 1; j++) {
				if (this.grid[i][j] != 10 && this.grid[i][j] != 11 && this.grid[i][j] != 12 && this.grid[i][j] != 13 && this.grid[i][j] != 0 && this.grid[i][j] != -1) {
					this.grid[i][j] = 0;
				}
			}
		}
	}

	public void resetUniverseObstacles() {
		for (int i = 1; i < this.height - 1; i++) {
			for (int j = 1; j < this.width - 1; j++) {
				if (this.grid[i][j] != 10 && this.grid[i][j] != 11 && this.grid[i][j] != 12 && this.grid[i][j] != 13 && this.grid[i][j] != 0) {
					this.grid[i][j] = 0;
				}
			}
		}
	}

	public void changeUniverseDim(int width, int height) {
		int [][] newgrid = new int[height][width];

		for (int i = 1; i < height - 1; i++) {
			for (int j = 1; j < width - 1; j++) {
				newgrid[i][j] = 0;
			}
		}

		for (int i = 1; i < height - 1 && i < this.height - 1; i++) {
			for (int j = 1; j < width - 1 && j < this.width - 1; j++) {
				newgrid[i][j] = this.grid[i][j];
			}
		}

		for (int i = 0; i < height; i++) {
			newgrid[i][0] = -1;
			newgrid[i][width - 1] = -1;
		}

		for (int j = 0; j < width; j++) {
			newgrid[height - 1][j] = -1;
			newgrid[0][j] = -1;
		}

		this.grid = newgrid;
		this.width = width;
		this.height = height;
	}

	public void changeUniverseStart(int pos_i, int pos_j, int dir) {
		this.grid[this.start_i][start_j] = 0;
		this.grid[pos_i][pos_j] = dir;

		this.start_i = pos_i;
		this.start_j = pos_j;
	}

	public void addObstacle(int pos_i, int pos_j) {
		if (this.grid[pos_i][pos_j] == 0) {
			this.grid[pos_i][pos_j] = -1;
		}
		else {}
	}

	public void removeObstacle(int pos_i, int pos_j) {
		if (this.grid[pos_i][pos_j] == -1) {
			this.grid[pos_i][pos_j] = 0;
		}
		else {}
	}

	public int possibleChoices(Situation s) {
		int i = s.pos_i;
		int j = s.pos_j;
		int d = s.direction;
		int c = s.nb_choix;

		switch (c) {
			case 0: {
				switch (d) {
					case 10:  // north
						if (this.grid[i - 1][j] == 0 || this.grid[i - 1][j] == 2)  return 1;  // front
						else if (this.grid[i][j - 1] == 0 && this.grid[i][j] == 0) return 2;  // left
						else if (this.grid[i][j + 1] == 0 && this.grid[i][j] == 0) return 3;  // right
						else                                                       return -1; // back

					case 11:  // south
						if (this.grid[i + 1][j] == 0 || this.grid[i + 1][j] == 2)  return 1;  // front
						else if (this.grid[i][j + 1] == 0 && this.grid[i][j] == 0) return 2;  // left
						else if (this.grid[i][j - 1] == 0 && this.grid[i][j] == 0) return 3;  // right
						else                                                       return -1; // back

					case 12:  // east
						if (this.grid[i][j + 1] == 0 || this.grid[i][j + 1] == 1)  return 1;  // front
						else if (this.grid[i - 1][j] == 0 && this.grid[i][j] == 0) return 2;  // left
						else if (this.grid[i + 1][j] == 0 && this.grid[i][j] == 0) return 3;  // right
						else                                                       return -1; // back

					case 13:  //west
						if (this.grid[i][j - 1] == 0 || this.grid[i][j - 1] == 1)  return 1;  // front
						else if (this.grid[i + 1][j] == 0 && this.grid[i][j] == 0) return 2;  // left
						else if (this.grid[i - 1][j] == 0 && this.grid[i][j] == 0) return 3;  // right
						else                                                       return -1; // back

					default: {}
				}
			}
			case 1: {
				switch (d) {
					case 10:  // north
						if (this.grid[i][j - 1] == 0 && this.grid[i][j] == 0)      return 2;  // left
						else if (this.grid[i][j + 1] == 0 && this.grid[i][j] == 0) return 3;  // right
						else                                                       return -1; // back

					case 11:  // south
						if (this.grid[i][j + 1] == 0 && this.grid[i][j] == 0)      return 2;  // left
						else if (this.grid[i][j - 1] == 0 && this.grid[i][j] == 0) return 3;  // right
						else                                                       return -1; // back

					case 12:  // east
						if (this.grid[i - 1][j] == 0 && this.grid[i][j] == 0)      return 2;  // left
						else if (this.grid[i + 1][j] == 0 && this.grid[i][j] == 0) return 3;  // right
						else                                                       return -1; // back

					case 13:  //west
						if (this.grid[i + 1][j] == 0 && this.grid[i][j] == 0)      return 2;  // left
						else if (this.grid[i - 1][j] == 0 && this.grid[i][j] == 0) return 3;  // right
						else                                                       return -1; // back

					default: {}
				}
			}
			case 2: {
				switch (d) {
					case 10:  // north
						if (this.grid[i][j + 1] == 0 && this.grid[i][j] == 0)      return 3;  // right
						else                                                       return -1; // back

					case 11:  // south
						if (this.grid[i][j - 1] == 0 && this.grid[i][j] == 0)      return 3;  // right
						else                                                       return -1; // back

					case 12:  // east
						if (this.grid[i + 1][j] == 0 && this.grid[i][j] == 0)      return 3;  // right
						else                                                       return -1; // back

					case 13:  //west
						if (this.grid[i - 1][j] == 0 && this.grid[i][j] == 0)      return 3;  // right
						else                                                       return -1; // back

					default: {}
				}
			}
			case 3:
				return -1; 

			default: return -1;
		}
	}

	public boolean canEvolve(Situation s) {
		return possibleChoices(s) != -1; 
	}

	public Situation evolve(Situation s) {
		int i = s.pos_i;
		int j = s.pos_j;
		int d = s.direction;
		int c = possibleChoices(s);

		// new status of the box

		if (c == 1 && (d == 10 || d == 11)) {
			if (this.grid[i][j] == 0) {
				this.grid[i][j] = 1;
				this.filled_boxes ++;
			} else {
				this.grid[i][j] = 3;
			}
		}
		if (c == 1 && (d == 12 || d == 13)) {
			if (this.grid[i][j] == 0) {
				this.grid[i][j] = 2;
				this.filled_boxes ++;
			} else {
				this.grid[i][j] = 3;
			}
		}
		if ((c == 3 && d == 10) || (c == 2 && d == 13)) {
			this.grid[i][j] = 4;
			this.filled_boxes ++;
			this.nb_mirors ++;
		}
		if ((c == 2 && d == 12) || (c == 3 && d == 11)) {
			this.grid[i][j] = 5;
			this.filled_boxes ++;
			this.nb_mirors ++;
		}
		if ((c == 2 && d == 10) || (c == 3 && d == 12)) {
			this.grid[i][j] = 6;
			this.filled_boxes ++;
			this.nb_mirors ++;
		}
		if ((c == 2 && d == 11) || (c == 3 && d == 13)) {
			this.grid[i][j] = 7;
			this.filled_boxes ++;
			this.nb_mirors ++;
		}

		// changing the position of the situation

		if (c == 1 && d == 10 || c == 2 && d == 12 || c == 3 && d == 13) {
			i --;
			d = 10;
		}
		else if (c == 1 && d == 11 || c == 2 && d == 13 || c == 3 && d == 12) {
			i ++;
			d = 11;
		}
		else if (c == 1 && d == 12 || c == 2 && d == 11 || c == 3 && d == 10) {
			j ++;
			d = 12;
		}
		else if (c == 1 && d == 13 || c == 2 && d == 10 || c == 3 && d == 11) {
			j --;
			d = 13;
		}

		return new Situation(i, j, d, 0);
	}

	public void reset(Situation s) {
		int i = s.pos_i;
		int j = s.pos_j;
		int d = s.direction;

		if (this.grid[i][j] == 3) {
			if (d == 10 || d == 11) {
				this.grid[i][j] = 2;
			} else {
				this.grid[i][j] = 1;
			}
		} else {
			if (this.grid[i][j] == 4 || this.grid[i][j] == 5 || this.grid[i][j] == 6 || this.grid[i][j] == 7) {this.nb_mirors--;}
			this.filled_boxes--;
			this.grid[i][j] = 0;
		}
	}

	public void save(String fileName) {
		try {
			File file = new File("./saves/" + fileName + ".txt");

			file.createNewFile();

			FileWriter writer = new FileWriter("./saves/" + fileName + ".txt");
			writer.write((this.height - 2) + "\n" + (this.width - 2) + "\n");

			for (int i = 1; i < this.height - 1; i++) {
				for (int j = 1; j < this.width - 1; j++) {
					if (this.grid[i][j] == 10 || this.grid[i][j] == 11 || this.grid[i][j] == 12 || this.grid[i][j] == 13) writer.write(this.grid[i][j] + "\n" + i + "\n" + j + "\n");
				}
			}

			for (int i = 1; i < this.height - 1; i++) {
				for (int j = 1; j < this.width - 1; j++) {
					if (this.grid[i][j] == -1) writer.write(i + "\n" + j + "\n");
				}
			}

			writer.close();
		}
		catch (Exception e) {}
	}

	public int getFilledBoxes() {
		return this.filled_boxes;
	}

	public int getNbMirrors() {
		return this.nb_mirors;
	}

	public int[][] getGrid() {
		return this.grid;
	}

	public int[][] copyGrid() {
		int [][] newGrid = new int[this.height][this.width];

		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				newGrid[i][j] = this.grid[i][j];
			}
		} 

		return newGrid;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public int[] getStartCoords() {
		int [] tab = new int[2];
		tab[0] = this.start_i;
		tab[1] = this.start_j;
		return tab;
	}
}
