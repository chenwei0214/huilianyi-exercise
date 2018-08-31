package com.cloudhelios.atlantis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * author: chenwei
 * createDate: 18-8-27 上午11:00
 * description:
 */

@Table(name="atl_department")
public class AtlDepartment {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @Column(name="department_name")
    private String departmentName;

    @Column(name="department_code")
    private String departmentCode;

    private Long parentId;

    @JsonIgnore
    private String path;

    @Transient
    private Integer num;

    @Transient
    private List<AtlDepartment> subDeparts=new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public List<AtlDepartment> getSubDeparts() {
        return subDeparts;
    }

    public void setSubDeparts(List<AtlDepartment> subDeparts) {
        this.subDeparts = subDeparts;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
