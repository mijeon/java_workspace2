package test;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class MapTest {

	public static void main(String[] args) {
		//데이터를 저장할 때 key와 value가 짝을 이뤄 저장되는컬렉션
		//<> 안에는 객체 자료형만 들어감 int -> Integer : boxing
		HashMap<String, Integer> map=new HashMap();
		
		map.put("apple", 1);  //요소추가
		map.put("banana", 2);
		map.put("orenge", 3);
		
		//순서가 없는것을 순서있게 처리하여 반복문 사용해보기
		Set<String> set=map.keySet();
		
		Iterator<String> it=set.iterator();
		
		while(it.hasNext()) {
			String key=it.next();
			
			int value=map.get(key);  //int -> Integer -> int : unboxing
			System.out.println(value);
		}
	}

}
