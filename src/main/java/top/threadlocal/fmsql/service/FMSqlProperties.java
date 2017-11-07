package top.threadlocal.fmsql.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "fmsql")
public class FMSqlProperties {

    private String sqlLocation = "sql";

    public String getSqlLocation() {
        return sqlLocation;
    }

    public void setSqlLocation(String sqlLocation) {
        this.sqlLocation = sqlLocation;
    }
}
