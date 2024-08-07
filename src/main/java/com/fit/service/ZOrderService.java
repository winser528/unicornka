package com.fit.service;

import com.alibaba.fastjson.JSONArray;
import com.fit.config.SnowFlake;
import com.fit.entity.*;
import com.fit.util.DateUtils;
import com.fit.util.FastJsonUtil;
import com.pay.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
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
    private CarmisService carmisService;
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
    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 订单成功方法
     *
     * @param orderSn     订单号
     * @param actualPrice 实际支付金额
     * @param tradeNo     第三方订单号
     */
    @Transient
    public void doneOrder(String orderSn, BigDecimal actualPrice, String tradeNo) throws Exception {
        Orders order = new Orders();
        order.setOrderSn(orderSn);
        order = this.orderService.get(order);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        if (order.getStatus() == 4) {
            throw new RuntimeException("订单已经处理");
        }
        if (order.getActualPrice().compareTo(actualPrice) != 0) {
            throw new RuntimeException("订单金额不一致");
        }
        order.setTradeNo(tradeNo);
        if (order.getType()) { // 自动发货
            // 获得卡密
            List<Carmis> carmis = getCarmis(order.getGoodsId(), order.getBuyAmount());
            if (carmis.size() != order.getBuyAmount().intValue()) {
                order.setInfo("库存可使用卡密不足，请联系管理员核查");
                order.setStatus(6);
            } else {
                StringBuffer infos = new StringBuffer();
                List<Long> ids = new ArrayList<>();
                for (Carmis carmi : carmis) {
                    ids.add(carmi.getId());
                    infos.append(carmi.getCarmi()).append("\n");
                }
                order.setInfo(infos.toString());
                order.setStatus(4);
                this.updateCarmis(ids);
            }
        } else {
            order.setStatus(2);
            Goods goods = this.goodService.get(order.getGoodsId());
            Integer inStock = goods.getInStock();
            int buyAmount = order.getBuyAmount().intValue();
            if (inStock > buyAmount) {
                goods.setInStock(inStock - buyAmount);
                this.goodService.update(goods);
            } else {
                throw new RuntimeException("商品数量不足");
            }
        }
        this.orderService.update(order);
    }

    /**
     * 更新卡密状态为已使用
     */
    public void updateCarmis(List<Long> ids) {
        String sql = "UPDATE `carmis` SET `status`=1 WHERE id in (%s)";
        jdbcTemplate.batchUpdate(String.format(sql, ids.toString().replace("\\[|]", "")));
    }

    /**
     * 获取卡密列表
     *
     * @param goodsId   商品ID
     * @param buyAmount 购买数量
     * @return
     */
    public List<Carmis> getCarmis(Long goodsId, BigDecimal buyAmount) {
        Map<String, Object> param = new HashMap<>();
        param.put("goodsId", goodsId);
        param.put("status", false);
        param.put("offset", 0);
        param.put("limit", buyAmount);
        return this.carmisService.findList(param);
    }


    /**
     * 创建订单
     *
     * @param bean      订单信息
     * @param inCoupons 优惠码
     * @throws Exception
     */
    @Transient
    public void creatOrder(Orders bean, String inCoupons) throws Exception {
        try {
            Goods good = this.goodService.get(bean.getGoodsId());// 商品信息
            int buyAmount = bean.getBuyAmount().intValue(); //购买数量
            Integer buyLimitNum = good.getBuyLimitNum(); // 限购数
            if (buyLimitNum != 0 && buyLimitNum < buyAmount) {
                throw new RuntimeException("订单购买数量不符合限购规则");
            }
            // 商品库存满足购买数
            if ((good.getInStock() - buyAmount) > 0) {
                bean.setCreateTime(DateUtils.nowDate());
                bean.setOrderSn(snowFlake.nextSn());
                bean.setTitle(good.getGdName());
                bean.setGoodsPrice(good.getActualPrice());
                bean.setTotalPrice(good.getActualPrice().multiply(bean.getBuyAmount()));// 单价*数量
                bean.setStatus(1);// 待支付
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

    public List<Orders> getCookieOrder(JSONArray array) {
        String sql = "SELECT * from orders where id IN (?)";
        return this.jdbcTemplate.queryForList(sql, Orders.class, array.toString().replace("\\[|]", ""));
    }
}
