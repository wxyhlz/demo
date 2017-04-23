package com.wxyh.demo.service.component.ftp;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Workbook;

public interface FtpClientService {

	/**
	 * 获取ftp服务器上文件的输入流，可用于直接读取ftp服务器上的文件
	 * @param remoteFilePath ftp文件路径
	 * @return
	 * @throws IOException
	 */
	InputStream getRemoteFileStream(String remoteFilePath) throws IOException;
	
	/**
	 * 上传excel文件至ftp服务器
	 * @param remoteFilePath
	 * @param workbook
	 * @throws IOException
	 */
	void uploadExportExcel(String remoteFilePath, Workbook workbook) throws IOException;

}
