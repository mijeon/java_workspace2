/*순서없는 컬렉션 중 Map을 학습한다*/
package collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class MapTest {
	public static void main(String[] args) {
		//데이터를 저장할 때 key와 value가 짝을 이뤄 저장되는컬렉션
		//Kisses 초콜릿 연상하자
		//Key-String, Value-String
		HashMap<String, String> map=new HashMap<String, String>();
		
		map.put("apple", "사과");  //요소 추가(Key, Value)
		map.put("banana", "바나나");
		map.put("orenge", "오렌지");
		
		//순서없는 것을 순서있게 처리하여 반복문 사용해보기
		//value가 아닌 key만 set에 저장(순서없게)
		Set<String> set=map.keySet();
		
		//Key값을 늘어뜨리자 Iterator
		Iterator<String> it=set.iterator();
		
		while(it.hasNext()) {
			String key=it.next();
			//System.out.println(key);
			
			String value=map.get(key);  //키를 통해서 value을 찾는 방식
			System.out.println(value);
		}
	} 
}
