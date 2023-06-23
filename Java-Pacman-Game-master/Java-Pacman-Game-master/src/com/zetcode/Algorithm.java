package com.zetcode;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Algorithm {
    private static short levelData[] = {
            19, 26, 26, 26, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22,
            21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
            21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
            21, 0, 0, 0, 17, 16, 16, 24, 16, 16, 16, 16, 16, 16, 20,
            17, 18, 18, 18, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 20,
            17, 16, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 16, 24, 20,
            25, 16, 16, 16, 24, 24, 28, 0, 25, 24, 24, 16, 20, 0, 21,
            1, 17, 16, 20, 0, 0, 0, 0, 0, 0, 0, 17, 20, 0, 21,
            1, 17, 16, 16, 18, 18, 22, 0, 19, 18, 18, 16, 20, 0, 21,
            1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21,
            1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21,
            1, 17, 16, 16, 16, 16, 16, 18, 16, 16, 16, 16, 20, 0, 21,
            1, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0, 21,
            1, 25, 24, 24, 24, 24, 24, 24, 24, 24, 16, 16, 16, 18, 20,
            9, 8, 8, 8, 8, 8, 8, 8, 8, 8, 25, 24, 24, 24, 28
    };
    public static int[][]  CreateInput(short[] InputArray,int size){
          int output[][];
         output = new int[size][size];
         for (int i=0;i<size*size;i++){
             int row= i/size;
             int column= i%size;
             if((InputArray[i]& 16)!=0)
             { output[row][column]=1;}
             else output[row][column]=0;
         }

      return output;
    }
    public static int FindwaytoPacman(int[][] Inputdata,int ghost_x,int ghost_y,int Pacman_x,int Pacman_y){
        int direct=-1;
        int dx[]= {1,-1,0,0};
        int dy[]= {0,0,1,-1};
        int [][] chase = new int[15][15];
        boolean visited[][]= new boolean[15][15];
        for (int i=0;i<15;i++)
            for(int j=0;j<15;j++)
            {
                visited[i][j]=false;
                chase[i][j]=-1;
            }
        Queue<int[]> queue= new LinkedList<>();
       int [] begin= {ghost_x,ghost_y};
       queue.add(begin);
       visited[ghost_x][ghost_y]=true;
       while (  !queue.isEmpty() && visited[Pacman_x][Pacman_y]==false){
           int [] head= new int[2];
           head= queue.remove();
           for (int i=0;i<4;i++){
               int x_test=head[0]+dx[i];
               int y_test= head[1]+dy[i];
               if (x_test>=0 && x_test<15 && y_test>=0 && y_test<15 && Inputdata[x_test][y_test]!=0 && visited[x_test][y_test]==false)
               /*
               010101010-16 bit
               bit dau the hien co tuong ben trai
               bit 2 the hien co tuong tren
               bit 3 the hien co tuong ben trai
               bit 4 the hien co tuong duoi
               bit cuoi la co diem hoac khong
               */
               {    int [] next={x_test,y_test};
                   queue.add(next);
                   visited[x_test][y_test]=true;
                   chase[x_test][y_test]=i;
                   if(visited[Pacman_x][Pacman_y]==true){
                      return ChaseMap(Inputdata,chase,Pacman_x,Pacman_y,ghost_x,ghost_y);

                   }

               }}}

        return direct;
    }
    public static int ChaseMap(int [][] Input,int[][] chase,int pacman_x,int pacman_y,int ghost_x,int ghost_y){

        while ((pacman_x!=ghost_x) || (pacman_y!=ghost_y)){
            if(chase[pacman_x][pacman_y]==0){
                pacman_x--;
                if(pacman_x==ghost_x && pacman_y==ghost_y){
                    return 0;}
                }

            if(chase[pacman_x][pacman_y]==1){
                pacman_x++;
                if(pacman_x==ghost_x && pacman_y==ghost_y){
                    return 1;
                }

            }
            if(chase[pacman_x][pacman_y]==2){
                pacman_y--;

                if(pacman_x==ghost_x && pacman_y==ghost_y){
                    return 2;
                }
                }

            if(chase[pacman_x][pacman_y]==3){
                pacman_y++;

                if(pacman_x==ghost_x && pacman_y==ghost_y){
                    return 3;
                }
            }}

      return 5;
    }
    public static void main(String args[]){


    }

}
