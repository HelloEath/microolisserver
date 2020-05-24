package com.hello.adminservice.util.enumeration;

/**
 * 角色唯一标识
 * Created by hzh on 2018/7/25.
 */
public enum RoleCode {
    OPERATION("总行操作岗","role_operation"),
    APPROVAL("总行审批岗","role_approval"),
    MANAGEMENT("总行管理岗","role_management"),
    BOPERATION("分行操作岗","role_branch_operation"),
    BAPPROVAL("分行审批岗","role_branch_approval"),
    BMANAGEMENT("分行管理岗","role_branch_management"),
    FINAL("总行终审岗","role_final"),
    SYSTEM("总行系统岗","role_system");

    private String name;
    private String value;
    private String description = "一些通用常量枚举";

    RoleCode(String name,String value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public int getIntegerValue(){
        return  Integer.valueOf(value);
    }

    public String getDescription() {
        return description;
    }
}
