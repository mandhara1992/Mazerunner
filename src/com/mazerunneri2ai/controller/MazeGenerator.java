package com.mazerunneri2ai.controller;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public class MazeGenerator {

    //Display maze
    public static void displayMaze(int maze[][], int dim){

        System.out.println("\n********\n");
        System.out.print("  ");
        for(int i=0;i<dim;i++){
            System.out.print(i+" ");
        }
        System.out.println("\n");
        for(int i=0;i<dim;i++){
            System.out.print(i);
            for(int j=0;j<dim;j++){
                System.out.print(" "+maze[i][j]);
            }
            System.out.println("\n");
        }   

    }

    public static void displayMaze(double maze[][], int dim){

        System.out.print("  ");
        for(int i=0;i<dim;i++){
            System.out.print(i+" ");
        }
        System.out.println("\n");
        for(int i=0;i<dim;i++){
            System.out.print(i);
            for(int j=0;j<dim;j++){
                System.out.print(" "+maze[i][j]);
            }
            System.out.println("\n");
        }   

    }

    public static int[][] calcManhattanDistance(int maze[][],int dim){

        int manhattan[][] = new int[dim][dim];
        for(int i=0;i<dim;i++){
            for (int j=0;j<dim;j++){
                if(maze[i][j] == 0)
                    manhattan[i][j] = 2*(dim-1)-i-j;
                
                else
                    manhattan[i][j] = -1;
            }
        }
        return manhattan;
    }

    public static double[][] calcEuclideanDistance(int maze[][],int dim){

        double euclidean[][] = new double[dim][dim];
        for(int i=0;i<dim;i++){
            for (int j=0;j<dim;j++){
            	System.out.println("i - "+i+" j - "+j);
                if(maze[i][j] == 0)
                    euclidean[i][j]= Math.sqrt(Math.pow((dim-1-i),2)+Math.pow((dim-1-j),2));
                else
                    euclidean[i][j] = -1;
            }
        }
        return euclidean;
    }
}
