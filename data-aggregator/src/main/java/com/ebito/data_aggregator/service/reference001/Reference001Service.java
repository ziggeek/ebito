package com.ebito.data_aggregator.service.reference001;

import com.ebito.data_aggregator.client.PrinterService;
import com.ebito.data_aggregator.api.controller.response.PrintedGuids;
import com.ebito.data_aggregator.service.Reference000Service;
import com.ebito.data_aggregator.model.PrintRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Reference001Service extends Reference000Service {

    private final PrinterService printerService;

    @Override
    public PrintedGuids execute(final PrintRequest printRequest) {
        final var printed = printerService.getPrint(printRequest);
        return printed;
    }
}
