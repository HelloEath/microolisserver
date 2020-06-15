package com.hello.adminserver.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hello.adminserver.repository.PermissionRepository;
import com.hello.adminserver.repository.RoleRepository;
import com.hello.adminserver.repository.UserRepository;
import com.hello.adminserver.repository.olis.RegionRepository;
import com.hello.common.dto.olis.Region;
import com.hello.common.entity.system.Permission;
import com.hello.common.entity.system.Role;
import com.hello.common.entity.system.User;
import com.hello.common.util.PasswordUtils;
import com.hello.common.util.StringUtil;
import com.hello.common.util.enumeration.Commons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 做一些初始化工作
 * Created by hzh on 2018/7/5.
 */
@Service
@Transactional
public class InitService {

    @Autowired
    private CommonService commonService;

    @Autowired
    RegionRepository regionRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    FileService fileService;



    @Autowired
    PermissionRepository permissionRepository;



    @Value("classpath:dictionary.json")
    Resource dictionary;

    @Value("classpath:region.json")
    Resource regions;


    @Value("classpath:customerdatapackage.json")
    Resource datapackage;

    @Value("classpath:permission.json")
    Resource permissions;


    public String init(String systemType) {
        if (StringUtil.isEmpty(systemType))
            return "systemType参数值不能为空";
        List<User> usersList = userRepository.findBySystemType(systemType);
        //有数据则不进行初始化
        if (usersList != null && usersList.size() > 0) return "已有User数据,不进行初始化";
        //dictService.deleteAll();
        //List<Region> regionList = this.initRegion(regions,systemType);
        List<Permission> permissionList=permissionRepository.findAll();
        if (permissionList==null|| permissionList.size()==0) {
            this.initPermission(permissions);

        }
        //dictService.initDictionary(dictionary);
        List<Role> rolesList = this.initRole(systemType);
        //Region region = regionList.get(0);
        User user = new User();
        user.setName("系统用户");
        //user.setRegion(region);
        user.setEmail("admin@admin.com");
        user.setPassword(PasswordUtils.encoder("888888"));
        user.setSystemType(systemType);
        user.setPhone("");
        user.setStatus(0);
        user.setOp(Commons.YES.getIntegerValue());
        user.setUsername("admin");
        user.setRole(rolesList.get(0));
        user = userRepository.save(user);

/**
 * **********************************************************
 */


        return "初始化完成";

    }

    /**
     * 初始化地区
     *
     * @param resource
     */
    public List<Region> initRegion(Resource resource, String systemType) {
        try {
            String dictionaryString = com.hello.common.util.FileUtils.getDictionaryString(resource);
            JSONArray jsonArray = JSON.parseArray(dictionaryString);
            List<Region>  regionList= new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Region region = new Region();
                //department.setId(1L + i);
                region.setRegionName(jsonObject.getString("name"));
                region.setSystemType(systemType);
                regionList.add(region);
            }
            regionRepository.saveAll(regionList);
            return regionList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }




    /**
     * 初始化权限数据
     *
     * @param resource
     */
    @Transactional
    public List<Permission> initPermission(Resource resource) {
        try {
            String dictionaryString = com.hello.common.util.FileUtils.getDictionaryString(resource);
            JSONArray jsonArray = JSON.parseArray(dictionaryString);
            List<Permission> list = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Permission permission = new Permission();
//                permission.setId(jsonObject.getLong("id"));
                permission.setName(jsonObject.getString("name"));
                permission.setIconCls(jsonObject.getString("iconCls"));
//                permission.setParentId(jsonObject.getLong("parentId"));
                permission.setPath(jsonObject.getString("path"));
                permission.setSorting(jsonObject.getInteger("sorting"));
                permission.setCreateTime(new Date());
                permission.setDescription("-");
                list.add(permission);
            }
            permissionRepository.saveAll(list);

            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 初始化角色
     */
    public List<Role> initRole(String systemType) {
        List<Permission> permissions = permissionRepository.findAll();


        List<Role> list = new ArrayList<>();
        Role role = new Role();
        role.setName("系统管理员");
        role.setSystemType(systemType);
        role.setDescription("系统管理员");
        role.setPermissions(permissions);
        role.setCode("role_system");
        list.add(role);

        list = roleRepository.saveAll(list);
        return list;
    }


}
