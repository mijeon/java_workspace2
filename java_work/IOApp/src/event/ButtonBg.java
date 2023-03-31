package event;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ButtonBg extends JFrame {
	JPanel p;
	JButton bt_create, bt_color;
	int x=0;  //버튼의 라벨 순서를 적용하는 맴버변수
	JButton[] btn=new JButton[10];  //빈 공간 10개 확보
	//배열은 크기가 고정되어 있으므로, 동적으로 채워질 버튼들을 담기엔
	//한계가 있음 따라서 크기를 자유롭게 동적으로 바꿀 수 있는 컬렉션 프레임웍이
	//지원하는 객체 중 List 중, ArrayList를 사용해 봄
	ArrayList<JButton> list=new ArrayList<JButton>();  //size가 0인 상태

	
	public ButtonBg() {
		bt_create=new JButton("생성");
		bt_color=new JButton("색상");
		p=new JPanel();
		p.setPreferredSize(new Dimension(500, 30));
		p.setBackground(Color.PINK);
		
		setLayout(new FlowLayout());
		
		add(p);
		p.add(bt_create);
		p.add(bt_color);
		
		setSize(500, 600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//버튼에 리스너 연결
		bt_create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createButton();
			}
		});	
		
		bt_color.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setBg();
			}
		});
	}
	
	//생성된 버튼들을 대상으로, 모든 버튼의 색상을 변경함
	//자바 배열의 단점 : C, C# 등 일반적인 프로그래밍 언어의 배열의 특징은, 
	//생성 시 그 크기를 정해야 한다는 단점이 있음, 따라서 본 예제는 배열로 해결 불가능
	//sun에서는 객체를 모아서 처리하는데 유용한 api를 제공해주는데 이를 가리켜 Collection Framework이라 하며,
	//java.util 패키지에서 지원함
	public void setBg() {
		for(int i=0;i<list.size();i++) {
			JButton bt=list.get(i);
			bt.setBackground(Color.RED);
		}
	}
	//버튼을 동적으로 생성하여 화면(JFrame)에 붙이기
	public void createButton() {
		JButton bt=new JButton("버튼"+x);
		//배열에 담기
		//btn[x]=bt;  //0부터 배열에 담기, x++;이 밑에 옴
		list.add(bt);  //js push()와 동일
		x++;
		
		add(bt);	
		//개발자가 paint() 메서드를 재정의한후, 시스템에게 호출하도록
		//요청할 때는 repaint() 메서드를 호출해야 하지만, 
		//GUI 컴포넌트를 부착한 경우에는 paint() 재정의한 것이 아니므로
		//repaint()가 아닌, 컴포넌트의 갱신을 요청해야 함 -> updateUI();
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public static void main (String[] args) {
		new ButtonBg();
	}
}
