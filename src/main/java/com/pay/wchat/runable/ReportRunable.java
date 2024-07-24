package com.pay.wchat.runable;

import java.net.SocketTimeoutException;

import com.pay.wchat.services.WchatReportService;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;

/**
 * @TITLE ReportRunable.java
 * @NAME 交易保障线程
 * @DATE 2017-1-18
 */
public class ReportRunable implements Runnable {

    private WchatReportService reportService;
    private Thread t;

    public ReportRunable(WchatReportService rs) {
        reportService = rs;
    }

    public void run() {
        try {
            reportService.getReportRet();
            t = new Thread(this);
            t.setDaemon(true); // 后台线程
            t.start();
        } catch (ConnectionPoolTimeoutException e) {
            e.printStackTrace();
        } catch (ConnectTimeoutException e) {
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}