package com.fit.service;

import com.fit.entity.ZMenuNode;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @AUTO 菜单节点服务
 * @Author AIM
 * @DATE 2019/4/25
 */
@Service
public class ZMenuNodeService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 获取角色ID
     *
     * @param username 用户名
     */
    public List<Long> getRoles(String username) {
        return jdbcTemplate.queryForList("select u.`role_id` from sys_user u where u.username=?", Long.class, username);
    }

    /**
     * 获取栏目列表
     */
    public List<ZMenuNode> getUserMenuNodes(List<Long> roleIds) {
        if (roleIds == null || roleIds.size() == 0) {
            return new ArrayList<ZMenuNode>();
        } else {
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT r.`id`,r.`pid` AS `parentId`,r.`icon`,r.`name`,r.`url`,");
            sb.append("r.`MOLD`,r.`sort` AS `sortOrder`,r.`LEVEL` FROM`sys_menu` r");
            sb.append(" INNER JOIN `sys_role_menu` m ON r.`ID` = m.`MENU_ID`");
            sb.append(" WHERE m.`role_id` IN ( ");
            for (int i = 0; i < roleIds.size(); i++) {
                sb.append("?,");
            }
            if (sb.toString().endsWith(",")) {
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append(" ) AND r.`MOLD` = 1 AND r.`enabled` = 1 AND r.`pid` != 0 ORDER BY r.`level`,r.`sort` ASC");
            List<ZMenuNode> menus = jdbcTemplate.query(sb.toString(), BeanPropertyRowMapper.newInstance(ZMenuNode.class), roleIds.toArray(new Object[roleIds.size()]));
            return ZMenuNode.buildTitle(menus, 1);
        }
    }

    /**
     * 根据配置文件设置过滤接口文档信息
     *
     * @param nodes    查询到的数据
     * @param menuName 指定的栏目名称
     */
    private static List<ZMenuNode> filterMenuByName(List<ZMenuNode> nodes, String menuName) {
        List<ZMenuNode> menuNodesCopy = new ArrayList<>(nodes.size());
        for (ZMenuNode menuNode : nodes) {
            if (!menuName.equals(menuNode.getName())) {
                menuNodesCopy.add(menuNode);
            }
            List<ZMenuNode> childrenList = menuNode.getChildren();
            if (childrenList != null && childrenList.size() > 0) {
                menuNode.setChildren(filterMenuByName(childrenList, menuName));
            }
        }
        return menuNodesCopy;
    }
}
