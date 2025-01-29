package com.aleksandar_simeunovic.drawing;

import com.aleksandar_simeunovic.entities.Ball;
import com.aleksandar_simeunovic.entities.Strela;

import java.awt.*;

public class Drawing {

    public static void DrawBall(Ball ball, Graphics2D g2d){
        g2d.setColor(Color.red);
        g2d.fillOval(ball.getX(), ball.getY(), 2*ball.getR(), 2*ball.getR());
    }

    public static void DrawStrela(Strela strela, Graphics2D g2d){
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine(strela.getX(), strela.getY(),strela.getX()+strela.getLength()-6,strela.getY());
        int x_cord[]=new int[3];
        int y_cord[]=new int[3];
        x_cord[0]=strela.getX()+strela.getLength()-6;
        x_cord[1]=x_cord[0];
        x_cord[2]= strela.getX()+ strela.getLength();
        y_cord[0]= strela.getY()-3;
        y_cord[1]= strela.getY()+3;
        y_cord[2]= strela.getY();
        Polygon p=new Polygon(x_cord,y_cord,3);
        g2d.fillPolygon(p);
    }
}
