// Antoine CRETUAL, Lukian LEIZOUR, 14/02/2024

import java.util.Scanner;

public class AppLaser {
    public static void main(String [] args) {

        boolean cli = false;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-cli":
                    cli = true;
                    break;

                default:
                    System.out.println("Unknown argument " + args[i]);
                    System.exit(0);
            }
        }

        int universe_width    = 3;
        int universe_height   = 3;
        int start_i           = 0;
        int start_j           = 0;
        int start_dir         = 11;
        int firstState_i      = start_i;
        int firstState_j      = start_j;

        if      (start_dir == 10) firstState_i = start_i - 1;
        else if (start_dir == 11) firstState_i = start_i + 1;
        else if (start_dir == 12) firstState_j = start_j + 1;
        else if (start_dir == 13) firstState_j = start_j - 1;

        if (cli == true) {

            Stack <Situation> stack = new Stack <Situation>(); 
            Universe universe = new Universe(universe_width + 2, universe_height + 2, start_i + 1, start_j + 1, start_dir);
            Situation currentState = new Situation(firstState_i + 1, firstState_j + 1, start_dir, 0);

            Scanner scanner = new Scanner(System.in);

            int choice;

            do {
                // clear screen

                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.print("\033[?25h");

                // print the universe

                universe.print(2, 4);

                // print the menu

                System.out.println("\n1 - Resize universe");
                System.out.println("2 - Change start position");
                System.out.println("3 - Add obstacle");
                System.out.println("4 - Resolve");
                System.out.println("0 - Exit");

                // prompt the user

                System.out.print("\nChoice : ");
                choice = scanner.nextInt();

                switch (choice) {

                    case 1:
                        System.out.print("Enter the width of the universe : ");
                        universe_width = scanner.nextInt();
                        System.out.print("Enter the length of the universe : ");
                        universe_height = scanner.nextInt();

                        universe.changeUniverseDim(universe_width + 2, universe_height + 2);

                        break;

                    case 2:
                        System.out.print("Enter the position of the start point in the first axis : ");
                        start_i = scanner.nextInt() + 1;
                        System.out.print("Enter the position of the start point in the seccond axis : ");
                        start_j = scanner.nextInt() + 1;
                        System.out.print("Enter the direction of the start point : ");
                        start_dir = scanner.nextInt();

                        firstState_i = start_i;
                        firstState_j = start_j;

                        if      (start_dir == 10) firstState_i = start_i - 1;
                        else if (start_dir == 11) firstState_i = start_i + 1;
                        else if (start_dir == 12) firstState_j = start_j + 1;
                        else if (start_dir == 13) firstState_j = start_j - 1;

                        currentState = new Situation(firstState_i + 1, firstState_j + 1, start_dir, 0);

                        universe.changeUniverseStart(start_i, start_j, start_dir);

                        break;

                    case 3:
                        int firstPos_i, firstPos_j, seccondPos_i, seccondPos_j;

                        System.out.print("\nFirst position of the obstacle i : ");
                        firstPos_i = scanner.nextInt();
                        System.out.print("First position of the obstacle j : ");
                        firstPos_j = scanner.nextInt();
                        System.out.print("Seccond position of the obstacle i : ");
                        seccondPos_i = scanner.nextInt();
                        System.out.print("Seccond position of the obstacle j : ");
                        seccondPos_j = scanner.nextInt();

                        for (int i = firstPos_i + 1; i < seccondPos_i + 2; i++) {
                            for (int j = firstPos_j + 1; j < seccondPos_j + 2; j++) {
                                universe.addObstacle(i, j);
                            }
                        }

                        break;

                    case 4:
                        boolean display_progress = false, display_regress = false;

                        System.out.println("\n1 - display progress and regress");
                        System.out.println("2 - display progress");
                        System.out.println("3 - display regress");
                        System.out.println("4 - display nothing");
                        System.out.print("\nChoice : ");
                        choice = scanner.nextInt();

                        switch (choice) {
                            case 1:
                                display_progress = true;
                                display_regress = true;
                                break;

                            case 2:
                                display_progress = true;
                                break;

                            case 3:
                                display_regress = true;
                                break;

                            case 4:
                                break;

                            default:
                                break;
                        }

                        System.out.print("\033[?25l");
                        System.out.print("\033[H\033[2J");
                        System.out.flush();

                        universe.resetUniverse();

                        universe.print(2, 4);

                        while (!universe.isSolved()) {
                            if (universe.canEvolve(currentState)) {
                                stack.push(currentState.copy(universe.possibleChoices(currentState)));
                                currentState = universe.evolve(currentState);

                                if (display_progress == true) universe.print(universe_height + 6, 4);
                            }
                            else if (stack.size() > 0) {
                                currentState = stack.pop();
                                universe.reset(currentState);

                                if (display_regress == true) universe.print(universe_height + 6, 4);
                            } else {
                                break;
                            }
                        }

                        System.out.println("\n\n");
                        universe.print(universe_height + 6, 4);

                        System.out.print("\033[?25h");
                        System.out.print("\nEnter anything to continue....");
                        scanner.nextInt();

                        break;

                    default:
                        break;

                }
            } while (choice != 0);
        }
        else {
            // definitions of the starting position and the universe dimensions

            start_i           = 3;
            start_j           = 1;
            start_dir         = 11;
            universe_width    = 10;
            universe_height   = 10;
            firstState_i      = start_i;
            firstState_j      = start_j;

            if      (start_dir == 10) firstState_i = start_i - 1;
            else if (start_dir == 11) firstState_i = start_i + 1;
            else if (start_dir == 12) firstState_j = start_j + 1;
            else if (start_dir == 13) firstState_j = start_j - 1;

            // creation of the stack, the universe and the first state

            Stack <Situation> stack = new Stack <Situation>(); 
            Universe universe = new Universe(universe_width + 2, universe_height + 2, start_i, start_j, start_dir);
            Situation currentState = new Situation(firstState_i, firstState_j, start_dir, 0);

            // obstacles creation

            universe.addObstacle(2, 3);
            universe.addObstacle(3, 3);
            universe.addObstacle(2, 4);
            universe.addObstacle(3, 4);

            System.out.print("\033[?25l");
            System.out.print("\033[H\033[2J");
            System.out.flush();

            universe.print(2, 4);

            while (!universe.isSolved()) {
                if (universe.canEvolve(currentState)) {
                    stack.push(currentState.copy(universe.possibleChoices(currentState)));
                    currentState = universe.evolve(currentState);
                }
                else if (stack.size() > 0) {
                    currentState = stack.pop();
                    universe.reset(currentState);

                    universe.print(universe_height + 6, 4);
                } else {
                    break;
                }
                //universe.print(universe_height + 6, 4);
            }

            System.out.println("\n\n");
            universe.print(universe_height + 6, 4);
            System.out.print("\033[?25h");
        }
        
    }
}
