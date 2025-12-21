package huangjiabin.tool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class TimeTool {
    private static final SimpleDateFormat FMT = new SimpleDateFormat("HH:mm:ss.SSS");

    public interface Task{
        void execute();
    }
    public static void check(String title,Task task){
        if(task == null) return;
        System.out.println("【" + title + "】");
        System.out.println("开始：" + FMT.format(new Date()));
        long begin = System.currentTimeMillis();
        task.execute();
        long end = System.currentTimeMillis();
        System.out.println("结束：" + FMT.format(new Date()));
        double delta = (end - begin) / 1000.0;
        System.out.println("耗时：" + delta + "秒");
        System.out.println("------------------------------------");
    }
}
