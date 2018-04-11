package ai;

public class DemoServiceImpl implements DemoService {

    @Override
    public void add() {
        System.out.println("执行添加的方法");
    }

    @Override
    public void del() {
        System.out.println("执行删除的方法");
    }
}
