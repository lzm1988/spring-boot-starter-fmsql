package top.threadlocal.fmsql.service;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Service(value = "propertiesSercice")
public class PropertiesSercice {

    private String sqlLocation ;

    private Properties properties = null;

    public String getSqlLocation() {
        return sqlLocation;
    }

    public void setSqlLocation(String sqlLocation) {
        this.sqlLocation = sqlLocation;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(){
        System.out.println("加载sql文件");
        properties = new Properties();
        String dir = Thread.currentThread().getContextClassLoader().getResource(getSqlLocation()).getPath();
        //String dir = Thread.currentThread().getContextClassLoader().getResource("/sql").getPath();
        File file = new File(dir);
        List<File> sqlList = new ArrayList<File>();
        if (file.isDirectory()){
            sqlList = Arrays.asList(file.listFiles());
        }
        for (File sqlFile : sqlList ) {
            SAXReader saxReader = new SAXReader();
            try {
                Document document = saxReader.read(sqlFile);
                Element root = document.getRootElement();
                String tableName = root.attributeValue("id");
                List<Element> sql = root.elements();
                for (Element e : sql ) {
                    String sqlId = tableName+"."+e.attributeValue("id");
                    properties.put(sqlId,e.getTextTrim());
                }
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
    }

    public String getSql(String sqlId){
        return getProperties().get(sqlId).toString();
    }

}
