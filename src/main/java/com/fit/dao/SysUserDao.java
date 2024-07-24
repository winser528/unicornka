package com.fit.dao;

import com.fit.base.BaseCrudDao;
import com.fit.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @AUTO
 * @Author AIM
 * @DATE 2024/07/17
 */
@Mapper
public interface SysUserDao extends BaseCrudDao<SysUser> {
}