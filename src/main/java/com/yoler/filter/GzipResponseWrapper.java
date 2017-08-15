package com.yoler.filter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.CharArrayWriter;
import java.io.PrintWriter;

public class GzipResponseWrapper extends HttpServletResponseWrapper {
    private PrintWriter cachedWriter;
    private CharArrayWriter bufferedWriter;

    public GzipResponseWrapper(HttpServletResponse response) {
        super(response);
        // ��������Ǳ��淵�ؽ���ĵط�
        bufferedWriter = new CharArrayWriter();
        // ����ǰ�װPrintWriter�ģ������н��ͨ�����PrintWriterд�뵽bufferedWriter��
        cachedWriter = new PrintWriter(bufferedWriter);
    }

    @Override
    public PrintWriter getWriter() {
        return cachedWriter;
    }

    /**
     * ��ȡԭʼ��HTMLҳ�����ݡ�
     *
     * @return
     */
    public String getResult() {
        return bufferedWriter.toString();
    }
}
