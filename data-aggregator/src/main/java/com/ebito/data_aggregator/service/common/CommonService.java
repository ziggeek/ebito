package com.ebito.data_aggregator.service.common;

import com.ebito.data_aggregator.repository.ClientRepository;
import com.ebito.data_aggregator.repository.OperationRepository;
import com.ebito.data_aggregator.model.Client;
import com.ebito.data_aggregator.model.Operation;
import com.ebito.data_aggregator.model.enumeration.Channel;
import com.ebito.data_aggregator.model.enumeration.DocumentType;
import com.ebito.data_aggregator.model.PrintRequest;
import com.ebito.data_aggregator.api.controller.request.PrintData;
import com.ebito.data_aggregator.api.controller.request.Reference001PrintData;
import com.ebito.data_aggregator.api.controller.request.PrintFormGenerationRequest;
import com.ebito.data_aggregator.api.controller.response.PrintedGuids;
import com.ebito.data_aggregator.service.Reference000Service;
import com.ebito.data_aggregator.util.Dictionary;
import com.ebito.data_aggregator.util.ModelMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonService {

    private final ApplicationContext context;
    private final ClientRepository clientRepository;
    private final OperationRepository operationRepository;
    private static final String PATH = "com.ebito.reference.service.reference%s.Reference%sService";

    public PrintedGuids selectPrintForm(final long clientId, final PrintFormGenerationRequest request) {
        Class<?> dogClass;
        try {
            dogClass = Class.forName(String.format(PATH, request.getReferenceCode(), request.getReferenceCode()));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Некорректный тип справки");
        }
        PrintRequest printRequest = this.buildPrintRequest(clientId, request.getReferenceCode());
        PrintedGuids printedGuids = ((Reference000Service) context.getBean(dogClass)).execute(printRequest);
        return printedGuids;
    }

    private PrintRequest buildPrintRequest(final long clientId, final String referenceCode) {
        Assert.notNull(referenceCode, "'referenceCode' не должен быть null");

        Client client = clientRepository.findById(clientId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Client with id=%d was not found", clientId))
        );

        if (Objects.equals("001", referenceCode)) {
            var printData = extractDataForReference001(client);
            printData.setTemplateName(Dictionary.getReferenceName(referenceCode));

            return PrintRequest.builder()
                    .documentType(DocumentType.PDF)
                    .printData(printData)
                    .build();

        } else {
            throw new RuntimeException("Неизвестный источник: " + referenceCode);
        }
    }

    private PrintData extractDataForReference001(Client client) {
        List<Operation> operations = operationRepository.findAllByClientIdOrderByTimestamp(client.getId());

        long totalAmount = operations.stream()
                .mapToLong(Operation::getSum)
                .sum();

        Reference001PrintData printData = Reference001PrintData.builder()
                .form("REFERENCE_001_BRANCH")
                .dateFrom(LocalDate.of(2000, 1, 1))
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
                .transactions(operations.stream()
                        .map(ModelMapper::convertToTransactionDTO)
                        .collect(Collectors.toList()))
                .build();

        return printData;
    }
}
