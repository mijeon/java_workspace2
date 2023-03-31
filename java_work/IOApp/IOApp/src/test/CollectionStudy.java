package test;

import java.util.ArrayList;

import javax.swing.JButton;

public class CollectionStudy {

	public static void main(String[] args) {
		ArrayList<String> list=new ArrayList<String>();
		list.add("apple");
		list.add("banana");
		list.add("orenge");
		
		
		System.out.println(list.size());
		
		for(String fruit:list) {
			System.out.println(fruit);
		}
	}

}
