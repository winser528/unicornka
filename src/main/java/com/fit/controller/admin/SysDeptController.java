package com.fit.controller.admin;

import com.fit.base.BaseController;
import com.fit.base.R;
import com.fit.entity.SysDept;
import com.fit.service.SysDeptService;
import com.fit.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author AIM
 * @Des 资源控制器
 * @DATE 2020/8/13
 */
@Controller
@RequestMapping("/admin/dept")
public class SysDeptController extends BaseController {

    @Autowired
    private SysDeptService service;

    @GetMapping("/list")
    public String resList() {
        return "admin/sys/dept/list";
    }

    /**
     * 资源列表
     */
    @PostMapping("/list")
    @ResponseBody
    public R resList(HttpServletRequest request) {
        Map<String, Object> map = getRequestParamsMap(request);
        List<SysDept> userList = service.findList(map);
        return R.tables(userList.size(), userList);
    }

    @GetMapping("/edit")
    public String edit(Long id, Model model) {
        if (isNotEmpty(id)) {
            SysDept bean = service.get(id);
            model.addAttribute("dept", bean);
        }
        return "admin/sys/dept/edit";
    }

    @PostMapping("/save")
    @ResponseBody
    public R save(HttpServletRequest request) {
        Map<String, Object> map = getRequestParamsMap(request);
        try {
            SysDept bean = BeanUtils.map2Bean(SysDept.class, map);
            if (isNotEmpty(bean.getId())) {
                service.update(bean);
            } else {
                service.save(bean);
            }
            return R.success();
        } catch (Exception e) {
            return R.error();
        }
    }

    /**
     * 修改状态
     */
    @RequestMapping("/setState")
    @ResponseBody
    public R changeState(@RequestParam Long id) {
        SysDept bean = this.service.get(id);
        if (bean != null) {
            if (bean.getEnabled()) {
                bean.setEnabled(false);
            } else {
                bean.setEnabled(true);
            }
            this.service.update(bean);
            return R.success();
        }
        return R.error("修改状态失败");
    }
}
