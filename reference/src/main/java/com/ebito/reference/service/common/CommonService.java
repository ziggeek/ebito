package com.ebito.reference.service.common;

import com.ebito.reference.model.Client;
import com.ebito.reference.model.request.PrinterRequest;
import com.ebito.reference.model.enumeration.DocumentType;
import com.ebito.reference.model.PrintData;
import com.ebito.reference.model.request.ReferenceGenerationRequest;
import com.ebito.reference.model.response.PrintedGuids;
import com.ebito.reference.repository.AccountRepository;
import com.ebito.reference.repository.ClientRepository;
import com.ebito.reference.repository.ContractRepository;
import com.ebito.reference.repository.OperationRepository;
import com.ebito.reference.service.Reference000Service;
import com.ebito.reference.util.Dictionary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonService {

    private final ApplicationContext context;
    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;
    private final ContractRepository contractRepository;
    private final OperationRepository operationRepository;
    private static final String PATH = "com.ebito.reference.service.reference%s.Reference%sService";

    public PrintedGuids selectReference(final long clientId, final ReferenceGenerationRequest request) {
        Class<?> dogClass;
        try {
            dogClass = Class.forName(String.format(PATH, request.getReferenceCode(), request.getReferenceCode()));
        } catch (Exception e) {
            throw new RuntimeException("Тип справки некорректен");
        }
        PrintData printData = this.buildPrintRequest(clientId, request.getReferenceCode());
        PrintedGuids printedGuids = ((Reference000Service) context.getBean(dogClass)).execute(printData);
        return printedGuids;
    }

    private PrintData buildPrintRequest(final long clientId, final String referenceCode) {
        Assert.notNull(referenceCode, "'referenceCode' не должен быть null");

        //получить данные из источника (потом разные источники доавится могут)
        if (Objects.equals("001", referenceCode)) {

            var templateName = Dictionary.getReferenceName(referenceCode);
            Client client = clientRepository.findById((int) clientId).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Client with id=%d was not found", clientId))
            );
            var bookmarks = extractClientData(client);

            var printerRequest = PrinterRequest.builder()
                    .templateName(templateName)
                    .bookmarks(bookmarks) //todo подумай как решить эту проблему и кооректно мне отправить данные
                    .build();

            var printData = PrintData.builder()
                    .documentType(DocumentType.PDF)
                    .printerRequest(printerRequest)
                    .build();

            return printData;

        } else {
            throw new RuntimeException("Неизвестный источник: " + referenceCode);
        }
    }

    private Map<String, Object> extractClientData(Client client) {
        return Map.of("firstName", client.getFirstName(),
                "lastName", client.getLastName(),
                "patronymic", client.getPatronymic(),
                "accountNumber", client.getAccount().getAccountNumber(),
                "accountCurrency", client.getAccount().getAccountCurrency(),
                "contractNumber", client.getContract().getContractNumber(),
                "contractDate", client.getContract().getContractDate(),
                "operations", operationRepository.findAllByClientId(client.getId()));
    }
}
