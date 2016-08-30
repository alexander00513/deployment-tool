package com.gmail.bogatyr.alexander.deployment.tool;

import com.gmail.bogatyr.alexander.deployment.tool.config.AppConfig;
import com.gmail.bogatyr.alexander.deployment.tool.manager.EarDeployManager;
import com.gmail.bogatyr.alexander.deployment.tool.property.UpgradePropertiesValidator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DeploymentToolApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        UpgradePropertiesValidator propertiesValidator = applicationContext.getBean(UpgradePropertiesValidator.class);
        boolean isValid = propertiesValidator.validate();

        if (isValid) {
            EarDeployManager earDeployManager = applicationContext.getBean(EarDeployManager.class);
            earDeployManager.deploy();
        }
    }
}
