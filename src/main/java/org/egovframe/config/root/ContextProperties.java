package org.egovframe.config.root;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.egovframe.rte.fdl.property.impl.EgovPropertyServiceImpl;

@Configuration
public class ContextProperties {
    @Bean(destroyMethod = "destroy")
    public EgovPropertyServiceImpl propertiesService() {
        EgovPropertyServiceImpl egovPropertyServiceImpl = new EgovPropertyServiceImpl();

        Map<String, String> properties = new HashMap<String, String>();
        properties.put("pageUnit", "10");
        properties.put("pageSize", "10");
        properties.put("posblAtchFileSize", "5242880");
        properties.put("Globals.fileStorePath", "/user/file/sht/");
        properties.put("Globals.addedOptions", "false");

        egovPropertyServiceImpl.setProperties(properties);
        return egovPropertyServiceImpl;
    }
}
