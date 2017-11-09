package top.threadlocal.fmsql.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.threadlocal.fmsql.service.DbClient;
import top.threadlocal.fmsql.service.FMSqlProperties;
import top.threadlocal.fmsql.service.PropertiesSercice;

@Configuration
@EnableConfigurationProperties(FMSqlProperties.class)
@ConditionalOnClass(PropertiesSercice.class)
@ConditionalOnProperty(prefix = "fmsql",value = "enabled",matchIfMissing = true)
public class FmSqlAutoConfig {

    @Autowired
    FMSqlProperties fmSqlProperties;

    @Autowired
    PropertiesSercice propertiesSercice;

    @Bean
    DbClient dbClient(){
        propertiesSercice.setProperties();
        return new DbClient();
    }



}
