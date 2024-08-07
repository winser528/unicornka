package com.fit.job;

import com.fit.entity.Coupons;
import com.fit.entity.Orders;
import com.fit.service.CouponsService;
import com.fit.service.OrdersService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * @AUTO 待支付订单处理
 * @Author AIM
 * @DATE 2024/8/5
 */
@Data
@DisallowConcurrentExecution //不并发执行
@Slf4j
public class OrderExpired implements Job {
    /**
     * 任务最大尝试次数。
     */
    private Integer tries = 3;

    /**
     * 任务可以执行的最大秒数 (超时时间)。
     */
    private Integer timeout = 300;

    @Autowired
    private OrdersService service;
    @Autowired
    private CouponsService couponsService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap map = context.getMergedJobDataMap();
        if (map.containsKey("tries")) {
            tries = map.getIntValue("tries");
        }
        if (map.containsKey("timeout")) {
            timeout = map.getIntValue("timeout");
        }
        Orders orders = new Orders();
        orders.setStatus(1);
        List<Orders> list = this.service.findList(orders);
        for (Orders order : list) {
            try {
                Date createTime = order.getCreateTime();
                if (calculateSecondsDifference(createTime) > timeout) {
                    order.setStatus(-1);
                    if (order.getCouponId() != null) {
                        order.setCouponRetBack(true);
                        Coupons coupons = this.couponsService.get(order.getCouponId());
                        if (coupons != null) {
                            coupons.setIsUse(true);
                            coupons.setRet(coupons.getRet() + 1);
                            this.couponsService.update(coupons);
                        }
                    }
                    this.service.update(order);
                }
            } catch (Exception e) {
                log.error(String.format("待支付订单处理异常,SN: %s", order.getOrderSn()), e);
            }
        }
    }


    /**
     * 计算两个时间之间的毫秒数差
     *
     * @param date
     * @return
     */
    public static long calculateSecondsDifference(Date date) {
        // 获取当前时间
        LocalDateTime currentTime = LocalDateTime.now();
        Instant instant = date.toInstant();
        LocalDateTime time = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return Duration.between(time, currentTime).getSeconds();
    }
}
