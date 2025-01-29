package com.aleksandar_simeunovic.logic;

public class BGame {
    private final static Game game = new Game();

    public static Game build(){
        return game;
    }
}
