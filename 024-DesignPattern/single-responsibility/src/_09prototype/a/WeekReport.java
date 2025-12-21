package _09prototype.a;

import java.io.*;
import java.util.Date;

public class WeekReport implements Cloneable,Serializable{
    private String id;
    private String name;
    private Date time;
    private String work;

    @Override
    public WeekReport clone() throws CloneNotSupportedException {
        WeekReport clone =null;
        /*
            // 第一种避免浅拷贝方法（太麻烦）
            clone = (WeekReport) super.clone();
            Date time = (Date)clone.getTime().clone();
            clone.setTime(time);
        */
        try {
            //第二种避免浅拷贝方法（写在硬盘）
            //OutputStream out = new FileOutputStream("WeekReport.class");
            //第三种避免浅拷贝方法 （写在内存里）
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(this);
            oos.close();
            //第二种避免浅拷贝方法（写在硬盘）
            //InputStream in = new FileInputStream("WeekReport.class");
            //第三种避免浅拷贝方法 （写在内存里）
            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(in);
            clone = (WeekReport)ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return clone;
    }

    @Override
    public String toString() {
        return "WeekReport{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", work='" + work + '\'' +
                '}';
    }

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(final Date time) {
        this.time = time;
    }

    public String getWork() {
        return this.work;
    }

    public void setWork(final String work) {
        this.work = work;
    }
}
