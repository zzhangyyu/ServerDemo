package com.yoler.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class GzipRequestWrapper extends HttpServletRequestWrapper {
    private static final Logger LOG = LoggerFactory.getLogger(GzipRequestWrapper.class);
    private HttpServletRequest request;

    public GzipRequestWrapper(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        ServletInputStream stream = request.getInputStream();
        try {
            final GZIPInputStream gzipInputStream = new GZIPInputStream(stream);
            ServletInputStream newStream = new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) {

                }

                @Override
                public int read() throws IOException {
                    return gzipInputStream.read();
                }
            };
            return newStream;
        } catch (Exception e) {
            LOG.error("ungzip content fail.", e);
        }
        return stream;
    }
}
