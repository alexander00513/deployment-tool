package com.gmail.bogatyr.alexander.deployment.tool.task.ear;

import com.gmail.bogatyr.alexander.deployment.tool.property.UpgradeProperties;
import com.gmail.bogatyr.alexander.deployment.tool.service.ResourceService;
import com.gmail.bogatyr.alexander.deployment.tool.task.Task;
import com.gmail.bogatyr.alexander.deployment.tool.task.TaskContext;
import com.gmail.bogatyr.alexander.deployment.tool.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Alexander Bogatyrenko on 08.08.16.
 * <p>
 * This class represents...
 */
@Component
@Scope("prototype")
public class EarRestoreBackupTask implements Task {

    protected static Logger logger = LoggerFactory.getLogger(EarRestoreBackupTask.class);

    private TaskContext context;

    @Autowired
    private UpgradeProperties props;

    @Autowired
    private ResourceService resourceService;

    @Override
    public TaskResult call() throws Exception {
        String ear = context.getEar();
        Path source  = Paths.get(props.getBackupFolder(), ear);
        Path target= Paths.get(props.getJbossHome(), "standalone", "deployments");
        return new TaskResult(resourceService.copy(source, target, ear));
    }

    @Override
    public TaskContext getContext() {
        return context;
    }

    @Override
    public void setContext(TaskContext context) {
        this.context = context;
    }
}
