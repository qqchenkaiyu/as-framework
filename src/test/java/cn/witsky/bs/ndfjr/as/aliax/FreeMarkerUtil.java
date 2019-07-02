package cn.witsky.bs.ndfjr.as.aliax;



import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.StringWriter;
import java.util.Map;

/**
 * 
 * <b>
 *    FreeMarkerUtil
 * </b>
 * @author kangxu
 *
 */
public class FreeMarkerUtil {
    

    public static String geneFileStr(String fltFile,String templatePath,Map<String, Object> datas){
        
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_0);
        try {
            cfg.setDirectoryForTemplateLoading(new File(templatePath));
            Template template = cfg.getTemplate(fltFile,"UTF-8");
           //template.setEncoding("UTF-8");
            
            StringWriter out = new StringWriter();  
            
            template.process(datas, out);  
            
            out.flush();
            out.close();
            
            return out.getBuffer().toString();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
        return null;
        
    }
 
}