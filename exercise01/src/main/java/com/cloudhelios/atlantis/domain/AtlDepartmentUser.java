package com.cloudhelios.atlantis.domain;

import javax.persistence.Table;

/**
 * author: chenwei
 * createDate: 18-8-27 上午11:04
 * description:
 */
@Table(name = "atl_department_user")
public class AtlDepartmentUser {


    private Long departmentId;
    private Long userId;

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
