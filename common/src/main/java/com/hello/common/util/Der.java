package com.hello.common.util;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/5/1  10:20
 */
public class Der {
    public static void main(String[] args) {
        String ddf="df";
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("");
        StringBuffer stringBuffer=new StringBuffer();
        String strs[]={"flower","flow","flight"};
        String ans=strs[0];
        for (int i=0;i<strs.length;i++){
            int j=0;
            for (;j<ans.length()&&j<strs[i].length();j++){
                if (ans.charAt(j)!=strs[i].charAt(j))break;
            }
            ans=ans.substring(0,j);
            if (ans.equals(""))        System.out.print(ans);
            ;
        }
        System.out.print(ans);
    }
}
