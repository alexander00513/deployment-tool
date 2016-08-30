package com.gmail.bogatyr.alexander.deployment.tool.task.attachment.storage;

import com.gmail.bogatyr.alexander.deployment.tool.property.AttachmentType;
import com.gmail.bogatyr.alexander.deployment.tool.task.TaskResult;
import com.gmail.bogatyr.alexander.deployment.tool.task.attachment.AttachmentZipTask;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by Alexander Bogatyrenko on 08.08.16.
 * <p>
 * This class represents...
 */
@Component
@Scope("prototype")
public class AttachmentZipStorageTask extends AttachmentZipTask {

    @Override
    public TaskResult call() throws Exception {
        String path = serverProperties.get(AttachmentType.STORAGE);
        return new TaskResult(zip(AttachmentType.STORAGE, path));
    }
}
