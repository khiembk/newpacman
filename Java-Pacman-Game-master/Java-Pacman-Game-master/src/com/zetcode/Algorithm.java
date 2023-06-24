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

    public static int FindwaytoPacman(short[] Inputdata,int ghost_x,int ghost_y,int Pacman_x,int Pacman_y){
        int direct=-1;

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
           int pos = head[0]  +  (head[1])*15;
           int dx[]= {1,-1,0,0};
           int dy[]= {0,0,1,-1};
           for(int i=0;i<4;i++){
               if((Inputdata[pos] &1)!=0 ){
                   if(dx[i]==-1){
                       dx[i]=0;
                   }
               }
               if((Inputdata[pos] & 4)!=0){
                  if(dx[i]==1){
                      dx[i]=0;
                  }
               }
               if( (Inputdata[pos] & 8)!=0){
                   if(dy[i]==1){
                       dy[i]=0;
                   }
               }
               if((Inputdata[pos] & 2)!=0){
                   if(dy[i]==-1){
                       dy[i]=0;

                   }
               }
           }
           for(int i=0;i<4;i++){
               if(dx[i]!=dy[i]){
                   int x_test= head[0]+dx[i];
                   int y_test= head[1]+dy[i];
                   if(x_test>= 0 && x_test<15 && y_test>=0 && y_test<15 && visited[x_test][y_test]==false ){
                       int [] next={x_test,y_test};
                       queue.add(next);
                       visited[x_test][y_test]= true;
                       chase[x_test][y_test]=i;
                       if(visited[Pacman_x][Pacman_y]==true){
                           return ChaseMap(chase,Pacman_x,Pacman_y,ghost_x,ghost_y);
                       }

                   }

               }

           }

               }

        return direct;
    }
    public static int ChaseMap(int[][] chase,int pacman_x,int pacman_y,int ghost_x,int ghost_y){

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
        System.out.println(FindwaytoPacman(levelData,7,11,7,11));
    }

}
