package com.hello.common.util.db;

import com.alibaba.druid.util.DruidPasswordCallback;
import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

@SuppressWarnings("serial")
public class DbPasswordCallback extends DruidPasswordCallback {
	/**
	 * 描述 数据库密码回调解密
	 *
	 */
	@Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        String pwd = properties.getProperty("password");
        if (StringUtils.isNotBlank(pwd)) {
            try {
                //对application.properties配置的密码密文 进行解密
               // String password = PwdUtil.getDecriptPwd(pwd);
                String password = pwd;
                setPassword(password.toCharArray());
            } catch (Exception e) {
                setPassword(pwd.toCharArray());
            }
        }
    }
}