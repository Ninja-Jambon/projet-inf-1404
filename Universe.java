// Antoine CRETUAL, Lukian LEIZOUR, 14/02/2024

public class Universe {
    // Atributes

    private int[][] grid;
    private int length, height;

    // Constructors

    public Universe(int length, int height, int i_start, int j_start, int dir_start) {
        this.grid = new int[height][length];
        this.height = height;
        this.length = length;
        int i, j;
        for (i = 1; i < this.height - 1; i++) {
            for (j = 1; j < this.length - 1; j++) {
                this.grid[i][j] = 0;
            }
        }

        for (i = 0; i < this.height; i++) {
            this.grid[i][0] = -1;
            this.grid[i][length - 1] = -1;
        }

        for (j = 0; j < this.length; j++) {
            this.grid[0][j] = -1;
            this.grid[height - 1][j] = -1;
        }

        this.grid[i_start][j_start] = dir_start;
    }

    // Methods

    public void addObstacle(int pos_i, int pos_j) {
        if (this.grid[pos_i][pos_j] == 0) {
            this.grid[pos_i][pos_j] = -1;
        }
        else {}
    }

    public void print() {
        int i, j;
        for (i = 0; i < this.height; i++) {
            for (j = 0; j < this.length; j++) {
                System.out.printf("%3d", this.grid[i][j]);
            }
            System.out.print("\n");
        }
    }
}
