package com.fit.service;

import com.fit.base.BaseCrudService;
import com.fit.dao.SysUserDao;
import com.fit.entity.SysUser;
import org.springframework.stereotype.Service;

/**
 * @AUTO 用户表服务实现类
 * @Author AIM
 * @DATE 2020-08-18 11:51:22
 */
@Service
public class SysUserService extends BaseCrudService<SysUserDao, SysUser> {
}