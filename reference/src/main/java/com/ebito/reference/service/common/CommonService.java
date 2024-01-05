package com.ebito.reference.service.common;

import com.ebito.reference.model.Client;
import com.ebito.reference.model.Operation;
import com.ebito.reference.model.enumeration.Channel;
import com.ebito.reference.model.enumeration.DocumentType;
import com.ebito.reference.model.PrintRequest;
import com.ebito.reference.model.request.PrintData;
import com.ebito.reference.model.request.Reference001PrintData;
import com.ebito.reference.model.request.ReferenceGenerationRequest;
import com.ebito.reference.model.response.PrintedGuids;
import com.ebito.reference.repository.ClientRepository;
import com.ebito.reference.repository.OperationRepository;
import com.ebito.reference.service.Reference000Service;
import com.ebito.reference.util.Dictionary;
import com.ebito.reference.util.ModelMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
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

    public PrintedGuids selectReference(final long clientId, final ReferenceGenerationRequest request) {
        Class<?> dogClass;
        try {
            dogClass = Class.forName(String.format(PATH, request.getReferenceCode(), request.getReferenceCode()));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Некорректный тип справки");
        }
        PrintRequest printRequest = this.buildPrintRequest(clientId, request.getReferenceCode(),
                request.getDateFrom(), request.getDateTo());
        PrintedGuids printedGuids = ((Reference000Service) context.getBean(dogClass)).execute(printRequest);
        return printedGuids;
    }

    private PrintRequest buildPrintRequest(final long clientId, final String referenceCode,
                                           @Nullable final LocalDate dateFrom, @Nullable final LocalDate dateTo) {
        Assert.notNull(referenceCode, "'referenceCode' не должен быть null");

        Client client = clientRepository.findById(clientId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Client with id=%d was not found", clientId))
        );

        if (Objects.equals("001", referenceCode)) {
            if (dateFrom == null || dateTo == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "dateFrom and dateTo must not be null when referenceCode is 001");
            }

            PrintData printData = extractDataForReference001(client, dateFrom, dateTo);

            printData.setTemplateName(Dictionary.getReferenceName(referenceCode));
            printData.setForm("REFERENCE_001_BRANCH");
            printData.setReferenceCode(referenceCode);
            printData.setChannel(Channel.BRANCH);

            return PrintRequest.builder()
                    .documentType(DocumentType.PDF)
                    .printData(printData)
                    .build();

        } else {
            throw new RuntimeException("Неизвестный источник: " + referenceCode);
        }
    }

    private Reference001PrintData extractDataForReference001(Client client, LocalDate dateFrom, LocalDate dateTo) {
        List<Operation> operations = operationRepository
                .findAllByClientIdAndDateBetweenOrderByDateAscTimeAsc(client.getId(), dateFrom, dateTo);

        long totalAmount = operations.stream()
                .mapToLong(Operation::getSum)
                .sum();

        return Reference001PrintData.builder()
                .dateFrom(dateFrom)
                .dateTo(dateTo)
                .lastName(client.getLastName())
                .firstName(client.getFirstName())
                .middleName(client.getPatronymic())
                .agreementNumber(client.getContract().getContractNumber())
                .dateSigningAgreement(client.getContract().getContractDate())
                .accountNumber(client.getAccount().getAccountNumber())
                .accountCurrency(client.getAccount().getAccountCurrency().name())
                .accountCurrencyFullName(client.getAccount().getAccountCurrency().getCurrencyFullName())
                .totalAmount(totalAmount)
                .transactions(operations.stream()
                        .map(ModelMapper::convertToTransactionDTO)
                        .collect(Collectors.toList()))
                .build();
    }
}
