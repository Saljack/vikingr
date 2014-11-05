/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vikingr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author saljack
 */
public class Player {

    private int ID;

    private boolean[] playWith;
    private boolean[] playOpponent;

    public Player(int ID) {
        this.ID = ID;
        playWith = new boolean[Generator.numberOfPlayer];
        playOpponent = new boolean[Generator.numberOfPlayer];
        Arrays.fill(playWith, false);
        Arrays.fill(playOpponent, false);
        playOpponent[ID] = true;
        playWith[ID] = true;
    }

    static List<Integer> cross(boolean[] actualPlayed, Player A1) {
        List<Integer> players = new ArrayList<Integer>();
        for (int i = 0; i < actualPlayed.length; i++) {
            if (!A1.playWith[i] && !actualPlayed[i]) {
                players.add(i);
            }
        }
        return players;
    }

    static List<Integer> crossOponent(boolean[] actualPlayed, Player A1, Player A2) {
        List<Integer> players = new ArrayList<Integer>();
        for (int i = 0; i < actualPlayed.length; i++) {
            if (!A1.playOpponent[i] && !A2.playOpponent[i] && !actualPlayed[i]) {
                players.add(i);
            }
        }
        return players;
    }

    static List<Integer> crossOponentWith(boolean[] actualPlayed, Player A1, Player A2, Player B1) {
        List<Integer> players = new ArrayList<Integer>();
        for (int i = 0; i < actualPlayed.length; i++) {
            if (!B1.playWith[i] && !A1.playOpponent[i] && !A2.playOpponent[i] && !actualPlayed[i]) {
                players.add(i);
            }
        }
        return players;
    }

    static List<Integer> cross(boolean[] actualPlayed, Player A1, Player A2, Player B1, Player B2) {
        List<Integer> players = new ArrayList<Integer>();
        for (int i = 0; i < actualPlayed.length; i++) {
            if (!A1.playWith[i] && !actualPlayed[i]) {
                players.add(i);
            }
        }
        return players;
    }

    List<Integer> doCrossWith(boolean[] actualPlayed) {
        List<Integer> players = new ArrayList<Integer>();
        for (int i = 0; i < playWith.length; i++) {
            if (!playWith[i] && !actualPlayed[i]) {
                players.add(i);
            }
        }
        return players;
    }

    List<Integer> doCrossOpponents(boolean[] actualPlayed) {
        List<Integer> players = new ArrayList<Integer>();
        for (int i = 0; i < playOpponent.length; i++) {
            if (!playOpponent[i] && !actualPlayed[i]) {
                players.add(i);
            }
        }
        return players;
    }

    public void playWith(int p) {
        playWith[p] = true;
    }

    public void setOpponents(int a, int b) {
        playOpponent[a] = true;
        playOpponent[b] = true;
    }

    public void removePlayWith(int p) {
        playWith[p] = false;
    }

    public void removeSetOpponents(int a, int b) {
        playOpponent[a] = false;
        playOpponent[b] = false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Player ");
        sb.append(ID);
        sb.append('\n');
        for (int i = 0; i < playWith.length; i++) {
            sb.append(i);
            sb.append(":");
            if (playWith[i]) {
                sb.append("X");
            } else {
                sb.append("0");
            }
            sb.append(" ");

        }
        sb.append('\n');

        for (int i = 0; i < playOpponent.length; i++) {
            sb.append(i);
            sb.append(":");
            if (playOpponent[i]) {
                sb.append("X");
            } else {
                sb.append("0");
            }
            sb.append(" ");

        }
        sb.append('\n');
        return sb.toString();
    }

    void reset() {
        Arrays.fill(playWith, false);
        Arrays.fill(playOpponent, false);
        playOpponent[ID] = true;
        playWith[ID] = true;
    }

}
