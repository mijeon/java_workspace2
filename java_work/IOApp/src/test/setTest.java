package test;

import java.util.HashSet;
import java.util.Iterator;

public class setTest {

	public static void main(String[] args) {
		//set : 중복을 허용하지 않음
		HashSet<String> set=new HashSet<String>();
		set.add("apple");  //add : 요소 추가 //HashSet 함수 안에 있는 methed는 도트 연산자를 찍고 직접 사용 가능
		set.add("banana");
		set.add("orenge");
		
		for(String a : set) {
			System.out.println(a);
		}
		/*
		//순서없는 배열을 순서있게 나열하는 도구
		Iterator<String> it=set.iterator();
		
		//그 다음 요소가 존재하는지 여부를 알려줌 
		while(it.hasNext()) {
			String fruit=it.next();
			System.out.println(fruit);
		}*/
	}

}
