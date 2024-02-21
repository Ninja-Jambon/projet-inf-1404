// Antoine CRETUAL, Lukian LEIZOUR, 14/02/2024

public class AppLaser {
    
    public static void main(String [] args) {
        int start_i = 4;
        int start_j = 1;
        int start_dir = 12;

        Universe universe = new Universe(8, 8, start_i, start_j, start_dir);

        Situation previousState, currentState = new Situation(start_i, start_j, start_dir, 0);

        universe.addObstacle(4, 4);

        universe.print();
    }

}
