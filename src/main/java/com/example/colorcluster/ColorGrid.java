package com.example.colorcluster;

import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class ColorGrid {

    Color[][] colors;
    int colorInputSize;
    int radius;
    float learnRate;
    ArrayList<Color> arrayList;
    Random random;
    int x;
    int y;
    int epic ;


    ColorGrid() {
        this(new Random(), new ArrayList<>());
    }

    ColorGrid(Random random) {
        this.random = random;
        this.arrayList = new ArrayList<>();
    }

    ColorGrid(Random random, ArrayList<Color> arrayList) {
        this.random = random;
        this.arrayList = arrayList;
        System.out.println("object Created ");

    }



    void fillGrid(int x, int y) {
        colors = new Color[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                colors[i][j] = Color.rgb(
                        random.nextInt(256),
                        random.nextInt(256),
                        random.nextInt(256)
                );
            }
        }
        System.out.println("array Filled");
    }


    void insertColor(int red, int green, int blue) {
        this.arrayList.add(
                Color.rgb(
                        red,
                        green,
                        blue
                )
        );
    }
    void  solve( ){
        boolean changing=true;
        System.out.println("epic is "+this.epic);
        // TODO:epoch or stops changing
//// && this.epic !=0

        int count =0;
        if(this.epic!=0) {
            while (this.epic != 0) {
                for (Color color : this.arrayList) {
                    Pair<Integer, Integer> bmu = bestMatchingUnit(color);
                    changing = update(color, bmu);
                }
                epic--;

            }
        }
        else{
            while(changing) {
                if (count>100000)
                    break;
               System.out.println(count++);
                for (Color color : this.arrayList) {
                    Pair<Integer, Integer> bmu = bestMatchingUnit(color);
                    changing=update(color, bmu);
                }

            }

        }

    }


    Pair<Integer,Integer> bestMatchingUnit(Color inputColor){
        double minDist;
        minDist=Double.MAX_VALUE;
        double red;
        double blue;
        double green;
        double distance;
        Pair<Integer,Integer> pos=null;
        for (int i=1;i<x;i++){
            for (int j=1;j<y;j++){
                red= this.colors[i][j].getRed()-inputColor.getRed();
                blue= this.colors[i][j].getBlue()-inputColor.getBlue();
                green= this.colors[i][j].getGreen()-inputColor.getGreen();
                distance=Math.sqrt(red+blue+green);
                if(minDist>distance) {
                    minDist = distance;
                    pos=new Pair<>(i,j);
                }
            }
        }
        return pos;
    }
    boolean update(Color inputColor,Pair<Integer,Integer> pos){
        boolean change=false;
        for (int i=0;i<x;i++){
            for (int j=0;j<y;j++) {
                int distance = (i - pos.getKey()) * (i - pos.getKey()) + (j - pos.getValue()) * (j - pos.getValue());//pos
                if(distance <=radius*radius){
                    double learnAndW=learnRate*weight(distance);
                    double red=colors[i][j].getRed()+learnAndW*(inputColor.getRed()-colors[i][j].getRed());
                    double green=colors[i][j].getGreen()+learnAndW*(inputColor.getGreen()-colors[i][j].getGreen());
                    double blue=colors[i][j].getBlue()+learnAndW*(inputColor.getBlue()-colors[i][j].getBlue());
                    Color nColor=Color.color(red,green,blue);
                    change= change || !Objects.equals(nColor, colors[i][j]);
                    colors[i][j]=Color.color(red,green,blue);

                }
            }
        }
        return change;
    }


    double weight(double d ){
        return  Math.exp(-(d/(2*Math.pow(radius,2))));
    }








}
