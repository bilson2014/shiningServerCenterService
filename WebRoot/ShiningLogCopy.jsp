<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@page import="java.io.*"%>
<%@page import="java.security.*"%>
<%@page import="java.text.*"%>
<%@page  import="java.util.*;" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<body>
	<%
			String result = null;
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update("shiningpassword".getBytes());
				byte b[] = md.digest();

				int i;

				StringBuffer buf = new StringBuffer("");
				for (int offset = 0; offset < b.length; offset++) {
					i = b[offset];
					if (i < 0)
						i += 256;
					if (i < 16)
						buf.append("0");
					buf.append(Integer.toHexString(i));
				}
				result = buf.toString();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			String logname = request.getParameter("logname");
			String message=request.getParameter("message");
			if(result.equals(message)&&logname!=null)
			{
			OutputStream outputStream = response.getOutputStream();
			byte b[] = new byte[600];
			File fileload = new File(
					"D:/serverLogs/shiningserver/log/daylogs/"+logname);
					if(fileload.exists()){
						long fileLength = fileload.length();
			            String length = String.valueOf(fileLength);
			            response.setHeader("Content_length", length);
			            FileInputStream inputStream = new FileInputStream(fileload);
			            int n = 0;
			             while ((n = inputStream.read(b)) != -1) {
				         outputStream.write(b, 0, n);
			               }
						    inputStream.close();
					}else
					{
						outputStream.write("NoFiles".getBytes());
					}
					   

			            outputStream.flush();
			            outputStream.close();
			            outputStream = null;
			            response.flushBuffer();
			            out.clear();
			            out = pageContext.pushBody();
			}
		%>
</body>
</html>
