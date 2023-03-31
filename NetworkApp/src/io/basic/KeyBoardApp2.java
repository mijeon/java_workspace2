package io.basic;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public class KeyBoardApp2 {
	public static void main(String[] args) {
		InputStream is=System.in;  //시스템에서 불러옴
		PrintStream ps=System.out;  //문자열 기반 스트림 (Char 가능)
		
		int data=-1;
		
		try {
			while(true) {
				data=is.read();  //1byte씩 읽음, 입력하지 않으면 무한대기상태에 빠짐
				//ps.println(data);  //평상시 사용해오던 System.out.println
				ps.println((char)data); //문자열로 출력
			}
		} catch (IOException e) {
			e.printStackTrace();
		}  //시스템에서 얻어오는거라 닫지 않음
	}
}
