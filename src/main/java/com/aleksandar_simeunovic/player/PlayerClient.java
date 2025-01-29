package com.aleksandar_simeunovic.player;

import lombok.Data;
import lombok.Getter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
public class PlayerClient {
    private final Socket cs;

    private DataInputStream dis;
    private DataOutputStream dos;

    public PlayerClient(Socket cs){
        this.cs=cs;

        try{
            dis=new DataInputStream(cs.getInputStream());
            dos=new DataOutputStream(cs.getOutputStream());
        }
        catch (IOException exception)
        {
            Logger.getLogger(PlayerClient.class.getName()).log(Level.SEVERE, null, exception);
        }
    }


}
