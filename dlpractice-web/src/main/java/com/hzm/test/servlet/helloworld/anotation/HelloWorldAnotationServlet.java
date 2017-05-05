package com.hzm.test.servlet.helloworld.anotation;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




/**
 * @author usky
 * @date 2017年3月14日下午7:38:58
 */
@WebServlet(
	name = "HelloWorldAnotationServlet",
	urlPatterns = {"/helloworldanotation","/testzhujie"},
	loadOnStartup = 1,
	initParams ={
		@WebInitParam(name="hou",value="zhiming"),
		@WebInitParam(name="sun",value="wukong")
	}
)
public class HelloWorldAnotationServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

    public HelloWorldAnotationServlet() {
    	System.out.println("HelloWorldAnotationServlet Constructor...");
    }
    
    /**
     * 可以使用该方法，读取属性文件，或者使用jdbc数据库,该方法在servlet实例化后调用
     */
    @Override
    public void init() throws ServletException {
    	System.out.println("Servlet "+this.getServletName()+" has started.");
    }
    /**
     * 接收一个get请求返回 html 显示：hello, world!
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//response编码处理
		response.setContentType("text/html;charset=UTF-8");//方式一
//		response.setHeader("content-type", "text/html;charset=UTF-8");//方式二
		
		
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        out.println("<HTML>");
        out.println("  <HEAD><TITLE>HelloWorldAnotationServlet</TITLE></HEAD>");
        out.println("  <BODY>");
        out.println("<h1>hello, world!</h1>");
        
        //获取当前Servlet类名
        out.println("className: "+this.getClass());
        out.println("<hr/>");
        
        //获取Servlet初始化参数
        out.print("获取Servlet初始化参数--getServletConfig()："+this.getServletConfig().getInitParameter("hou"));
        out.print("获取Servlet初始化参数--getServletConfig()："+this.getServletConfig().getInitParameter("sun"));
        
        
        //获取参数名为id的参数
        out.println("getParameter: "+request.getParameter("id"));
        out.println("<hr/>");
        
        //获取所有参数的名称的Enumeration
        out.println("getParameterNames: ");
        Enumeration<String> en = request.getParameterNames();
        while (en.hasMoreElements()) {
        	out.println(en.nextElement());
			
		}
        out.println("<hr/>");
        
        //获取所有参数,参数名的Map
        out.println("getParameterMap: ");
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
        	out.println("parameter: "+entry.getKey()+"; value: "+Arrays.toString(entry.getValue())+"<br/>");
		}
        out.println("<hr/>");
        
        out.println("getParameterValues: "+Arrays.toString(request.getParameterValues("names")));//获取参数名为names的所有参数值，例如：names = 123,12334,4444
        
        
        out.println("获取请求的mime类型--getContentType： "+request.getContentType()+"<hr/>");//获取请求的mime类型
        out.println("获取请求正文的长度--getContentLength： "+request.getContentLength()+"<hr/>");//获取请求正文的长度
        out.println("获取请求正文的长度(请求内容超过2GB的)--getContentLengthLong： "+request.getContentLengthLong()+"<hr/>");//获取请求正文的长度(请求内容超过2GB的)
        out.println("获取请求正文的长度(请求内容超过2GB的)--getHeader('Content-Length')： "+request.getHeader("Content-Length")+"<hr/>");//获取请求正文的长度(请求内容超过2147483647字节的)
        out.println("获取请求正文字符编码格式--getCharacterEncoding： "+request.getCharacterEncoding()+"<hr/>");//获取请求正文字符编码格式
        
        
        //获取请求特有的数据 url uri 请求头
        out.println("获取请求特有的数据-- url： "+request.getRequestURL()+"<hr/>");//获取请求特有的数据 url
        out.println("获取请求特有的数据-- uri： "+request.getRequestURI()+"<hr/>");//获取请求特有的数据 url
        out.println("获取请求特有的数据-- servlet映射url路径 ： "+request.getServletPath()+"<hr/>");//获取请求特有的数据请求头
        
       
        
        
        out.println("<hr/>");
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
