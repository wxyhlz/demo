package com.wxyh.demo.web.system.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 生成验证码Controller
 * @author wxyh
 *
 */
public class VerificationCodeController {

	private static final String VERIFICATION_CODE = "VERIFICATION_CODE";
	
	private static final char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
	
	@RequestMapping(value = "/verifiCode")
	public void verifiedImage(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		BufferedImage img = new BufferedImage(68, 22, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		g.setColor(new Color(200, 150, 255));
		g.fillRect(0, 0, 68, 22);
		
		Random r = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			int index = r.nextInt(chars.length);
			g.setColor(new Color(r.nextInt(88), r.nextInt(188), r.nextInt(255)));
			g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 22));
			g.drawString("" + chars[index], (i * 15) + 3, 18);
			sb.append(chars[index]);
		}
		
		HttpSession session = request.getSession();
		session.setAttribute(VERIFICATION_CODE, sb.toString());
		ImageIO.write(img, "JPG", response.getOutputStream());
	}
	
}
