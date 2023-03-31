package io.basic;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MemoApp extends JFrame {
	JButton bt;
	JButton bt_open;  //파일 탐색기 띄우는 버튼
	JButton bt_save;  //area의 내용을 파일에 출력하기 위한 버튼
	JFileChooser chooser;
	JTextArea area;
	JScrollPane scroll;
	String path = "C:/java_workspace2/data/NatworkApp/res/memo.txt";
	File file;  //현재 열어놓았던 파일

	public MemoApp() {
		bt = new JButton("Load");
		bt_open=new JButton("파일열기");
		bt_save=new JButton("저장");
		chooser=new JFileChooser();
		area = new JTextArea();
		scroll = new JScrollPane(area);
		setLayout(new FlowLayout());

		scroll.setPreferredSize(new Dimension(570, 320));
		
		// 프레임에 부착
		add(bt); 
		add(bt_open);
		add(bt_save);
		add(scroll);

		setVisible(true);
		setSize(600, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadData2();
			}
		});
		
		bt_open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openFile();	
			}
		});
		
		bt_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveFile();
			}
		});
	}
	
	public void saveFile() {
		FileWriter writer=null;  //파일을 대상으로 한 문자기반 출력스트림
		BufferedWriter buffw=null;  //버퍼처리된 문자기반 스트림
		
		try {
			writer=new FileWriter(file);
			buffw=new BufferedWriter(writer);  //writer를 덮어씌움
			buffw.write(area.getText());
			JOptionPane.showMessageDialog(this, "저장완료");
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(buffw!=null) {
				try {
					buffw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(writer!=null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
	}
	
	public void openFile() {
			int result=chooser.showOpenDialog(this);
			if(result==JFileChooser.APPROVE_OPTION) {
				file=chooser.getSelectedFile();
				
				//문자기반 스트림 필요
				//아래 객체의 정체는 ? InputSreamReader의 자식
				// 문자기반 스트림을 이용할 경우 : 기존 바이트 기반 스트림 + 바이너리 스트림+bufferd 스트림
				FileReader reader=null;
				BufferedReader buffr=null;
				
				try {
					reader=new FileReader(file);
					buffr=new BufferedReader(reader);
					String data=null;
					int count=0;
					
					while(true) {
						data=buffr.readLine();
						
						if(data==null) break;
						//data는 한줄의 스트링이 들어있으며, 한줄을 area에 출력 시마다 area의 개행을 처리하자
						area.append(data+"\n");

						count++;
					}
					System.out.println("읽어드린 횟수는 "+count);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}finally {
					if(reader!=null){
						try {
							reader.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if(buffr!=null){
						try {
							buffr.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

	// 개발자가 문자기반 스트림의 존재를 모를 경우
	public void loadData() {
		// 바이트기반 (문자를 이해 못함)
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
			int data = -1;

			data = fis.read(); // input이 없으면 대기상태에 빠짐 //1byte씩 읽어드리므로 한글이 처리되지 않음
			System.out.println((char) data);

			data = fis.read(); // input이 없으면 대기상태에 빠짐
			System.out.println((char) data);

			data = fis.read(); // input이 없으면 대기상태에 빠짐
			System.out.println((char) data);

			data = fis.read(); // input이 없으면 대기상태에 빠짐
			System.out.println((char) data);

			data = fis.read(); // input이 없으면 대기상태에 빠짐
			System.out.println((char) data);

			data = fis.read(); // input이 없으면 대기상태에 빠짐
			System.out.println((char) data);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	// 문자기반 스트림을 이용할 경우 : 기존 바이트 기반 스트림 + 바이너리 스트림
	//바이너리 기반 스트림을 보완하여 사용하는 개념이라 문자를 이해함, 2바이트씩 해석이 가능
	public void loadData2() {
		InputStreamReader reader = null;
		FileInputStream fis = null;
		try {
			// 바이트 기반 스트림 생성
			fis = new FileInputStream(path);

			// 바이트 기반 스트림을 문자기반 스트림으로 업그레이드
			reader = new InputStreamReader(fis);
			int data = -1;
			
			while(true) {
				data = reader.read();  //문자기반으로 한글도 한번에 읽음
				//System.out.println(data);
				if(data==-1) break;
				area.append(Character.toString((char)data));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	

	public static void main(String[] args) {
		new MemoApp();
	}

}
