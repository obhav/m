import java.util.*;

public class BullyAlgorithm {

    static boolean alive[] = new boolean[5];
    static int coordinator = 5;

    static void StartElection(int initiator) {
        System.out.println("Process " + initiator + " starts the election.");
        boolean higherAlive = false;

        for (int i = initiator + 1; i <= 5; i++) {
            if (alive[i - 1]) {
                System.out.println("Process " + initiator + " passes election to Process " + i);
                higherAlive = true;
                StartElection(i);  // Recursive call to pass election upward
                return;
            }
        }

        // No higher process alive, so initiator becomes coordinator
        coordinator = initiator;
        System.out.println("Process " + initiator + " becomes the new coordinator.");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Arrays.fill(alive, true);  // Initially all are alive
        System.out.println("Initially, all processes are up. Process 5 is the Coordinator.");

        int choice;
        do {
            System.out.println("\n1. Process Bring Up\n2. Process Down\n3. Send Message\n4. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1: {
                    System.out.print("Enter the process (1-5) to Bring Up: ");
                    int up = sc.nextInt();
                    if (up < 1 || up > 5) {
                        System.out.println("Invalid process number.");
                        break;
                    }
                    if (alive[up - 1]) {
                        System.out.println("Process " + up + " is already up.");
                    } else {
                        alive[up - 1] = true;
                        System.out.println("Process " + up + " is now up.");
                        if (up > coordinator) {
                            StartElection(up);
                        }
                    }
                    break;
                }

                case 2: {
                    System.out.print("Enter the process (1-5) to bring Down: ");
                    int down = sc.nextInt();
                    if (down < 1 || down > 5) {
                        System.out.println("Invalid process number.");
                        break;
                    }
                    if (!alive[down - 1]) {
                        System.out.println("Process " + down + " is already down.");
                    } else {
                        alive[down - 1] = false;
                        System.out.println("Process " + down + " is now down.");
                        if (coordinator == down) {
                            System.out.println("Coordinator Process " + coordinator + " is down.");
                            for (int i = 5; i >= 1; i--) {
                                if (alive[i - 1]) {
                                    StartElection(i);
                                    break;
                                }
                            }
                        }
                    }
                    
                    break;
                }

                case 3: {
                    System.out.print("Enter the process (1-5) to send message: ");
                    int p = sc.nextInt();
                    if (p < 1 || p > 5) {
                        System.out.println("Invalid process number.");
                        break;
                    }
                    if (!alive[p - 1]) {
                        System.out.println("Process " + p + " is down.");
                    } else if (!alive[coordinator - 1]) {
                        System.out.println("Coordinator is down! Starting election...");
                        StartElection(p);
                    } else {
                        System.out.println("Message sent from Process " + p + " to Coordinator " + coordinator + ".");
                    }
                    break;
                }

                case 4:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 4);
    }
}

