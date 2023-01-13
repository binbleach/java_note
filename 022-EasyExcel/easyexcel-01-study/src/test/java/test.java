import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.huangjiabin.easyexcel.listener.StudentListener;
import com.huangjiabin.easyexcel.po.Student;
import org.junit.Test;

public class test {
    /*
    *   工作簿：一个excel文件就是一个工作簿
    *   工作表：一个工作薄中可以有多个工作表（sheet）
    */

    //读取 excel
    @Test
    public void read(){
        //获得一个工作簿对象
        ExcelReaderBuilder read = EasyExcel.read("src/main/resources/excel/excel-model.xlsx", Student.class, new StudentListener());

        //获得一个工作表对象
        ExcelReaderSheetBuilder sheet = read.sheet();

        //读取表中内容
        sheet.doRead();

    }
}
