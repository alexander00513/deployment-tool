package com.gmail.bogatyr.alexander.deployment.tool.task.attachment.publication;

import com.gmail.bogatyr.alexander.deployment.tool.property.AttachmentType;
import com.gmail.bogatyr.alexander.deployment.tool.task.attachment.AttachmentUnzipTask;
import com.gmail.bogatyr.alexander.deployment.tool.task.TaskResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by Alexander Bogatyrenko on 08.08.16.
 * <p>
 * This class represents...
 */
@Component
@Scope("prototype")
public class AttachmentUnzipPublicationTask extends AttachmentUnzipTask {

    @Override
    public TaskResult call() throws Exception {
        String path = serverProperties.get(AttachmentType.PUBLICATION_STORAGE);
        return new TaskResult(unzip(AttachmentType.PUBLICATION_STORAGE, path));
    }
}
