package Game_Leaderboard_And_Player_Ranking_System;

public class Player {

    String name;
    int id;
    int score;
    int level;
    String team;

    // Constructor for NEW single player (score starts at 0)
    public Player(String name, int id, int level) {
        this.name = name;
        this.id = id;
        this.level = level;
        this.score = 0;
        this.team = null;
    }

    // Constructor for NEW team player (score starts at 0)
    public Player(String name, int id, String team, int level) {
        this.name = name;
        this.id = id;
        this.team = team;
        this.level = level;
        this.score = 0;
    }

    // Constructor for LOADING single player from file
    public Player(String name, int id, int score, int level) {
        this.name = name;
        this.id = id;
        this.score = score;
        this.level = level;
        this.team = null;
    }

    // Constructor for LOADING team player from file
    public Player(String name, int id, int score, String team, int level) {
        this.name = name;
        this.id = id;
        this.score = score;
        this.team = team;
        this.level = level;
    }

    // Display player details as a tabular row
    @Override
    public String toString() {
        String teamName = (team == null || team.equals("null")) ? "-" : team;
        return String.format("%-5d | %-20s | %-15s | %-6d | %-5d",
                id, name, teamName, level, score);
    }
}