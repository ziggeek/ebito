package com.ebito.reference.service.common;

import com.ebito.reference.model.Client;
import com.ebito.reference.model.Operation;
import com.ebito.reference.model.enumeration.Channel;
import com.ebito.reference.model.request.FormGenerationData;
import com.ebito.reference.model.request.PrinterRequest;
import com.ebito.reference.model.enumeration.DocumentType;
import com.ebito.reference.model.PrintData;
import com.ebito.reference.model.request.Reference001FormGenerationData;
import com.ebito.reference.model.request.ReferenceGenerationRequest;
import com.ebito.reference.model.response.PrintedGuids;
import com.ebito.reference.repository.AccountRepository;
import com.ebito.reference.repository.ClientRepository;
import com.ebito.reference.repository.ContractRepository;
import com.ebito.reference.repository.OperationRepository;
import com.ebito.reference.service.Reference000Service;
import com.ebito.reference.util.Dictionary;
import com.ebito.reference.util.ModelMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Collectors;

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
            Client client = clientRepository.findById(clientId).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Client with id=%d was not found", clientId))
            );

            var printerRequest = PrinterRequest.builder()
                    .templateName(templateName)
                    .formGenerationData(extractClientDataFor001(client))
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

    private FormGenerationData extractClientDataFor001(Client client) {
        var operations = operationRepository.findAllByClientId(client.getId());
        long totalAmount = operations.stream().mapToLong(Operation::getSum).sum();

        return Reference001FormGenerationData.builder()
                .form("REFERENCE_001_BRANCH")
                .dateFrom(LocalDate.now())
                .dateTo(LocalDate.now())
                .lastName(client.getLastName())
                .firstName(client.getFirstName())
                .middleName(client.getPatronymic())
                .agreementNumber(client.getContract().getContractNumber())
                .dateSigningAgreement(client.getContract().getContractDate())
                .accountNumber(client.getAccount().getAccountNumber())
                .accountCurrency(client.getAccount().getAccountCurrency().name())
                .accountCurrencyFullName(client.getAccount().getAccountCurrency().getCurrencyFullName())
                .referenceCode("001")
                .channel(Channel.BRANCH)
                .totalAmount(totalAmount)
                .transactions(operations.stream().map(ModelMapper::convertToTransactionDTO).collect(Collectors.toList()))
                .build();
    }
}
