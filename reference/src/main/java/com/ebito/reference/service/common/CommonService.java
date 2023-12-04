package com.ebito.reference.service.common;

import com.ebito.reference.model.request.PrinterRequest;
import com.ebito.reference.model.enumeration.DocumentType;
import com.ebito.reference.model.PrintData;
import com.ebito.reference.model.request.ReferenceGenerationRequest;
import com.ebito.reference.model.response.PrintedGuids;
import com.ebito.reference.service.Reference000Service;
import com.ebito.reference.util.Dictionary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonService {

    private final ApplicationContext context;
    private static final String PATH = "com.ebito.reference.service.reference%s.Reference%sService";

    public PrintedGuids selectReference(final String clientId, final ReferenceGenerationRequest request) {
        Class<?> dogClass;
        try {
            dogClass = Class.forName(String.format(PATH, request.getReferenceCode(), request.getReferenceCode()));
        } catch (Exception e) {
            throw new RuntimeException("Тип справки некорректен");
        }
        PrintData printData = this.buildPrintRequest(request.getReferenceCode());
        PrintedGuids printedGuids = ((Reference000Service) context.getBean(dogClass)).execute(printData);
        return printedGuids;
    }

    private PrintData buildPrintRequest(final String referenceCode) {
        Assert.notNull(referenceCode, "'referenceCode' не должен быть null");

        //получить данные из источника (потом разные источники доавится могут)
        if (Objects.equals("001", referenceCode)) {

            var templateName = Dictionary.getReferenceName(referenceCode);
            var bookmarks = Map.of(); //todo: реализй тут логику с наполением данными
            var printData = PrinterRequest.builder()
                    .templateName(templateName)
                    .bookmarks(bookmarks) //todo подумай как решить эту проблему и кооректно мне отправить данные
                    .build();

            var printerRequest = PrintData.builder()
                    .documentType(DocumentType.PDF)
                    .printerRequest(printData)
                    .build();

            return printerRequest;

        } else {
            throw new RuntimeException("Неизвестный источник: " + referenceCode);
        }
    }
}
