package com.cloudhelios.atlantis.service;

import com.cloudhelios.atlantis.domain.AtlUser;
import com.cloudhelios.atlantis.dto.AtlUserInDTO;
import com.cloudhelios.atlantis.util.Page;
import org.apache.lucene.search.Query;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

public interface AtlUserService extends UserDetailsService{

    void checkUserUpdate(AtlUser atlUser);

    void checkUserCreate(AtlUser atlUser);

    void checkAtlUserExists(Long userId);

    void checkAtlUserInfo(AtlUser atlUser);

    void checkExistsByEmployeeId(String employeeId);

    void checkExistsByEmail(String email);

    void checkExistsByPhone(String phone);

    void create(Long departmentId, AtlUser atlUser);

    void del(Long id);

    void update(Long id, AtlUser atlUser);

    Page<AtlUser> search(AtlUserInDTO atlUserInDTO);

    Page<AtlUser> searchByLucene(AtlUserInDTO user);

    void createIndex() throws IOException;

    AtlUser getAtlUserByEmployeeId(String employeeId);
}
