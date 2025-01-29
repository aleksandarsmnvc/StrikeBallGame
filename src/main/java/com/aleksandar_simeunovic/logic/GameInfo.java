package com.aleksandar_simeunovic.logic;

import com.aleksandar_simeunovic.player.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class GameInfo {
    private final int number_of_players;
    private boolean game_running;
    public boolean getGame(){return  game_running;}
    private String winner;

}
