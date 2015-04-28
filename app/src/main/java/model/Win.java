package model;

/**
 * Created by mertsimsek on 09/01/15.
 */
public class Win {
    User voter;
    User loser;

    public Win() {
    }

    public Win(User voter, User loser) {
        this.voter = voter;
        this.loser = loser;
    }

    public User getVoter() {
        return voter;
    }

    public void setVoter(User voter) {
        this.voter = voter;
    }

    public User getLoser() {
        return loser;
    }

    public void setLoser(User loser) {
        this.loser = loser;
    }
}
