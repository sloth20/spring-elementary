package hello.core.singleton;

public class StatefulService {
    private int price; // state

    public void order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        this.price = price; // 공유되는 필드인 price를 특정 클라이언트가 변경할 수 있음 -> 문제 발생
    }

    public int getPrice() {
        return price;
    }
}
