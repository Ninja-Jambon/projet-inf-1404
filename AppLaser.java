// Antoine CRETUAL, Lukian LEIZOUR, 14/02/2024

public class AppLaser {
    
    public static void main(String [] args) {
        int start_i = 3;
        int start_j = 1;
        int start_dir = 10;
        int firstState_i = 0;
        int firstState_j = 0;

        if (start_dir == 10) {
            firstState_i = start_i - 1;
            firstState_j = start_j;
        }
        else if (start_dir == 11) {
            firstState_i = start_i + 1;
            firstState_j = start_j;
        }
        else if (start_dir == 12) {
            firstState_i = start_i;
            firstState_j = start_j + 1;
        }
        else if (start_dir == 13) {
            firstState_i = start_i;
            firstState_j = start_j - 1;
        }


        Stack <Situation> stack = new Stack <Situation>(); 

        Universe universe = new Universe(8, 8, start_i, start_j, start_dir);

        Situation previousState, currentState = new Situation(firstState_i, firstState_j, start_dir, 0);

        //universe.addObstacle(4, 4);

        universe.print();

        int i = 0;
        while (!universe.isSolved()) {
            //System.out.printf("i: %d, j: %d, dir: %d, choice: %d, possible: %d\n", currentState.pos_i, currentState.pos_j, currentState.direction, currentState.nb_choix, universe.possibleChoices(currentState));

            if (universe.canEvolve(currentState)) {
                stack.push(currentState.copy(universe.possibleChoices(currentState)));
                currentState = universe.evolve(currentState);
            }
            else if (stack.size() > 0) {
                currentState = stack.pop();
                universe.reset(currentState);

                //System.out.println("\n\n");
                //universe.print();
            } else {
                break;
            }
        }

        System.out.println("\n\n");
        universe.print();
    }
}
