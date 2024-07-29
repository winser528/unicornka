package com.fit.service;

import com.fit.base.BaseCrudService;
import com.fit.dao.SysJobDao;
import com.fit.entity.SysJob;
import org.springframework.stereotype.Service;

/**
 * @AUTO 定时任务服务实现类
 * @Author AIM
 * @DATE 2024/07/26
 */
@Service
public class SysJobService extends BaseCrudService<SysJobDao, SysJob> {

    public int count(){
        return 0;
    }
}