package com.cloudhelios.atlantis.mapper;

import com.cloudhelios.atlantis.domain.AtlDepartment;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * author: chenwei
 * createDate: 18-8-27 上午11:07
 * description:
 */
public interface AtlDepartmentMapper extends Mapper<AtlDepartment> {

    Integer getNumById(String path);

    List<AtlDepartment> getAllChild(String path);
}
