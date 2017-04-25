package com.wxyh.demo.web.system.converter;

import java.io.IOException;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wxyh.demo.service.component.ftp.FtpClientService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-context.xml", "classpath:spring-db.xml"})
public class FtpClientServiceTest {

	@Autowired
	FtpClientService ftpClientService;
	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
//	@Test
//	public void testGetRemoteFileStream() throws IOException {
//		String remoteFilePath = "test/test-1.txt";
//		InputStream in = ftpClientService.getRemoteFileStream(remoteFilePath);
//		BufferedReader br = new BufferedReader(new InputStreamReader(in));
//		String str = null;
//		while ((str = br.readLine()) != null) {
//			System.out.println(str);
//		}
//		br.close();
//		in.close();
//	}
	
	@Test
	public void testUploadExportExcel() throws IOException {
		String remoteFilePath = "test/excel-1.xlsx";
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet();
		for (int i = 0; i < 500000; i++) {
			Row row = sheet.createRow(i);
			for (int j = 0; j < 5; j++) {
				Cell cell = row.createCell(j, Cell.CELL_TYPE_STRING);
				cell.setCellValue(Math.random());
			}
		}
		ftpClientService.uploadExportExcel(remoteFilePath, wb);
	}
	
}
