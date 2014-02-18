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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author saljack
 */
public class Generator {

    public static int numberOfPlayer;
    public static final int PLAYERS_IN_MATCH = 4;
    public static int maxAttempts = 100;

    private Player[] players;
    private int numberOfAttempts;

    public Generator(int numberOfPlayer) {
        Generator.numberOfPlayer = numberOfPlayer;
        players = new Player[numberOfPlayer];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(i);

        }
        numberOfAttempts = 0;
    }

    public boolean generateAndWrite(String path, int numberOfRounds) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        Random rnd = new Random();
        boolean[] played = new boolean[numberOfPlayer];
        Set<Integer> playersToChoose = new HashSet<Integer>(numberOfPlayer);
        for (int round = 0; round < numberOfRounds; round++) {
            Arrays.fill(played, false);
            playersToChoose.clear();
            for (int p = 0; p < numberOfPlayer; ++p) {
                playersToChoose.add(p);
            }
            Match[] roundMatch = new Match[numberOfPlayer / PLAYERS_IN_MATCH];
            try {
                ++numberOfAttempts;
                for (int p = 0; p < numberOfPlayer / PLAYERS_IN_MATCH; ++p) {
                    //First A1
                    int teamA1 = (int) playersToChoose.toArray()[rnd.nextInt(playersToChoose.size())];
                    removeAndSetPlayed(teamA1, played, playersToChoose);
                    Match match = new Match();
                    match.teamA1 = teamA1;

                    //Second A2
                    List<Integer> tmp = players[teamA1].doCrossWith(played);
                    int teamA2 = (int) playersToChoose.toArray()[rnd.nextInt(tmp.size())];
                    removeAndSetPlayed(teamA2, played, playersToChoose);
                    match.teamA2 = teamA2;

                    //Third B1
                    List<Integer> tmpB1 = players[teamA2].doCrossOpponents(played);
                    int teamB1 = (int) playersToChoose.toArray()[rnd.nextInt(tmpB1.size())];
                    removeAndSetPlayed(teamB1, played, playersToChoose);
                    match.teamB1 = teamB1;

                    //Forth B2
                    List<Integer> tmpB2 = players[teamB1].doCrossWith(played);
                    int teamB2 = (int) playersToChoose.toArray()[rnd.nextInt(tmpB2.size())];
                    removeAndSetPlayed(teamB2, played, playersToChoose);
                    match.teamB2 = teamB2;

                    setMatch(match);
                    roundMatch[p] = match;
                }
                writeRoundMatchs(round, roundMatch, bw);
            } catch (IllegalArgumentException ex) {
                if (numberOfAttempts > maxAttempts) {
                    System.out.println("I'm not able generate this. To match attempts to generate.");
                    bw.close();
                    return false;
                }
                resetRound(roundMatch);
                --round;
            }
        }
        System.out.println("\nNumber of attempts: " + numberOfAttempts);
        bw.close();
        return true;

    }

    private void removeAndSetPlayed(int player, boolean[] played, Set<Integer> playersToChoose) {
        played[player] = true;
        playersToChoose.remove(player);
    }

    void setMatch(Match match) {
        players[match.teamA1].playWith(match.teamA2);
        players[match.teamA2].playWith(match.teamA1);

        players[match.teamB1].playWith(match.teamB1);
        players[match.teamB2].playWith(match.teamB2);

        players[match.teamA1].setOpponents(match.teamB1, match.teamB2);
        players[match.teamA2].setOpponents(match.teamB1, match.teamB2);

        players[match.teamB1].setOpponents(match.teamA1, match.teamA2);
        players[match.teamB2].setOpponents(match.teamA1, match.teamA2);
    }

    void removeSetMatch(Match match) {
        players[match.teamA1].removePlayWith(match.teamA2);
        players[match.teamA2].removePlayWith(match.teamA1);

        players[match.teamB1].removePlayWith(match.teamB1);
        players[match.teamB2].removePlayWith(match.teamB2);

        players[match.teamA1].removeSetOpponents(match.teamB1, match.teamB2);
        players[match.teamA2].removeSetOpponents(match.teamB1, match.teamB2);

        players[match.teamB1].removeSetOpponents(match.teamA1, match.teamA2);
        players[match.teamB2].removeSetOpponents(match.teamA1, match.teamA2);
    }

    void writeRoundMatchs(int round, Match[] matchs, BufferedWriter bw) throws IOException {
        System.out.println("\nRound " + round);
        if (round != 0) {
            bw.write("\n");
        }
        for (Match match : matchs) {
            System.out.println((match.teamA1 + 1) + ";" + (match.teamA2 + 1) + ";" + (match.teamB1 + 1) + ";" + (match.teamB2 + 1));
            bw.write((match.teamA1 + 1) + ";" + (match.teamA2 + 1) + ";" + (match.teamB1 + 1) + ";" + (match.teamB2 + 1) + "\n");
        }
    }

    private void resetRound(Match[] roundMatch) {
        for (int i = 0; i < roundMatch.length; i++) {
            Match match = roundMatch[i];
            if (match == null) {
                return;
            }
            removeSetMatch(match);

        }
    }

}
