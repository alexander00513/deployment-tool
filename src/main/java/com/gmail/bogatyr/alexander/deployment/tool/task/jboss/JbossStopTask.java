package com.gmail.bogatyr.alexander.deployment.tool.task.jboss;

import com.gmail.bogatyr.alexander.deployment.tool.service.JbossService;
import com.gmail.bogatyr.alexander.deployment.tool.task.Task;
import com.gmail.bogatyr.alexander.deployment.tool.task.TaskContext;
import com.gmail.bogatyr.alexander.deployment.tool.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by Alexander Bogatyrenko on 08.08.16.
 * <p>
 * This class represents...
 */
@Component
@Scope("prototype")
public class JbossStopTask implements Task {

    protected static Logger logger = LoggerFactory.getLogger(JbossStopTask.class);

    private TaskContext context;

    @Autowired
    private JbossService jbossService;

    @Override
    public TaskResult call() throws Exception {
        jbossService.stop();
        return new TaskResult(true);
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
