/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aleksandar_simeunovic.dto;

import com.aleksandar_simeunovic.entities.Ball;
import com.aleksandar_simeunovic.entities.Strela;
import com.aleksandar_simeunovic.player.Player;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.ArrayList;

@Data
@Builder
@Jacksonized
public class ServerResponse {
    public ArrayList<Strela> allStrela;
    public ArrayList<Ball> allBall;
    public int points;
    public int attempts;
    public boolean game_running;
    public String winner;
}
