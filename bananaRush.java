/*
바나나러쉬(상하좌우캐릭터이동, 스페이스바 버튼으로공격, 보스잡기, 보스체력구현,피격시 게임오버,보스처치시 보스사라짐)
소프트웨어 작성 : 2017. 06. 20
소프트웨어 수정 : 2017. 06. 26
작성자 : 201301884 이효범
*/
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class bananaRush {
    public static void main(String[] ar){
        bananaRush_Frame fms = new bananaRush_Frame();
  
    }
}

class bananaRush_Frame extends Frame implements KeyListener, Runnable{ 

    int f_width	  	 = 800;
    int f_height 	 = 600;
 
    int x, y; 
    long hp = 500;
    boolean KeyUp       = false; 
    boolean KeyDown     = false;
    boolean KeyLeft     = false;
    boolean KeyRight    = false;
    boolean KeySpace    = false; 

    int cnt;
    int cnt2=101;
    int LoseCnt=0;
    int WinCnt=0;

    Thread th; 
 
    Toolkit tk = Toolkit.getDefaultToolkit();
    Image Player_img;
    Image Boss_img;
    Image Tan_img;

    Image BossTan_img;  
    

    ArrayList Tan_List 		= new ArrayList();
    ArrayList BossTan_List 	= new ArrayList();
    ArrayList BossTan2_List 	= new ArrayList();

    Image buffImage; 
    Graphics buffg; 
    
    Tan ta;
    BossTan bt;
    BossTan2 bt2;
 
    bananaRush_Frame(){
	init();
	start();
	addWindowListener(new MyWindowHandler()); 
	setTitle("바나나러쉬");
	setSize(f_width, f_height);
  
	Dimension screen = tk.getScreenSize();

	int f_xpos = (int)(screen.getWidth() / 2 - f_width / 2);
	int f_ypos = (int)(screen.getHeight() / 2 - f_height / 2);

	setLocation(f_xpos, f_ypos);
	setResizable(false);
	setVisible(true);
    }

    public void init(){
	x = 100; 
	y = 100;
	f_width = 800;
	f_height = 600;

	Player_img 	= tk.getImage("player.gif"); 
	Boss_img 	= tk.getImage("boss.gif");
	Tan_img 	= tk.getImage("tan.png");
	BossTan_img	= tk.getImage("bosstan.png");
	
    }

    public void start(){


	addKeyListener(this); 
	th = new Thread(this);  
	th.start(); 

    }

    public void run(){ 
	try{ 
	    while(true){ 
		BossProcess();
		KeyProcess(); 
		TanProcess();
		BossTanProcess();
		BossTan2Process();

		repaint();
		Thread.sleep(20);
		cnt++;
		cnt2++;

	    }
	} catch (Exception e){}
    }

    public void BossProcess(){
	if(hp < 0){
	    Boss_img = null;
	    WinCnt ++;	    
	}
    }

    public void BossTanProcess(){
	for (int i = 0 ; i < BossTan_List.size() ; ++i ){ 
	    bt = (BossTan)(BossTan_List.get(i)); 
	
	    bt.move();
	    
	    if(bt.x < -200){ 
	        BossTan_List.remove(i); 
	    }
        }
	if ( cnt % 200 == 0 ){ 
	    bt = new BossTan(600, 100);
	    BossTan_List.add(bt); 	    
	    bt = new BossTan(600, 200);
	    BossTan_List.add(bt);
	    bt = new BossTan(600, 300);
	    BossTan_List.add(bt);
	    bt = new BossTan(600, 400);
	    BossTan_List.add(bt);
	    bt = new BossTan(600, 500);
	    BossTan_List.add(bt);
	}
    }

    public void BossTan2Process(){
	for (int i = 0 ; i < BossTan2_List.size() ; ++i ){ 
	    bt2 = (BossTan2)(BossTan2_List.get(i)); 
	
	    bt2.move();
	    
	    if(bt2.x < -200){ 
	        BossTan2_List.remove(i); 
	    }
        }
	if ( cnt2 % 200 == 0 ){ 
	    bt2 = new BossTan2(600, 50);
	    BossTan2_List.add(bt2); 	    
	    bt2 = new BossTan2(600, 150);
	    BossTan2_List.add(bt2);
	    bt2 = new BossTan2(600, 250);
	    BossTan2_List.add(bt2);
	    bt2 = new BossTan2(600, 350);
	    BossTan2_List.add(bt2);
	    bt2 = new BossTan2(600, 450);
	    BossTan2_List.add(bt2);
	}
    }


    public void KeyProcess(){
        
	if(KeyUp == true)   
	    if(y > 0){
		 y -= 5;
	    }
	if(KeyDown == true)
	    if(y < 450){
		y += 5;
	    }
	if(KeyLeft == true)
	    if(x > 0){
		x -= 5;
	    }
	if(KeyRight == true)
	    if(x < 400){
	        x += 5;
	    }
    }

    

    public void TanProcess(){
        if(LoseCnt < 1){
            if(KeySpace){
                ta = new Tan(x,y);
	        Tan_List.add(ta);
            }
        }
    }



    public void paint(Graphics g){
        buffImage = createImage(f_width, f_height); 
	buffg = buffImage.getGraphics(); 

	update(g);
    }

    public void update(Graphics g){
	Draw_Player();
	Draw_Boss();
	Draw_Tan();
	Draw_BossTan();
	Draw_BossTan2();
	Draw_Hp();

	g.drawImage(buffImage, 0, 0, this); 
	
    }

    public void Draw_Player(){ 
	buffg.clearRect(0, 0, f_width, f_height);
	buffg.drawImage(Player_img, x, y, this);
    }

    public void Draw_Boss(){
	buffg.drawImage(Boss_img, 500, 0, this);
    }

    public void Draw_BossTan(){ 
	if(WinCnt<1){
	    for (int i = 0 ; i < BossTan_List.size() ; ++i ){
	        bt = (BossTan)(BossTan_List.get(i));
	        buffg.drawImage(BossTan_img, bt.x, bt.y, this);
	        if(bt.x == x+20){
		    LoseCnt++;
		    Player_img = null;
	        }
	    }
        }
    }

    public void Draw_BossTan2(){ 
	if(WinCnt<1){
	    for (int i = 0 ; i < BossTan2_List.size() ; ++i ){
	        bt2 = (BossTan2)(BossTan2_List.get(i));
	        buffg.drawImage(BossTan_img, bt2.x, bt2.y, this);
	        if(bt2.x == x+20){
		    LoseCnt++;
		    Player_img = null;
	        }
	    }
        }
    }

    public void Draw_Tan(){ 
	for (int i = 0; i < Tan_List.size(); ++i){    
	    ta = (Tan) (Tan_List.get(i));  
	    buffg.drawImage(Tan_img, ta.pos.x + 60, ta.pos.y+35 , this); 
	    ta.move();  
	    if ( ta.pos.x > 600 ){ 
		hp --;
		Tan_List.remove(i);	   
	    }
	}
    }

    public void Draw_Hp(){
	if(hp < 0){
	    buffg.drawString("HP :" +0, 650, 50);
	} 
	else{
	    buffg.drawString("HP :" + hp, 650 , 50);	    
	}
    }



    public void keyPressed(KeyEvent e){
	switch(e.getKeyCode()){
	    case KeyEvent.VK_UP :
	    KeyUp = true;
	    break;
	    case KeyEvent.VK_DOWN :
	    KeyDown = true;
	    break;
	    case KeyEvent.VK_LEFT :
	    KeyLeft = true;
	    break;
	    case KeyEvent.VK_RIGHT :
	    KeyRight = true;
	    break;
	    case KeyEvent.VK_SPACE :
	    KeySpace = true;
	    break;
	}    
    }

    public void keyReleased(KeyEvent e){
	switch(e.getKeyCode()){
	    case KeyEvent.VK_UP :
	    KeyUp = false;
	    break;
	    case KeyEvent.VK_DOWN :
	    KeyDown = false;
	    break;
	    case KeyEvent.VK_LEFT :
 	    KeyLeft = false;
	    break;
	    case KeyEvent.VK_RIGHT :
	    KeyRight = false;
	    break;
	    case KeyEvent.VK_SPACE :
	    KeySpace = false;
	    break;
        }
    }

    public void keyTyped(KeyEvent e){}
    class Tan{  
        Point pos; 
	Tan(int x, int y){ 
	pos = new Point(x, y); 
	}
	    public void move(){ 
	        pos.x += 10; 
	    }
    }
   
    class BossTan{
	int x;
	int y;

	BossTan(int x, int y){ 
	this.x = x;
	this.y = y;
        }

	public void move(){ 
	x -= 3;
	}


    }
    class BossTan2{
	int x;
	int y;

	BossTan2(int x, int y){ 
	this.x = x;
	this.y = y;
        }

	public void move(){ 
	x -= 3;
	}


    }

    class MyWindowHandler extends WindowAdapter{
        public void windowClosing(WindowEvent e) {
	    System.exit(0);
	}
    }
   
}

