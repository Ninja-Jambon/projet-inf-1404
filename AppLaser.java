// Antoine CRETUAL, Lukian LEIZOUR, 14/02/2024

import java.util.Scanner;
import java.time.Instant;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import userInterface.UserInterface;
import universe.*;

public class AppLaser {
	public static void main(String [] args) {

		boolean cli = false, optimize_duration = false;

		for (int i = 0; i < args.length; i++) {
			switch (args[i]) {
				case "-cli":
					cli = true;
					break;

				case "--optimize-duration":
					optimize_duration = true;
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

				Universe.print(universe.getGrid(), universe_width + 2, universe_height + 2, 2, 4);

				// print the menu

				System.out.println("\n1 - Resize universe");
				System.out.println("2 - Change start position");
				System.out.println("3 - Add obstacle");
				System.out.println("4 - Reset universe");
				System.out.println("5 - Reset obstacles");
				System.out.println("6 - Save universe");
				System.out.println("7 - Load universe");
				System.out.println("8 - Resolve");
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
						start_i = scanner.nextInt();
						System.out.print("Enter the position of the start point in the seccond axis : ");
						start_j = scanner.nextInt();
						System.out.print("Enter the direction of the start point : ");
						start_dir = scanner.nextInt();

						firstState_i = start_i;
						firstState_j = start_j;

						if      (start_dir == 10) firstState_i = start_i - 1;
						else if (start_dir == 11) firstState_i = start_i + 1;
						else if (start_dir == 12) firstState_j = start_j + 1;
						else if (start_dir == 13) firstState_j = start_j - 1;

						currentState = new Situation(firstState_i + 1, firstState_j + 1, start_dir, 0);

						universe.changeUniverseStart(start_i + 1, start_j + 1, start_dir);

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
						universe.resetUniverse();

						break;

					case 5:
						universe.resetUniverseObstacles();

						break;


					case 6:
						System.out.print("\nHow do you want to name your universe : ");
						String name = "";

						while (name.isEmpty()) {
							name = scanner.nextLine();
						}

						universe.save(name);

						break;

					case 7:

						Set<String> files = Stream.of(new File("./saves").listFiles()).filter(file -> !file.isDirectory()).map(File::getName).collect(Collectors.toSet());

						System.out.println("\nAvailable files : \n");

						for (String element : files) {
							System.out.println(element.replace(".txt", ""));
						}

						System.out.print("\nWhat universe do you want load ? : ");
						String name2 = "";

						while (name2.isEmpty()) {
							name2 = scanner.nextLine();
						}

						try {
							BufferedReader reader = new BufferedReader(new FileReader("./saves/" + name2 + ".txt"));
							universe_height = Integer.valueOf(reader.readLine());
							universe_width = Integer.valueOf(reader.readLine());
							start_dir = Integer.valueOf(reader.readLine());
							start_i = Integer.valueOf(reader.readLine());
							start_j = Integer.valueOf(reader.readLine());


							universe.changeUniverseDim(universe_width + 2, universe_height + 2);

							firstState_i = start_i;
							firstState_j = start_j;

							if      (start_dir == 10) firstState_i = start_i - 1;
							else if (start_dir == 11) firstState_i = start_i + 1;
							else if (start_dir == 12) firstState_j = start_j + 1;
							else if (start_dir == 13) firstState_j = start_j - 1;

							currentState = new Situation(firstState_i, firstState_j, start_dir, 0);

							universe.changeUniverseStart(start_i, start_j, start_dir);

							while (true) {
								try {
									int pos1 = Integer.valueOf(reader.readLine());
									int pos2 = Integer.valueOf(reader.readLine());

									universe.addObstacle(pos1, pos2);
								} 
								catch (Exception e) {
									break;
								}
							}
						}
						catch (Exception e) {}

						break;


					case 8:
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

						Universe.print(universe.getGrid(), universe_width + 2, universe_height + 2, 2, 4);

						int start_time = (int) Instant.now().getEpochSecond();

						int [][] bestGrid = universe.copyGrid();
						int best_filled_boxes = 0;
						int best_nb_mirrors = 0;

						do {
							if (universe.canEvolve(currentState)) {
								stack.push(currentState.copy(universe.possibleChoices(currentState)));
								currentState = universe.evolve(currentState);

								if (display_progress == true) Universe.print(universe.getGrid(), universe_width + 2, universe_height + 2, universe_height + 6, 4);

								if ((universe.getFilledBoxes() > best_filled_boxes) || (universe.getFilledBoxes() == best_filled_boxes && universe.getNbMirrors() < best_nb_mirrors)) {
									bestGrid = universe.copyGrid();
									best_filled_boxes = universe.getFilledBoxes();
									best_nb_mirrors = universe.getNbMirrors();
					
									Universe.print(bestGrid, universe_width + 2, universe_height + 2, 2, 2 * universe_width + 10);
									System.out.println("Miroirs: " + best_nb_mirrors + " Cases: " + best_filled_boxes);
								}
							}
							else if (stack.size() > 0) {
								currentState = stack.pop();
								universe.reset(currentState);

								if (display_regress == true) Universe.print(universe.getGrid(), universe_width + 2, universe_height + 2, universe_height + 6, 4);
							} else {
								break;
							}

							if ((int) Instant.now().getEpochSecond() - start_time > 60 && optimize_duration == true) {
								display_progress = false;
							}
							if ((int) Instant.now().getEpochSecond() - start_time > 2 * 60 && optimize_duration == true) {
								display_regress = false;
							}
						} while (stack.size() != 0);

						System.out.println("\n\n");

						Universe.print(bestGrid, universe_width + 2, universe_height + 2, universe_height + 6, 4);

						System.out.println("\nSolved in " + ((int) Instant.now().getEpochSecond() - start_time) + " secconds");

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
			UserInterface userInterface= new UserInterface();
			userInterface.start();
		} 
	}
}
