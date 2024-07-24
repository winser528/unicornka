package com.fit.service;

import com.fit.base.BaseCrudService;
import com.fit.dao.GoodsDao;
import com.fit.entity.Goods;
import org.springframework.stereotype.Service;

/**
 * @AUTO 商品表服务实现类
 * @Author AIM
 * @DATE 2024/07/18
 */
@Service
public class GoodsService extends BaseCrudService<GoodsDao, Goods> {
}