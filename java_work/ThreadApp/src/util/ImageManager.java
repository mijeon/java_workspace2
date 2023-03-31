package util;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
//나~중에 이미지를 또 그려야할일이 생길 때를 대비해서 경로를 매개변수로 받는것
public class ImageManager {
	//이미지에 대한 경로를 전달하면, 이미지 객체를 반환하는 메서드 정의
	public Image[] createImages(String[] imgName) {
		//현재 클래스에 대한 정보를 가진 클래스를 얻음
				Class myClass=this.getClass(); 
				Image[] images=new Image[imgName.length];
				
				//자원의 위치를 의미, 패키지경로도 처리가능
				for(int i=0;i<imgName.length;i++) {			
					URL url=myClass.getClassLoader().getResource(imgName[i]);
					try {
						//생성된 이미지를 배열에 담기
						images[i]=ImageIO.read(url);  //이미지 열기, 경로를 이미지 객체로 반환함
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		return images;
	}


/*---------------------------------------------
 	path : 클래스 패스 내의 이미지 경로
 	width, height : 크기를 조정하고 싶은 값
  ---------------------------------------------*/
	
	//버튼 메뉴에 사용할 아이콘 생성하기
	public ImageIcon getIcon(String path, int width, int height) {
		Class myClass=this.getClass();
		ClassLoader loader=myClass.getClassLoader();
		URL url=loader.getResource(path);
		
		Image scaledImg=null;  //이미지 크기 조정
		try {
			Image img=ImageIO.read(url);  //이미지 객체 가져오기
			scaledImg=img.getScaledInstance(width, height, Image.SCALE_SMOOTH);  //이미지가 보유한 상수
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ImageIcon icon=new ImageIcon(scaledImg);  //(url)
		
		return icon;
		
	}
}
