package jimpossiblemission.model;

import java.io.Serializable;

/**
 * User Profile
 * 
 * @author Filippo Taiuti
 */
public class User implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String nickname;
    private String avatar;
    private int gamesPlayed;
    private int gamesWon;
    private int gamesLost;
    private int level;
    private int bestScore;
    private int completedLevels;

    public User(String nickname, String avatar)
    {
        this.nickname = nickname;
        this.avatar = avatar;
        this.gamesPlayed = 0;
        this.gamesWon = 0;
        this.gamesLost = 0;
        this.level = 1;
        this.bestScore = 0;
        this.completedLevels = 0;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public int getGamesPlayed()
    {
        return gamesPlayed;
    }

    public void addGamePlayed()
    {
        this.gamesPlayed += 1;
    }

    public int getGamesWon()
    {
        return gamesWon;
    }

    public void addGamesWon()
    {
        this.gamesWon += 1;
    }

    public int getGamesLost()
    {
        return gamesLost;
    }

    public void addGamesLost()
    {
        this.gamesLost += 1;
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public int getBestScore()
    {
        return bestScore;
    }

    public void setBestScore(int bestScore)
    {
        this.bestScore = bestScore;
    }

    public int getCompletedLevels()
    {
        return completedLevels;
    }

    public void addCompletedLevel()
    {
        this.completedLevels += 1;
    }

    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }

}
