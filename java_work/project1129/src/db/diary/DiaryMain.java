package db.diary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DiaryMain extends JFrame implements ActionListener{
	DbManager dbManager=DbManager.getInstance();
	DiaryDAO diaryDAO=new DiaryDAO();  //일반클래스
	
	//서쪽영역
	JPanel p_west;
	JComboBox<String> box_yy;  //연
	JComboBox<String> box_mm;  //월
	JComboBox<String> box_dd;  //일
	JTextArea area;
	JScrollPane scroll;
	JComboBox<String> box_icon;  //다이어리에 사용할 아이콘
	JButton bt_regist;  //등록, 수정
	JButton bt_del;  //삭제

	//센터영역
	//플로우를 적용하기 위한 센터의 최상위 컨테이너
	JPanel p_center;  
	JPanel p_title;  //현재 연, 월 및 이전, 다음 선택 버튼 영역
	JPanel p_dayOfWeek;  //요일 셀(JPanel)이 올곳
	JPanel p_dayOfMonth;  //날짜 셀이 붙여질 곳
	JButton bt_prev;
	JLabel la_title;
	JButton bt_next;
	
	//요일 처리
	DayCell[] dayCells=new DayCell[7];
	String[] dayTitle= {"Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat"};
	
	//날짜
	DateCell[][] dateCells=new DateCell[6][7];
	
	//현재 사용자가 보게될 날짜 정보
	Calendar currentObj=Calendar.getInstance();  //추상클래스이므로 new() 불가

	public DiaryMain() {
		//서쪽영역
		p_west=new JPanel();
		box_yy=new JComboBox<String>();
		box_mm=new JComboBox<String>();
		box_dd=new JComboBox<String>();
		area=new JTextArea();
		scroll=new JScrollPane(area);
		box_icon=new JComboBox<String>();
		bt_regist=new JButton("등록");
		bt_del=new JButton("삭제");
		
		//서쪽 디자인
		p_west.setPreferredSize(new Dimension(150, 500));
		p_west.setBackground(Color.white);
		Dimension d=new Dimension(140, 20);
		box_yy.setPreferredSize(d);
		box_mm.setPreferredSize(d);
		box_dd.setPreferredSize(d);
		area.setPreferredSize(new Dimension(140, 150));
		box_icon.setPreferredSize(d);
		
		
		add(p_west, BorderLayout.WEST);
		p_west.add(box_yy);
		p_west.add(box_mm);
		p_west.add(box_dd);
		p_west.add(scroll);
		p_west.add(box_icon);
		p_west.add(bt_regist);
		p_west.add(bt_del);
		
		
		//센터 영역
		p_center=new JPanel();
		p_title=new JPanel();
		p_dayOfWeek=new JPanel();
		p_dayOfMonth=new JPanel();
		bt_prev=new JButton("◀");
		la_title=new JLabel();
		bt_next=new JButton("▶");
		
		//센터 디자인
		p_dayOfWeek.setPreferredSize(new Dimension(750, 50));
		p_dayOfMonth.setPreferredSize(new Dimension(750, 400));
		p_dayOfWeek.setLayout(new GridLayout(1, 7));
		p_dayOfMonth.setLayout(new GridLayout(6, 7));

		add(p_center);
		p_center.add(p_title);
		p_title.add(bt_prev);
		p_title.add(la_title);
		p_title.add(bt_next);
		p_center.add(p_dayOfWeek);
		p_center.add(p_dayOfMonth);
		
		createDayOfWeek();  //요일출력
		createDayOfMonth();  //날짜출력
		
		calculate();  //날짜처리 메서드
		
		setSize(950, 550);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		//다음월
		bt_next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//현재 날짜 객체정보를 먼저 얻자
				int mm=currentObj.get(Calendar.MONTH);  //현재월
				currentObj.set(Calendar.MONTH, mm+1);
				
				//System.out.println(currentObj.get(Calendar.MONTH));
				//printTitle();
				calculate();
			}
		});
		
		//이전월
				bt_prev.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						//현재 날짜 객체정보를 먼저 얻자
						int mm=currentObj.get(Calendar.MONTH);  //현재월
						currentObj.set(Calendar.MONTH, mm-1);
						
						//System.out.println(currentObj.get(Calendar.MONTH));
						//printTitle();
						calculate();
					}
				});
				
				bt_regist.addActionListener(this);
	}
	
	//요일출력
	public void createDayOfWeek() {
		//7개를 생성하여 패널에 부착
		for(int i=0;i<dayCells.length;i++) {
			dayCells[i]=new DayCell(dayTitle[i], "", 19, 20, 20);
			p_dayOfWeek.add(dayCells[i]);  //화면에 부착
		}
	}
	
	//날짜출력
	public void createDayOfMonth() {
		//int n=0;
		
		for(int i=0;i<dateCells.length;i++) {  //층
			for(int a=0;a<dateCells[i].length;a++) {  //호수
				//n++;
				dateCells[i][a]=new DateCell(this, "", "", 12, 40, 10);  //Integer.toString(n)
				
				//패널에 부착
				p_dayOfMonth.add(dateCells[i][a]);
			}
		}
	}
	
	//제목 출력
	public void printTitle() {
		int yy=currentObj.get(Calendar.YEAR);
		int mm=currentObj.get(Calendar.MONTH);
		
		String str=yy+"년" +(mm+1)+"월";
	
		la_title.setText(str);
	}
	
	/*
	 달력을 구현하기 위한 두가지 정보
	 1) 해당 월이 무슨 요일부터 시작하는가?
	 -. 날짜 객체를 생성하여, 조작(1일로 보냄)
	 -. 1일인 상태에서 해당 날짜 객체에게 요일을 물어봄
	 -. ex) 3요일 --> 화요일
	  
	 2) 해당 월이 몇일까지 인가?
	 -. 날짜 객체를 생성하여 조작
	 달을 +1 한 다음에 0은 전 달을 나타냄
	 */
	public int getStartDayOfWeek() {
		Calendar cal=Calendar.getInstance();
		int yy=currentObj.get(Calendar.YEAR);  //년도
		int mm=currentObj.get(Calendar.MONTH);  //월
		
		cal.set(yy, mm, 1);  //1일로 조작
		int day=cal.get(Calendar.DAY_OF_WEEK);  //요일 추출
		
		return day;
	}
	
	public int getLastDayOfMonth() {
		Calendar cal=Calendar.getInstance();
		int yy=currentObj.get(Calendar.YEAR);
		int mm=currentObj.get(Calendar.MONTH);
		
		cal.set(yy, mm+1, 0);  //조작완료
		int date=cal.get(Calendar.DATE);  
		
		return date;
	}
	
	//날짜출력
	//글씨만 변경
	public void printDate() {
		//모든 셀에 접근하여 알맞는 문자 출력
		int n=0;  //시작 시점 지표
		int d=0;  //날짜 출력 변수
		for(int i=0;i<dateCells.length;i++) {  //층수
			for(int a=0;a<dateCells[i].length;a++) {  //호수
				DateCell cell=dateCells[i][a];
				n++;
				if(n>=getStartDayOfWeek() && d< getLastDayOfMonth()){
					d++;  //꾹참았다가, n이 시작요일 이상일 때부터 ++
					cell.title=Integer.toString(d);
				}else {
					cell.title="";
				}
			}
		}
		p_dayOfMonth.repaint();  //패널 갱신
	}
	
	public void calculate() {
		printTitle();  //현재날짜 제목출력
		//getStartDayOfWeek();
		//getLastDayOfMonth();  
		printDate();  //날짜 번호 출력
		printLog();  //기록된 다이어리 출력
	}
	
	public void printLog() {
		int yy=currentObj.get(Calendar.YEAR);
		int mm=currentObj.get(Calendar.MONTH);
		
		List<Diary> diaryList=diaryDAO.selectAll(yy, mm);  //현재 보고있는 연도, 현재 보고있는 월
		System.out.println("등록된 다이어리 수는 "+diaryList.size());
		
		//현재 월의 모든 날짜를 대상으로 반복문 수행
		for(int i=0;i<dateCells.length;i++) {
			for(int a=0;a<dateCells[i].length;a++) {
				if(dateCells[i][a].title.equals("")==false) {  //숫자가 아닌 "" 공백과 같은 문자열은 정수화 시킬 수 없으므로 조건문으로 걸러내자				
					int date=Integer.parseInt(dateCells[i][a].title);  //날짜, 숫자 추출하기  //"3"만 parseInt 가능, "a", ""
					
					//불러온 데이터만큼 반복문 수행
					for(int x=0;x<diaryList.size();x++) {
						Diary obj=diaryList.get(x);  //다이어리 한건 추출
						if(date==obj.getDd()) {  
							//해당 셀에 데이터 표현
							dateCells[i][a].color=Color.ORANGE;
							dateCells[i][a].content=obj.getContent();
						}
					}
				}
			}
		}
		p_dayOfMonth.repaint();
	}
	
	//데이터 베이스와 관련된 쿼리 로직을 중복 정의하지 않기 위해
	//즉, 코드 재사용을 위해 쿼리만을 전담하는 객체를 별도로 정의하여
	//유지보수성을 높이자 이러한 목적으로 정의되는 객체를 어플리케이션 
	//설계분야에서는 DAO라 함 (Data Access Object)
	//What to make? how to make?
	public void regist() {
		//Diary DTO생성 (Empty 상태=텅빈 상태)
		Diary d=new Diary();
		System.out.println("호출 전 d"+d);
		
		//UnBoxing : 객체형이 기본자료형으로 변경  
		String yy=(String)box_yy.getSelectedItem();  
		String mm=(String)box_mm.getSelectedItem();  
		String dd=(String)box_dd.getSelectedItem();  
		String content=area.getText();
		String icon=(String)box_icon.getSelectedItem();
		
		//레코드 한건 채워넣기
		d.setYy(Integer.parseInt(yy));
		d.setMm(Integer.parseInt(mm));
		d.setDd(Integer.parseInt(dd));
		d.setContent(content);
		d.setIcon(icon);
		
		int result=diaryDAO.insert(d);  //값이 채워짐
		if(result>0) {
			JOptionPane.showMessageDialog(this, "등록성공");
			printLog();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		if(obj==bt_regist) {
			regist();
		}else if(obj==bt_del) {
			
		}
	}
	
	//셀을 선택할 경우 콤보박스의 값 변경
	public void setDateInfo(String title) {
		//콤보박스에 아이템을 누적하지 말고 싹 지운 상태에서 추가
		box_yy.removeAllItems();
		box_mm.removeAllItems();
		box_dd.removeAllItems();
		
		int yy=currentObj.get(Calendar.YEAR);
		int mm=currentObj.get(Calendar.MONTH);
		
		//Boxing : 객체화된걸 감쌈 int -> Object 
		box_yy.addItem(Integer.toString(yy));  //기존 콤보박스에 아이템 누적
		box_mm.addItem(Integer.toString(mm));  //기존 콤보박스에 아이템 누적
		box_dd.addItem(title);  //기존 콤보박스에 아이템 누적
	}
	
	public static void main(String[] args) {
		new DiaryMain();
	}
}
