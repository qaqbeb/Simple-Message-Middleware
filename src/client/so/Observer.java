package so;

public class Observer extends IObserver {
    private String name = null;

    public Observer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void update(String str) {
        System.out.println("Observer观察者" + name + "\n消费了一条消息:" + str);
    }
}
