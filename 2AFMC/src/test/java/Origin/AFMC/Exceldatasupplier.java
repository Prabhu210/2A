package Origin.AFMC;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Exceldatasupplier {
    
    @DataProvider(name = "logindata")
    public Object[][] getdata() throws IOException, ParseException {
        File excelfile = new File("C:\\Users\\Acviss\\git\\repository8\\2AFMC\\Excel\\18-04-2024.xlsx");
        FileInputStream fis = new FileInputStream(excelfile);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet("Sheet1"); // Assuming the sheet name is "Sheet1"
        int noOfRows = sheet.getPhysicalNumberOfRows();
        Object[][] data = new Object[noOfRows - 1][6]; // Array to store both code and product

        for (int i = 1; i < noOfRows; i++) { // Starting from 1 to skip header row
            DataFormatter df = new DataFormatter();
            data[i - 1][0] = df.formatCellValue(sheet.getRow(i).getCell(1)); // Code
            data[i - 1][1] = df.formatCellValue(sheet.getRow(i).getCell(3)); // Product
            data[i - 1][2] = df.formatCellValue(sheet.getRow(i).getCell(4)); // Pack
            data[i - 1][3] = df.formatCellValue(sheet.getRow(i).getCell(7)); // Batch
            data[i - 1][4] = formatDate(df.formatCellValue(sheet.getRow(i).getCell(8))); // Mfd date
            data[i - 1][5] = formatDate(df.formatCellValue(sheet.getRow(i).getCell(9))); // Exp date
        }

        workbook.close();
        fis.close();
        return data;
    }

    private String formatDate(String dateString) throws ParseException {
        if (dateString.isEmpty()) {
            // Handle empty string case here
            return null; // or return a default value
        }

        // Define the input and output date formats
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssXXX");
        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");

        // Parse the input date string
        Date date = inputFormat.parse(dateString.trim());

        // Format the parsed date to the desired format
        return outputFormat.format(date);
    }

}
