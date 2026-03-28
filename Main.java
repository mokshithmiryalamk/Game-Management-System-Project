package Game_Leaderboard_And_Player_Ranking_System;

import java.util.Scanner;
import java.util.Random;

public class Main {

    static {
        System.out.println("==============================================");
        System.out.println("        GAME LEADERBOARD & RANKING SYSTEM     ");
        System.out.println("==============================================");
        System.out.println("Manage Players | Play Matches | Track Winners");
        System.out.println("==============================================");
    }

    // Roll number utility
    public static int rollNumber() {
        Random rand = new Random();
        System.out.print("Rolling");
        for (int i = 0; i < 3; i++) {
            try { Thread.sleep(500); } catch (Exception e) {}
            System.out.print(".");
        }
        System.out.println();
        return rand.nextInt(100) + 1;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        PlayerArray manager = new PlayerArray(100);
        PlayerQueue queue = new PlayerQueue(50);
        MatchHistoryStack history = new MatchHistoryStack(100);

        manager.loadFromFile();

        int choice;

        do {
            System.out.println("\n=========== MAIN MENU ===========");

            System.out.println("\nPLAYER MANAGEMENT");
            System.out.println("1 Register Player");
            System.out.println("2 Display All Players");
            System.out.println("3 Search Player");

            System.out.println("\nGAMEPLAY");
            System.out.println("4 Join Match Queue");
            System.out.println("5 Show Waiting Players");
            System.out.println("6 Start Match");

            System.out.println("\nRANKINGS");
            System.out.println("7 Show Leaderboard");

            System.out.println("\nSYSTEM");
            System.out.println("8 Show Match History");
            System.out.println("9 Undo Last Match");
            System.out.println("10 Exit");

            System.out.print("\nEnter choice: ");
            choice = sc.nextInt(); sc.nextLine();

            switch (choice) {

                case 1: // Register Player
                    System.out.println("\n1 Single Player");
                    System.out.println("2 Team Player");
                    System.out.print("Choose type: ");
                    int type = sc.nextInt(); sc.nextLine();

                    System.out.print("Enter name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    System.out.print("Enter level: ");
                    int level = sc.nextInt(); sc.nextLine();

                    Player p;
                    if (type == 1) p = new Player(name, id, level);
                    else {
                        System.out.print("Enter team name: ");
                        String team = sc.nextLine();
                        p = new Player(name, id, team, level);
                    }

                    manager.addPlayer(p);
                    System.out.println("Player registered successfully!");
                    break;

                case 2: // Display All
                    manager.displayAll();
                    break;

                case 3: // Search Player
                    System.out.println("\nSEARCH MENU");
                    System.out.println("1 Search by ID (Linear)");
                    System.out.println("2 Search by Name");
                    System.out.println("3 Search by Team");
                    System.out.println("4 Search by ID (Binary)");
                    System.out.println("5 Search by ID (Hashing)");
                    System.out.print("Choose search type: ");
                    int s = sc.nextInt(); sc.nextLine();

                    if (s == 1) {
                        System.out.print("Enter ID: ");
                        int sid = sc.nextInt();
                        manager.linearSearchById(sid);
                    } else if (s == 2) {
                        System.out.print("Enter Name: ");
                        String sname = sc.nextLine();
                        manager.linearSearchByName(sname);
                    } else if (s == 3) {
                        System.out.print("Enter Team: ");
                        String steam = sc.nextLine();
                        manager.linearSearchByTeam(steam);
                    } else if (s == 4) {
                        System.out.print("Enter ID: ");
                        int sid = sc.nextInt();
                        manager.sortById();
                        manager.binarySearchById(sid);
                    } else if (s == 5) {
                        System.out.print("Enter ID: ");
                        int sid = sc.nextInt();
                        manager.searchByIdHash(sid);
                    } else {
                        System.out.println("Invalid search choice.");
                    }
                    break;

                case 4: // Join Queue
                    System.out.print("Enter player ID to join queue: ");
                    int pid = sc.nextInt();
                    Player player = null;
                    for (int i = 0; i < manager.getCount(); i++) {
                        if (manager.players[i].id == pid) {
                            player = manager.players[i];
                            break;
                        }
                    }
                    if (player != null) queue.enqueue(player);
                    else System.out.println("Player not found.");
                    break;

                case 5: // Show waiting players
                    queue.displayQueue();
                    break;

                case 6: // Start match
                    manager.startMatch(queue, history, sc);
                    break;

                case 7: // Leaderboard
                    manager.leaderboardQuick();
                    break;

                case 8: // Match history
                    history.displayHistory();
                    break;

                case 9: // Undo last match
                    Player last = history.pop();
                    if (last != null) {
                        manager.decrementScore(last, 10);
                        System.out.println("Last match undone. Score reverted for " + last.name);
                    } else System.out.println("No match to undo.");
                    break;

                case 10: // Exit
                    manager.saveAllPlayersToFile();
                    System.out.println("System closing...");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 10);

        sc.close();
    }
}