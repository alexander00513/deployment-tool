package com.gmail.bogatyr.alexander.deployment.tool.task.attachment;

import com.gmail.bogatyr.alexander.deployment.tool.config.AppConstants;
import com.gmail.bogatyr.alexander.deployment.tool.property.AttachmentType;
import com.gmail.bogatyr.alexander.deployment.tool.property.UpgradeProperties;
import com.gmail.bogatyr.alexander.deployment.tool.service.ResourceService;
import com.gmail.bogatyr.alexander.deployment.tool.task.TaskContext;
import com.gmail.bogatyr.alexander.deployment.tool.task.Task;
import com.gmail.bogatyr.alexander.deployment.tool.zip.ZipHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.EnumMap;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * Created by Alexander Bogatyrenko on 08.08.16.
 * <p>
 * This class represents...
 */
public abstract class AttachmentZipTask implements Task {

    protected static Logger logger = LoggerFactory.getLogger(AttachmentZipTask.class);

    protected TaskContext context;

    @Autowired
    protected UpgradeProperties props;

    @Autowired
    protected ResourceService resourceService;

    protected EnumMap<AttachmentType, String> serverProperties;

    @Override
    public TaskContext getContext() {
        return context;
    }

    @Override
    public void setContext(TaskContext context) {
        this.context = context;
    }

    protected boolean zip(AttachmentType attachmentType, String source) {
        if (!isEmpty(source)) {
            String ear = context.getEar();
            String target = props.getBackupFolder() + File.separator + ear;
            String zipName = String.format(AppConstants.ARCHIVE_NAME_PATTERN, ear, attachmentType.getShortName());
            return new ZipHelper(source, target).zip(zipName);
        }
        return true;
    }

    @PostConstruct
    private void postConstruct() {
        serverProperties = resourceService.parseServerProperties();
    }
}
