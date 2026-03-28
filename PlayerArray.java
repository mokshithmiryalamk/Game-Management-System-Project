package Game_Leaderboard_And_Player_Ranking_System;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class PlayerArray {

    Player[] players;
    int count;
    private static final String FILE_PATH = "players.txt";
    private HashMap<Integer, Player> playerById;  // For hash-based search

    public PlayerArray(int size) {
        players = new Player[size];
        count = 0;
        playerById = new HashMap<>();
    }

    // ---------------- GET COUNT ----------------
    public int getCount() { return count; }

    // ---------------- ADD PLAYER ----------------
    public void addPlayer(Player p) {
        if (count >= players.length) {
            System.out.println("Player list is full.");
            return;
        }
        players[count++] = p;
        playerById.put(p.id, p);
        savePlayerToFile(p);
    }

    // ---------------- SAVE SINGLE PLAYER ----------------
    public void savePlayerToFile(Player p) {
        try (FileWriter fw = new FileWriter(FILE_PATH, true)) {
            String teamName = (p.team == null) ? "null" : p.team;
            fw.write(p.name + "," + p.id + "," + p.score + "," + p.level + "," + teamName + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    // ---------------- SAVE ALL PLAYERS ----------------
    public void saveAllPlayersToFile() {
        try (FileWriter fw = new FileWriter(FILE_PATH)) {
            for (int i = 0; i < count; i++) {
                String teamName = (players[i].team == null) ? "null" : players[i].team;
                fw.write(players[i].name + "," + players[i].id + "," + players[i].score + "," +
                        players[i].level + "," + teamName + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving players.");
        }
    }

    // ---------------- LOAD PLAYERS ----------------
    public void loadFromFile() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
                return;
            }

            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.isEmpty()) continue;

                String[] data = line.split(",");
                String name = data[0];
                int id = Integer.parseInt(data[1]);
                int score = Integer.parseInt(data[2]);
                int level = Integer.parseInt(data[3]);
                String team = data[4];

                Player p = team.equals("null") ? new Player(name, id, score, level)
                                               : new Player(name, id, score, team, level);

                players[count++] = p;
                playerById.put(p.id, p);
            }
            sc.close();

        } catch (IOException e) {
            System.out.println("Error loading players.");
        }
    }

    // ---------------- DISPLAY ALL ----------------
    public void displayAll() {
        if (count == 0) {
            System.out.println("No players in system.");
            return;
        }
        printTableHeader();
        for (int i = 0; i < count; i++) {
            String teamName = (players[i].team == null) ? "-" : players[i].team;
            System.out.printf("%-5d | %-5d | %-20s | %-15s | %-6d | %-5d%n",
                    i + 1, players[i].id, players[i].name, teamName, players[i].level, players[i].score);
        }
    }

    // ---------------- LINEAR SEARCH ----------------
    public void linearSearchById(int id) {
        boolean found = false;
        printTableHeader();
        for (int i = 0; i < count; i++) {
            if (players[i].id == id) {
                String teamName = (players[i].team == null) ? "-" : players[i].team;
                System.out.printf("%-5d | %-5d | %-20s | %-15s | %-6d | %-5d%n",
                        i + 1, players[i].id, players[i].name, teamName, players[i].level, players[i].score);
                found = true;
                break;
            }
        }
        if (!found) System.out.println("Player not found (linear search).");
    }

    public void linearSearchByName(String name) {
        boolean found = false;
        printTableHeader();
        for (int i = 0; i < count; i++) {
            if (players[i].name.equalsIgnoreCase(name)) {
                String teamName = (players[i].team == null) ? "-" : players[i].team;
                System.out.printf("%-5d | %-5d | %-20s | %-15s | %-6d | %-5d%n",
                        i + 1, players[i].id, players[i].name, teamName, players[i].level, players[i].score);
                found = true;
            }
        }
        if (!found) System.out.println("Player not found (linear search by name).");
    }

    public void linearSearchByTeam(String team) {
        boolean found = false;
        printTableHeader();
        for (int i = 0; i < count; i++) {
            if (players[i].team != null && players[i].team.equalsIgnoreCase(team)) {
                String teamName = players[i].team;
                System.out.printf("%-5d | %-5d | %-20s | %-15s | %-6d | %-5d%n",
                        i + 1, players[i].id, players[i].name, teamName, players[i].level, players[i].score);
                found = true;
            }
        }
        if (!found) System.out.println("Team not found.");
    }

    // ---------------- HASH SEARCH ----------------
    public void searchByIdHash(int id) {
        if (playerById.containsKey(id)) {
            printTableHeader();
            Player p = playerById.get(id);
            String teamName = (p.team == null) ? "-" : p.team;
            System.out.printf("%-5d | %-5d | %-20s | %-15s | %-6d | %-5d%n",
                    1, p.id, p.name, teamName, p.level, p.score);
        } else {
            System.out.println("Player not found (hash search).");
        }
    }

    // ---------------- SORT & BINARY SEARCH ----------------
    public void sortById() {
        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - i - 1; j++) {
                if (players[j].id > players[j + 1].id) {
                    Player temp = players[j];
                    players[j] = players[j + 1];
                    players[j + 1] = temp;
                }
            }
        }
    }

    public void binarySearchById(int id) {
        sortById();
        int start = 0, end = count - 1;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (players[mid].id == id) {
                printTableHeader();
                Player p = players[mid];
                String teamName = (p.team == null) ? "-" : p.team;
                System.out.printf("%-5d | %-5d | %-20s | %-15s | %-6d | %-5d%n",
                        mid + 1, p.id, p.name, teamName, p.level, p.score);
                return;
            }
            if (players[mid].id < id) start = mid + 1;
            else end = mid - 1;
        }
        System.out.println("Player not found (binary search).");
    }

    // ---------------- LEADERBOARD ----------------
    public void leaderboardQuick() {
        quickSort(0, count - 1);
        displayAll();
    }

    private void quickSort(int start, int end) {
        if (start < end) {
            int pivotIndex = partition(start, end);
            quickSort(start, pivotIndex - 1);
            quickSort(pivotIndex + 1, end);
        }
    }

    private int partition(int start, int end) {
        int pivot = players[end].score;
        int i = start - 1;
        for (int j = start; j < end; j++) {
            if (players[j].score > pivot) {
                i++;
                Player temp = players[i];
                players[i] = players[j];
                players[j] = temp;
            }
        }
        Player temp = players[i + 1];
        players[i + 1] = players[end];
        players[end] = temp;
        return i + 1;
    }

    // ---------------- SCORE METHODS ----------------
    public void incrementScore(Player p, int points) { p.score += points; }
    public void decrementScore(Player p, int points) { p.score -= points; }

    // ---------------- START MATCH (1v1 or 2v2) ----------------
    public void startMatch(PlayerQueue queue, MatchHistoryStack history, Scanner sc) {

        System.out.println("\n1 Solo Match (1v1)");
        System.out.println("2 Team Match (2v2)");
        System.out.print("Choose match type: ");
        int matchType = sc.nextInt(); sc.nextLine();

        if (matchType == 1) {  // 1v1
            Player p1 = queue.dequeue();
            Player p2 = queue.dequeue();
            if (p1 == null || p2 == null) {
                System.out.println("Not enough challengers in the arena!");
                // if one player was dequeued, put them back
                if (p1 != null) queue.enqueue(p1);
                if (p2 != null) queue.enqueue(p2);
                return;
            }

            System.out.println("\n===== MATCH STARTED =====");
            System.out.println(p1.name + " VS " + p2.name);

            System.out.println("\nPress ENTER to roll for " + p1.name); sc.nextLine();
            int roll1 = Main.rollNumber(); System.out.println(p1.name + " rolled: " + roll1);

            System.out.println("\nPress ENTER to roll for " + p2.name); sc.nextLine();
            int roll2 = Main.rollNumber(); System.out.println(p2.name + " rolled: " + roll2);

            Player winner = (roll1 > roll2) ? p1 : p2;
            incrementScore(winner, 10);
            history.push(winner);

            System.out.println("\nWinner: " + winner.name + " | Score +10");
            System.out.println("==========================");

        } else if (matchType == 2) {  // 2v2
            Player[] team1 = {queue.dequeue(), queue.dequeue()};
            Player[] team2 = {queue.dequeue(), queue.dequeue()};
            if (team1[0] == null || team1[1] == null || team2[0] == null || team2[1] == null) {
                System.out.println("Not enough players for 2v2 match!");
                // put back any dequeued players
                for (Player p : team1) if (p != null) queue.enqueue(p);
                for (Player p : team2) if (p != null) queue.enqueue(p);
                return;
            }

            int rollTeam1 = 0, rollTeam2 = 0;

            System.out.println("\n===== TEAM MATCH STARTED =====");
            System.out.println(team1[0].name + " & " + team1[1].name + " VS " +
                               team2[0].name + " & " + team2[1].name);

            for (Player pl : team1) { System.out.println("\nPress ENTER to roll for " + pl.name); sc.nextLine();
                int r = Main.rollNumber(); System.out.println(pl.name + " rolled: " + r); rollTeam1 += r;
            }
            for (Player pl : team2) { System.out.println("\nPress ENTER to roll for " + pl.name); sc.nextLine();
                int r = Main.rollNumber(); System.out.println(pl.name + " rolled: " + r); rollTeam2 += r;
            }

            Player[] winningTeam = (rollTeam1 > rollTeam2) ? team1 : team2;
            for (Player pl : winningTeam) incrementScore(pl, 10);
            for (Player pl : winningTeam) history.push(pl);

            System.out.println("\nTeam Score: " + rollTeam1 + " VS " + rollTeam2);
            System.out.print("Winning Team: "); for (Player pl : winningTeam) System.out.print(pl.name + " ");
            System.out.println(" | Each +10 Score");
            System.out.println("==========================");

        } else System.out.println("Invalid match type.");
    }

    // ---------------- TABLE HEADER ----------------
    private void printTableHeader() {
        System.out.printf("%-5s | %-5s | %-20s | %-15s | %-6s | %-5s%n",
                "Rank", "ID", "Name", "Team", "Level", "Score");
        System.out.println("---------------------------------------------------------------");
    }
}