package tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ParsingFilesTest {

    private final ClassLoader cl = ParsingFilesTest.class.getClassLoader();

    @Test
    void zipFileParsingTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(
                cl.getResourceAsStream("filesfortest.zip")
        )) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                System.out.println(entry.getName());
            }
        }
    }
    @Test
    @DisplayName("Проверка PDF файла")
    void pdfFileParsingTest() throws Exception {
        boolean havepdf = false;
        try (ZipInputStream is = new ZipInputStream(
                cl.getResourceAsStream("filesfortest.zip")
        )) {
            ZipEntry entry;

            while ((entry = is.getNextEntry()) != null) {
                if (entry.getName().endsWith("pdf")) {
                    havepdf = true;
                    PDF pdf = new PDF(is);
                    Assertions.assertTrue(pdf.text.contains("Автоматизация"));
                    break;
                }
            }
        }
        Assertions.assertTrue(havepdf, "Не найден файл PDF");
    }

    @Test
    @DisplayName("Проверка CSV файла")
    void csvFileParsingTest() throws Exception {
        boolean havecsv = false;
        try (ZipInputStream is = new ZipInputStream(
                cl.getResourceAsStream("filesfortest.zip")
        )) {
            ZipEntry entry;

            while ((entry = is.getNextEntry()) != null) {
                if (entry.getName().endsWith("csv")) {
                    havecsv = true;
                    CSVReader csvReader = new CSVReader(new InputStreamReader(is));
                    List <String[]> data = csvReader.readAll();
                    Assertions.assertEquals(3, data.size());
                    Assertions.assertArrayEquals(
                            new String [] {"first_name","last_name"},
                            data.get(0)
                    );
                    Assertions.assertArrayEquals(
                            new String [] {"James","Butt"},
                            data.get(1)
                    );
                    Assertions.assertArrayEquals(
                            new String [] {"Josephine","Darakjy"},
                            data.get(2)
                    );
                }
            }
        }
        Assertions.assertTrue(havecsv, "Не найден файл CSV");
    }
    @Test
    @DisplayName("Проверка Excel таблицы")
    void xlsFileParsingTest() throws Exception{
        boolean havexls = false;
        try (ZipInputStream is = new ZipInputStream(
                cl.getResourceAsStream("filesfortest.zip")
        )) {
            ZipEntry entry;

            while ((entry = is.getNextEntry()) != null) {
                if (entry.getName().endsWith("xlsx")) {
                    havexls = true;
                    XLS xls = new XLS(is);
                    String actualValue = xls.excel.getSheetAt(2).getRow(0).getCell(0).getStringCellValue();
                    Assertions.assertTrue(actualValue.contains("Третья страница Excel таблички для  урока №8 по автоматизации тестирования на Java"));
                }
            }
        }
        Assertions.assertTrue(havexls, "Не найден файл Excel");
    }
}