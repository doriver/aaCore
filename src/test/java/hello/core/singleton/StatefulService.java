package hello.core.singleton;

public class StatefulService {
	
	private int price; // 공유되는 필드

	public void order(String name, int price) {
		System.out.println(name + " " + price);
		this.price = price; // 여기가 문제
	}
	
	public int getPrice() { return price; }
}
