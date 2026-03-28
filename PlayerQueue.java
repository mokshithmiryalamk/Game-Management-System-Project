package Game_Leaderboard_And_Player_Ranking_System;

public class PlayerQueue {

    Player[] queue;
    int front;
    int rear;
    int size;

    public PlayerQueue(int capacity) {
        queue = new Player[capacity];
        front = 0;
        rear = -1;
        size = 0;
    }

    // -------- JOIN MATCH LOBBY --------
    public void enqueue(Player p) {

        if (size == queue.length) {
            System.out.println("Lobby full! Wait for the next match.");
            return;
        }

        rear++;
        queue[rear] = p;
        size++;

        System.out.println(p.name + " entered the match lobby.");
    }

    // -------- REMOVE PLAYER FROM LOBBY --------
    public Player dequeue() {

        if (size == 0) {
            System.out.println("No players waiting in the lobby.");
            return null;
        }

        Player p = queue[front];
        front++;
        size--;

        return p;
    }

    // -------- SHOW WAITING PLAYERS --------
    public void displayQueue() {

        if (size == 0) {
            System.out.println("No players waiting in the lobby.");
            return;
        }

        System.out.println("\n=== MATCH LOBBY ===");

        for (int i = front; i <= rear; i++) {
            System.out.println(queue[i]);
        }
    }

    // -------- CHECK IF MATCH CAN START --------
    public boolean hasTwoPlayers() {
        return size >= 2;
    }
}