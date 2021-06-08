package hello.core.singleton;

public class SingletonService {

    //1. static 영역에 객체를 딱 1개만 생성해둔다
    private static final SingletonService instance = new SingletonService();


    //2. public 으로 열어서 필요하면 이거 스태틱으 가져가기만 해라
    public static SingletonService getInstance() {
        return instance;
    }

    //3. 생성자는 private로 만들어라 (new 로 못만들게 막ㅇ기)
    private SingletonService() {

    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
