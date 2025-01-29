package com.aleksandar_simeunovic.player;

import com.aleksandar_simeunovic.dto.ClientMessage;
import com.aleksandar_simeunovic.dto.ServerResponse;
import com.aleksandar_simeunovic.entities.Strela;
import com.aleksandar_simeunovic.logic.BGame;
import com.aleksandar_simeunovic.logic.Game;
import com.aleksandar_simeunovic.logic.GameUtil;
import com.aleksandar_simeunovic.model.BModel;
import com.aleksandar_simeunovic.model.Model;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

@Data
public class Player implements Runnable {
    private PlayerInfo playerInfo;
    private PlayerClient client;
    private final Strela strela;
    private static final Game game=BGame.build();
    private static final Model model=BModel.build();

    private static final ObjectMapper mapper=new ObjectMapper();
    private static final ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

    public Player(Socket cs,int id,Strela strela){
        client=new PlayerClient(cs);
        playerInfo=new PlayerInfo(0,0,id,"",false,false);
        this.strela=strela;
    }

    public void run(){

        try {
            DataInputStream dis=client.getDis();

            while(true)
            {
                String s=dis.readUTF();
                ClientMessage message=mapper.readValue(s,ClientMessage.class);

                switch(message.type){
                    case HIT:
                        if(playerInfo.getReady()){
                            playerInfo.setHitting(true);
                            System.out.println("Hit");
                        }

                        break;
                    case PAUSE:
                        playerInfo.setReady(false);
                        System.out.println("Pause");
                        break;
                    case READY:
                        playerInfo.setReady(true);
                        if(PlayerUtil.allPlayersReady(Game.getPlayers())){
                            System.out.println("Ready");
                            synchronized (game) {
                                game.notifyAll();
                            }

                        }
                        break;
                    case SET_NAME:
                        if(PlayerUtil.NoPlayerWithName(Game.getPlayers(),message.message)) {
                            playerInfo.setName(message.message);
                            System.out.println("SetName");
                            if(PlayerUtil.allPlayersHaveName(Game.getPlayers())){
                                synchronized (game){
                                    game.notifyAll();
                                }
                            }
                        }

                        break;
                }
            }
        }
        catch (IOException exception){
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, exception);
        }
    }

    public void SendData(){
        ServerResponse response=ServerResponse.builder()
                .allBall(model.getAllBall())
                .allStrela(model.getAllStrela())
                .points(playerInfo.getPoints())
                .attempts(playerInfo.getAttempts())
                .game_running(Game.getGame_info().isGame_running())
                .winner(Game.getGame_info().getWinner())
                .build();
        try {
            client.getDos().writeUTF(ow.writeValueAsString(response));
        }
        catch (IOException exception){
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, exception);
        }
    }
}
