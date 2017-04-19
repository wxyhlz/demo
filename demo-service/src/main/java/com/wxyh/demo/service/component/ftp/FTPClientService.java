package com.wxyh.demo.service.component.ftp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.Assert;

public class FTPClientService implements InitializingBean {

	private final Logger logger = LoggerFactory.getLogger(FTPClientService.class);
	
	private static final int DEFAULT_CONNECT_TIMEOUT = 10000;
	private static final int DEFAULT_BUF_SIZE = 2048;
	
	private Resource location;
	
	private final Properties props = new Properties();

	public void setLocation(Resource location) {
		this.location = location;
	}

	private FTPClient createFTPClient() throws IOException {
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect(props.getProperty("ftp.hostname"), 
				getPropValueAsInt("ftp.port", FTPClient.DEFAULT_PORT));
		ftpClient.login(props.getProperty("ftp.username"), props.getProperty("ftp.password"));
		ftpClient.setConnectTimeout(getPropValueAsInt("ftp.connectTimeout", DEFAULT_CONNECT_TIMEOUT));
		ftpClient.setBufferSize(getPropValueAsInt("ftp.bufSize", DEFAULT_BUF_SIZE));
		ftpClient.setControlEncoding("UTF-8");
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		return ftpClient;
	}

	private void closeFtpClient(FTPClient ftpClient) {
		if (ftpClient != null && ftpClient.isConnected()) {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				logger.error("关闭FTP连接失败！", e);
			}
		}
	}
	
	private int getPropValueAsInt(String key, int defaultValue) {
		return NumberUtils.toInt(props.getProperty(key), FTPClient.DEFAULT_PORT);
	}
	
	@Override
	public void afterPropertiesSet() throws IOException {
		Assert.notNull(location, "Resource [location] cannot be null!");
		PropertiesLoaderUtils.fillProperties(props, location);
	}
	
	/**
	 * 上传excel文件至ftp服务器
	 * @param remoteFilePath
	 * @param workbook
	 * @throws IOException
	 */
	public void uploadExportExcel(String remoteFilePath, Workbook workbook) throws IOException {
		Assert.notNull(workbook, "Workbook [workbook] cannot be null.");
		FTPClient ftpClient = createFTPClient();
		OutputStream out = null;
		try {
			ftpClient.changeWorkingDirectory("/");
			String remote = encodingPath(remoteFilePath);
			out = ftpClient.storeFileStream(remote);
			workbook.write(out);
			ftpClient.completePendingCommand();
		}
		finally {
			if (out != null) {
				try {
					out.close();
				}
				catch (IOException e) {
				}
			}
			closeFtpClient(ftpClient);
		}
	}
	
	/**
	 * 获取ftp服务器上文件的输出流，可用于直接把数据写入ftp服务器上的文件中
	 * @param remoteFilePath ftp文件路径
	 * @return
	 * @throws IOException
	 */
	public OutputStream getStoreFileStream(String remoteFilePath) throws IOException {
		FTPClient ftpClient = createFTPClient();
		OutputStream out = null;
		try {
			ftpClient.changeWorkingDirectory("/");
			String remote = encodingPath(remoteFilePath);
			out = ftpClient.storeFileStream(remote);
			ftpClient.completePendingCommand();
			return out;
		}
		finally {
			closeFtpClient(ftpClient);
		}
	}
	
	/**
	 * 获取ftp服务器上文件的输入流，可用于直接读取ftp服务器上的文件
	 * @param remoteFilePath ftp文件路径
	 * @return
	 * @throws IOException
	 */
	public InputStream getRemoteFileStream(String remoteFilePath) throws IOException {
		FTPClient ftpClient = createFTPClient();
		ftpClient.changeWorkingDirectory("/");
		String remote = encodingPath(remoteFilePath);
		InputStream in = ftpClient.retrieveFileStream(remote);
		ftpClient.completePendingCommand();
		return in;
	}
	
	private String encodingPath(String path) throws UnsupportedEncodingException {
		// FTP协议里面，规定文件名编码为iso-8859-1，所以目录名或文件名需要转码
		return new String(path.replaceAll("//", "/").getBytes("GBK"), "iso-8859-1");
	}
	
}
