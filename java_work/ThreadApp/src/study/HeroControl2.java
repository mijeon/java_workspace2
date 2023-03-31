package study;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

import util.ImageManager;

public class HeroControl2 extends JFrame{
	JPanel p;
	//Image image;  
	ImageManager imageManager;
	String[] imgName= {
			"res/hero/image1.png",
			"res/hero/image2.png",
			"res/hero/image3.png",
			"res/hero/image4.png",
			"res/hero/image5.png",
			"res/hero/image6.png",
			"res/hero/image7.png",
			"res/hero/image8.png",
			"res/hero/image9.png",
			"res/hero/image10.png",
			"res/hero/image11.png",
			"res/hero/image12.png",
			"res/hero/image13.png",
			"res/hero/image14.png",
			"res/hero/image15.png",
			"res/hero/image16.png",
			"res/hero/image17.png",
			"res/hero/image18.png"
	};
	Image[] images;
	int index;  //이미지 배열의 몇번째를 보여줄지를 결정짓는 index
	Thread thread;
	
	public HeroControl2() {
		imageManager=new ImageManager();
		images=imageManager.createImages(imgName);
		
		p=new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2=(Graphics2D)g;
				/*
				//이미지 객체를 갖고오는 방법 -> 현재 2가지
				// 1: Toolkit을 이용한 방법
				// 2: Classpath를 이용한 방법 => 이게더좋음
				Class myClass=this.getClass();
				URL url = myClass.getClassLoader().getResource("res/hero/image1.png");
				try {
					image = ImageIO.read(url);
				} catch (IOException e) {
					e.printStackTrace();
				}
				*/
				g2.clearRect(0, 0, 800, 600);
				g2.drawImage(images[index], 50, 50, 200, 200, HeroControl2.this);
				
				if(index>=images.length-1) {
					index=0;
				}
			}
		};
		
		thread=new Thread() {
			public void run() {
				while(true) {
					gameLoop();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		thread.start();   //Runnable로 밀어넣기		
		
		add(p);
		
		setSize(800, 600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	//물리량의 변화
	public void tick() {
		index++;
	}
	public void render() {
		p.repaint();
	}
	public void gameLoop() {
		tick();
		render();
	}
	public static void main(String[] args) {
		new HeroControl2();
	}

}
