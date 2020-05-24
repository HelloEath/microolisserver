package com.hello.adminservice.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/4/24  14:50
 *
 */
public class TokenUtil {

    /**
     * 根据参数列表和md5转换生成key
     */
    public static String generateTokenKey(Object... args){
        StringBuilder sb=new StringBuilder();
        for (Object o:args){
            sb.append(o.toString());
        }
        return DigestUtils.md5Hex(sb.toString());
    }
}
