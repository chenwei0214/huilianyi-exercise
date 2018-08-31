package com.cloudhelios.atlantis.service;

import com.cloudhelios.atlantis.domain.AtlDepartment;

import java.util.List;

public interface AtlDepartmentService {


    void checkDepartExists(Long departmentId);

    /**
     * 创建时指定父部门
     * @param parentId
     * @param atlDepartment
     */
    void create(Long parentId, AtlDepartment atlDepartment);

    /**
     * 删除该部门的同时删除子部门
     * @param id
     */
    void del(Long id);

    /**
     * 根据id更新
     * @param id
     * @param atlDepartment
     */
    void update(Long id,AtlDepartment atlDepartment);

    /**
     * 获取部门树
     * @param id
     * @return
     */
    List<AtlDepartment> getDepartTree(Long id);

    /**
     * 统计人数
     * @param id
     * @return
     */
    Integer getNumById(Long id);


}
