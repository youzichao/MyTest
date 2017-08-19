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
	//输出验证码图片
	public void checkcode(HttpServletRequest request,HttpServletResponse response) throws IOException{
		System.out.println("checkcode......");
		/**
		 * step1 生成图片
		 */
		//创建一个画布
		BufferedImage image=new BufferedImage(80, 30, BufferedImage.TYPE_INT_RGB);
		//获得画笔
		Graphics g=image.getGraphics();
		//给笔设置颜色
		g.setColor(new Color(255,255,255));
		//设置画布的背景颜色
		g.fillRect(0, 0, 80, 30);
		Random r=new Random();
		//给笔设置颜色
		
		g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
		//设置字体
		g.setFont(new Font(null,Font.BOLD,24));
		//生成一个随机 数
		String number=getNumber(5);
		//获取session对象,将验证码绑定到session对象上
		HttpSession s=request.getSession();
		s.setAttribute("number", number);
		//在图片上绘制随机数
		g.drawString(number,3, r.nextInt(25));
		
		//添加干扰线
		for(int i=0;i<8;i++){
			
			g.drawLine(r.nextInt(80), r.nextInt(30), r.nextInt(80), r.nextInt(30));
			g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
		}
		
		/**
		 * step2 压缩图片并输出
		 */
		//告诉浏览器，服务器返回的是一张图片
		response.setContentType("image/jpeg");
		//要获得字节输出流
		OutputStream output=response.getOutputStream();
		//将原始数据按照指定的格式（jpeg）进行压缩，然后输出
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
		//将请求分发给业务层处理
		//try {
			User user=ls.checkLogin(username, pwd);
			//登录成功，将用户对象绑定到session对象上
			session.setAttribute("user", user);
			
//		} catch (Exception e) {
//			e.printStackTrace();
//			//依据异常类型，分别做不同处理
//			if(e instanceof ApplicationException){
//				//应用异常，应明确提示客户
//				request.setAttribute("login_failed", e.getMessage());
//				return "login";
//			}
//			//系统异常，提示用户稍后重试
//			return "error";
//			
		//}
		//登录成功
		//重定向到首页
		
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


