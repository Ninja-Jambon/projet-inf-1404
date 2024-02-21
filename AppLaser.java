// Antoine CRETUAL, Lukian LEIZOUR, 14/02/2024

public class AppLaser {
    
    public static void main(String [] args) {
        Universe universe = new Universe(20, 10, 2, 3, 12);

        universe.addObstacle(4, 4);
        universe.addObstacle(5, 4);
        universe.addObstacle(6, 4);
        universe.addObstacle(7, 4);
        universe.addObstacle(8, 4);
        universe.addObstacle(9, 4);

        universe.print();
    }

}
