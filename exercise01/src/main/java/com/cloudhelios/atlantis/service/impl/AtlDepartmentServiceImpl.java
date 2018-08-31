package com.cloudhelios.atlantis.service.impl;

import com.cloudhelios.atlantis.domain.AtlDepartment;
import com.cloudhelios.atlantis.domain.AtlDepartmentUser;
import com.cloudhelios.atlantis.exception.CustomException;
import com.cloudhelios.atlantis.mapper.AtlDepartmentMapper;
import com.cloudhelios.atlantis.mapper.AtlDepartmentUserMapper;
import com.cloudhelios.atlantis.service.AtlDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * author: chenwei
 * createDate: 18-8-29 上午9:36
 * description:
 */
@Service
public class AtlDepartmentServiceImpl implements AtlDepartmentService {

    @Autowired
    private AtlDepartmentMapper atlDepartmentMapper;

    @Autowired
    private AtlDepartmentUserMapper atlDepartmentUserMapper;


    @Override
    public void checkDepartExists(Long departmentId) {
        if (departmentId != null) {
            if (atlDepartmentMapper.selectByPrimaryKey(departmentId) == null) {
                throw new CustomException("10014", "" + departmentId);
            }
        }
    }

    @Override
    public void create(Long parentId, AtlDepartment atlDepartment) {
        checkDepartExists(parentId);
        //最顶层
        if (parentId == null) {
            atlDepartmentMapper.insertSelective(atlDepartment);
            String path = "" + atlDepartment.getId();
            atlDepartment.setPath(path);
            atlDepartmentMapper.updateByPrimaryKeySelective(atlDepartment);
            return;
        }
        AtlDepartment parentDepartment = atlDepartmentMapper.selectByPrimaryKey(parentId);
        //保存父部门的id
        atlDepartment.setParentId(parentDepartment.getId());
        atlDepartmentMapper.insertSelective(atlDepartment);
        String parentPath = parentDepartment.getPath();
        String path = parentPath + "/" + atlDepartment.getId();
        //更新path
        AtlDepartment temp = new AtlDepartment();
        temp.setId(atlDepartment.getId());
        temp.setPath(path);
        atlDepartmentMapper.updateByPrimaryKeySelective(temp);
    }

    @Override
    public void del(Long id) {
        checkDepartExists(id);
        Example example = new Example(AtlDepartment.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("path", "%" + id + "%");
        //删除path中包含id的部门
        atlDepartmentMapper.deleteByExample(example);
        //删除中间表数据
        AtlDepartmentUser atlDepartmentUser = new AtlDepartmentUser();
        atlDepartmentUser.setDepartmentId(id);
        atlDepartmentUserMapper.delete(atlDepartmentUser);
    }

    @Override
    public void update(Long id, AtlDepartment atlDepartment) {
        checkDepartExists(id);
        atlDepartment.setId(id);
        atlDepartmentMapper.updateByPrimaryKeySelective(atlDepartment);
    }

    @Override
    public List<AtlDepartment> getDepartTree(Long id) {
        checkDepartExists(id);
        AtlDepartment atlDepartment = atlDepartmentMapper.selectByPrimaryKey(id);
        //获取所有的子部门
        List<AtlDepartment> nodes = atlDepartmentMapper.getAllChild(atlDepartment.getPath());
        //加上最上层
        nodes.add(atlDepartment);
        return getTree(nodes, id);
    }

    @Override
    public Integer getNumById(Long id) {
        checkDepartExists(id);
        AtlDepartment atlDepartment = atlDepartmentMapper.selectByPrimaryKey(id);
        return atlDepartmentMapper.getNumById(atlDepartment.getPath());
    }

    public List<AtlDepartment> getTree(List<AtlDepartment> nodes, Long id) {
        List<AtlDepartment> atlDepartments = buildTree(nodes, id);
        if (atlDepartments == null || atlDepartments.isEmpty()) {
            return null;
        }
        return atlDepartments;
    }

    public List<AtlDepartment> buildTree(List<AtlDepartment> treeList, Long id) {
        List<AtlDepartment> retList = new ArrayList<>();
        for (AtlDepartment parent : treeList) {
            parent.setNum(atlDepartmentMapper.getNumById(parent.getPath()));
            if (parent.getId().equals(id)) {
                retList.add(findChildren(parent, treeList));
            }
        }
        return retList;
    }

    private static AtlDepartment findChildren(AtlDepartment parent, List<AtlDepartment> treeList) {
        for (AtlDepartment child : treeList) {
            if (parent.getId().equals(child.getParentId())) {
                if (parent.getSubDeparts() == null) {
                    parent.setSubDeparts(new ArrayList<>());
                }
                parent.getSubDeparts().add(findChildren(child, treeList));
            }
        }
        return parent;
    }
}
