package com.hello.apigatewayservice.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.stereotype.Component;

/**
 * 自定义数据库命名策略，只使用于hibernate 5.0.x之后版本
 * Created by hzh on 2018/6/19.
 */
@Component
public class LocalNamingStrategy extends SpringPhysicalNamingStrategy {

    private String prefix = "t_";

    @Override
    public Identifier toPhysicalTableName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        if (identifier==null||identifier.getText().length()==0)return null;
        Identifier id = super.toPhysicalTableName(identifier,jdbcEnvironment);
        return Identifier.toIdentifier(prefix + id.getText());
    }
}
