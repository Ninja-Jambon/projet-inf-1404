// Antoine CRETUAL, Lukian LEIZOUR, 14/02/2024

public class Universe {
    // Atributes

    private int[][] grid;
    private int length, height;

    // Constructors

    public Universe(int length, int height) {
        this.grid = new int[height][length];
        this.height = height;
        this.length = length;
        int i, j;
        for (i = 1; i < this.height - 1; i++) {
            for (j = 1; j < this.length - 1; j++) {
                grid[i][j] = 0;
            }
        }

        for (i = 0; i < this.height; i++) {
            grid[i][0] = -1;
            grid[i][length - 1] = -1;
        }

        for (j = 0; j < this.length; j++) {
            grid[0][j] = -1;
            grid[height - 1][j] = -1;
        }
    }

    // Methods

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
