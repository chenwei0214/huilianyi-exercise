package com.cloudhelios.atlantis.dto;

import com.cloudhelios.atlantis.domain.AtlUser;

/**
 * author: chenwei
 * createDate: 18-8-27 下午1:50
 * description:
 */
public class AtlUserInDTO extends AtlUser{
    private Integer page=1;
    private Integer pageSize=20;
    private String departmentCode;

    private String keyword;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
