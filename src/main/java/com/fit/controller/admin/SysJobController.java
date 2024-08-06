package com.fit.controller.admin;

import com.fit.base.BaseController;
import com.fit.base.R;
import com.fit.config.quartz.QuartzHandler;
import com.fit.config.quartz.StatusEnum;
import com.fit.config.security.utils.SecurityHelper;
import com.fit.entity.SysJob;
import com.fit.service.SysJobService;
import com.fit.util.BeanUtils;
import com.fit.util.DateUtils;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @AUTO 任务管理器
 * @Author AIM
 * @DATE 2024/7/26
 */
@Controller
@RequestMapping("/admin/job")
public class SysJobController extends BaseController {

    @Autowired
    private SysJobService service;
    @Autowired
    private QuartzHandler quartz;

    @GetMapping("/list")
    public String userList() {
        return "admin/sys/job/list";
    }

    /**
     * 用户列表
     */
    @PostMapping("/list")
    @ResponseBody
    public R userList(HttpServletRequest request) {
        Map<String, Object> map = getRequestParamsMap(request);
        Object keywords = map.get("keywords");
        if (isNotEmpty(keywords)) {
            map.remove("keywords");
            if (keywords.toString().contains("@")) {
                map.put("email", keywords);
            } else {
                map.put("name", keywords);
            }
        }
        List<SysJob> list = service.findList(map);
        return R.tables(list.size(), list);
    }

    @GetMapping("/edit")
    public String edit(Long id, Model model) {
        if (isNotEmpty(id)) {
            SysJob bean = service.get(id);
            model.addAttribute("job", bean);
        }
        return "admin/sys/job/edit";
    }

    @PostMapping("/save")
    @ResponseBody
    public R save(HttpServletRequest request) {
        Map<String, Object> map = getRequestParamsMap(request);
        try {
            SysJob bean = BeanUtils.map2Bean(SysJob.class, map);
            if (isNotEmpty(bean.getId())) {
                bean.setUpdateUser(SecurityHelper.getUserId());
                bean.setUpdateTime(DateUtils.nowDate());
                service.update(bean);
                quartz.updateCronExpression(bean);
            } else {
                bean.setStatus(StatusEnum.PAUSED.getState());
                bean.setCreateUser(SecurityHelper.getUserId());
                bean.setCreateTime(DateUtils.nowDate());
                service.save(bean);
            }
            return R.success();
        } catch (Exception e) {
            return R.error();
        }
    }

    @PostMapping("/del")
    @ResponseBody
    public Object userDel(HttpServletRequest request) {
        Map<String, Object> map = getRequestParamsMap(request);
        try {
            String[] ids = map.get("ids").toString().trim().split(",");
            for (String id : ids) {
                SysJob job = this.service.get(Long.valueOf(id));
                service.delete(job.getId());
                quartz.delete(job);
            }
            return R.success("删除成功");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return R.success("删除失败");
    }

    /**
     * 修改定时状态
     *
     * @param id     任务ID
     * @param status 任务状态
     * @return
     */
    @PostMapping("/setState")
    @ResponseBody
    public Object setState(@RequestParam Long id, String status) {
        SysJob bean = this.service.get(id);
        bean.setStatus(status);
        int c = service.update(bean);
        if (c > 0) {
            quartz.runStatus(status, bean);
            return R.success("操作成功");
        } else {
            return R.error("操作失败");
        }
    }

    @PostMapping("/trigger")
    @ResponseBody
    public Object trigger(HttpServletRequest request) {
        Map<String, Object> map = getRequestParamsMap(request);
        SysJob bean = BeanUtils.map2Bean(SysJob.class, map);
        if (isNotEmpty(bean.getId())) {
            bean = this.service.get(bean.getId());
            quartz.trigger(bean);
            return R.success("执行成功");
        }
        return R.error("执行失败");
    }
}
