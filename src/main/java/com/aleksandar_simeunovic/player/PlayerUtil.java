package com.aleksandar_simeunovic.player;

import java.util.ArrayList;

public class PlayerUtil {
    public static void ResetPlayerInfo(Player player){
        player.getPlayerInfo().setHitting(false);
        player.getPlayerInfo().setReady(false);
        player.getPlayerInfo().setAttempts(0);
        player.getPlayerInfo().setPoints(0);
    }

    public static boolean allPlayersReady(ArrayList<Player> players){
        return players.stream().
                allMatch(player -> player.getPlayerInfo().getReady());
    }

    public static boolean allPlayersHaveName(ArrayList<Player> players){
        return players.stream().noneMatch(player->player.getPlayerInfo().getName().isEmpty());
    }

    public static boolean NoPlayerWithName(ArrayList<Player> players,String name){
        return players.stream().
                noneMatch(player -> player.getPlayerInfo().getName().equals(name));
    }
}
