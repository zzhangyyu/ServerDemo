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

        // 使用我们自定义的响应包装器来包装原始的ServletResponse
        GzipResponseWrapper respWrapper = new GzipResponseWrapper(response);

        // 这句话非常重要，注意看到第二个参数是我们的包装器而不是response
        chain.doFilter(request, respWrapper);

        // 处理截获的结果并进行处理，比如替换所有的“名称”为“铁木箱子”
        String result = respWrapper.getResult();
        result = result.replace("登录", "铁木箱子");
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
