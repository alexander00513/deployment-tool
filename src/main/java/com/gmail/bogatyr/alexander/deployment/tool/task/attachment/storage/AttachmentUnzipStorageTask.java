package com.gmail.bogatyr.alexander.deployment.tool.task.attachment.storage;

import com.gmail.bogatyr.alexander.deployment.tool.property.AttachmentType;
import com.gmail.bogatyr.alexander.deployment.tool.task.TaskResult;
import com.gmail.bogatyr.alexander.deployment.tool.task.attachment.AttachmentUnzipTask;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by Alexander Bogatyrenko on 08.08.16.
 * <p>
 * This class represents...
 */
@Component
@Scope("prototype")
public class AttachmentUnzipStorageTask extends AttachmentUnzipTask {

    @Override
    public TaskResult call() throws Exception {
        String path = serverProperties.get(AttachmentType.STORAGE);
        return new TaskResult(unzip(AttachmentType.STORAGE, path));
    }
}
