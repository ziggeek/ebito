package com.ebito.printed_form.api.controller.request;

import com.ebito.printed_form.model.Channel;
import com.ebito.printed_form.model.Form;
import com.ebito.printed_form.model.FormGenerationRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.ValidationException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static com.ebito.printed_form.util.DataFormatUtils.*;

/**
 * Данные для заполнения Выписки по начислениям абонента
 */
@Data
@RequiredArgsConstructor
public class Reference001FormGenerationRequest implements FormGenerationRequest {

    @Schema(description = "Дата C",
            example = "2021-03-15")
    @NotNull(message = "must not be null")
    private final LocalDate dateFrom;

    @Schema(description = "Дата ПО",
            example = "2023-03-15")
    @NotNull(message = "must not be null")
    private final LocalDate dateTo;

    @Schema(description = "Фамилия",
            example = "Ivanov")
    @NotBlank(message = "must not be blank")
    @Size(max = 50, message = "must contain less than 50")
    private final String lastName;

    @Schema(description = "Имя",
            example = "Ivan")
    @NotBlank(message = "must not be blank")
    @Size(max = 50, message = "must contain less than 50")
    private final String firstName;

    @Schema(description = "Отчество",
            example = "Ivanovich")
    @Nullable
    @Size(max = 50, message = "must contain less than 50 letters")
    private final String middleName;

    @Schema(description = "Номер договора",
            example = "123456")
    @NotBlank(message = "must not be blank")
    @Size(max = 6, message = "must contain less than 6")
    private final String agreementNumber;

    @Schema(description = "Дата заклюения договора",
            example = "2010-03-15")
    @NotNull(message = "must not be null")
    private final LocalDate dateSigningAgreement;

    @Schema(description = "Номер счета",
            example = "12345678901234567890")
    @NotBlank(message = "must not be blank")
    @Size(max = 25, message = "must contain less than 25")
    private final String accountNumber;

    @Schema(description = "Валюта",
            example = "RUB")
    @NotBlank(message = "must not be blank")
    @Size(max = 40, message = "must contain less than 40")
    private final String accountCurrency;

    @Schema(description = "Валюта",
            example = "Рубль РФ")
    @Nullable
    @Size(max = 50, message = "must contain less than 50 letters")
    private final String accountCurrencyFullName;

    @Schema(description = "Код справки",
            example = "001")
    @NotBlank(message = "must not be blank")
    @Size(max = 3, message = "must contain less than 3")
    private final String referenceCode;

    @NotNull
    @Schema(description = "Канал запроса на генерацию документа",
            allowableValues = {"BRANCH", "ONLINE", "MOBILE"})
    private final Channel channel;

    @Schema(description = "Сумма поступлений за период dateFrom-dateTo",
            example = "1295.01")
    @NotBlank(message = "must not be blank")
    @Size(max = 50, message = "must contain less than 50")
    private final String totalAmount;

    @Schema(description = "Транзакции начисления абонента", example = "", required = true)
    @NotNull(message = "must not be null")
    private final List<Transaction> transactions;

    @Data
    @Builder
    public static class Transaction {

        @Schema(description = "Идентификатор транзакции", example = "12345678", required = true)
        @NotBlank(message = "must not be blank")
        private final String id;

        @Schema(description = "Дата совершения транзакции",
                example = "2010-03-15", required = true)
        @NotNull(message = "must not be null")
        private final LocalDate date;

        @Schema(description = "Время совершения транзакции",
                example = "12:30:00", required = true)
        @NotNull(message = "must not be null")
        private final LocalTime time;

        @Schema(description = "Способ пополнения", example = "СБП", required = true)
        @NotBlank(message = "must not be blank")
        private final String paymentMethod;

        @Schema(description = "Сумма пополнения", example = "1000.01", required = true)
        @NotBlank(message = "must not be blank")
        private final String sum;

        public String getId() {
            return this.id;
        }

        public String getDate() {
            return getDateInRightFormat(this.date);
        }
        public String getTime() {
            return getTimeInRightFormat(this.time);
        }
        public String getPaymentMethod() {
            return this.paymentMethod;
        }
        public String getSum() {
            return this.sum;
        }

    }


    public String getDateFrom() {
        return getDateInRightFormat(this.dateFrom);
    }

    public String getDateTo() {
        return getDateInRightFormat(this.dateTo);
    }

    public String getClientFIO() {
        return getFullName(this.lastName, this.firstName, this.middleName);
    }

    public String getAgreementNumber() {
        return this.agreementNumber;
    }
    public String getReferenceCode() {
        return this.referenceCode;
    }

    public String getDateSigningAgreement() {
        return getDateInRightFormat(this.dateSigningAgreement);
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public String getAccountCurrency() {
        return this.accountCurrency;
    }

    public String getAccountCurrencyFullName() {
        return this.accountCurrencyFullName;
    }

    public String getChannel() {
        return this.channel.getChannelName();
    }

    public String getTotalAmount() {
        return this.totalAmount;
    }

    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    @Override
    public Form getForm() {
        switch (channel) {
            case BRANCH:
                return Form.REFERENCE_001_BRANCH;
            case ONLINE:
                return Form.REFERENCE_001_ONLINE;
            case MOBILE:
                return Form.REFERENCE_001_MOBILE;
            default:
                throw new ValidationException("Not found appropriate printed form for channel: " + channel.name());
        }
    }
}