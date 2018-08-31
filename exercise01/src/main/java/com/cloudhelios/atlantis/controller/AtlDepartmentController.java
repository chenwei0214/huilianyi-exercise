package com.cloudhelios.atlantis.controller;

import com.cloudhelios.atlantis.domain.AtlDepartment;
import com.cloudhelios.atlantis.service.AtlDepartmentService;
import com.cloudhelios.atlantis.util.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author: chenwei
 * createDate: 18-8-27 上午11:11
 * description:
 */
@RestController
@RequestMapping("/depart")
public class AtlDepartmentController {

    @Autowired
    private AtlDepartmentService atlDepartmentService;

    @GetMapping("/tree")
    public DataResult getTree(@RequestParam(required = false) Long id) {
        List<AtlDepartment> departTree = atlDepartmentService.getDepartTree(id == null ? 1l : id);
        return DataResult.ok(departTree);
    }

    @PutMapping("/add")
    public DataResult create(Long parentId,@RequestBody AtlDepartment atlDepartment){
        atlDepartmentService.create(parentId,atlDepartment);
        return DataResult.ok(atlDepartment);
    }

    @DeleteMapping("/del/{id}")
    public DataResult del(@PathVariable Long id){
        atlDepartmentService.del(id);
        return DataResult.ok();
    }

    @PostMapping("/update/{id}")
    public DataResult update(@PathVariable Long id,@RequestBody AtlDepartment atlDepartment){
        atlDepartmentService.update(id,atlDepartment);
        return DataResult.ok();
    }

    @GetMapping("/num/{id}")
    public DataResult getNumById(@PathVariable Long id){
        return DataResult.ok( atlDepartmentService.getNumById(id));
    }
}
