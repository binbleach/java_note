package Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class test {
    public static void main(String[] args) {
        Date a = new Date(); //现在的时间
        System.out.println(a);
        SimpleDateFormat  b = new SimpleDateFormat("yy-MM-dd HH:mm:ss SSS");
        String c=b.format(a);
        System.out.println(c);

        String date1 = "2012-12-14";
        SimpleDateFormat t = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date tt=t.parse(date1); //String转Date
            System.out.println(tt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String date = "1999-12-14 12:13:13 123";
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS"); //格式不能错
        try {
            Date p=d.parse(date);
            System.out.println(p);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
