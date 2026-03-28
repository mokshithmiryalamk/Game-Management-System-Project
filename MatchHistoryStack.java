package Game_Leaderboard_And_Player_Ranking_System;

public class MatchHistoryStack {

    Player[] stack;
    int top;

    public MatchHistoryStack(int capacity) {
        stack = new Player[capacity];
        top = -1;
    }

    // -------- STORE MATCH WINNER --------
    public void push(Player p) {

        if (top == stack.length - 1) {
            System.out.println("Match history storage full.");
            return;
        }

        top++;
        stack[top] = p;
    }

    // -------- UNDO LAST MATCH --------
    public Player pop() {

        if (top == -1) {
            System.out.println("No match history available.");
            return null;
        }

        Player p = stack[top];
        top--;

        // Undo score gained from match
        p.score -= 10;

        return p;
    }

    // -------- DISPLAY MATCH HISTORY --------
    public void displayHistory() {

        if (top == -1) {
            System.out.println("No matches played yet.");
            return;
        }

        System.out.println("\n===== MATCH HISTORY =====");

        for (int i = top; i >= 0; i--) {
            System.out.println("Winner: " + stack[i].name +
                               " | Score: " + stack[i].score);
        }
    }

    // -------- CHECK IF STACK EMPTY --------
    public boolean isEmpty() {
        return top == -1;
    }
}