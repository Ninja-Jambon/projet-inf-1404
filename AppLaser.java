// Antoine CRETUAL, Lukian LEIZOUR, 14/02/2024

public class AppLaser {
    
    public static void main(String [] args) {
        int start_i = 4;
        int start_j = 1;
        int start_dir = 12;

        Stack <Situation> stack = new Stack <Situation>(); 

        Universe universe = new Universe(6, 6, start_i, start_j, start_dir);

        Situation previousState, currentState = new Situation(start_i, start_j, start_dir, 0);

        //universe.addObstacle(4, 4);

        universe.print();

        do {
            if (universe.canEvolve(currentState)) {
                stack.push(currentState.copy(universe.possibleChoices(currentState)));
                currentState = universe.evolve(currentState);
            }
            else {
                currentState = stack.pop();
            }

            universe.print();
        } while (stack.size() != 0);
    }

}
