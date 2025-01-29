package com.aleksandar_simeunovic.player;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor()
public class PlayerInfo {
    private int attempts;
    private int points;
    private int id;
    private String name;
    private Boolean ready;
    private Boolean hitting;

    public void addAttempt(){attempts++;}

    public void addPoints(int points){this.points+=points;}
}
