package one;

public class OneServlet extends Father {
    @Override
    public void get() {
        super.get();
        System.out.println("oneservlet,get");
    }

    @Override
    public void post() {
        super.post();
        System.out.println("oneservlet,post");
    }
}
