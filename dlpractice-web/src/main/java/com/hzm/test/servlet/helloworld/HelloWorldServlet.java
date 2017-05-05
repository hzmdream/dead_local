package com.hzm.test.servlet.helloworld;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * @author usky
 * @date 2017年3月14日下午7:38:58
 */
public class HelloWorldServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

    public HelloWorldServlet() {
    	
    }
    
    /**
     * 可以使用该方法，读取属性文件，或者使用jdbc数据库
     */
    @Override
    public void init() throws ServletException {
    	System.out.println("Servlet "+this.getServletName()+" has started.");
    }
    /**
     * 接收一个get请求返回 html 显示：hello, world!
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        out.println("<HTML>");
        out.println("  <HEAD><TITLE>HelloWorldServlet</TITLE></HEAD>");
        out.println("  <BODY>");
        out.print("hello world !  This is ");
        out.print(this.getClass());
        out.println(", using the GET method!");
        
        
        //获取上下文初始化参数
        String fozu = request.getServletContext().getInitParameter("佛祖");
        String zhizhe = request.getServletContext().getInitParameter("智者");
        
        //获取Servlet初始化参数
        String hou = this.getServletConfig().getInitParameter("hou");
        String sun = this.getServletConfig().getInitParameter("sun");
        
        out.println("<br/><hr/><br/>");
        out.println("获取上下文初始化参数："+fozu);
        out.println("获取上下文初始化参数："+zhizhe);
        out.println("<br/><hr/><br/>");
        out.println("获取Servlet初始化参数："+hou);
        out.println("获取Servlet初始化参数："+sun);
        out.println("<br/><hr/><br/>");
        
        
        out.println("</BODY>");
        out.println("</HTML>");
        /**
         * 并不需要调用PrintWriter（从参数response中获得）的close方法
         * 一般来说，在java中只需要关闭自己创建的自愿即可，web容器创建了该资源，所以它也会负责关闭该资源
         * 即使将该实例赋给一个局部变量，并且调用了它的几个方法，也是如此
         */
        //out.flush();
        //out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * 总是应该使用destroy方法清理Servlet持有的资源（在所有请求的处理过程中）
	 */
	@Override
	public void destroy() {
		System.out.println("Servlet "+this.getServletName()+" has stopped.");
	}
}
