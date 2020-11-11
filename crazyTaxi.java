import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
import java.util.Scanner;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Shape;


import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class crazyTaxi 
{
 
 public static void main(String...args)throws Exception
 {
  new Opening();
 }
 
 public crazyTaxi() //creates JFrame
  {
     int marker = 30;
     JFrame j = new JFrame();  
     MyPanelb m = new MyPanelb(marker);
     j.setSize(m.getSize());
     j.add(m); 
     j.addKeyListener(m.getKeyListener());
     j.setVisible(true); 

     j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 }
}

//*****************************************************************************************************

class MyPanelb extends JPanel implements ActionListener, KeyListener //adds properties to JFrame
{
  private Timer time;
  private int x,y,x2;
  private int add;
  private int add2;
  private boolean up, jumped = true;
  private final int changeSpeed = 60;
  private Rectangle groundBox,carBox,cactiBox;
  private int score;
  boolean gameOver = false;
 
 MyPanelb(int a) //sets timer for movement
  {
    time = new Timer(15, this); 
    setSize(2000, 1500);
    setVisible(true); 
    time.start();
    add=a;
    x=600;
    y=500;
    x2=600;
    add2=5;
  }

 public void paintComponent(Graphics g) //draws Images  and Objects, also keeps track of score and detects Death
 {
    g.setColor(Color.white);
    g.fillRect(0,0,2000,1500);  
  
    BufferedImage image = null;
    try
    {
         image = ImageIO.read(new File("projectimage.jpg"));
    } 
    catch (IOException e) 
    { 
      System.out.println ("Import failed");
    }
  
    g.drawImage (image,0,0,null); 
    drawBird(g,x,y);
    drawDust(g,x,y);
    drawBackground(g,x,y);
    drawTree(g,x,y);
    drawCar(g,x,y);
    blinkLights(g,x,y);
    drawCacti(g,x,y);
    drawRoad(g,x,y); 
 
    
    if (!carBox.intersects(cactiBox) || gameOver)
    {
        move(g);
    }
    else
    {
     gameOver = true;
        
         
    }
    
    if (gameOver){
     Color mustard4 = new Color (255,194,54);
        g.setColor(mustard4);
        
        g.fillRect(0,0,2000,1500);
        g.setColor(Color.RED);
        g.setFont(new Font("Impact", 0 , 100));
        g.drawString("GAME OVER!", 700,350);
        
        BufferedImage image6 = null;
         try
         {
            image6 = ImageIO.read(new File("pepe.png"));
         } 
         catch (IOException e) 
         { 
            System.out.println ("Import failed");
         }
        
         g.drawImage (image6,750,350,null); 
         g.setFont(new Font("Gill Sans MT", 0 , 60));
         g.drawString("Press \"R\" to restart!", 700,800);
    }
    
    g.setFont(new Font("Impact", 0 , 60));
    g.drawString("Score:" + score, 20,80);
    
    if(carBox.intersects(groundBox) && gameOver == false)
    {
     score++;
    
    }
}
 
 public void keyPressed(KeyEvent e) //if space is pressed
 {
    int key = e.getKeyCode();
    if (key == KeyEvent.VK_SPACE)
    { 
      //jumped = false;
      up = true;
    }
    
    if (gameOver && e.getKeyCode() == KeyEvent.VK_R){
     gameOver = false;
     score=0;
    }
    
 }
 
  public void keyReleased(KeyEvent e) //if space is released
  {
    int key = e.getKeyCode();
  
    if (key == KeyEvent.VK_SPACE)
    {
      up = false;
    }
  }
 
 public void keyTyped(KeyEvent e) //implementation not shown
  {
    //do nothing 
 }
 
  public void move(Graphics g) //controls animation speed and control Taxi's jump
  {
    if (x>=2400)
      x=1; 
     x+=add;
    if (x2>=2000)
      x2=1;
     x2+=add2;
  
    if(x==200)
      add*=-1;
    if(x==10)
      add*=-1;
  
    if(up==true/* && !jumped*/)
    { 
     jumped = true;
       y=y+changeSpeed;
    }
  
    if(up==false && !(carBox.intersects(groundBox))) 
    {
    //jumped = false;
      y=y-changeSpeed;
    }
 }
 
 public void actionPerformed(ActionEvent e) //actions
  {
    repaint();
  }
 
 public void drawCar(Graphics g, int x, int y) //draws Car
  {
    //body
    carBox = new Rectangle(400,500-y,20,250);
    Color mustard1 = new Color (255,194,54);
    g.setColor(mustard1);
    g.fillRect(400,500-y,900,200);
    g.fillRect(600,350-y,500,200);

    //tires
    g.setColor(Color.black);
    g.fillOval(500,600-y,200,200);
    g.fillOval(1000,600-y,200,200);
  
    //rims
    g.setColor(Color.lightGray);
    g.fillOval(550,650-y,100,100);
    g.fillOval(1050,650-y,100,100);
  
    //ground
    groundBox = new Rectangle(0,770,2000,800);
    Color arid=new Color(229,93,0);
    g.setColor(Color.black);
    g.fillRect(0,800,2000,800);
  
    //window
    g.setColor(Color.darkGray);
    g.fillRect(620,370-y,460,120);
    g.setColor(mustard1);
    g.fillRect(845,350-y,10,200);
  
    //headlights
    g.setColor(Color.yellow);
    g.fillArc(1275,575-y,50,50,-90,-180);
  
    BufferedImage image3 = null;
         try
         {
            image3 = ImageIO.read(new File("taxisign.png"));
         } 
         catch (IOException e) 
         { 
            System.out.println ("Import failed");
         }
        
         g.drawImage (image3,730,450-y,null); 
      
    BufferedImage image4 = null;
         try
         {
            image4 = ImageIO.read(new File("taxisign3.png"));
         } 
         catch (IOException e) 
         { 
            System.out.println ("Import failed");
         }
        
         g.drawImage (image4,750,275-y,null); 
 }
 
 
  public void drawCacti(Graphics g, int x, int y) //draws Cacti
  {
    //body
    cactiBox = new Rectangle(1600-x,425,500,475);
    Color cactusGreen=new Color(74,186,2);
    g.setColor(cactusGreen);
    int[] xpoints = {1950-x,1950-x,1800-x,1800-x,1850-x,1850-x,1950-x,1950-x,2000-x,2100-x,2150-x,2150-x,2250-x,2250-x,2300-x,2300-x,2150-x,2150-x};
    int[] ypoints = {800,625,625,525,525,575,575,450,425,425,450,575,575,525,525,625,625,800};
    g.fillPolygon(xpoints, ypoints, 18);
    
    //thorns
    g.setColor(Color.BLACK);
    g.fillRect(1800-x, 800,10,20);
  }
  
  public void drawTree (Graphics g, int x, int y)
  {
    Color brown1 = new Color (145,55,10);
    Color brown2 = new Color (105,54,7);
    Color brown3 = new Color (89,46,6);
    Color brown4 = new Color (153,79,0);
    Color darkgreen = new Color (13,117,23);
    
    //drawTree1
    g.setColor(brown4);
    g.fillRect(200-x,500,100,300);
    g.setColor(darkgreen);
    g.fillArc(100-x,380,200,200,0,360);
    g.fillArc(200-x,300,200,200,0,360);
    g.fillArc(225-x,350,200,200,0,360);
    g.fillArc(150-x,300,200,200,0,360);
    g.fillArc(150-x,420,200,200,0,360);
    //drawApples1
    g.setColor(Color.RED);
    g.fillArc(200-x,350,20,20,0,360);
    g.fillArc(270-x,580,20,20,0,360);    
    g.fillArc(220-x,430,20,20,0,360);
    g.fillArc(310-x,520,20,20,0,360);
    g.fillArc(350-x,420,20,20,0,360);
    g.fillArc(250-x,520,20,20,0,360);
    g.fillArc(280-x,380,20,20,0,360);
    g.fillArc(180-x,550,20,20,0,360);
    
    //drawTree2
    g.setColor(brown4);
    g.fillRect(700-x,500,100,200);
    g.setColor(darkgreen);
    g.fillArc(600-x,380,200,200,0,360);
    g.fillArc(700-x,300,200,200,0,360);
    g.fillArc(725-x,350,200,200,0,360);
    g.fillArc(650-x,300,200,200,0,360);
    g.fillArc(650-x,420,200,200,0,360);
     //drawApples2
    g.setColor(Color.RED);
    g.fillArc(700-x,350,20,20,0,360);
    g.fillArc(770-x,580,20,20,0,360);    
    g.fillArc(720-x,430,20,20,0,360);
    g.fillArc(810-x,520,20,20,0,360);
    g.fillArc(850-x,420,20,20,0,360);
    g.fillArc(750-x,520,20,20,0,360);
    g.fillArc(780-x,380,20,20,0,360);
    g.fillArc(680-x,550,20,20,0,360);
    
    //drawTree3
    g.setColor(brown4);
    g.fillRect(1200-x,500,100,400);
    g.setColor(darkgreen);
    g.fillArc(1100-x,380,200,200,0,360);
    g.fillArc(1200-x,300,200,200,0,360);
    g.fillArc(1225-x,350,200,200,0,360);
    g.fillArc(1150-x,300,200,200,0,360);
    g.fillArc(1150-x,420,200,200,0,360);
    //drawApples3
    g.setColor(Color.RED);
    g.fillArc(1200-x,350,20,20,0,360);
    g.fillArc(1270-x,580,20,20,0,360);    
    g.fillArc(1220-x,430,20,20,0,360);
    g.fillArc(1310-x,520,20,20,0,360);
    g.fillArc(1350-x,420,20,20,0,360);
    g.fillArc(1250-x,520,20,20,0,360);
    g.fillArc(1280-x,380,20,20,0,360);
    g.fillArc(1180-x,550,20,20,0,360);
    
    //drawTree4
    g.setColor(brown4);
    g.fillRect(1700-x,500,100,400);
    g.setColor(darkgreen);
    g.fillArc(1600-x,380,200,200,0,360);
    g.fillArc(1700-x,300,200,200,0,360);
    g.fillArc(1725-x,350,200,200,0,360);
    g.fillArc(1650-x,300,200,200,0,360);
    g.fillArc(1650-x,420,200,200,0,360);
     //drawApples4
    g.setColor(Color.RED);
    g.fillArc(1700-x,350,20,20,0,360);
    g.fillArc(1770-x,580,20,20,0,360);    
    g.fillArc(1720-x,430,20,20,0,360);
    g.fillArc(1810-x,520,20,20,0,360);
    g.fillArc(1850-x,420,20,20,0,360);
    g.fillArc(1750-x,520,20,20,0,360);
    g.fillArc(1780-x,380,20,20,0,360);
    g.fillArc(1680-x,550,20,20,0,360);   
    
    //drawTree5
    g.setColor(brown4);
    g.fillRect(2200-x,500,100,300);
    g.setColor(darkgreen);
    g.fillArc(2100-x,380,200,200,0,360);
    g.fillArc(2200-x,300,200,200,0,360);
    g.fillArc(2225-x,350,200,200,0,360);
    g.fillArc(2150-x,300,200,200,0,360);
    g.fillArc(2150-x,420,200,200,0,360);
    //drawApples5
    g.setColor(Color.RED);
    g.fillArc(2200-x,350,20,20,0,360);
    g.fillArc(2270-x,580,20,20,0,360);    
    g.fillArc(2220-x,430,20,20,0,360);
    g.fillArc(2310-x,520,20,20,0,360);
    g.fillArc(2350-x,420,20,20,0,360);
    g.fillArc(2250-x,520,20,20,0,360);
    g.fillArc(2280-x,380,20,20,0,360);
    g.fillArc(2180-x,550,20,20,0,360);
    
    //drawTree6
    g.setColor(brown4);
    g.fillRect(2700-x,500,100,300);
    g.setColor(darkgreen);
    g.fillArc(2600-x,380,200,200,0,360);
    g.fillArc(2700-x,300,200,200,0,360);
    g.fillArc(2725-x,350,200,200,0,360);
    g.fillArc(2650-x,300,200,200,0,360);
    g.fillArc(2650-x,420,200,200,0,360);
    //drawApples6
    g.setColor(Color.RED);
    g.fillArc(2700-x,350,20,20,0,360);
    g.fillArc(2670-x,550,20,20,0,360);    
    g.fillArc(2720-x,430,20,20,0,360);
    g.fillArc(2810-x,520,20,20,0,360);
    g.fillArc(2850-x,420,20,20,0,360);
    g.fillArc(2750-x,520,20,20,0,360);
    g.fillArc(2780-x,380,20,20,0,360);
    g.fillArc(2680-x,550,20,20,0,360);
   
    //drawTree7
    g.setColor(brown4);
    g.fillRect(3200-x,500,100,300);
    g.setColor(darkgreen);
    g.fillArc(3100-x,380,200,200,0,360);
    g.fillArc(3200-x,300,200,200,0,360);
    g.fillArc(3225-x,350,200,200,0,360);
    g.fillArc(3150-x,300,200,200,0,360);
    g.fillArc(3150-x,420,200,200,0,360);
    //drawApples7
    g.setColor(Color.RED);
    g.fillArc(3200-x,350,20,20,0,360);
    g.fillArc(3170-x,550,20,20,0,360);    
    g.fillArc(3220-x,430,20,20,0,360);
    g.fillArc(3310-x,520,20,20,0,360);
    g.fillArc(3350-x,420,20,20,0,360);
    g.fillArc(3250-x,520,20,20,0,360);
    g.fillArc(3280-x,380,20,20,0,360);
    g.fillArc(3180-x,550,20,20,0,360);
    
    //drawTree8
    g.setColor(brown4);
    g.fillRect(3700-x,500,100,300);
    g.setColor(darkgreen);
    g.fillArc(3600-x,380,200,200,0,360);
    g.fillArc(3700-x,300,200,200,0,360);
    g.fillArc(3725-x,350,200,200,0,360);
    g.fillArc(3650-x,300,200,200,0,360);
    g.fillArc(3650-x,420,200,200,0,360);
    //drawApples8
    g.setColor(Color.RED);
    g.fillArc(3700-x,350,20,20,0,360);
    g.fillArc(3670-x,550,20,20,0,360);    
    g.fillArc(3720-x,430,20,20,0,360);
    g.fillArc(3810-x,520,20,20,0,360);
    g.fillArc(3850-x,420,20,20,0,360);
    g.fillArc(3750-x,520,20,20,0,360);
    g.fillArc(3780-x,380,20,20,0,360);
    g.fillArc(3680-x,550,20,20,0,360);
    
    //drawTree9
    g.setColor(brown4);
    g.fillRect(4200-x,500,100,300);
    g.setColor(darkgreen);
    g.fillArc(4100-x,380,200,200,0,360);
    g.fillArc(4200-x,300,200,200,0,360);
    g.fillArc(4225-x,350,200,200,0,360);
    g.fillArc(4150-x,300,200,200,0,360);
    g.fillArc(4150-x,420,200,200,0,360);
    //drawApples9
    g.setColor(Color.RED);
    g.fillArc(4200-x,350,20,20,0,360);
    g.fillArc(4170-x,550,20,20,0,360);    
    g.fillArc(4220-x,430,20,20,0,360);
    g.fillArc(4310-x,520,20,20,0,360);
    g.fillArc(4350-x,420,20,20,0,360);
    g.fillArc(4250-x,520,20,20,0,360);
    g.fillArc(4280-x,380,20,20,0,360);
    g.fillArc(4180-x,550,20,20,0,360);
  }
  
  public void drawRoad (Graphics g, int x, int y) //draws Road
  {
    //stripes on road
    g.setColor(Color.yellow);
    g.fillRect(50-x,820,150,25);
    g.fillRect(300-x,820,150,25);
    g.fillRect(550-x,820,150,25);
    g.fillRect(800-x,820,150,25);
    g.fillRect(1050-x,820,150,25);
    g.fillRect(1300-x,820,150,25);
    g.fillRect(1550-x,820,150,25);
    g.fillRect(1800-x,820,150,25);
    g.fillRect(2050-x,820,150,25);
    g.fillRect(2300-x,820,150,25);
    g.fillRect(2550-x,820,150,25);
    g.fillRect(2800-x,820,150,25);
    g.fillRect(3050-x,820,150,25);
    g.fillRect(3300-x,820,150,25);
    g.fillRect(3550-x,820,150,25);
    g.fillRect(3800-x,820,150,25);
    g.fillRect(4050-x,820,150,25);
    g.fillRect(4300-x,820,150,25);
          
 }
 
 public void drawBackground(Graphics g, int x, int y) //draws Background
  {
    //sun
    Color yellow=new Color(251,229,50);
    g.setColor(yellow);
    g.fillArc(-175+x2,50,175,175,0,360); 
  
    //mountain1
    Color hilly=new Color(71,44,16);
    g.setColor(hilly);
    int[] xpoints={0,0,300,700};
    int[] ypoints={800,600,100,800};
    g.fillPolygon(xpoints,ypoints,4);
  
    //mountain2
    Color hilly2=new Color(53,30,7);
    g.setColor(hilly2);
    int [] xpoints2={300,700,1000};
    int [] ypoints2={800,150,800};
    g.fillPolygon(xpoints2,ypoints2,3);
  
    //mountain3
    Color hilly3=new Color(143,97,50);
    g.setColor(hilly3);
    int [] xpoints3={500,1000,1500};
    int [] ypoints3={800,200,800};
    g.fillPolygon(xpoints3,ypoints3,3);
  
    //mountain4
    Color hilly4=new Color(103,67,29);
    g.setColor(hilly4);
    int [] xpoints4={900,1300,1700};
    int [] ypoints4={800,100,800};
    g.fillPolygon(xpoints4,ypoints4,3);
  
    //mountain5
    Color hilly5=new Color(122,65,6);
    g.setColor(hilly5);
    int [] xpoints5={1300,1700,2000,2000};
    int [] ypoints5={800,150,600,800};
    g.fillPolygon(xpoints5,ypoints5,4);     
 }

 public void drawDust(Graphics g, int x, int y) //draws dust
    {
        int o;
        int p;
        int q=1;
        Color brown = new Color (76,41,10);
        g.setColor(brown);
        while(q<=500)//draws 500 stars in random places
        {
            o=((int)(Math.random()*1200)+10);
            p=((int)(Math.random()*2000)+10);
            g.fillOval(p,o,5,5);
            q++;
        }
    }
 
 public void drawBird (Graphics g, int x, int y) //draws Bird
 {
   g.setColor(Color.BLACK);
   g.fillRect(200+x,50,50,50);
   int [] xpoints7 = {200+x,50+x,200+x};
   int [] ypoints7 = {100,60,50};
   
   int [] xpoints8 = {250+x,250+x,400+x};
   int [] ypoints8 = {100,50,60};
   g.fillPolygon (xpoints7, ypoints7, 3);
   g.fillPolygon (xpoints8, ypoints8, 3);   
   g. fillRect(210+x,100,10,30);
   g.fillRect(240+x,100,10,30);
           
   g.fillArc(200+x,5,60,60,0,360);
   g.setColor(Color.WHITE);
   g.fillArc(215+x,15,10,10,0,360);   
   g.fillArc(235+x,15,10,10,0,360);   
   
 
 }
 
   
 
 public MyPanelb getKeyListener() //gets KeyListeners for MyPanelb
 {
    return this;
 }
 
 public void blinkLights(Graphics g, int x, int y) 
 {
   //implementation not shown
 }

 
}

//*****************************************************************************************************

class Opening extends JPanel implements KeyListener //creates opening frame
{
 JFrame frame;
 JButton okay;
    
 public Opening() throws Exception
 {
  //Code to play the song
        //File path = new File("cheer.wav");
        //AudioInputStream inputStream = AudioSystem.getAudioInputStream(path);
        //Clip clip = AudioSystem.getClip();
        //clip.open(inputStream);
          //clip.loop(Clip.LOOP_CONTINUOUSLY);*/
     
       frame = new JFrame("Crazy Taxi!");
      
        okay = new JButton("START");
        okay.setBounds(450, 525, 150, 80);
        Font impact1 = new Font ("Impact", Font.ITALIC, 35);
 //okay.setFont(new Font("Courier", 1, 25));
 okay.setFont(impact1);
        okay.setFocusPainted(false);
        okay.setForeground(Color.BLUE);

        MouseAdapter MA1 = new MouseAdapter() 
        {
  @Override
  public void mouseEntered(MouseEvent m) 
  {
                 Font impact2 = new Font ("Impact", Font.ITALIC, 42);
                 okay.setFont(impact2);
                 okay.setForeground(Color.red);
             }

             @Override
             public void mouseExited(MouseEvent m) 
  {
                 Font impact3 = new Font ("Impact", Font.ITALIC, 35);
                 okay.setFont(impact3);
                 okay.setForeground(Color.blue);
             }
        };

        okay.addMouseListener(MA1);
        ActionListener AL1 = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                StartButtonPressed();
            }
        };
        okay.addActionListener(AL1);
        
        frame.setSize(1000, 650);
    
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setBackground(new Color(255,194,54));
        frame.add(okay);
        frame.add(this);
        frame.setVisible(true);
    }
    
    public void paint(Graphics g){
        
      
        //Write the title
        //g.setFont(new Font("Gill Sans Ultra Bold", 1, 45));
        //g.drawString("Crazy Taxi!", 100, 100);
        
        BufferedImage image2 = null;
        try
        {
          image2 = ImageIO.read(new File("intro.jpg"));
        } 
        
        catch (IOException e) 
        { 
          System.out.println ("Import failed");
        }
        
        g.drawImage (image2,0,0,null); 
        //Write the instructions for the game.
        g.setFont(new Font("Gill Sans MT", 0 , 30));
        g.drawString("- You are going to take the role of a taxi driver!", 75, 360); 
        g.drawString("- Your goal is to avoid hitting the cacti and make it as far as possible!", 75, 400); 
        g.setFont(new Font("Impact", 0 , 30));
        g.drawString("- PRESS & HOLD SPACEBAR to jump over the cacti!", 75, 440);   
        g.setFont(new Font("Gill Sans MT", 0 , 30));
        g.drawString("- Click the START button to enter into the game!", 75, 480);
        
        g.setColor(Color.RED);
        g.setFont(new Font("Impact", 0 , 25));
        g.drawString("WARNING:",20,590);
        
        g.setColor(Color.BLACK);
        g.setFont(new Font("Gill Sans MT", 0 , 22));
        
        g.drawString("Game will begin immediately!",130,570);
        g.drawString("Be prepared to press SPACEBAR!",130,600);
 
        
        g.setFont(new Font("Gill Sans MT", 0 , 25));
        g.drawString("Altamash Ali & Satyam Jadav, 2016", 620, 600);
    }
     
    public void StartButtonPressed() {
      try
      {
            File path = new File("knightrider.wav");
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(path);
            Clip clip = AudioSystem.getClip();
            clip.open(inputStream);
            clip.loop(1);
      }
      
      catch(Exception e)
      {
       System.out.print("p");
      }
      
       frame.dispose();
       new crazyTaxi();
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }
    
}


