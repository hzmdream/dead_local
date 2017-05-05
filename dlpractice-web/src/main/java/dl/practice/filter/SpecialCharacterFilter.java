package dl.practice.filter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SpecialCharacterFilter implements Filter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SpecialCharacterFilter.class);

	private static List<String> specialCharacters;

	public static List<String> getSpecialCharacters() {
		return specialCharacters;
	}

	public static void setSpecialCharacters(List<String> specialCharacters) {
		SpecialCharacterFilter.specialCharacters = specialCharacters;
	}

	@Override
	public void destroy() {

	}

	private String getURI(HttpServletRequest req) {
		String uri = req.getRequestURI();
		return uri.substring(req.getContextPath().length() + 1,uri.length() - 3);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String uri = getURI(req);
		if (uri.equals("pay") || uri.equals("refundPay")) {
			chain.doFilter(req, res);
			return;
		}
		if (ServletFileUpload.isMultipartContent(req)) {
			// List<FileItem> fileItems;
			// try {
			// fileItems = new ServletFileUpload(new
			// DiskFileItemFactory()).parseRequest(req);
			// for (FileItem fileItem : fileItems) {
			// if (fileItem.isFormField()) {
			// String val = fileItem.getString("UTF-8");
			// if (hasSpecialChar(val)) {
			// LOGGER.error("[SPECIALCHAR] " + fileItem.getFieldName() + " : " +
			// val);
			// ResponseHelper
			// .output(res, BCException.SPECIAL_CHAR);
			// //
			// res.sendError(HttpServletResponse.SC_BAD_REQUEST,"请求内容含有特殊字符");
			// return;
			// }
			// }
			// }
			// } catch (FileUploadException e) {
			// LOGGER.error(e.getMessage());
			// }
		} else {
			Map<String, String[]> params = req.getParameterMap();
			for (Entry<String, String[]> kvpair : params.entrySet()) {
				String[] vals = kvpair.getValue();
				if (vals != null && vals.length > 0) {
					for (String val : vals) {
						if (hasSpecialChar(val)) {
							LOGGER.error("[SPECIALCHAR] " + kvpair.getKey()
									+ " : " + val);
							// ResponseHelper.output(res,
							// BCException.SPECIAL_CHAR);
							// res.sendError(HttpServletResponse.SC_BAD_REQUEST,"请求内容含有特殊字符");
							return;
						}
					}
				}
			}
		}
		chain.doFilter(req, res);
	}

	// "drop"之后的字符串为2015-2-13之后新加的
	// , "$", "%", "@", "\"", "\\", "()", "+", ",", "0x0d", "0x0a", "0X0D",
	// "0X0A","0X0d", "0X0a", "0x0D", "0x0A"

	public boolean hasSpecialChar(String val) {
		for (String errflag : specialCharacters) {
			if (val.contains(errflag) || val.contains(errflag.toUpperCase())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
