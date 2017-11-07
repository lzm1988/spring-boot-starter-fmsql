package top.threadlocal.fmsql.service;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

@Service(value = "freemarkerService")
public class FreemarkerService {

    @Autowired
    PropertiesSercice propertiesSercice;

    public String getSql(String sqlId, Map<String, ?> param) {
        try {
            Configuration cfg = new Configuration();
            StringTemplateLoader sTmpLoader = new StringTemplateLoader();
            sTmpLoader.putTemplate(sqlId, getSqlTemplate(sqlId));
            cfg.setTemplateLoader(sTmpLoader);
            cfg.setDefaultEncoding("UTF-8");
            Template template = cfg.getTemplate(sqlId);
            StringWriter writer = new StringWriter();
            template.process(param, writer);
            return writer.toString();
        } catch (TemplateException e) {
            throw new RuntimeException("Parse sql failed", e);
        } catch (IOException e) {
            throw new RuntimeException("Parse sql failed", e);
        }
    }

    public String getSqlTemplate(String sqlAllId) {
        // 取得配置文件中的SQL文模板
        return propertiesSercice.getSql(sqlAllId);
    }

}
