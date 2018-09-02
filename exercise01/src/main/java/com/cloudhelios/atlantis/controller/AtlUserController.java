package com.cloudhelios.atlantis.controller;

import com.cloudhelios.atlantis.domain.AtlUser;
import com.cloudhelios.atlantis.dto.AtlUserInDTO;
import com.cloudhelios.atlantis.service.AtlUserService;
import com.cloudhelios.atlantis.util.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * author: chenwei
 * createDate: 18-8-27 下午2:42
 * description:
 */
@RestController
@RequestMapping("/user")
public class AtlUserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AtlUserService atlUserService;

    /**
     * @api {GET} /user/searchdb search-user-db
     * @apiName searchdb
     * @apiDescription 从数据库中搜索用户
     * @apiGroup user
     * @apiHeader {String} access-token Users unique access-token.
     * @apiHeaderExample {json} Header-Example:
     * {
     *   "Authorization":"Bearer ff4cc574-7e82-4187-a4ec-f2883cce8f88"
     * }
     * @apiParam {Integer} page 页码，默认为1
     * @apiParam {Integer} pageSize 每次最多返回记录数，默认为20
     * @apiParam {String} keyword 搜索关键字，按照用户编码，手机号，邮箱，用户名查找
     * @apiParamExample Request-Example:
     * /user/searchdb?keyword=李四&page=1&pageSiez=20
     * @apiSuccessExample {json} Success-Response:
     * {
     *   "status": 200,
     *   "message": "ok",
     *   "data": {
     *   "page": 1,
     *   "pageSize": 20,
     *   "totalPage": 1,
     *   "totalRecord": 1,
     *   "data": [
     *     {
     *       "id": 2,
     *       "username": "李四",
     *       "password": null,
     *       "employeeId": "1002",
     *       "email": "12371293@qq.com",
     *       "phone": "15825923411",
     *       "statue": null,
     *       "language": null
     *     }
     *    ]
     *   }
     * }
     * @apiVersion 0.0.1
     * @param atlUserInDTO
     * @return
     */
    @GetMapping("/searchdb")
    public DataResult search(AtlUserInDTO atlUserInDTO) {
        return DataResult.ok(atlUserService.search(atlUserInDTO));
    }

    /**
     * @api {GET} /user/search_lu search-user-lu
     * @apiName search_lu
     * @apiDescription 从索引库中搜索用户
     * @apiGroup user
     * @apiHeader {String} access-token Users unique access-token.
     * @apiHeaderExample {json} Header-Example:
     * {
     *   "Authorization":"Bearer ff4cc574-7e82-4187-a4ec-f2883cce8f88"
     * }
     * @apiParam {Integer} page 页码，默认为1
     * @apiParam {Integer} pageSize 每次最多返回记录数，默认为20
     * @apiParam {String} keyword 搜索关键字，按照用户编码，手机号，邮箱，用户名查找
     * @apiParamExample Request-Example:
     * /user/searchdb?keyword=李四&page=1&pageSiez=20
     * @apiSuccessExample {json} Success-Response:
     * {
     *   "status": 200,
     *   "message": "ok",
     *   "data": {
     *   "page": 1,
     *   "pageSize": 20,
     *   "totalPage": 1,
     *   "totalRecord": 1,
     *   "data": [
     *     {
     *       "id": 2,
     *       "username": "李四",
     *       "password": null,
     *       "employeeId": "1002",
     *       "email": "12371293@qq.com",
     *       "phone": "15825923411",
     *       "statue": null,
     *       "language": null
     *     }
     *    ]
     *   }
     * }
     * @apiVersion 0.0.1
     * @param atlUserInDTO
     * @return
     */
    @GetMapping("/search_lu")
    public DataResult searchByLucene(AtlUserInDTO atlUserInDTO) {
        return DataResult.ok(atlUserService.searchByLucene(atlUserInDTO));
    }

    /**
     * @api {PUT} /user/register user-register
     * @apiName register
     * @apiDescription 用户注册
     * @apiGroup user
     * @apiHeader {String} Content-Type Request Content-Type.
     * @apiHeaderExample {json} Header-Example:
     * {
     *   "Content-Type":"application/json"
     * }
     * @apiParam {Long} departId 部门ID
     * @apiParam {String} username 用户名
     * @apiParam {String} password 密码
     * @apiParam {String} employeeId 职工编号
     * @apiParam {String} email 邮箱
     * @apiParam {String} phone 手机号
     * @apiParamExample {json} Request-Example:
     * /user/register?departId=2
     * {
     *  "username":"wangwu",
     *  "password":"111111",
     *  "employeeId":"1003",
     *  "email":"abcd@qq.com",
     *  "phone":"15825923412"
     * }
     * @apiSuccessExample {json} Success-Response:
     * {
     *   "status": 200,
     *   "message": "ok",
     *   "data": null
     * }
     * @apiVersion 0.0.1
     */
    @PutMapping("/register")
    public DataResult register(Long departId, @RequestBody AtlUser atlUser) {
        String password = passwordEncoder.encode(atlUser.getPassword());
        atlUser.setPassword(password);
        atlUserService.create(departId, atlUser);;
        return DataResult.ok();
    }

    /**
     * @api {DELETE} /user/del/:id user-delete
     * @apiName delUser
     * @apiDescription 删除用户
     * @apiGroup user
     * @apiHeader {String} access-token Users unique access-token.
     * @apiHeaderExample {json} Header-Example:
     * {
     *   "Authorization":"Bearer ff4cc574-7e82-4187-a4ec-f2883cce8f88"
     * }
     * @apiParam {Long} id 用户ID
     * @apiParamExample {json} Request-Example:
     * /user/del/2
     * @apiSuccessExample {json} Success-Response:
     * {
     *   "status": 200,
     *   "message": "ok",
     *   "data": null
     * }
     * @apiVersion 0.0.1
     */
    @DeleteMapping("/del/{id}")
    public DataResult del(@PathVariable Long id) {
        atlUserService.del(id);
        return DataResult.ok();
    }

    /**
     * @api {POST} /user/update user-update
     * @apiName updateUser
     * @apiDescription 更新用户
     * @apiGroup user
     * @apiHeader {String} access-token Users unique access-token.
     * @apiHeaderExample {json} Header-Example:
     * {
     *   "Authorization":"Bearer ff4cc574-7e82-4187-a4ec-f2883cce8f88"
     * }
     * @apiParam {Long} id 用户ID
     * @apiParam {String} username 用户名
     * @apiParam {String} password 密码
     * @apiParam {String} email 邮箱
     * @apiParam {String} phone 手机号
     * @apiParamExample {json} Request-Example:
     * /user/update?id=2
     * {
     *  "username":"wangwu",
     *  "password":"111111",
     *  "email":"abcd@qq.com",
     *  "phone":"15825923412"
     * }
     * @apiSuccessExample {json} Success-Response:
     * {
     *   "status": 200,
     *   "message": "ok",
     *   "data": null
     * }
     * @apiVersion 0.0.1
     */
    @PostMapping("/update")
    public DataResult update(Long id,@RequestBody AtlUser atlUser) {
        atlUserService.update(id, atlUser);
        return DataResult.ok();
    }

    /**
     * @api {POST} /user/index init-user-index
     * @apiName initUserIndex
     * @apiDescription 初始化索引库
     * @apiGroup user
     * @apiHeader {String} access-token Users unique access-token.
     * @apiHeaderExample {json} Header-Example:
     * {
     *   "Authorization":"Bearer ff4cc574-7e82-4187-a4ec-f2883cce8f88"
     * }
     * @apiParamExample {json} Request-Example:
     * /user/index
     * @apiSuccessExample {json} Success-Response:
     * {
     *   "status": 200,
     *   "message": "ok",
     *   "data": null
     * }
     * @apiVersion 0.0.1
     */
    @GetMapping("/index")
    public DataResult createIndex() {
        try {
            atlUserService.createIndex();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DataResult.ok();
    }


}
