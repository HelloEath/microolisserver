package com.hello.apigatewayservice.dto;


import com.hello.common.entity.system.Permission;
import lombok.Data;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzh on 2018/8/3.
 */
@Data
public class Menu {
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String description;

    private List<Menu> children;

    private String path;

    /**
     * 图标
     */
    private String iconCls;

    private Integer sorting;

    public Menu(Permission permission){
        if(permission!=null){
            this.id = permission.getId();
            this.name = permission.getName();
            this.path = permission.getPath();
            this.iconCls = permission.getIconCls();
            this.children = new ArrayList<>();
            this.description = permission.getDescription();
            this.sorting = permission.getSorting();
        }
    }

    
}
