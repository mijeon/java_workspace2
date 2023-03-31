package image;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

import util.ImageManager;

/*자바에서 이미지를 얻는 방법은 번거로운 Toolkit만 있는 것이 아님
 * 사용중인 OS의 디렉토리를 명시하는 것보다, 클래스패스를 통한 자원접근이 
 * 더 자바스럽고, 플랫폼에 중립적이다, 따라서 자원들도 package에 담을 수 있음*/
public class HeroControl extends JFrame{
	JPanel p;
	//Image image; 이미지 하나만 표시할 때 사용
	ImageManager imageManager;
	//패키지에는 꼭 클래스만 넣을 수 있는게 아님
	//만일 패키지에 넣은 파일이 클래스인 경우 원래대로 패키지와 패키지구분을 
	//.점을 찍어서 구분하고, 만일 패키지에 넣은 파일이 클래스 이외의 파일인 경우 
	//클래스가 아니므로 디렉토리 취급함, 따라서 .점이 아닌 /로 구분해야 함
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
	Image[] images;  //이미지 경로에서 뽑아낸 이미지 객체를 다시 배열에 저장하기 위해 사용
	int index;  //이미지 배열의 몇번째를 보여줄지를 결정짓는 index
	Thread thread; //게임 쓰레드 (loop)
	
	public HeroControl() {
		imageManager=new ImageManager();
		images=imageManager.createImages(imgName);
		/*
		 * //클래스에 대한 정보를 가진 클래스
		 * 
		 * Class myClass=this.getClass(); //클래스 정보를 알면 그 안의 패키지 정보를 알 수 있음 Method[]
		 * methods=myClass.getMethods(); for(Method m:methods) {
		 * System.out.println(m.getName());
		 * 
		 * }
		 */
		  	
		p=new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2=(Graphics2D)g;  //Graphics : super, Graphics2D : sub 이므로 다운캐스팅 해줌
				g2.clearRect(0, 0, 800, 600);
				//ImageObserver observer 때문에 JPanel을 감싸고 있는 부모를 명시함
				g2.drawImage(images[index], 50, 50, 200, 200, HeroControl.this);
				
				if(index>=images.length-1) {
					index=0;
				}
			}
		};
		
		thread=new Thread() {
			public void run() {
				while(true) {
					gameLoop();  //게임 루프 호출
					try {
						Thread.sleep(10);  
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		thread.start();  //Thread를 Runnable로 밀어넣기		
		
		add(p);
		
		setSize(800, 600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);  //윈도우창을 닫으면 메인쓰레드를 종료시킴
		setLocationRelativeTo(null);  //윈도우 중앙배치(부모요소가 들어오는데 윈도우는 부모가 없기 때문에 NULL을 써줌)
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
		new HeroControl();
	}

}
