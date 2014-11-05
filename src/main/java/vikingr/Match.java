/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vikingr;

/**
 *
 * @author saljack
 */
public class Match {
    int teamA1;
    int teamA2;
    int teamB1;
    int teamB2;

    public Match() {
        teamA1 = -1;
        teamA2 = -1;
        teamB1 = -1;
        teamB2 = -1;
    }
    
    public Match(int teamA1, int teamA2, int teamB1, int teamB2) {
        this.teamA1 = teamA1;
        this.teamA2 = teamA2;
        this.teamB1 = teamB1;
        this.teamB2 = teamB2;
    }
}
