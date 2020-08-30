package com.sign.edu_service.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sign.commonutils.R;
import com.sign.edu_service.entity.EduTeacher;
import com.sign.edu_service.entity.vo.TeacherQuery;
import com.sign.edu_service.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author Sign
 * @since 2020-08-03
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService TeacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R list() {
        List<EduTeacher> list = TeacherService.list(null);
        return R.ok().data("items", list);
    }

    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {
        boolean flag = TeacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @ApiOperation(value = "分页讲师列表")
    @GetMapping("a/{page}/{limit}")
    public R pageQuery(
            @ApiParam(name = "page", value = "当前页码")
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数")
            @PathVariable Long limit,

            @ApiParam(name = "teacherQuery", value = "查询对象")
                    TeacherQuery teacherQuery) {

        Page<EduTeacher> pageParam = new Page<>(page, limit);

        TeacherService.pageQuery(pageParam, teacherQuery);
        List<EduTeacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();

        HashMap<Object, Object> dataMap = new HashMap<>();
        dataMap.put("total", total);
        dataMap.put("rows", records);

        return R.ok().data("data", dataMap);
    }

    //添加讲师接口
    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = TeacherService.save(eduTeacher);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //根据讲师ID查询
    @ApiOperation(value = "根据讲师ID查询")
    @GetMapping("getTeacher/{id}")
    public R getTeacherById(@PathVariable String id) {
        EduTeacher eduTeacher = TeacherService.getById(id);
        return R.ok().data("teacher", eduTeacher);
    }

    //讲师修改功能
    @ApiOperation(value = "讲师修改功能")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = TeacherService.updateById(eduTeacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }


}

