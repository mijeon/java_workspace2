package db.diary;

public class UseDog {
	
	public static void main(String[] args) {
		//new Dog();
		Dog d1=Dog.getInstance();
		Dog d2=Dog.getInstance();
		Dog d3=Dog.getInstance();
		Dog d4=Dog.getInstance();
		Dog d5=Dog.getInstance();
		
		System.out.println(d1);
		System.out.println(d2);
		System.out.println(d3);
		System.out.println(d4);
		System.out.println(d5);

	}
}
