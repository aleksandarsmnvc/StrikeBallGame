/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aleksandar_simeunovic.server;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.aleksandar_simeunovic.constants.Constants;
import com.aleksandar_simeunovic.logic.BGame;
import com.aleksandar_simeunovic.logic.Game;
import com.aleksandar_simeunovic.logic.GameInfo;
import com.aleksandar_simeunovic.player.Player;

public class MainServer {
    private static Game game;
    InetAddress ip = null;
    public MainServer(){
        game= BGame.build();
    }
    public void ServerStart(){
        ServerSocket ss;
        try {
            Set<Socket> connectedSockets=new HashSet<>();

            ip = InetAddress.getLocalHost();
            ss = new ServerSocket(Constants.DEFAULT_SERVER_PORT_NUMBER, 0, ip);
            System.out.append("Server start\n");
            int id=0;
            GameInfo gameInfo=Game.getGame_info();
            int num_of_players=gameInfo.getNumber_of_players();
            while(id!=num_of_players)
            {
                Socket cs;
                cs = ss.accept();
                if(!connectedSockets.contains(cs)) {
                    System.out.println("Connection accepted! Id:+"+id);
                    connectedSockets.add(cs);
                    game.addPlayer(cs,id++);
                }
            }
            
        } catch (IOException exception) {
            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, exception);
        }
        System.out.println("Game Started");
        game.GameLoop();
    }

    public static void main(String[] args) {
        new MainServer().ServerStart();
    }
}
