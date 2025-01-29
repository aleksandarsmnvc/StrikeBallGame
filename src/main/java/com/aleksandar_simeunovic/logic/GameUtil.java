package com.aleksandar_simeunovic.logic;

import com.aleksandar_simeunovic.constants.Constants;
import com.aleksandar_simeunovic.entities.Ball;
import com.aleksandar_simeunovic.entities.Strela;
import com.aleksandar_simeunovic.player.Player;

public class GameUtil {

    public static Boolean StrelaHitBall(Strela strela,Ball ball){
        return strela.getY() >= ball.getY() && strela.getY() <= ball.getY() + 2 * ball.getR()
                && strela.getX() + strela.getLength() >= ball.getX() &&
                strela.getX() + strela.getLength() <= ball.getX() + ball.getR();
    }

    public static Boolean StrelaHitWall(Strela strela){
        return strela.getX() + strela.getLength() >= Constants.DEFAULT_PLAYING_WINDOW_WIDTH;
    }

    public static Boolean BallHitWall(Ball ball){
        return ball.getY()+2* ball.getR()+
                ball.getSpeed()>Constants.DEFAULT_PLAYING_WINDOW_HEIGHT||
                ball.getY()+ ball.getSpeed()<0;
    }

    public static void HitSuccess(Player player,Ball ball){
        player.getPlayerInfo().setHitting(false);
        player.getPlayerInfo().addAttempt();
        player.getPlayerInfo().addPoints(ball.getBall_value());
        player.getStrela().moveOnStartingPosition();
        ball.moveOnStartingPosition();
    }

    public static void HitFail(Player player){
        player.getPlayerInfo().setHitting(false);
        player.getPlayerInfo().addAttempt();
        player.getStrela().moveOnStartingPosition();
    }

}
