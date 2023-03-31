package db.diary;


public class ObjectTest {
	
	public static void main(String[] args) {
		
		String s1="Korea";
		String s2="Korea";
		
		//System.err.println(s1==s2);  //같은 값이 상수풀에 들어갈 경우 다음 값은 이미 존재하므로 상수풀로 들어가지 않음
		System.err.println(s1.equals(s2));
	}
}
