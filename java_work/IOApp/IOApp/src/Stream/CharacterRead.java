package Stream;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class CharacterRead extends JFrame {
	JButton bt;
	JTextArea area;

	public CharacterRead() {
		bt = new JButton("읽기");
		area = new JTextArea();
		area.setPreferredSize(new Dimension(480, 550));
		Font font = new Font("Verdana", Font.BOLD, 30);
		area.setFont(font);
		setLayout(new FlowLayout());

		add(bt);
		add(area);
		setVisible(true);
		setSize(500, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// 버튼과 리스너와의 연결
		// 내부익명클래스란? 클래스코드 안에, 또 다른 이름없는 클래스를 둘 수 있는데
		// 이러한 용도의 클래스를 가리켜 내부익명클래스라 함
		// 내부익명클래스는, 특히 이벤트 구현 시 재사용성이 없는 클래스를
		// 물리적인 .java 파일로까지 만들 필요가 없으므로, 자주 사용됨
		// new 뒤에 부모자료형이 오고 뒤에 오는 {}를 자식클래스로 봄
		// 내부익명클래스의 장점 - 내부익명클래스 자신이 소속되어 있는 외부 클래스
		// 클래스(CharacterRead)의 맴버변수를 내것처럼 맘대로 접근할 수 있음
		bt.addActionListener(new ActionListener() { // 리스너 구현코드를 매개변수 자리에 통째로 집어넣음 new 생성자를 통해 뒤에 자식클래스 실행
			public void actionPerformed(ActionEvent e) {
				// System.out.println("test");
				// area.append("눌렀어\n");
				readFile();
			}
		});
	}

	// 메모장 파일 읽기(한글 포함된)
	public void readFile() {
		// 지금까지 써왔던 FileInputStream은 바이트기반 스트림이었음
		// 따라서 데이터 처리를 1byte 씩 처리함, 때문에 처리대상
		// 파일에 영문자가 포함되어 있을 경우 문자화시켜도 아무런 문제가 없었음
		// 하지만, 한글의 경우는 2byte로 구성되어 있으므로 문자화시킬 경우
		// 한글을 제대로 표현할 수 없음(깨짐)
		// 주의) 읽어드린 1byte의 데이터를 char형으로 변경할 때 깨지는것이지,
		// 파일 복사 시 원본을 그대로 복원하는 경우 중간에 문자로 변환하여
		// 확인하는 것이 아니기 때문에 깨질 일이 없음
		// 해결책) 읽어드린 데이터를 대상으로, 한글로 변환하기 위해서는 문자기반 스트림을 이용해야 함
		// FileInputStream fis=null; //지역변수는 반드시 초기화해야 함
		FileReader reader = null; // 전세계 모든 문자가 깨지지 않는 문자인식 스트림

		String path = "C:/java_workspace2/IOApp/data/memo.txt";

		// 스트림을 생성하자
		try {
			// fis=new FileInputStream(path);
			reader = new FileReader(path);
			System.out.println("스트림 생성 성공");

			int data = -1;

			// data=fis.read(); //1byte 읽기
			// area.append(Character.toString((char)data));
			data = reader.read(); // 1byte 읽기
			System.out.print((char) data); // char형으로는 한글을 읽을 수 없기 때문에 toString을 써줌
			data = reader.read(); // 1byte 읽기
			System.out.print((char) data);
			data = reader.read(); // 1byte 읽기
			System.out.print((char) data);
			data = reader.read(); // 1byte 읽기
			System.out.print((char) data);
			data = reader.read(); // 1byte 읽기
			System.out.print((char) data);
			data = reader.read(); // 1byte 읽기
			System.out.print((char) data);
			data = reader.read(); // 1byte 읽기
			System.out.print((char) data);
			data = reader.read(); // 1byte 읽기
			System.out.print((char) data);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		new CharacterRead();
	}
}
