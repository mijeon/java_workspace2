/*
 * Collection Framework 란?
 * - 객체를 모아서 처리할 때 유용한 api
 * 
 * Sun에서는 이 세상의 모든 객체들이 모여있는 모습을 크게 2가지로 구분
 * 1) 순서있는 집합
 * 		-. List 유형 (배열과 거의 같음)
 * 			- 차이점1 : 자바의 배열은 기본자료형, 객체자료형 등 모든 자료형을 대상으로 하지만,
 * 						List는 오직 객체만 담음
 * 			-차이점2 : 자바의 배열은 생성 시 그 크기를 명시해야 하므로, 크기가 고정되어 있지만
 * 						컬렉션들은 js 시절처럼 크기가 동적으로 변할 수 있음 (유연함)
 * 
 * 2) 순서없는 집합
 * 		-. Set 유형
 * 		-. Map 유형
 * 
 * */
package collection;

import java.util.ArrayList;
import java.util.List;

public class CollectionStudy {

	public static void main(String[] args) {
		/*List를 사용해 본다
		   순서있는 집합을 처리할 때 사용하는 대표적인 자료형
		   웹개발 시 압도적으로 많이 사용함]
		   배열과 차의점 - 크기가 동적, 오직 객체만 가능
		   배열 - 기본 + 객체자료형, 배열은 섞어서 사용 가능
		 */
		//Generic 타입 : 컬렉션에서 해당 컬렉션객체의 자료형을 
		//특정 자료형만으로 한정지을 수 있는 방법
		//-> List는 제너리를 선언하지 않았을 경우 섞어서 사용 가능 (문자, 버튼, 텍스트필드...)
		//장점 1) 객체가 섞여들어갈 수 없도록 컴파일 타임에 방지함
		//장점 2) 꺼낼 때, 번거로운 형변환 과정이 필요없음
		ArrayList<String> list=new ArrayList<String>();  //<> : 자료형 고정
		list.add("apple");
		list.add("banana");
		list.add("orenge");
		//list.add(new JButton(""));  //<String>으로 명시해줘서 JButton 사용 불가함
		
		System.out.println(list.size());  //요소의 길이를 반환, 배열의 length와 같음
		/*
		for(int i=0;i<list.size();i++) {
			Object obj=list.get(i);  //Object obj=(String)list.get(i);
			System.out.println(obj);
		}
		*/
		
		//java 5 버전부터 improved for문이 추가됨
		//객체를 대상으로 한 반복문을 좀 더 간결하게 
		for(String fruit:list) {  //Object
			System.out.println(fruit);
		}
	}
}
