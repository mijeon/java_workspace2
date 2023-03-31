package study;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageManager2 {
	public Image[] createImages(String[] imgName) {
		Class myClass=this.getClass();
		Image[] images=new Image[imgName.length];
		
		for(int i=0;i<imgName.length;i++) {
			URL url=myClass.getClassLoader().getResource(imgName[i]);
			try {
				//생성된 이미지를 배열에 담기
				images[i]=ImageIO.read(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return images;
	}


	/*
	 * 버튼 메뉴에 사용할 아이콘 생성하기 path : 클래스패스 내의 이미지 경로 width, height : 크기를 조정하고 싶은 값
	 */
	public ImageIcon getIcon(String path, int width, int height) {
		Class myClass = this.getClass();
		ClassLoader loader = myClass.getClassLoader();
		URL url = loader.getResource(path);
		Image img = null;  //이미지 크기 조정
		try {
			img = ImageIO.read(url).getScaledInstance(width, height, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}

		ImageIcon icon = new ImageIcon(img);

		return icon;
	}
}
