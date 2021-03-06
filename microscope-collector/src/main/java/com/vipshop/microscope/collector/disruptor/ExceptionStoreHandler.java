package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventHandler;
import com.vipshop.microscope.collector.storager.MessageStorager;
import com.vipshop.microscope.common.util.ThreadPoolUtil;
import com.vipshop.microscope.client.exception.ExceptionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

/**
 * Exception store handler.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class ExceptionStoreHandler implements EventHandler<ExceptionEvent> {

    public static final Logger logger = LoggerFactory.getLogger(ExceptionStoreHandler.class);

    private final MessageStorager messageStorager = MessageStorager.getMessageStorager();

    private final ExecutorService exceptionStorageWorkerExecutor = ThreadPoolUtil.newFixedThreadPool(1, "exception-saveAppLog-worker-pool");

    @Override
    public void onEvent(final ExceptionEvent event, long sequence, boolean endOfBatch) throws Exception {
        exceptionStorageWorkerExecutor.execute(new Runnable() {
            @Override
            public void run() {
                processMetrics(event.getResult());
            }
        });
    }

    private void processMetrics(final ExceptionData exception) {
        messageStorager.save(exception);
    }

}