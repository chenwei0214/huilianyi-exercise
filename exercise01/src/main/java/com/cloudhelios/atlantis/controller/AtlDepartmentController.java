package com.cloudhelios.atlantis.controller;

import com.cloudhelios.atlantis.domain.AtlDepartment;
import com.cloudhelios.atlantis.service.AtlDepartmentService;
import com.cloudhelios.atlantis.util.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author: chenwei
 * createDate: 18-8-27 上午11:11
 * description:
 */
@RestController
@RequestMapping("/depart")
public class AtlDepartmentController {

    @Autowired
    private AtlDepartmentService atlDepartmentService;


    /**
     * @api {GET} /depart/tree get-depart-tree
     * @apiName getDepartTree
     * @apiDescription 获取部门组织结构
     * @apiGroup depart
     * @apiHeader {String} access-token Users unique access-token.
     * @apiHeaderExample {json} Header-Example:
     * {
     *   "Authorization":"Bearer ff4cc574-7e82-4187-a4ec-f2883cce8f88"
     * }
     * @apiParam {Long} id 部门ID，可选项，默认为1
     * @apiParamExample Request-Example:
     * /depart/tree?id=1
     * @apiSuccessExample {json} Success-Response:
     * {
     *  "status": 200,
     *  "message": "ok",
     *  "data": [
     *   {
     *     "id": 1,
     *     "departmentName": "abcd",
     *     "departmentCode": "1001",
     *     "parentId": null,
     *     "num": 5,
     *     "subDeparts": [
     *      {
     *        "id": 2,
     *        "departmentName": "bcd",
     *        "departmentCode": "1002",
     *        "parentId": 1,
     *        "num": 3,
     *        "subDeparts": []
     *      }
     *     ]
     *   }
     *  ]
     * }
     * @apiVersion 0.0.1
     * @param id
     * @return
     */
    @GetMapping("/tree")
    public DataResult getTree(@RequestParam(required = false) Long id) {
        List<AtlDepartment> departTree = atlDepartmentService.getDepartTree(id == null ? 1l : id);
        return DataResult.ok(departTree);
    }

    /**
     * @api {PUT} /depart/add add-depart
     * @apiName addDepart
     * @apiDescription 添加部门
     * @apiGroup depart
     * @apiHeader {String} access-token Users unique access-token.
     * @apiHeaderExample {json} Header-Example:
     * {
     *   "Authorization":"Bearer ff4cc574-7e82-4187-a4ec-f2883cce8f88",
     *   "Content-Type":"application/json"
     * }
     * @apiParam {Long} parentId 父部门ID
     * @apiParam {String} departmentName 部门名称
     * @apiParam {String} departmentCode 部门编码
     * @apiParamExample Request-Example:
     * /depart/add?parentId=1
     * {
     *     "departmentName":"销售部",
     *     "departmentCode":"1003"
     * }
     * @apiSuccessExample {json} Success-Response:
     * {
     *  "status": 200,
     *  "message": "ok",
     *  "data": null
     * }
     * @apiVersion 0.0.1
     * @param parentId
     * @param atlDepartment
     * @return
     */
    @PutMapping("/add")
    public DataResult create(Long parentId,@RequestBody AtlDepartment atlDepartment){
        atlDepartmentService.create(parentId,atlDepartment);
        return DataResult.ok(atlDepartment);
    }

    /**
     * @api {DELETE} /depart/del delete-depart
     * @apiName delDepart
     * @apiDescription 删除部门
     * @apiGroup depart
     * @apiHeader {String} access-token Users unique access-token.
     * @apiHeaderExample {json} Header-Example:
     * {
     *   "Authorization":"Bearer ff4cc574-7e82-4187-a4ec-f2883cce8f88"
     * }
     * @apiParam {Long} id 部门ID
     * @apiParamExample Request-Example:
     * /depart/del/1
     * @apiSuccessExample {json} Success-Response:
     * {
     *  "status": 200,
     *  "message": "ok",
     *  "data": null
     * }
     * @apiVersion 0.0.1
     * @param id
     * @return
     */
    @DeleteMapping("/del/{id}")
    public DataResult del(@PathVariable Long id){
        atlDepartmentService.del(id);
        return DataResult.ok();
    }

    /**
     * @api {POST} /depart/update update-depart
     * @apiName updateDepart
     * @apiDescription 更新部门
     * @apiGroup depart
     * @apiHeader {String} access-token Users unique access-token.
     * @apiHeaderExample {json} Header-Example:
     * {
     *   "Authorization":"Bearer ff4cc574-7e82-4187-a4ec-f2883cce8f88",
     *   "Content-Type":"application/json"
     * }
     * @apiParam {Long} id 部门ID
     * @apiParam {String} departmentName 部门名称
     * @apiParam {String} departmentCode 部门编码
     * @apiParamExample Request-Example:
     * /depart/update/1
     * {
     *     "departmentName":"销售部",
     *     "departmentCode":"1003"
     * }
     * @apiSuccessExample {json} Success-Response:
     * {
     *  "status": 200,
     *  "message": "ok",
     *  "data": null
     * }
     * @apiVersion 0.0.1
     * @param id
     * @param atlDepartment
     * @return DataResult
     */
    @PostMapping("/update/{id}")
    public DataResult update(@PathVariable Long id,@RequestBody AtlDepartment atlDepartment){
        atlDepartmentService.update(id,atlDepartment);
        return DataResult.ok();
    }


    /**
     * @api {GET} /depart/num get-depart-num
     * @apiName getDepartNum
     * @apiDescription 获取部门人数
     * @apiGroup depart
     * @apiHeader {String} access-token Users unique access-token.
     * @apiHeaderExample {json} Header-Example:
     * {
     *   "Authorization":"Bearer ff4cc574-7e82-4187-a4ec-f2883cce8f88"
     * }
     * @apiParam {Long} id 部门ID
     * @apiParamExample Request-Example:
     * /depart/num/1
     * @apiSuccessExample {json} Success-Response:
     * {
     *  "status": 200,
     *  "message": "ok",
     *  "data": 5
     * }
     * @apiVersion 0.0.1
     * @param id
     * @return DataResult
     */
    @GetMapping("/num/{id}")
    public DataResult getNumById(@PathVariable Long id){
        return DataResult.ok( atlDepartmentService.getNumById(id));
    }
}
