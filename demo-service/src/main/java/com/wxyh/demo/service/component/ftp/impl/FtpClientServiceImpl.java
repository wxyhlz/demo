package com.wxyh.demo.service.component.ftp.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.Assert;

import com.wxyh.demo.service.component.ftp.FtpClientService;

/**
 * FTP服务类
 * @author wxyh
 *
 */
public class FtpClientServiceImpl implements FtpClientService {

	private final Logger logger = LoggerFactory.getLogger(FtpClientServiceImpl.class);
	
	private static final int DEFAULT_CONNECT_TIMEOUT = 10000;
	private static final int DEFAULT_BUF_SIZE = 2048;
	
	private static ThreadLocal<FTPClient> ftpClientTL = new ThreadLocal<FTPClient>();
	
	private Resource location;
	
	private final Properties props = new Properties();

	public void setLocation(Resource location) {
		this.location = location;
	}
	
	/**
	 * bean初始化方法
	 * @throws IOException
	 */
	public void init() throws IOException {
		Assert.notNull(this.location, "Resource [location] must not be null!");
		PropertiesLoaderUtils.fillProperties(this.props, this.location);
	}

	private FTPClient createFtpClient() throws IOException {
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect(props.getProperty("ftp.hostname"), 
				getIntPropValue("ftp.port", FTPClient.DEFAULT_PORT));
		ftpClient.login(props.getProperty("ftp.username"), props.getProperty("ftp.password"));
		ftpClient.setConnectTimeout(getIntPropValue("ftp.connectTimeout", DEFAULT_CONNECT_TIMEOUT));
		ftpClient.setBufferSize(getIntPropValue("ftp.bufSize", DEFAULT_BUF_SIZE));
		ftpClient.setControlEncoding("UTF-8");
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		return ftpClient;
	}

	@SuppressWarnings("unused")
	private void closeFtpClient() {
		FTPClient ftpClient = ftpClientTL.get();
		if (ftpClient != null && ftpClient.isConnected()) {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				logger.error("关闭FTP连接失败！", e);
			}
		}
	}
	
	private FTPClient getFtpClient() throws IOException {
		FTPClient ftpClient = ftpClientTL.get();
		if (ftpClient == null || !ftpClient.isConnected()) {
			ftpClient = createFtpClient();
			ftpClientTL.set(ftpClient);
		}
		return ftpClient;
	}
	
	private int getIntPropValue(String key, int defaultValue) {
		return NumberUtils.toInt(props.getProperty(key), FTPClient.DEFAULT_PORT);
	}
	
	@Override
	public void uploadExportExcel(String remoteFilePath, Workbook workbook) throws IOException {
		Assert.notNull(workbook, "Workbook [workbook] must not be null.");
		FTPClient ftpClient = getFtpClient();
		ftpClient.changeWorkingDirectory("/");
		String remote = encodingPath(remoteFilePath);
		workbook.write(ftpClient.storeFileStream(remote));
		ftpClient.completePendingCommand();
	}
	
	@Override
	public InputStream getRemoteFileStream(String remoteFilePath) throws IOException {
		FTPClient ftpClient = getFtpClient();
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
