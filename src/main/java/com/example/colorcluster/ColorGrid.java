package com.example.colorcluster;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class ColorGrid {

    Color[][] colors;
    int colorInputSize;
    int radius;
    float learnRate;
    ArrayList<Color> arrayList;
    Random random;
    int x,y,x_pos,y_pos;
    Color minColor;
    double minDist;

    ColorGrid() {
        this(new Random(), new ArrayList<>());
    }

    ColorGrid(Random random) {
        this.random = random;
        this.arrayList = new ArrayList<>();
        //System.out.println("object Created ");


    }

    ColorGrid(Random random, ArrayList<Color> arrayList) {
        this.random = random;
        this.arrayList = arrayList;
        System.out.println("object Created ");

    }

//    void solve() {
//        for (Color color : arrayList) {
//            for (int i = 0; i < colors.length; i++) {
//                for (int j = 0; j < colors[0].length; j++) {
//                    ///bestMatchingUnit();
//                }
//            }
//
//
//
//
//
//            for (int i = 0; i < colors.length; i++) {
//                for (int j = 0; j < colors[0].length; j++) {
//                    //* change best match radius
//                }
//            }
//        }
//    }

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

    /*
    * for ( int i=0;i<epic;i++){
    * raduis=raduis*Math.exp()
    * }
    *
    *
    *
    * */



    void  solve( ){

        for(int i = 0; i < this.arrayList.size(); i++){
            bestMatchingUnit( arrayList.get(i) );
            update(arrayList.get(i));
        }

    }


    void  bestMatchingUnit(Color inputColor ){

        int red= (int) (this.colors[0][0].getRed()-inputColor.getRed());
        int blue= (int) (this.colors[0][0].getBlue()-inputColor.getBlue());
        int green= (int) (this.colors[0][0].getGreen()-inputColor.getGreen());
        minDist=Math.sqrt(red+blue+green);
        double distance;
        for (int i=1;i<x;i++){
            for (int j=1;j<y;j++){
               red= (int) (this.colors[i][j].getRed()-inputColor.getRed());
                 blue= (int) (this.colors[i][j].getBlue()-inputColor.getBlue());
                 green= (int) (this.colors[i][j].getGreen()-inputColor.getGreen());
                 distance=Math.sqrt(red+blue+green);
                 if(minDist>distance) {
                     minDist = distance;
                     minColor=this.colors[i][j];
                     x_pos=i;
                     y_pos=j;
                 }

            }


        }


    }

    void update(Color inputColor){

        for (int i=0;i<x;i++){
            for (int j=0;j<y;j++) {
            if((i-x_pos)*(i-x_pos)+(j - y_pos)*(j - y_pos)<=radius*radius) ///(x - xCenter)*(x - xCenter) + (y - yCenter)*(y - yCenter) <= r*r
            {
                int    red= (int) ((int ) (inputColor.getRed()-this.colors[i][j].getRed())*learnRate*weight((i-x_pos)*(i-x_pos)+(j - y_pos)*(j - y_pos)));
                int  blue= (int) ((int )(inputColor.getBlue()-this.colors[i][j].getBlue())*learnRate*weight((i-x_pos)*(i-x_pos)+(j - y_pos)*(j - y_pos)));
                int  green= (int) ((int)(inputColor.getGreen()-this.colors[i][j].getGreen())*learnRate*weight((i-x_pos)*(i-x_pos)+(j - y_pos)*(j - y_pos)));
                Color c = Color.rgb(Math.abs(red),Math.abs(green),Math.abs(blue));
                System.out.print(red);

                red= (int) (colors[i][j].getRed()+c.getRed());
                green= (int) (colors[i][j].getGreen()+c.getGreen());
                blue= (int) (colors[i][j].getBlue()+c.getBlue());

                c=Color.rgb((int)Math.abs(red),(int)Math.abs(green),(int)Math.abs(blue));
                colors[i][j]=c;
            }


            }
        }



    }


    double weight(float d ){
        return  Math.exp(-(Math.pow(d,2)/(2*Math.pow(radius,2))));

    }








}
