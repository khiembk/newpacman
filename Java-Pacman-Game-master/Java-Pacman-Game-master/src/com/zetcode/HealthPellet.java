package com.zetcode;

import javax.swing.*;
import java.awt.*;

public class HealthPellet {
    int x;
    int y;
    boolean eaten;
    Image Img;
    int getX(){
        return x;
    }
    int getY(){
        return y;
    }
    void setX(int x){
        this.x=x;
    }
    void setY(int y){
        this.y=y;
    }
    Image getImg(){
        return Img;
    }
    public HealthPellet(){
        int x= (int)(Math.random()*15)*24;
        int y= (int)(Math.random()*15)*24;
        this.setX(x);
        this.setY(y);
        String dir= System.getProperty("user.dir");
        this.Img= new ImageIcon(dir+"/resources/images/cherry.png").getImage();
        this.eaten=false;
    }
    public void setEaten(boolean eat){
        this.eaten=eat;
    }
    public boolean getEaten(){
        return this.eaten;
    }
}
