package com.example.colorcluster;

import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class ColorGrid {

    private Color[][] colors;
    private int colorInputSize;
    private int initialRadius;
    private float  initialLearningRate;
    private final ArrayList<Color> arrayList;
    private final Random random;
    private int x;
    private int y;
    private int epoch;

    public Color[][] getColors() {
        return colors;
    }

    public int getColorInputSize() {
        return colorInputSize;
    }

    public void setColorInputSize(int colorInputSize) {
        this.colorInputSize = colorInputSize;
    }

    public void setRadius(int initialRadius) {
        this.initialRadius = initialRadius;
    }

    public void setLearnRate(float initialLearningRate) {
        this.initialLearningRate = initialLearningRate;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setEpoch(int epoch) {
        this.epoch = epoch;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    ColorGrid() {
        this(new Random(), new ArrayList<>());
    }

    ColorGrid(Random random, ArrayList<Color> arrayList) {
        this.random = random;
        this.arrayList = arrayList;
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
        for(int i = 0; i<this.epoch; i++){
            for (Color color : this.arrayList) {
                Pair<Integer, Integer> bmu = bestMatchingUnit(color);
                double learnRate=initialLearningRate*equation(i);
                int radius=(int)(initialRadius*equation(i));
                if(radius==0)continue;
                changing = update(color, bmu,learnRate,radius);

            }
            if(!changing)break;
        }
    }


    Pair<Integer,Integer> bestMatchingUnit(Color inputColor){
        double minDist=Double.MAX_VALUE;
        double red;
        double blue;
        double green;
        double distance;
        Pair<Integer,Integer> pos=null;
        for (int i=0;i<x;i++){
            for (int j=0;j<y;j++){
                red= this.colors[i][j].getRed()-inputColor.getRed();
                blue= this.colors[i][j].getBlue()-inputColor.getBlue();
                green= this.colors[i][j].getGreen()-inputColor.getGreen();
                distance=Math.sqrt(red*red+blue*blue+green*green);
                if(distance<minDist) {
                    minDist = distance;
                    pos=new Pair<>(i,j);
                }
            }
        }
        return pos;
    }
    boolean update(Color inputColor,Pair<Integer,Integer> pos,double learnRate,int radius){
        boolean change=false;
        for (int i=0;i<x;i++){
            for (int j=0;j<y;j++) {
                int distance = (i - pos.getKey()) * (i - pos.getKey()) + (j - pos.getValue()) * (j - pos.getValue());//pos
                double learnAndW=learnRate*weight(distance,radius);
                double red=colors[i][j].getRed()+learnAndW*(inputColor.getRed()-colors[i][j].getRed());
                double green=colors[i][j].getGreen()+learnAndW*(inputColor.getGreen()-colors[i][j].getGreen());
                double blue=colors[i][j].getBlue()+learnAndW*(inputColor.getBlue()-colors[i][j].getBlue());
                Color nColor=Color.color(red,green,blue);
                change= change || !Objects.equals(nColor, colors[i][j]);
                colors[i][j]=Color.color(red,green,blue);
            }
        }
        return change;
    }
    double equation(int currentEpoch){
        return Math.exp(-currentEpoch*(Math.log((double)x*y)/epoch));
    }

    double weight(double d ,int radius){
        return  Math.exp(-(d/(2*Math.pow(radius,2))));
    }








}
