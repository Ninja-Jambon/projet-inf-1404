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

        int i = 0;

        do {
            System.out.printf("%d %d %d %d %d", currentState.pos_i, currentState.pos_j, currentState.direction, currentState.nb_choix, universe.possibleChoices(currentState));

            if (universe.canEvolve(currentState)) {
                stack.push(currentState.copy(universe.possibleChoices(currentState)));
                currentState = universe.evolve(currentState);
            }
            else {
                currentState = stack.pop();
                universe.reset(currentState);

                System.out.println("\n\n");
                universe.print();
            }


        } while (!universe.isSolved());
    }

}
