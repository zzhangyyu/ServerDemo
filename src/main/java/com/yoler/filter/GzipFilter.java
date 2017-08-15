package com.yoler.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class GzipFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        GzipRequestWrapper reqWrapper = new GzipRequestWrapper(request);

        // ʹ�������Զ������Ӧ��װ������װԭʼ��ServletResponse
        GzipResponseWrapper respWrapper = new GzipResponseWrapper(response);

        // ��仰�ǳ���Ҫ��ע�⿴���ڶ������������ǵİ�װ��������response
        chain.doFilter(request, respWrapper);

        // ����ػ�Ľ�������д��������滻���еġ����ơ�Ϊ����ľ���ӡ�
        String result = respWrapper.getResult();
        result = result.replace("��¼", "��ľ����");
        resp.setContentLength(200);

        PrintWriter out = response.getWriter();
        out.write(result);
        out.flush();
        out.close();
    }

    @Override
    public void destroy() {

    }
}
