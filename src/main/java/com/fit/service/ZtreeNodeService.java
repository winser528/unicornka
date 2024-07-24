package com.fit.service;

import com.fit.entity.ZTreeNode;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @AUTO 获取ztree节点服务
 * @Author AIM
 * @DATE 2019/4/28
 */
@Service
public class ZtreeNodeService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 获取部门树节点集合
     */
    public List<ZTreeNode> deptZtree() {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT `id`, `pid` AS parentId, `FULL_NAME` AS title,`LEVEL`,");
        sb.append("(CASE WHEN (`pid` = 0 OR `pid` IS NULL) THEN 'true' ELSE 'false' END ) AS OPEN FROM sys_dept");
        return jdbcTemplate.query(sb.toString(), BeanPropertyRowMapper.newInstance(ZTreeNode.class));
    }

    /**
     * 获取角色树节点集合
     */
    public List<ZTreeNode> roleZtree() {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT `id`, `pid` AS parentId, `NAME` AS title,'1' AS `LEVEL`,");
        sb.append("(CASE WHEN (`pid` = 0 OR `pid` IS NULL) THEN 'true' ELSE 'false' END ) AS OPEN FROM sys_role");
        return jdbcTemplate.query(sb.toString(), BeanPropertyRowMapper.newInstance(ZTreeNode.class));
    }

    /**
     * 获取栏目树节点集合
     */
    public List<ZTreeNode> menuZtree() {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT `id`, `pid` AS parentId, `NAME` AS title, `LEVEL`,");
        sb.append("(CASE WHEN (`pid` = 0 OR `pid` IS NULL) THEN 'true' ELSE 'false' END ) AS OPEN FROM `sys_menu`");
        return jdbcTemplate.query(sb.toString(), BeanPropertyRowMapper.newInstance(ZTreeNode.class));
    }
}
