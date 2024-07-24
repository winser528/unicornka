package com.fit.dao;

import com.fit.base.BaseCrudDao;
import com.fit.entity.Goods;
import org.apache.ibatis.annotations.Mapper;

/**
 * @AUTO
 * @Author AIM
 * @DATE 2024/07/18
 */
@Mapper
public interface GoodsDao extends BaseCrudDao<Goods> {
}