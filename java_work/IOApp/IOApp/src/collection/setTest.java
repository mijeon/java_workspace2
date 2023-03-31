package collection;

import java.util.HashSet;
import java.util.Iterator;

/*순서없는 컬렉션 자료형 중 set을 학습해본다*/
public class setTest {
	public static void main(String[] args) {
		//순서가 없는 컬렉션 선언
		HashSet<String> set= new HashSet<String>();
		set.add("apple");  //add : 요소 추가 //HashSet 함수 안에 있는 methed는 도트 연산자를 찍고 직접 사용 가능
		set.add("banana");
		set.add("orenge");
		
		//for(int i=0;i<set.size();i++) {
			//Iterator : 순서없는 배열을 순서있게 나열하는 도구
			//순서없는 Set을 나열해보자, Iterator를 통해서
		
		Iterator<String> it=set.iterator();
			
		///그 다음 요소가 존재하는지 여부를 알려줌
		//반복에 더 많은 요소가 있으면 hasNext()를 통해 true를 반환하고 next()를 통해 한칸씩 이동함
		while(it.hasNext()) {
			String fruit=it.next();  //한칸 이동
			System.out.println(fruit); 
				
			}
	}
}
