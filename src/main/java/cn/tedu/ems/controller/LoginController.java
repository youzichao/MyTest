package cn.tedu.ems.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.tedu.ems.entity.User;
import cn.tedu.ems.service.ApplicationException;
import cn.tedu.ems.service.LoginService;

@Controller
public class LoginController {
	@Resource(name="loginService")
	private LoginService ls;
	
	@RequestMapping("/checkcode.do")
	//�����֤��ͼƬ
	public void checkcode(HttpServletRequest request,HttpServletResponse response) throws IOException{
		System.out.println("checkcode......");
		/**
		 * step1 ����ͼƬ
		 */
		//����һ������
		BufferedImage image=new BufferedImage(80, 30, BufferedImage.TYPE_INT_RGB);
		//��û���
		Graphics g=image.getGraphics();
		//����������ɫ
		g.setColor(new Color(255,255,255));
		//���û����ı�����ɫ
		g.fillRect(0, 0, 80, 30);
		Random r=new Random();
		//����������ɫ
		
		g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
		//��������
		g.setFont(new Font(null,Font.BOLD,24));
		//����һ����� ��
		String number=getNumber(5);
		//��ȡsession����,����֤��󶨵�session������
		HttpSession s=request.getSession();
		s.setAttribute("number", number);
		//��ͼƬ�ϻ��������
		g.drawString(number,3, r.nextInt(25));
		
		//��Ӹ�����
		for(int i=0;i<8;i++){
			
			g.drawLine(r.nextInt(80), r.nextInt(30), r.nextInt(80), r.nextInt(30));
			g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
		}
		
		/**
		 * step2 ѹ��ͼƬ�����
		 */
		//��������������������ص���һ��ͼƬ
		response.setContentType("image/jpeg");
		//Ҫ����ֽ������
		OutputStream output=response.getOutputStream();
		//��ԭʼ���ݰ���ָ���ĸ�ʽ��jpeg������ѹ����Ȼ�����
		javax.imageio.ImageIO.write(image, "jpeg",output );
		output.close();
	}
	private String getNumber(int size){
		String chars="ABCDEFJHIJKLMNOPQRSTUVWXYZ0123456789";
		String number="";
		Random rand=new Random();
		for(int i=0;i<size;i++){
			number+=chars.charAt(rand.nextInt(chars.length()));
		}
		return number;
	}
	
	@RequestMapping("/toLogin.do")
	public String toLogin(){
		System.out.println("toLogin()");
		return "login";
	}
	
	@RequestMapping("/login.do")
	public String login(HttpServletRequest request,HttpSession session){
		System.out.println("login()");
		
		String username=request.getParameter("username");
		String pwd=request.getParameter("pwd");
		System.out.println(username+":"+pwd);
		//������ַ���ҵ��㴦��
		//try {
			User user=ls.checkLogin(username, pwd);
			//��¼�ɹ������û�����󶨵�session������
			session.setAttribute("user", user);
			
//		} catch (Exception e) {
//			e.printStackTrace();
//			//�����쳣���ͣ��ֱ�����ͬ����
//			if(e instanceof ApplicationException){
//				//Ӧ���쳣��Ӧ��ȷ��ʾ�ͻ�
//				request.setAttribute("login_failed", e.getMessage());
//				return "login";
//			}
//			//ϵͳ�쳣����ʾ�û��Ժ�����
//			return "error";
//			
		//}
		//��¼�ɹ�
		//�ض�����ҳ
		
		return "redirect:toIndex.do";
	}
	@RequestMapping("/toIndex.do")
	public String toIndex(){
		return "index";
	}
	@ExceptionHandler
	public String handle(Exception e,HttpServletRequest request){
		if(e instanceof ApplicationException){
			request.setAttribute("login_failed", e.getMessage());
			return "login";
		}
		return "error";
	}
}


