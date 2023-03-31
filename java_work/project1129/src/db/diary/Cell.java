package db.diary;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

	
public class Cell extends JPanel{
	String title;  //날짜
	String content;  //내용
	int fontSize;
	int x;
	int y;
	
	public Cell(String title, String content, int fontSize, int x, int y) {
		this.title=title;
		this.content=content;
		this.fontSize=fontSize;
		this.x=x;
		this.y=y;

	}
}
