package com.ebito.data_aggregator.client;

import com.ebito.data_aggregator.model.PrintRequest;
import com.ebito.data_aggregator.api.controller.response.PrintedGuids;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrinterService {

    private final PrinterClient printerClient;

    public PrintedGuids getPrint(PrintRequest printRequest) {

        try {
            PrintedGuids printed = this.sendRequest(printRequest);
            log.info("*** Пришел ответ от сервиса печатных форм: {}", printed);
            return printed;
        } catch (FeignException ex) {
            log.error("Сервис печатных форм вернул ошибку: ", ex);
            throw new RuntimeException(ex);
        }
    }

    private PrintedGuids sendRequest(PrintRequest body) {

        log.info("*** Отправляем запрос в сервис печатных форм: {}", body);
        PrintedGuids printedGuids;
        switch (body.getDocumentType()) {
            case PDF:
                printedGuids = printerClient.generatePrintForm(body.getPrintData()).getBody();
                break;
            default:
                throw new IllegalStateException("*** Формат документа не найден " + body.getDocumentType());
        }

        return printedGuids;
    }
}
