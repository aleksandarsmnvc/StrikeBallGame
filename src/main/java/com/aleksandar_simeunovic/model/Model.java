/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aleksandar_simeunovic.model;

import com.aleksandar_simeunovic.entities.Ball;
import com.aleksandar_simeunovic.entities.Strela;

import java.util.ArrayList;

/**
 *
 * @author aleks
 */
public class Model {
    private ArrayList<Strela> all_Strela = new ArrayList<>();
    private ArrayList<Ball> all_Ball=new ArrayList();
    private ArrayList<IObserver> all_Observers = new ArrayList<>();
    
    public void ref()
    {
        for (IObserver o : all_Observers) {
            o.ref();
        }
    }
    
    
    public void setAllStrela(ArrayList<Strela> allStrela){
        this.all_Strela = allStrela;
    }
    public void setAllBall(ArrayList<Ball> allBall)
    {
        this.all_Ball=allBall;
    }
    
    public  void addObserver(IObserver o)
    {
        all_Observers.add(o);
    }
    
    public void addBall(Ball b)
    {
        all_Ball.add(b);
    }
    public void addStrela(Strela p)
    {
        all_Strela.add(p);
    }

    public ArrayList<Strela> getAllStrela() {
        return all_Strela;
    }
    public ArrayList<Ball> getAllBall()
    {
        return all_Ball;
    }
}
