/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aleksandar_simeunovic.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Getter
@Builder
public class Strela {
    private final int starting_x;
    private final int starting_y;
    private int x,y;
    private int speed,length;

    /*public Strela(int starting_x,int starting_y,int speed,int length){
        this.starting_x=starting_x;
        this.x=starting_x;
        this.starting_y=starting_y;
        this.y=starting_y;
        this.speed=speed;
        this.length=length;
    }*/
    public void moveStrela(){
        x+=speed;
    }

    public void moveOnStartingPosition(){this.x=starting_x; this.y=starting_y;}

}
