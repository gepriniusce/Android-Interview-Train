package pr.tongson.train_java;

/**
 * <b>Create Date:</b> 2020-03-13<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 * 内部定义一个很大的数组，目的是创建对象时，会 得到更多的内存，以提高回收的可能性!
 *
 * @author tongson
 */
public class Store {
    public static final int SIZE = 10000;
    private double[] arr = new double[SIZE];
    private String id;

    public Store() {
    }

    public Store(String id) {
        super();
        this.id = id;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Store{" + "id='" + id + '\'' + '}';
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println(id + "被回收了");
    }
}
