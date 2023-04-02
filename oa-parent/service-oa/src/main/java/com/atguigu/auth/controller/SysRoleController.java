package com.atguigu.auth.controller;

import com.atguigu.auth.service.SysRoleService;
import com.atguigu.common.Result;
import com.atguigu.model.system.SysRole;
import com.atguigu.vo.system.SysRoleQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName SysRoleController
 * @Description
 * @Author simplelife
 * @Date 2023/3/29 23:01
 * @Version 1.0
 */

@Api(tags = "角色管理接口")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;


    @ApiOperation("添加角色")
    @PostMapping("save")
    public Result save(@RequestBody @Validated SysRole sysRole) {
        boolean is_success = sysRoleService.save(sysRole);
        if (is_success) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @ApiOperation("根据id删除角色信息")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        boolean is_success = sysRoleService.removeById(id);
        if (is_success) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @ApiOperation("批量删除角色")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        boolean is_success = sysRoleService.removeByIds(idList);
        if (is_success) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @ApiOperation("修改角色信息")
    @PutMapping("update")
    public Result updateById(@RequestBody SysRole sysRole) {
        boolean is_success = sysRoleService.updateById(sysRole);
        if (is_success) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    /**
     * @return json
     */
    @ApiOperation("查询所有角色")
    @GetMapping("/findAll")
    public Result<List<SysRole>> findAll() {
        List<SysRole> roleList = sysRoleService.list();
        return Result.ok(roleList);
    }

    /**
     * @param page  当前页码
     * @param limit 每页显示条数
     * @return json
     */
    @ApiOperation("条件分页查询")
    @GetMapping("{page}/{limit}")
    public Result PageQueryRole(@PathVariable Long page, @PathVariable Long limit, SysRoleQueryVo sysRoleQueryVo) {
        // 调用service方法进行实现
        Page<SysRole> pageParam = new Page<>(page, limit);
        // 封装条件
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        String roleName = sysRoleQueryVo.getRoleName();
        if (!StringUtils.isEmpty(roleName)) {
            // 封装like模糊查询
            wrapper.like(SysRole::getRoleName, roleName);
        }
        IPage<SysRole> pageModel = sysRoleService.page(pageParam, wrapper);
        return Result.ok(pageModel);
    }

    @ApiOperation("根据id获取角色信息")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        SysRole role = sysRoleService.getById(id);
        return Result.ok(role);
    }

}
