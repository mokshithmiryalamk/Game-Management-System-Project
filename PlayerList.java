package Game_Leaderboard_And_Player_Ranking_System;

public class PlayerList {

    class Node {
        Player player;
        Node next;

        Node(Player p) {
            player = p;
            next = null;
        }
    }

    Node head;

    public PlayerList() {
        head = null;
    }

    // ---------------- ADD PLAYER (RECENTLY ACTIVE LIST) ----------------
    public void addRecent(Player p) {

        Node newNode = new Node(p);

        newNode.next = head;
        head = newNode;
    }

    // ---------------- ADD PLAYER FOR TOURNAMENT ----------------
    public void registerTournament(Player p) {

        Node newNode = new Node(p);

        if (head == null) {
            head = newNode;
            return;
        }

        Node temp = head;

        while (temp.next != null)
            temp = temp.next;

        temp.next = newNode;
    }

    // ---------------- INSERT PLAYER BASED ON SCORE (RANKING) ----------------
    public void insertByScore(Player p) {

        Node newNode = new Node(p);

        if (head == null || head.player.score < p.score) {

            newNode.next = head;
            head = newNode;
            return;
        }

        Node temp = head;

        while (temp.next != null && temp.next.player.score >= p.score) {
            temp = temp.next;
        }

        newNode.next = temp.next;
        temp.next = newNode;
    }

    // ---------------- DISPLAY LIST ----------------
    public void display() {

        if (head == null) {
            System.out.println("List is empty.");
            return;
        }

        Node temp = head;

        while (temp != null) {

            System.out.println(temp.player);

            temp = temp.next;
        }
    }

    // ---------------- DISPLAY TOP RANKED PLAYERS ----------------
    public void displayTopPlayers(int n) {

        if (head == null) {
            System.out.println("No ranked players.");
            return;
        }

        Node temp = head;
        int count = 0;

        while (temp != null && count < n) {

            System.out.println("Rank " + (count + 1) + ": " + temp.player);

            temp = temp.next;
            count++;
        }
    }
}