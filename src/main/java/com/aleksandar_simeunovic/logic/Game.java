package com.aleksandar_simeunovic.logic;

import com.aleksandar_simeunovic.constants.Constants;
import com.aleksandar_simeunovic.entities.Ball;
import com.aleksandar_simeunovic.entities.Strela;
import com.aleksandar_simeunovic.model.BModel;
import com.aleksandar_simeunovic.model.Model;
import com.aleksandar_simeunovic.player.Player;
import com.aleksandar_simeunovic.player.PlayerClient;
import com.aleksandar_simeunovic.player.PlayerUtil;
import lombok.RequiredArgsConstructor;

import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.*;


public class Game{
    private static final GameInfo game_info=new GameInfo(Constants.DEFAULT_NUM_OF_PLAYERS);
    private static final Model model=BModel.build();
    private static final ArrayList<Player> players=new ArrayList<>();
    private static final ExecutorService service=Executors.newCachedThreadPool();

    public Game(){
        model.addObserver(()->{
            players.forEach(Player::SendData);
        });
        CreateGameElements();
    }

    public static ArrayList<Player> getPlayers(){return players;}
    public static GameInfo getGame_info(){return game_info;}

    public void addPlayer(Socket cs,int id){
        Player player=new Player(cs,id,model.getAllStrela().get(id));
        players.add(player);
    }

    private void StartPlayerThreads(){
        players.forEach((service::submit));
    }

    private void CreateGameElements(){
        int n= game_info.getNumber_of_players();

        for(int i=0;i<n;i++)
        {
            int starting_y=(Constants.DEFAULT_PLAYING_WINDOW_HEIGHT-Constants.TOP_MARGIN_OFFSET
                    -Constants.BOTTOM_MARGIN_OFFSET)/(n-1);
            model.addStrela(Strela.builder()
                            .starting_x(Constants.DEFAULT_START_X_STRELA)
                            .starting_y(starting_y)
                            .x(Constants.DEFAULT_START_X_STRELA)
                            .y(starting_y)
                            .speed(Constants.DEFAULT_STRELA_SPEED)
                            .length(Constants.DEFAULT_STRELA_LENGTH)
                            .build());
        }
        model.addBall(Ball.builder()
                        .starting_x(Constants.DEFAULT_PLAYING_WINDOW_WIDTH / 2)
                        .starting_y(Constants.DEFAULT_START_Y_BALL)
                        .x(Constants.DEFAULT_PLAYING_WINDOW_WIDTH/2)
                        .y(Constants.DEFAULT_START_Y_BALL)
                        .r(10)
                        .speed(4)
                        .ball_value(1)
                        .build());
        model.addBall(Ball.builder()
                .starting_x(3*Constants.DEFAULT_PLAYING_WINDOW_WIDTH / 4)
                .starting_y(Constants.DEFAULT_START_Y_BALL)
                .x(3*Constants.DEFAULT_PLAYING_WINDOW_WIDTH / 4)
                .y(Constants.DEFAULT_START_Y_BALL)
                .r(5)
                .speed(8)
                .ball_value(2)
                .build());
    }

    public void CheckForEnd(){
        Stream<Player>stream=players.stream();
        Stream<Player>winnerStream=stream
                .filter((player)->player.getPlayerInfo().getPoints()>= Constants.POINTS_TO_WIN);
        Optional<Player>winnerPlayer=winnerStream.findFirst();
        if(winnerPlayer.isPresent()){
            game_info.setGame_running(false);
            game_info.setWinner(winnerPlayer.get().getPlayerInfo().getName());
        }
    }

    public void ResetGameState(){
        game_info.setGame_running(true);

        players.forEach(PlayerUtil::ResetPlayerInfo);

        model.getAllBall().forEach(Ball::moveOnStartingPosition);

        model.getAllStrela().forEach(Strela::moveOnStartingPosition);
    }

    public void NextState(){
        players.forEach((player)-> {
            Strela player_strela = player.getStrela();
            model.getAllBall().forEach((ball) -> {
                if (GameUtil.StrelaHitBall(player_strela, ball)) GameUtil.HitSuccess(player, ball);
            });
            if (GameUtil.StrelaHitWall(player_strela)) GameUtil.HitFail(player);
            if(player.getPlayerInfo().getHitting())player_strela.moveStrela();
        });

        model.getAllBall().forEach((ball)->{
            if(GameUtil.BallHitWall(ball)) ball.changeDirection();
            ball.moveBall();
        });
    }

    public void GameLoop(){
        StartPlayerThreads();
        game_info.setGame_running(true);
        while(true){
            if(PlayerUtil.allPlayersReady(players)&& PlayerUtil.allPlayersHaveName(players))
            {
                System.out.println("Wait ended");
                NextState();
                CheckForEnd();
                model.ref();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException exception) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, exception);
                }
            }
            else
            {
                try {
                    synchronized(this)
                    {
                        System.out.println("Waiting");
                        wait();
                    }
                } catch (InterruptedException exception) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, exception);
                }
            }
            if(!game_info.isGame_running())ResetGameState();
        }
    }
}
