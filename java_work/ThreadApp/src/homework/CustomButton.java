package homework;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/*1) JButton 현재 우리의 상황에 맞지 않기 때문에, 우리가 원하는 버튼으로 그림을 재정의 하기 위함*/
public class CustomButton extends JButton{
	int width;
	int height;
	int index;  //7-2) 내가 몇번째 버튼인지 알 수있는 기준값
	ShurekGallery shurekGallery;  //기존 슈렉갤러리를 얻어와야 함
	
	//4) 버튼 초기화
	public CustomButton(ShurekGallery shurekGallery, int index, int width, int height) {  ////기존 슈렉갤러리를 얻어와야 함-> 매개변수로 받아옴
		this.shurekGallery=shurekGallery;
		this.width=width;
		this.height=height;
		this.setBorder(null);
		this.setPreferredSize(new Dimension(width, height));
		
		//7-1) 버튼에 리스너 연결
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shurekGallery.targetX=index*-500;
				System.out.println((index*-500)+"번째부터 그릴거애");
			}
		});
	}
	protected void paintComponent(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(0, 0, width, height);
		
		g.setColor(Color.pink);
		g.fillOval(0, 0, width, height);  //(0, 0, 20, 20)
	}
}
