package top.threadlocal.fmsql.service;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

@Service(value = "freemarkerService")
public class FreemarkerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FreemarkerService.class);

    @Autowired
    FMSqlProperties fmSqlProperties;

    @Autowired
    PropertiesSercice propertiesSercice;

    public String getSql(String sqlId, Map<String, Object> param) {
        try {
            Configuration cfg = new Configuration();
            StringTemplateLoader sTmpLoader = new StringTemplateLoader();
            sTmpLoader.putTemplate(sqlId, getSqlTemplate(sqlId));
            cfg.setTemplateLoader(sTmpLoader);
            cfg.setDefaultEncoding("UTF-8");
            Template template = cfg.getTemplate(sqlId);
            StringWriter writer = new StringWriter();

            for (Map.Entry<String, Object> entry : param.entrySet()) {
                String val = entry.getValue().toString().replaceAll("'","\\\\'");
                entry.setValue(val);
            }

            template.process(param, writer);
            String sql = writer.toString();
            if (fmSqlProperties.getShowSql()){
                LOGGER.info(sql);
            }
            return writer.toString();
        } catch (TemplateException | IOException e) {
            throw new RuntimeException("parse sql error!",e);
        }
    }

    public String getSqlTemplate(String sqlAllId) {
        // 取得配置文件中的SQL文模板
        return propertiesSercice.getSql(sqlAllId);
    }

}
