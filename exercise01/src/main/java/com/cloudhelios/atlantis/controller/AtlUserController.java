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

    @GetMapping("/searchdb")
    public DataResult search(AtlUserInDTO atlUserInDTO) {
        return DataResult.ok(atlUserService.search(atlUserInDTO));
    }

    @GetMapping("/search_lu")
    public DataResult searchByLucene(AtlUserInDTO atlUserInDTO) {
        return DataResult.ok(atlUserService.searchByLucene(atlUserInDTO));
    }

    @PutMapping("/register")
    public DataResult register(Long departId, @RequestBody AtlUser atlUser) {
        String password = passwordEncoder.encode(atlUser.getPassword());
        atlUser.setPassword(password);
        atlUserService.create(departId, atlUser);;
        return DataResult.ok();
    }

    @DeleteMapping("/del/{id}")
    public DataResult del(@PathVariable Long id) {
        atlUserService.del(id);
        return DataResult.ok();
    }

    @PostMapping("/update")
    public DataResult update(Long id,@RequestBody AtlUser atlUser) {
        atlUserService.update(id, atlUser);
        return DataResult.ok();
    }

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
