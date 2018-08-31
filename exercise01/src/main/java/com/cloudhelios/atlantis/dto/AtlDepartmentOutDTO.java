package com.cloudhelios.atlantis.dto;

import javax.persistence.Column;
import java.util.List;

/**
 * author: chenwei
 * createDate: 18-8-27 下午3:29
 * description:
 */

public class AtlDepartmentOutDTO {
    private Long id;

    private String name;

    private String code;

    private Long parentId;

    private Integer num;//人数

    private List<AtlDepartmentOutDTO> subDeparts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public List<AtlDepartmentOutDTO> getSubDeparts() {
        return subDeparts;
    }

    public void setSubDeparts(List<AtlDepartmentOutDTO> subDeparts) {
        this.subDeparts = subDeparts;
    }
}
