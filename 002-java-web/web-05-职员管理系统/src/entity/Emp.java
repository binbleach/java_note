package entity;

public class Emp {
    private String username;
    private String password;
    private int age;
    private String job;
    private double sal;

    public Emp(String username, String password, int age, String job, double sal) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.job = job;
        this.sal = sal;
    }

    public Emp() {
    }

    public String getuserName() {
        return username;
    }

    public void setName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public double getSal() {
        return sal;
    }

    public void setSal(double sal) {
        this.sal = sal;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "name='" + username + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", job='" + job + '\'' +
                ", sal=" + sal +
                '}';
    }
}
