package com.sheng.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sheng.commonutils.R;
import com.sheng.eduservice.entity.EduTeacher;
import com.sheng.eduservice.entity.vo.TeacherQuery;
import com.sheng.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 * @author cms
 * @since 2020-11-30
 */
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/eduservice/edu-teacher")
@CrossOrigin
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;

    // 1. 查询将讲师表中的所有数据
    @ApiOperation("所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items", list);
    }

    /**
     * 对查询出的讲师进行分页
     * @param current 当前页
     * @param limit 每页的记录数
     * @return
     */
    @ApiOperation("教师分页查询显示")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageList(@ApiParam(name = "current", value = "当前页码", required = true) @PathVariable long current,
                      @ApiParam(name = "limit", value = "每页的记录数", required = true) @PathVariable long limit) {
        Page<EduTeacher> teacherPage = new Page<>(current, limit);
        eduTeacherService.page(teacherPage, null);
        long total = teacherPage.getTotal();
        List<EduTeacher> records = teacherPage.getRecords();
        return R.ok().data("total", total).data("items", records);
    }

    /**
     * 根据条件查询讲师
     * @param current
     * @return
     */
    @ApiOperation("分页讲师查询列表")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@ApiParam(name = "current", value = "当前页码", required = true)@PathVariable long current,
                                  @ApiParam(name = "limit", value = "每页记录数", required = true)@PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {

        // 因为是将所有的参数封装到对象中，所以需要传递进来一个对象呀

        Page<EduTeacher> teacherPage = new Page<>(current, limit);

        // 构建条件
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        // mybatis学过的动态sql
        // 判断条件值是不是空
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if(!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }
        if(!StringUtils.isEmpty(level)) {
            queryWrapper.eq("level", level);
        }
        if(!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }
        if(!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_modified", end);
        }

        // 按照创建时间倒序
        queryWrapper.orderByDesc("gmt_create");
        eduTeacherService.page(teacherPage, queryWrapper);
        long total = teacherPage.getTotal();
        List<EduTeacher> records = teacherPage.getRecords();
        return  R.ok().data("total", total).data("rows", records);
    }

    /**
     * 对导师进行逻辑删除
     * @param id
     * @return
     */
    @ApiOperation("逻辑删除")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "讲师id", required = true)
            @PathVariable String id) {
        boolean flag = eduTeacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 新增老师
     * @param teacher
     * @return
     */
    @ApiOperation("新增老师")
    @PostMapping("addTeacher")
    public R save(@ApiParam(name = "teacher", value = "需要保存的老师", required = true)
                  @RequestBody EduTeacher teacher) {
        boolean save = eduTeacherService.save(teacher);
        if(save) {
          return R.ok();
        }else {
            return R.error();
        }
    }

    /**
     * 对讲师进行修改
     *
     * 进行手动添加id
     * @ApiParam(name = "id", value = "根据id进行修改", required = true)
     *                     @PathVariable String id,
     *
     *
     * @param teacher
     * @return
     */
    @ApiOperation("讲师的修改功能")
    @PostMapping("updateTeacher")
    public R update( @ApiParam(name = "teacher", value = "讲师对象", required = false)
                    @RequestBody EduTeacher teacher) {
        boolean b = eduTeacherService.updateById(teacher);
        return b == true? R.ok():R.error();
    }

    /**
     * 根据id查询讲师
     * @param id
     * @return
     */
    @ApiOperation("根据id进行查询")
    @GetMapping("searchTeacherById/{id}")
    public R searchTacherById(
            @ApiParam(name = "id", value = "根据id查老师", required = true)
            @PathVariable String id) {
        EduTeacher teacherById = eduTeacherService.getById(id);
        if(teacherById != null) {
            return R.ok().data("item", teacherById);
        }else {
            return R.error().message("没有查询到这条记录");
        }
    }
}

