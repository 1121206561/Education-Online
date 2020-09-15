import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class PoiReadAndWriteTest {
    @Test
    public void write() throws Exception {
        //创建工作簿
        Workbook workbook = new XSSFWorkbook(); 
        //创建sheet表
        Sheet sheet = workbook.createSheet("text");
        //创建row行
        Row row = sheet.createRow(0);
        //创建cell单元格
        Cell cell = row.createCell(0);
        //给单元格赋值
        cell.setCellValue("测试");
        //以流的形式输出到xlsx文件中去
        /*写大文件
        *     03版本只能向xls文件中写65536行的数据超过则不会再写
        *     07版本没有数量的限制但是性能比较差
        *     SXSSF 则对大数据的写进行了优化  分批写入数据  例如每10w个进行一次写操作
        * */
        OutputStream outputStream = new FileOutputStream("E:/text.xlsx");
        workbook.write(outputStream);
        outputStream.close();
    }

    @Test
    public void read() throws Exception {
        //以流的形式加载xlsx文件
        InputStream inputStream = new FileInputStream("E:/text.xlsx");
        //得到工作簿
        Workbook workbook = new XSSFWorkbook(inputStream);
        //得到sheet表
        Sheet sheet = workbook.getSheet("text");
        //得到row行
        Row row = sheet.getRow(0);
        //得到cell单元格
        Cell cell = row.getCell(0);
        //得到单元格的值
        String value = cell.getStringCellValue();
        System.out.println(value);
        inputStream.close();
    }
}
           
