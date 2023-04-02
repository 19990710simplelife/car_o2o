package com.atguigu.auth.controller;


import com.atguigu.auth.service.SysUserService;
import com.atguigu.common.Result;
import com.atguigu.model.system.SysUser;
import com.atguigu.vo.system.SysUserQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 用户表 前端控制器
 *
 * @author simplelife
 * @since 2023-03-30
 */
@Api(tags = "后台用户管理")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @ApiOperation("用户条件分页查询")
    @GetMapping("{page}/{limit}")
    public Result index(@PathVariable Long page, @PathVariable Long limit, SysUserQueryVo sysUserQueryVo) {
        // 1、创建一个page对象
        Page<SysUser> pageParam = new Page<>(page, limit);
        // 2、封装条件
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        // 获取条件值 用户名 开始时间 结束时间
        String username = sysUserQueryVo.getKeyword();
        String createTimeBegin = sysUserQueryVo.getCreateTimeBegin();
        String createTimeEnd = sysUserQueryVo.getCreateTimeEnd();
        // 判断条件是否为空，封装条件
        if (!StringUtils.isEmpty(username)) {
            wrapper.like(SysUser::getUsername, username);
        }
        if (!StringUtils.isEmpty(createTimeBegin)) {
            wrapper.ge(SysUser::getCreateTime, username);
        }
        if (!StringUtils.isEmpty(createTimeEnd)) {
            wrapper.le(SysUser::getCreateTime, createTimeEnd);
        }
        IPage<SysUser> pageModel = sysUserService.page(pageParam, wrapper);
        return Result.ok(pageModel);
    }

    @ApiOperation("根据id获取用户")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        SysUser user = sysUserService.getById(id);
        return Result.ok(user);
    }

    @ApiOperation("保存用户信息")
    @PostMapping("save")
    public Result save(@RequestBody SysUser user) {
        sysUserService.save(user);
        return Result.ok();
    }

    @ApiOperation("更新用户信息")
    @PutMapping("update")
    public Result updateById(@RequestBody SysUser user) {
        sysUserService.updateById(user);
        return Result.ok();
    }

    @ApiOperation("根据id删除用户")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        sysUserService.removeById(id);
        return Result.ok();
    }
}

