package com.gmail.bogatyr.alexander.deployment.tool.service;

import com.gmail.bogatyr.alexander.deployment.tool.config.AppConstants;
import com.gmail.bogatyr.alexander.deployment.tool.property.UpgradeProperties;
import com.gmail.bogatyr.alexander.deployment.tool.util.ProcessPrintUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

/**
 * Created by Alexander Bogatyrenko on 28.07.16.
 * <p>
 * This class represents...
 */
@Service
public class JbossService {

    private static Logger logger = LoggerFactory.getLogger(JbossService.class);

    @Autowired
    private UpgradeProperties props;

    @Autowired
    private Boolean isWindowsEnv;

    public void start() {
        try {
            String command = Paths.get(props.getJbossHome(), "bin", isWindowsEnv ? "standalone.bat" : "standalone.sh").toString();
            logger.info("Jboss start...");
            Process process = Runtime.getRuntime().exec(command);
            ProcessPrintUtil.printInBackground(process);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void stop() {
        try {
            Path path = Paths.get(props.getJbossHome(), "bin", isWindowsEnv ? "jboss-cli.bat" : "jboss-cli.sh");
            String command = path +
                    " --connect" +
                    " --controller=" + props.getJbossCliController() + ":" + props.getJbossCliPort() +
                    " --user=" + props.getJbossUser() +
                    " --password=" + props.getJbossPassword() +
                    " command=:shutdown";
            logger.info("Jboss stop...");
            Process process = Runtime.getRuntime().exec(command);
            ProcessPrintUtil.print(process);
            while (isServerStarted()) {
                try {
                    logger.info("Waiting jboss stopping 10 seconds");
                    Thread.sleep(TimeUnit.SECONDS.toMillis(10));
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            logger.info("Server has been stopped");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public boolean isServerStarted() {
        try {
            URL url = new URL(String.format(AppConstants.SERVER_URL_PATTERN, props.getJbossHost(), props.getJbossAdminPort()));
            Integer code = isUrlAvailable(url, "GET");
            if (code != null) {
                if (code.equals(200)) {
                    logger.info("Server is running");
                    return true;
                }
                if (code.equals(-1)) return false;
            }
        } catch (MalformedURLException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    public Boolean isDeployed(String ear) {
        String deployment = Paths.get(props.getJbossHome(), "standalone", "deployments").toString();
        Path success = Paths.get(deployment, ear + ".ear.deployed");
        Path fail = Paths.get(deployment, ear + ".ear.failed");
        if (Files.exists(success)) {
            logger.info("Ear {} has been deployed successful", ear);
            return true;
        } else if (Files.exists(fail)) {
            logger.info("Ear {} deployment is failed", ear);
            return false;
        }
        return null;
    }

    public boolean isAppStarted() {
        try {
            URL url = new URL(String.format(AppConstants.APP_URL_PATTERN, props.getJbossHost(), props.getJbossAppPort(), props.getJbossAppUrl()));
            Integer code = isUrlAvailable(url, "GET");
            if (code != null) {
                if (code.equals(200)) return true;
                if (code.equals(-1)) return false;
                if (code.equals(404)) {
                    logger.info("The application not responding, url - {}, code - {}", url.toString(), code);
                    return false;
                }
            }
        } catch (MalformedURLException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    private Integer isUrlAvailable(URL url, String method) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.connect();
            return connection.getResponseCode();

        } catch (IOException e) {
            boolean serverDown = "Connection refused".equalsIgnoreCase(e.getMessage());
            if (serverDown) {
                return -1;
            } else {
                logger.error(e.getMessage(), e);
                return null;
            }
        }
    }

    public boolean isDeploySuccess(String ear) {
        while (isDeployed(ear) == null) {
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(5));
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }
        boolean started = isAppStarted();
        logger.info("The application {} started correctly", started ? "was" : "was not");
        return started;
    }
}
