/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aleksandar_simeunovic.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class Ball {
    private final int starting_x;
    private final int starting_y;
    private int x,y;
    private int speed;
    private final int r;
    private final int ball_value;

    public void moveBall(){
        y+=speed;
    }

    /*public Ball(int starting_x,int starting_y,int speed,int r,int ball_value){
        this.starting_x=starting_x;
        this.starting_y=starting_y;
        this.x=starting_x;
        this.y=starting_y;
        this.speed=speed;
        this.r=r;
        this.ball_value=ball_value;
    }*/

    public void moveOnStartingPosition(){x=starting_x; y=starting_y;}

    public void changeDirection(){
        speed=-speed;
    }

}
