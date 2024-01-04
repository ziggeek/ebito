package com.ebito.reference.client;

import com.ebito.reference.model.PrintData;
import com.ebito.reference.model.response.PrintedGuids;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrinterService {

    private final PrinterClient printerClient;

    public PrintedGuids getPrint(PrintData printData) {

        try {
            PrintedGuids printed = this.sendRequest(printData);
            log.info("*** Пришел ответ от сервиса печатных форм: {}", printed);
            return printed;
        } catch (FeignException ex) {
            log.error("Сервис печатных форм вернул ошибку: ", ex);
            throw new RuntimeException(ex);
        }
    }

    private PrintedGuids sendRequest(PrintData body) {

        log.info("*** Отправляем запрос в сервис печатных форм: {}", body);
        PrintedGuids printedGuids;
        switch (body.getDocumentType()) {
            case PDF:
                printedGuids = printerClient.generatePdf(body.getPrinterRequest().getFormGenerationData()).getBody();
                break;
            default:
                throw new IllegalStateException("*** Формат документа не найден " + body.getDocumentType());
        }

        return printedGuids;
    }
}
