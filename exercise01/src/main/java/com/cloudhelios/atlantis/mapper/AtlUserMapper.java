package com.cloudhelios.atlantis.mapper;

import com.cloudhelios.atlantis.domain.AtlUser;
import com.cloudhelios.atlantis.dto.AtlUserInDTO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * author: chenwei
 * createDate: 18-8-27 上午11:06
 * description:
 */
public interface AtlUserMapper extends Mapper<AtlUser>{

    List<AtlUser> query(AtlUserInDTO atlUserInDTO);
}
