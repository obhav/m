import java.util.Scanner;

class RingElection{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();

        int pid[] = new int[n];         // Process IDs
        boolean alive[] = new boolean[n]; // Alive status

        for (int i = 0; i < n; i++) {
            System.out.print("Enter ID for Process " + i + ": ");
            pid[i] = sc.nextInt();
            alive[i] = true; // All processes are initially up
        }

        int coordinator = -1;

        while (true) {
            System.out.println("\nMenu:\n1. Start Election\n2. Bring Down Process\n3. Bring Up Process\n4. Show Coordinator\n5. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1: {
                    System.out.print("Enter index of initiator: ");
                    int ini = sc.nextInt();

                    if (!alive[ini]) {
                        System.out.println("Initiator process is down!");
                        break;
                    }

                    System.out.println("Election started by Process " + pid[ini]);

                    int maxId = pid[ini];
                    int maxIndex = ini;

                    int i = (ini + 1) % n;
                    while (i != ini) {
                        if (alive[i]) {
                            System.out.println("Message passes to Process " + pid[i]);
                            if (pid[i] > maxId) {
                                maxId = pid[i];
                                maxIndex = i;
                            }
                        }
                        i = (i + 1) % n;
                    }

                    coordinator = maxIndex;
                    System.out.println("Process " + pid[coordinator] + " becomes the new Coordinator.");
                    break;
                }

                case 2: {
                    System.out.print("Enter index of process to bring down: ");
                    int down = sc.nextInt();
                    if (!alive[down]) {
                        System.out.println("Process already down.");
                    } else {
                        alive[down] = false;
                        System.out.println("Process " + pid[down] + " is now down.");
                        if (coordinator == down) {
                            System.out.println("Coordinator was down. Start a new election.");
                        }
                    }
                    break;
                }

                case 3: {
                    System.out.print("Enter index of process to bring up: ");
                    int up = sc.nextInt();
                    if (alive[up]) {
                        System.out.println("Process already up.");
                    } else {
                        alive[up] = true;
                        System.out.println("Process " + pid[up] + " is now up.");
                        System.out.println("Starting election to re-evaluate coordinator...");
                        // Automatically start an election with this process
                        int ini = up;
                        int maxId = pid[ini];
                        int maxIndex = ini;
                        int i = (ini + 1) % n;
                        while (i != ini) {
                            if (alive[i]) {
                                if (pid[i] > maxId) {
                                    maxId = pid[i];
                                    maxIndex = i;
                                }
                            }
                            i = (i + 1) % n;
                        }
                        coordinator = maxIndex;
                        System.out.println("Process " + pid[coordinator] + " becomes the new Coordinator.");
                    }
                    break;
                }

                case 4: {
                    if (coordinator != -1 && alive[coordinator]) {
                        System.out.println("Current Coordinator is Process " + pid[coordinator]);
                    } else {
                        System.out.println("No coordinator elected or current coordinator is down.");
                    }
                    break;
                }

                case 5:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}

