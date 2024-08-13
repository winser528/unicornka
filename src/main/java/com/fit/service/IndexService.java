package com.fit.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @AUTO 首页内容服务
 * @Author AIM
 * @DATE 2024/7/19
 */
@Service
public class IndexService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getGroups() {
        StringBuffer sb = new StringBuffer("SELECT g.`id`,g.`gp_name` as gpName,g.`ord`,COALESCE(COUNT(s.`id`), 0) AS size ");
        sb.append("FROM `goods_group` g LEFT JOIN `goods` s ON s.`group_id`=g.`id` WHERE g.`is_open`=1 GROUP BY g.`id`,g.`ord`");
        return jdbcTemplate.queryForList(sb.toString());
    }
}
