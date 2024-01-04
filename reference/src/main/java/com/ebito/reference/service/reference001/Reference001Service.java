package com.ebito.reference.service.reference001;

import com.ebito.reference.client.PrinterService;
import com.ebito.reference.model.response.PrintedGuids;
import com.ebito.reference.service.Reference000Service;
import com.ebito.reference.model.PrintRequest;
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
