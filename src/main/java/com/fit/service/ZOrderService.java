package com.fit.service;

import com.fit.config.SnowFlake;
import com.fit.entity.Coupons;
import com.fit.entity.Goods;
import com.fit.entity.Orders;
import com.fit.entity.Pays;
import com.fit.util.DateUtils;
import com.fit.util.FastJsonUtil;
import com.pay.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.beans.Transient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @AUTO
 * @Author AIM
 * @DATE 2024/7/25
 */
@Slf4j
@Service
public class ZOrderService {
    @Autowired
    private CouponsService couponService;
    @Autowired
    private GoodsService goodService;
    @Autowired
    private OrdersService orderService;
    @Autowired
    private PaysService payService;
    @Autowired
    private SnowFlake snowFlake;

    @Transient
    public void bill(Orders bean, String inCoupons) throws Exception {
        try {
            Goods good = this.goodService.get(bean.getGoodsId());// 商品信息
            int inStock = good.getInStock() - bean.getBuyAmount().intValue();
            if (inStock > 0) {
                bean.setCreateTime(DateUtils.nowDate());
                bean.setOrderSn(snowFlake.nextSn());
                bean.setTitle(good.getGdName());
                bean.setGoodsPrice(good.getActualPrice());
                bean.setTotalPrice(good.getActualPrice().multiply(bean.getBuyAmount()));// 单价*数量
                bean.setStatus(1);// 待支付
                good.setInStock(inStock);
                BigDecimal discount = BigDecimal.ZERO;
                if (StringUtils.hasText(inCoupons)) {
                    Coupons coupons = new Coupons(); // 优惠信息
                    coupons.setCoupon(inCoupons);
                    coupons = this.couponService.get(coupons);
                    if (coupons != null) {
                        bean.setCouponId(coupons.getId());
                        discount = coupons.getDiscount();
                    }
                }
                bean.setCouponDiscountPrice(discount);
                bean.setActualPrice(bean.getTotalPrice().subtract(discount));// 总价-优惠价
                this.goodService.update(good);
                //保存订单
                this.orderService.save(bean);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("订单保存失败", e);
        }
    }

    public Map<String, Object> getPays() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Pays> pays1 = this.payService.findList(map);
        for (Pays pay : pays1) {
            map.put(pay.getId().toString(), pay.getPayName());
        }
        return map;
    }

    public List<Map<String, Object>> getListPays() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<Pays> pays1 = this.payService.findList(new Pays());
        for (Pays pay : pays1) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", pay.getId());
            map.put("payName", pay.getPayName());
            map.put("payCheck", pay.getPayCheck());
            list.add(map);
        }
        return list;
    }

    public Pays getPayById(Long id) {
        return this.payService.get(id);
    }
}
