package com.zetcode;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;



public class Board extends JPanel implements ActionListener {

    private Dimension d;
    private final Font smallFont = new Font("Helvetica", Font.BOLD, 14);

    private Image ii;
    private final Color dotColor = new Color(192, 192, 0);
    private Color mazeColor;

    private boolean inGame = false;
    private boolean dying = false;

    private final int BLOCK_SIZE = 24;
    private final int N_BLOCKS = 15;
    private final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
    private final int PAC_ANIM_DELAY = 2;
    private final int PACMAN_ANIM_COUNT = 4;
    private final int MAX_GHOSTS = 12;
    private final int PACMAN_SPEED = 6;
    private boolean eattenpoint= false;
    private int pacAnimCount = PAC_ANIM_DELAY;
    private int pacAnimDir = 1;
    private int pacmanAnimPos = 0;
    private int N_GHOSTS = 6;
    private int pacsLeft, score;
    private int[] dx, dy;
    private boolean ghostdeath[];

    private int[] ghost_x, ghost_y, ghost_dx, ghost_dy, ghostSpeed,ghost_color;
    private Image ghost,ghost1,ghostUp, ghostDown,ghostRight,ghostLeft;
    private Image pacman1, pacman2up,pacman2left, pacman2right, pacman2down;
    private Image pacman3up, pacman3down, pacman3left, pacman3right;
    private Image pacman4up, pacman4down, pacman4left, pacman4right;

    private int pacman_x, pacman_y, pacmand_x, pacmand_y;
    private int req_dx, req_dy, view_dx, view_dy;
    private int lukypoint_x;
    private int lukypoint_y;
    private int magicfood_x;
    private int magicfood_y;
    private boolean mad= false;
    private boolean eatfood;
    private int highscore=100;
    private final Image ghostUpList[]= {
            new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/ghostUp1.png").getImage(),
            new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/ghostUp2.png").getImage(),
            new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/ghostUp3.png").getImage(),
            new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/ghostUp4.png").getImage()};
    private final Image ghostDownList[]={
            new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/ghostDown1.png").getImage(),
            new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/ghostDown2.png").getImage(),
            new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/ghostDown3.png").getImage(),
            new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/ghostDown4.png").getImage()
    };
    private final Image ghostRightList[]={
            new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/ghostRight1.png").getImage(),
            new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/ghostRight2.png").getImage(),
            new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/ghostRight3.png").getImage(),
            new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/ghostRight4.png").getImage()
    };
    private final Image ghostLeftList[]= {
            new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/ghostLeft1.png").getImage(),
            new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/ghostLeft2.png").getImage(),
            new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/ghostLeft3.png").getImage(),
            new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/ghostLeft4.png").getImage()
    };
    private final short levelData[] = {
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
    private final short hustData[]={19, 18, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 18, 22,
            17, 20,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 17, 20,
            17, 16, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 16, 20,
            17, 16, 16, 16, 16, 16, 16, 16, 24, 24, 16, 24, 24, 16, 20,
            17, 20, 17, 20, 21, 17, 20, 21, 19, 18, 16, 22, 19, 16, 20,
            17, 20, 17, 20, 21, 17, 20, 21, 17, 16, 16, 20, 17, 16, 20,
            17, 20, 25, 28, 21, 17, 20, 21, 25, 24, 16, 20, 17, 16, 20,
            17, 20, 19, 22, 21, 17, 20, 17, 18, 22, 17, 20, 17, 16, 20,
            17, 20, 17, 20, 21, 17, 20, 17, 16, 20, 17, 20, 17, 16, 20,
            17, 20, 17, 20, 21, 25, 28, 17, 24, 28, 17, 20, 17, 16, 20,
            17, 16, 16, 16, 16, 18, 18, 16, 18, 18, 16, 16, 16, 16, 20,
            17, 16, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 16, 20,
            17, 20,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 17, 20,
            17, 16, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 16, 20,
            25, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 28
    };
    private final short matrixData[]={19, 26, 26, 26, 26, 26, 22, 19, 26, 26, 26, 22, 19, 26, 22,
            21, 19, 18, 26, 26, 22, 21, 25, 30, 19, 26, 24, 28, 27, 28,
            21, 21, 25, 26, 22, 21, 25, 26, 22, 21, 19, 26, 26, 22, 23,
            21, 25, 22, 19, 28, 21, 19, 30, 25, 28, 21, 19, 22, 21, 21,
            17, 30, 21, 29, 19, 28, 21, 19, 26, 26, 28, 21, 21, 21, 21,
            25, 22, 21, 19, 28, 27, 20, 21, 19, 18, 22, 21, 25, 24, 28,
            19, 28, 21, 21, 19, 22, 21, 21, 21, 21, 21, 17, 22, 19, 22,
            25, 26, 20, 21, 21, 25, 28, 25, 28, 21, 21, 21, 21, 21, 29,
            19, 22, 21, 21, 25, 26, 18, 30, 19, 28, 21, 21, 21, 21, 23,
            21, 25, 28, 25, 26, 22, 21, 19, 28, 19, 28, 21, 21, 21, 21,
            21, 23, 19, 26, 26, 28, 21, 21, 19, 28, 19, 28, 21, 21, 21,
            25, 28, 21, 19, 26, 26, 28, 21, 25, 22, 21, 23, 21, 21, 21,
            19, 26, 28, 25, 26, 22, 19, 20, 19, 28, 21, 25, 28, 21, 21,
            17, 26, 26, 26, 22, 25, 28, 21, 21, 23, 25, 26, 26, 28, 21,
            25, 26, 26, 30, 25, 26, 26, 28, 25, 24, 26, 26, 26, 26, 28};
    private final int validSpeeds[] = {1, 2, 3, 4, 6, 8};
    private final int maxSpeed = 6;

    private int currentSpeed = 4;
    private short[] screenData;
    private Timer timer;

    public Board() {

        loadImages();
        initVariables();
        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());

        setFocusable(true);

        setBackground(Color.black);
    }

    private void initVariables() {

        screenData = new short[N_BLOCKS * N_BLOCKS];
        mazeColor = new Color(5, 100, 5);
        d = new Dimension(400, 400);
        ghost_x = new int[MAX_GHOSTS];
        ghost_dx = new int[MAX_GHOSTS];
        ghost_y = new int[MAX_GHOSTS];
        ghost_dy = new int[MAX_GHOSTS];
        ghostSpeed = new int[MAX_GHOSTS];
        ghost_color= new int[MAX_GHOSTS];
        ghostdeath = new boolean[MAX_GHOSTS];
        dx = new int[4];
        dy = new int[4];
        for(int j=0;j<MAX_GHOSTS;j++){
            ghost_color[j]= j%4;
            ghostdeath[j]=false;
        }
        timer = new Timer(40, this);
        timer.start();
    }

    @Override
    public void addNotify() {
        super.addNotify();

        initGame();
    }

    private void doAnim() {

        pacAnimCount--;

        if (pacAnimCount <= 0) {
            pacAnimCount = PAC_ANIM_DELAY;
            pacmanAnimPos = pacmanAnimPos + pacAnimDir;

            if (pacmanAnimPos == (PACMAN_ANIM_COUNT - 1) || pacmanAnimPos == 0) {
                pacAnimDir = -pacAnimDir;
            }
        }
    }

    private void playGame(Graphics2D g2d) {

        if (dying) {

            death();

        } else {
            movePacman();
            drawPacman(g2d);
            moveGhosts(g2d);
            checkMaze();
        }
    }
    private void PacmanMad(){
      Thread madthread= new Thread(){
          public void run(){
             try {
                 setPacmanMad();
                 Thread.sleep(5000);
                 PacmancomeNomal();
                 mad=false;
             }catch(InterruptedException e){
                 throw new RuntimeException();
             }
          }
      };
     madthread.start();

    }
    private void showIntroScreen(Graphics2D g2d) {

        g2d.setColor(new Color(0, 32, 48));
        g2d.fillRect(50, SCREEN_SIZE / 2 - 50, SCREEN_SIZE - 100, 110);
        g2d.setColor(Color.white);
        g2d.drawRect(50, SCREEN_SIZE / 2 - 50, SCREEN_SIZE - 100, 110);

        String s = "Press s to start random map";
        String s1 = "Energizer";
        String s2 = "Health pellet";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g2d.setColor(Color.white);
        g2d.setFont(small);
        g2d.drawString(s, (SCREEN_SIZE - metr.stringWidth(s)) / 2, SCREEN_SIZE/2 - 20);
        g2d.drawString(s1, (SCREEN_SIZE - metr.stringWidth(s)) / 2 + 20, SCREEN_SIZE/2 + 10 );
        g2d.drawString(s2, (SCREEN_SIZE - metr.stringWidth(s)) / 2 + 20, SCREEN_SIZE/2 + 40);
        g2d.setColor(Color.blue);
        g2d.fillOval((SCREEN_SIZE - metr.stringWidth(s)) / 2, (SCREEN_SIZE / 2),10,10);
        g2d.setColor(Color.red);
        g2d.fillOval((SCREEN_SIZE - metr.stringWidth(s)) / 2, (SCREEN_SIZE / 2 + 30),10,10);


    }


    private void drawScore(Graphics2D g) {

        int i;
        String s;

        g.setFont(smallFont);
        g.setColor(new Color(96, 128, 255));
        s = "Score: " + score;
        String high= "High score: "+ highscore;
        g.drawString(s, SCREEN_SIZE / 2 + 96, SCREEN_SIZE + 16);
        g.drawString(high, SCREEN_SIZE / 2 -50, SCREEN_SIZE + 16);
        for (i = 0; i < pacsLeft; i++) {
            g.drawImage(pacman3left, i * 28 + 8, SCREEN_SIZE + 1, this);
        }
    }

    private void checkMaze() {

        short i = 0;
        boolean finished = true;

        while (i < N_BLOCKS * N_BLOCKS && finished) {

            if ((screenData[i] & 48) != 0) {
                finished = false;
            }

            i++;
        }


        if (finished) {

            score += 50;

            if (N_GHOSTS < MAX_GHOSTS) {
                N_GHOSTS++;
            }

            if (currentSpeed < maxSpeed) {
                currentSpeed++;
            }

            initLevel();
        }
    }

    private void death() {

        pacsLeft--;

        if (pacsLeft == 0) {
            inGame = false;
        }

        continueLevel();
    }

    private void moveGhosts(Graphics2D g2d) {

        short i;
        int pos;
        int count;

        for (i = 0; i < N_GHOSTS; i++) {
            if(ghostdeath[i]==true)
                 continue;
            if (ghost_x[i] % BLOCK_SIZE == 0 && ghost_y[i] % BLOCK_SIZE == 0) {
                pos = ghost_x[i] / BLOCK_SIZE + N_BLOCKS * (int) (ghost_y[i] / BLOCK_SIZE);

                count = 0;

                if ((screenData[pos] & 1) == 0 && ghost_dx[i] != 1) {
                    dx[count] = -1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 2) == 0 && ghost_dy[i] != 1) {
                    dx[count] = 0;
                    dy[count] = -1;
                    count++;
                }

                if ((screenData[pos] & 4) == 0 && ghost_dx[i] != -1) {
                    dx[count] = 1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 8) == 0 && ghost_dy[i] != -1) {
                    dx[count] = 0;
                    dy[count] = 1;
                    count++;
                }

                if (count == 0) {

                    if ((screenData[pos] & 15) == 15) {
                        ghost_dx[i] = 0;
                        ghost_dy[i] = 0;
                    } else {
                        ghost_dx[i] = -ghost_dx[i];
                        ghost_dy[i] = -ghost_dy[i];
                    }

                } else {

                    int gho_x= ghost_x[i]+(dx[0]*ghostSpeed[i]);
                    int gho_y= ghost_y[i]+(dy[0]*ghostSpeed[i]);
                    int dmin= Math.abs(pacman_y-gho_y)+Math.abs(pacman_x-gho_x);
                    int min=0;
                    int max=0;
                    int dmax=dmin;
                    for(int j=0;j<count;j++){
                         gho_x= ghost_x[i]+(dx[j]*ghostSpeed[i]);
                         gho_y= ghost_y[i]+(dy[j]*ghostSpeed[i]);
                         int d= Math.abs(pacman_y-gho_y)+Math.abs(pacman_x-gho_x);
                        if(d<dmin){
                            dmin=d;
                            min=j;
                        }
                        if(d>dmax){
                            dmax=d;
                            max=j;
                        }
                    }


                    if(mad==true && dmax <3*BLOCK_SIZE ){
                        ghost_dx[i]=dx[max];
                        ghost_dy[i]=dy[max];

                    }else{
                        if(i%2==0){
                            ghost_dx[i] = dx[min];
                            ghost_dy[i] = dy[min];
                        }else {
                            if(dmax<2*BLOCK_SIZE){
                                ghost_dx[i]=dx[min];
                                ghost_dy[i]=dy[min];
                            }else{
                            count=(int)(Math.random()*count);
                            if(count>3){
                                count=3;
                            }
                            ghost_dx[i]=dx[count];
                            ghost_dy[i]=dy[count];}
                        }
                    }
                }

            }

            ghost_x[i] = ghost_x[i] + (ghost_dx[i] * ghostSpeed[i]);
            ghost_y[i] = ghost_y[i] + (ghost_dy[i] * ghostSpeed[i]);
            if (ghost_dx[i]==1 && ghost_dy[i]==0)
            { drawGhost(g2d, ghost_x[i] + 1, ghost_y[i] + 1, 1,ghost_color[i]);}
            if(ghost_dx[i]==-1 && ghost_dy[i]==0){
                drawGhost(g2d, ghost_x[i] + 1, ghost_y[i] + 1, 2,ghost_color[i]);
            }
            if(ghost_dx[i]==0 && ghost_dy[i]==1){
                drawGhost(g2d, ghost_x[i] + 1, ghost_y[i] + 1, 3,ghost_color[i]);
            }
            if(ghost_dx[i]==0 && ghost_dy[i]==-1){
                drawGhost(g2d, ghost_x[i] + 1, ghost_y[i] + 1, 4,ghost_color[i]);
            }
            if (pacman_x > (ghost_x[i] - 12) && pacman_x < (ghost_x[i] + 12)
                    && pacman_y > (ghost_y[i] - 12) && pacman_y < (ghost_y[i] + 12)
                    && inGame  ) {
              if(ghostdeath[i]==false && mad== false)
                dying = true;
              if(ghostdeath[i]==false && mad==true){
                  ghostdeath[i]=true;
                  score+=50;
              }
            }
        }

    }

    private void drawGhost(Graphics2D g2d, int x, int y,int direction,int color) {
       if(direction==1)
       {g2d.drawImage(ghostRightList[color],x,y,this);}
       if(direction==2)
       {g2d.drawImage(ghostLeftList[color],x,y,this);}
       if(direction==3){
           g2d.drawImage(ghostDownList[color],x,y,this);
       }
       if(direction==4){
           g2d.drawImage(ghostUpList[color],x,y,this);
       }
    }


    private void movePacman() {

        int pos;
        short ch;

        if (req_dx == -pacmand_x && req_dy == -pacmand_y) {
            pacmand_x = req_dx;
            pacmand_y = req_dy;
            view_dx = pacmand_x;
            view_dy = pacmand_y;
        }

        if (pacman_x % BLOCK_SIZE == 0 && pacman_y % BLOCK_SIZE == 0) {
            pos = pacman_x / BLOCK_SIZE + N_BLOCKS * (int) (pacman_y / BLOCK_SIZE);
            ch = screenData[pos];

            if ((ch & 16) != 0) {
                screenData[pos] = (short) (ch & 15);
                score++;
                if(highscore<score){
                    highscore=score;
                }
            }

            if (req_dx != 0 || req_dy != 0) {
                if (!((req_dx == -1 && req_dy == 0 && (ch & 1) != 0)
                        || (req_dx == 1 && req_dy == 0 && (ch & 4) != 0)
                        || (req_dx == 0 && req_dy == -1 && (ch & 2) != 0)
                        || (req_dx == 0 && req_dy == 1 && (ch & 8) != 0))) {
                    pacmand_x = req_dx;
                    pacmand_y = req_dy;
                    view_dx = pacmand_x;
                    view_dy = pacmand_y;
                }
            }

            // Check for standstill
            if ((pacmand_x == -1 && pacmand_y == 0 && (ch & 1) != 0)
                    || (pacmand_x == 1 && pacmand_y == 0 && (ch & 4) != 0)
                    || (pacmand_x == 0 && pacmand_y == -1 && (ch & 2) != 0)
                    || (pacmand_x == 0 && pacmand_y == 1 && (ch & 8) != 0)) {
                pacmand_x = 0;
                pacmand_y = 0;
            }
        }
        pacman_x = pacman_x + PACMAN_SPEED * pacmand_x;
        pacman_y = pacman_y + PACMAN_SPEED * pacmand_y;

        if(pacman_x==lukypoint_x && pacman_y==lukypoint_y &&(eattenpoint==false)){
            eattenpoint=true;
            mad=true;
            PacmanMad();
        }
        if(pacman_x==magicfood_x && pacman_y==magicfood_y &&(eatfood==false)){
            eatfood=true;
            pacsLeft++;
        }
    }

    private void drawPacman(Graphics2D g2d) {

        if (view_dx == -1) {
            drawPacnanLeft(g2d);
        } else if (view_dx == 1) {
            drawPacmanRight(g2d);
        } else if (view_dy == -1) {
            drawPacmanUp(g2d);
        } else {
            drawPacmanDown(g2d);
        }
    }

    private void drawPacmanUp(Graphics2D g2d) {

        switch (pacmanAnimPos) {
            case 1:
                g2d.drawImage(pacman2up, pacman_x + 1, pacman_y + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3up, pacman_x + 1, pacman_y + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4up, pacman_x + 1, pacman_y + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pacman_x + 1, pacman_y + 1, this);
                break;
        }
    }

    private void drawPacmanDown(Graphics2D g2d) {

        switch (pacmanAnimPos) {
            case 1:
                g2d.drawImage(pacman2down, pacman_x + 1, pacman_y + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3down, pacman_x + 1, pacman_y + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4down, pacman_x + 1, pacman_y + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pacman_x + 1, pacman_y + 1, this);
                break;
        }
    }

    private void drawPacnanLeft(Graphics2D g2d) {

        switch (pacmanAnimPos) {
            case 1:
                g2d.drawImage(pacman2left, pacman_x + 1, pacman_y + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3left, pacman_x + 1, pacman_y + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4left, pacman_x + 1, pacman_y + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pacman_x + 1, pacman_y + 1, this);
                break;
        }
    }

    private void drawPacmanRight(Graphics2D g2d) {

        switch (pacmanAnimPos) {
            case 1:
                g2d.drawImage(pacman2right, pacman_x + 1, pacman_y + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3right, pacman_x + 1, pacman_y + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4right, pacman_x + 1, pacman_y + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pacman_x + 1, pacman_y + 1, this);
                break;
        }
    }

    private void drawMaze(Graphics2D g2d) {

        short i = 0;
        int x, y;

        for (y = 0; y < SCREEN_SIZE; y += BLOCK_SIZE) {
            for (x = 0; x < SCREEN_SIZE; x += BLOCK_SIZE) {

                g2d.setColor(mazeColor);
                g2d.setStroke(new BasicStroke(2));

                if ((screenData[i] & 1) != 0) {
                    g2d.drawLine(x, y, x, y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 2) != 0) {
                    g2d.drawLine(x, y, x + BLOCK_SIZE - 1, y);
                }

                if ((screenData[i] & 4) != 0) {
                    g2d.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 8) != 0) {
                    g2d.drawLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 16) != 0) {
                    g2d.setColor(dotColor);
                    if(x==lukypoint_x && y==lukypoint_y && eattenpoint==false)
                    {    g2d.setColor(Color.blue);
                        g2d.fillOval(x+9,y+9,8,8);}
                    if(x==magicfood_x && y==magicfood_y && eatfood ==false)
                    {
                        g2d.setColor(Color.red);
                        g2d.fillOval(x+9,y+9,8,8);
                    }else
                    {     g2d.setColor(dotColor);
                        g2d.fillRect(x + 11, y + 11, 2, 2);}
                }

                i++;
            }
        }

    }

    private void initGame() {

        pacsLeft = 3;
        score = 0;
        initLevel();
        N_GHOSTS = 4;
        currentSpeed = 3;
        for(int j=0;j<N_GHOSTS;j++){
            ghostdeath[j]=false;
        }
    }
    private void initGame(int level){
        pacsLeft = 3;
        score = 0;
        initLevel(level);
        N_GHOSTS = 6;
        currentSpeed = 3;
        for(int j=0;j<N_GHOSTS;j++){
            ghostdeath[j]=false;
        }
    }

    private void initLevel() {

        int i;
        for (i = 0; i < N_BLOCKS * N_BLOCKS; i++) {
            screenData[i] = hustData[i];
        }
        continueLevel();
    }
    private void initLevel(int level){
        if(level==1){
            for (int i=0;i<N_BLOCKS*N_BLOCKS;i++){
                screenData[i]= levelData[i];
            }
        }
        if(level==2){
            for (int i=0;i<N_BLOCKS*N_BLOCKS;i++){
                screenData[i]= hustData[i];
            }
        }
        if(level==3){
            for (int i=0;i<N_BLOCKS*N_BLOCKS;i++){
                screenData[i]= matrixData[i];
            }
        }
        continueLevel();
    }

    private void continueLevel() {

        short i;
        int dx = 1;
        int random;
        lukypoint_x= 4* BLOCK_SIZE;
        lukypoint_y= 4* BLOCK_SIZE;
        magicfood_x= (int)(Math.random()*N_BLOCKS)*BLOCK_SIZE;
        magicfood_y= (int)(Math.random()*N_BLOCKS)*BLOCK_SIZE;
        eattenpoint= false;
        eatfood=false;
        for (i = 0; i < N_GHOSTS; i++) {

            ghost_y[i] = 4 * BLOCK_SIZE;
            ghost_x[i] = 4 * BLOCK_SIZE;
            ghost_dy[i] = 0;
            ghost_dx[i] = dx;
            dx = -dx;
            random = (int) (Math.random() * (currentSpeed + 1));

            if (random > currentSpeed) {
                random = currentSpeed;
            }
            if(i%2==1)
            {ghostSpeed[i] = validSpeeds[random];}
            else {
                random=currentSpeed+ (int)(Math.random()*(maxSpeed-currentSpeed)/3);
                ghostSpeed[i]= validSpeeds[random];

            }
        }

        pacman_x = 7 * BLOCK_SIZE;
        pacman_y = 11 * BLOCK_SIZE;
        pacmand_x = 0;
        pacmand_y = 0;
        req_dx = 0;
        req_dy = 0;
        view_dx = -1;
        view_dy = 0;
        dying = false;
    }

    private void loadImages() {
        ghostUp= new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/ghostUp.png").getImage();
        ghostDown= new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/ghostDown.png").getImage();
        ghostRight= new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/ghostRight.png").getImage();
        ghostLeft= new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/ghostLeft.png").getImage();
        ghost = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/ghost1.png").getImage();
        ghost1 = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/ghost.png").getImage();
        pacman1 = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/pacman.png").getImage();
        pacman2up = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/up1.png").getImage();
        pacman3up = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/up2.png").getImage();
        pacman4up = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/up3.png").getImage();
        pacman2down = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/down1.png").getImage();
        pacman4down = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/down3.png").getImage();
        pacman2left = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/left1.png").getImage();
        pacman3down = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/down2.png").getImage();
        pacman3left = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/left2.png").getImage();
        pacman4left = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/left3.png").getImage();
        pacman2right = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/right1.png").getImage();
        pacman3right = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/right2.png").getImage();
        pacman4right = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/right3.png").getImage();

    }
    private void setPacmanMad(){
        pacman2up = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/pacman2Up1.png").getImage();
        pacman3up = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/pacman2Up2.png").getImage();
        pacman4up = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/pacman2Up3.png").getImage();
        pacman2down = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/pacman2Down1.png").getImage();
        pacman4down = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/pacman2Down3.png").getImage();
        pacman2left = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/pacman2Left1.png").getImage();
        pacman3down = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/pacman2Down2.png").getImage();
        pacman3left = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/pacman2Left2.png").getImage();
        pacman4left = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/pacman2Left3.png").getImage();
        pacman2right = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/pacman2Right1.png").getImage();
        pacman3right = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/pacman2Right2.png").getImage();
        pacman4right = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/pacman2Right3.png").getImage();
    }
    private void PacmancomeNomal(){
        pacman2up = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/up1.png").getImage();
        pacman3up = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/up2.png").getImage();
        pacman4up = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/up3.png").getImage();
        pacman2down = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/down1.png").getImage();
        pacman4down = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/down3.png").getImage();
        pacman2left = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/left1.png").getImage();
        pacman3down = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/down2.png").getImage();
        pacman3left = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/left2.png").getImage();
        pacman4left = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/left3.png").getImage();
        pacman2right = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/right1.png").getImage();
        pacman3right = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/right2.png").getImage();
        pacman4right = new ImageIcon("C:/Users/khiem/Desktop/Java-Pacman-Game-master/Java-Pacman-Game-master/src/resources/images/right3.png").getImage();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);

        drawMaze(g2d);
        drawScore(g2d);
        doAnim();

        if (inGame) {
            playGame(g2d);
        } else {
            showIntroScreen(g2d);
        }

        g2d.drawImage(ii, 5, 5, this);
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }

    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (inGame) {
                if (key == KeyEvent.VK_LEFT) {
                    req_dx = -1;
                    req_dy = 0;
                } else if (key == KeyEvent.VK_RIGHT) {
                    req_dx = 1;
                    req_dy = 0;
                } else if (key == KeyEvent.VK_UP) {
                    req_dx = 0;
                    req_dy = -1;
                } else if (key == KeyEvent.VK_DOWN) {
                    req_dx = 0;
                    req_dy = 1;
                } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    inGame = false;
                } else if (key == KeyEvent.VK_P) {
                    if (timer.isRunning()) {
                        timer.stop();
                    } else {
                        timer.start();
                    }
                }
            } else {
                if (key == 's' || key == 'S') {
                    inGame = true;
                    int level = (int)(Math.random()*4);
                    if(level>3)
                        level=3;
                    initGame(level);
                }else if(key=='1'){
                    inGame= true;
                    initGame(1);
                }else if(key=='2'){
                    inGame=true;
                    initGame(2);
                }else if(key=='3'){
                    inGame=true;
                    initGame(3);
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == Event.LEFT || key == Event.RIGHT
                    || key == Event.UP || key == Event.DOWN) {
                req_dx = 0;
                req_dy = 0;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
    }

}
