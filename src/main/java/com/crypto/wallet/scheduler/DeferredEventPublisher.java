package com.crypto.wallet.scheduler;

import com.common.library.utils.CommonUtils;
import com.crypto.wallet.entity.deferevent.DeferredEvent;
import com.crypto.wallet.enums.deferredevent.EventStatus;
import com.crypto.wallet.repository.DeferredEventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeferredEventPublisher {
    private static final Logger logger = LoggerFactory.getLogger(DeferredEventPublisher.class);
    private final DeferredEventRepository deferredEventRepository;

    @Scheduled(cron = "2 * * * * *")
    @Transactional
    public void performTask() {
        logger.info("Get all the deferredEvents which are in CREATED MODE and deferUntil time ");
        List<DeferredEvent> eligibleDeferredEvents = deferredEventRepository.findEligibleDeferredEvents(Arrays.asList(EventStatus.CREATED),
                CommonUtils.getEpochTimeStamp());
        logger.info("Eligible Deferred Events ready to publish {}",eligibleDeferredEvents.size());


    }
}
