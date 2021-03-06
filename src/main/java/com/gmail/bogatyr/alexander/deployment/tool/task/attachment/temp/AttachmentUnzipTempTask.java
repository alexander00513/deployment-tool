package com.gmail.bogatyr.alexander.deployment.tool.task.attachment.temp;

import com.gmail.bogatyr.alexander.deployment.tool.task.attachment.AttachmentUnzipTask;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.gmail.bogatyr.alexander.deployment.tool.task.TaskResult;

import static com.gmail.bogatyr.alexander.deployment.tool.property.AttachmentType.TEMP_STORAGE;

/**
 * Created by Alexander Bogatyrenko on 08.08.16.
 * <p>
 * This class represents...
 */
@Component
@Scope("prototype")
public class AttachmentUnzipTempTask extends AttachmentUnzipTask {

    @Override
    public TaskResult call() throws Exception {
        String path = serverProperties.get(TEMP_STORAGE);
        return new TaskResult(unzip(TEMP_STORAGE, path));
    }
}
