package db.diary;

//객체의 인스턴스가 오직 1개만 생성되는 패턴을 의미함 -> 여러개를 생성해도 하나의 주소값을 가르킴
public class Dog {
	String name="치와와";
	private static Dog instance;
		
	private Dog() {
		
	}
	
	//public Dog getInstance()  : 인스턴스 메서드로 new가 되어야 사용가능  
	//인스턴스가 생성되어야 사용가능함
	public static Dog getInstance() {  
		
		//만일 인스턴스 변수가 null이면
		//여기서 new을 해주자
		if(instance==null) {
			//나는 접근이 가능하므로 인스턴스를 생성해버리자
			instance=new Dog();
		}
		return instance;
	}
}
