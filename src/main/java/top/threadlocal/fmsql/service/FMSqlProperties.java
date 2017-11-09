package top.threadlocal.fmsql.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "fmsql")
public class FMSqlProperties {

    private String sqlLocation = "sql";

    private boolean showSql = true;

    private boolean showTime = true;

    public String getSqlLocation() {
        return sqlLocation;
    }

    public void setSqlLocation(String sqlLocation) {
        this.sqlLocation = sqlLocation;
    }

    public boolean getShowSql() {
        return showSql;
    }

    public void setShowSql(boolean showSql) {
        this.showSql = showSql;
    }

    public boolean getShowTime() {
        return showTime;
    }

    public void setShowTime(boolean showTime) {
        this.showTime = showTime;
    }
}
