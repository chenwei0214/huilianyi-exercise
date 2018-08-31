package com.cloudhelios.atlantis.service.impl;

import com.cloudhelios.atlantis.domain.AtlDepartmentUser;
import com.cloudhelios.atlantis.domain.AtlUser;
import com.cloudhelios.atlantis.dto.AtlUserInDTO;
import com.cloudhelios.atlantis.exception.CustomException;
import com.cloudhelios.atlantis.mapper.AtlDepartmentUserMapper;
import com.cloudhelios.atlantis.mapper.AtlUserMapper;
import com.cloudhelios.atlantis.security.entity.CustomUserDetails;
import com.cloudhelios.atlantis.service.AtlDepartmentService;
import com.cloudhelios.atlantis.service.AtlUserService;
import com.cloudhelios.atlantis.util.CheckUtil;
import com.cloudhelios.atlantis.util.Page;
import com.cloudhelios.atlantis.util.RedisUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

/**
 * author: chenwei
 * createDate: 18-8-27 上午11:22
 * description:
 */
@Service
public class AtlUserServiceImpl implements AtlUserService {

    @Value("${lucene.path}")
    private String path;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private AtlUserMapper atlUserMapper;

    @Autowired
    private AtlDepartmentUserMapper atlDepartmentUserMapper;

    @Autowired
    private AtlDepartmentService departmentService;

    @Autowired
    private LuceneService luceneService;

    @Override
    public void checkUserUpdate(AtlUser atlUser) {
        checkAtlUserInfo(atlUser);
        checkAtlUserExists(atlUser.getId());
        if(!StringUtils.isEmpty(atlUser.getPhone())){
            checkExistsByPhone(atlUser.getPhone());
        }
        if(!StringUtils.isEmpty(atlUser.getEmail())){
            checkExistsByEmail(atlUser.getEmail());
        }
    }

    @Override
    public void checkUserCreate(AtlUser atlUser) {
        if(!StringUtils.isEmpty(atlUser.getEmployeeId())){
            checkExistsByEmployeeId(atlUser.getEmployeeId());
        }
        if(!StringUtils.isEmpty(atlUser.getPhone())){
            checkExistsByPhone(atlUser.getPhone());
        }
        if(!StringUtils.isEmpty(atlUser.getEmail())){
            checkExistsByEmail(atlUser.getEmail());
        }
    }

    @Override
    public void checkAtlUserExists(Long userId) {
        if (userId != null) {
            if (atlUserMapper.selectByPrimaryKey(userId) == null) {
                throw new CustomException("10010", "ID为" + userId + "的用户不存在");
            }
        }
    }

    @Override
    public void checkAtlUserInfo(AtlUser atlUser) {
        if (atlUser.getEmail() != null) {
            if (!CheckUtil.checkEmail(atlUser.getEmail())) {
                throw new CustomException("10001", "邮箱格式不正确");
            }
        }
        if (atlUser.getPhone() != null) {
            if (!CheckUtil.checkMobileNumber(atlUser.getPhone())) {
                throw new CustomException("10002", "手机号码格式不正确");
            }
        }
    }

    @Override
    public void checkExistsByEmployeeId(String employeeId) {
        AtlUser atlUser = new AtlUser();
        atlUser.setEmployeeId(employeeId);
        if (atlUserMapper.selectOne(atlUser) != null) {
            throw new CustomException("10013", "用户已存在");
        }
    }

    @Override
    public void checkExistsByEmail(String email) {
        AtlUser atlUser = new AtlUser();
        atlUser.setEmail(email);
        if (atlUserMapper.selectOne(atlUser) != null) {
            throw new CustomException("10013", "用户已存在");
        }
    }

    @Override
    public void checkExistsByPhone(String phone) {
        AtlUser atlUser = new AtlUser();
        atlUser.setPhone(phone);
        if (atlUserMapper.selectOne(atlUser) != null) {
            throw new CustomException("10013", "用户已存在");
        }
    }

    @Transactional
    @Override
    public void create(Long departmentId, AtlUser atlUser) {
        //保存用户到redis
        if(!redisUtil.contains("USER:" + atlUser.getId())){
            atlUser.setPassword(null);
            redisUtil.set("USER:" + atlUser.getId(),atlUser);
        }
        checkAtlUserInfo(atlUser);
        departmentService.checkDepartExists(departmentId);
        checkUserCreate(atlUser);
        atlUserMapper.insertSelective(atlUser);
        AtlDepartmentUser atlDepartmentUser = new AtlDepartmentUser();
        atlDepartmentUser.setDepartmentId(departmentId);
        atlDepartmentUser.setUserId(atlUser.getId());
        atlDepartmentUserMapper.insert(atlDepartmentUser);
        //保存到用户到lucene
        luceneService.addDocument(luceneService.userToDocument(atlUser));
    }

    @Transactional
    @Override
    public void del(Long id) {
        //删除缓存
        if(!redisUtil.contains("USER:" + id)){
            redisUtil.remove("USER:" + id);
        }
        checkAtlUserExists(id);
        AtlDepartmentUser atlDepartmentUser = new AtlDepartmentUser();
        atlDepartmentUser.setUserId(id);
        atlDepartmentUserMapper.delete(atlDepartmentUser);
        atlUserMapper.deleteByPrimaryKey(id);
        //在索引库中删除
        luceneService.delDocument(new Term("id", "" + id));
    }

    @Transactional
    @Override
    public void update(Long id, AtlUser atlUser) {
        atlUser.setId(id);
        checkUserUpdate(atlUser);
        //删除缓存
        if(!redisUtil.contains("USER:" + id)){
            redisUtil.remove("USER:" + id);
        }
        atlUserMapper.updateByPrimaryKeySelective(atlUser);
        //更新lucene
        luceneService.updateDocument(new Term("id", "" + id),
                luceneService.userToDocument(atlUser));
    }

    @Override
    public Page<AtlUser> search(AtlUserInDTO atlUserInDTO) {
        PageHelper.startPage(atlUserInDTO.getPage(), atlUserInDTO.getPageSize());
        List<AtlUser> atlUsers = atlUserMapper.query(atlUserInDTO);
        PageInfo<AtlUser> pageInfo = new PageInfo<>(atlUsers);
        Page<AtlUser> pageResult = new Page<>();
        pageResult.setPage(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setTotalPage(pageInfo.getPages());
        pageResult.setTotalRecord(pageInfo.getTotal());
        pageResult.setData(pageInfo.getList());
        return pageResult;
    }

    @Override
    public Page<AtlUser> searchByLucene(AtlUserInDTO user) {
        String keyword = user.getKeyword();
        Query termQuery = new TermQuery(new Term("employeeId", keyword));
        termQuery.setBoost(10f);
        Query wildcardQuery1 = new WildcardQuery(new Term("employeeId", "*".concat(keyword).concat("*")));
        wildcardQuery1.setBoost(9f);
        Query wildcardQuery2 = new WildcardQuery(new Term("phone", "*".concat(keyword).concat("*")));
        wildcardQuery2.setBoost(8f);
        Query wildcardQuery3 = new WildcardQuery(new Term("email", "*".concat(keyword).concat("*")));
        wildcardQuery3.setBoost(7f);
        Query wildcardQuery4 = new WildcardQuery(new Term("username", "*".concat(keyword).concat("*")));
        wildcardQuery4.setBoost(7f);
        BooleanQuery.Builder queryBuilder = new BooleanQuery.Builder();
        queryBuilder.add(termQuery, BooleanClause.Occur.SHOULD);
        queryBuilder.add(wildcardQuery1, BooleanClause.Occur.SHOULD);
        queryBuilder.add(wildcardQuery2, BooleanClause.Occur.SHOULD);
        queryBuilder.add(wildcardQuery3, BooleanClause.Occur.SHOULD);
        queryBuilder.add(wildcardQuery4, BooleanClause.Occur.SHOULD);
        Page<AtlUser> page = new Page<>();
        page.setPage(user.getPage());
        page.setPageSize( user.getPageSize());
        return luceneService.searchUser(queryBuilder.build(), page);
    }

    @Override
    public void createIndex() throws IOException {
        List<AtlUser> list = atlUserMapper.selectAll();
        luceneService.addUsers(list);
    }

    @Override
    public AtlUser getAtlUserByEmployeeId(String employeeId) {
        AtlUser atlUser = new AtlUser();
        atlUser.setEmployeeId(employeeId);
        AtlUser temp = atlUserMapper.selectOne(atlUser);
        if (temp == null) {
            throw new CustomException("10010", "用户不存在");
        }
        return temp;
    }

    @Override
    public UserDetails loadUserByUsername(String employeeId) throws UsernameNotFoundException {
        return new CustomUserDetails(getAtlUserByEmployeeId(employeeId), true,
                true,
                true,
                true, null);
    }


}
