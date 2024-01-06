package com.ebito.document_generator.service;

import com.ebito.document_generator.api.controller.response.FormGenerationResponse;
import com.ebito.document_generator.exception.ConvertToPdfException;
import com.ebito.document_generator.exception.GeneratePrintedFormException;
import com.ebito.document_generator.template.FontProvider;
import com.ebito.document_generator.template.TemplateHandler;
import com.ebito.document_generator.util.FormUtils;
import com.ebito.document_generator.util.YargUtils;
import com.ebito.document_generator.api.controller.request.FormGenerationRequest;
import com.sun.star.util.FileIOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Service
@RequiredArgsConstructor
public class FormGeneratorServiceImpl implements FormGeneratorService {

    private final TemplateHandler templateHandler;
    private final YargUtils yargUtils;
    private final FormUtils formUtils;
    private final FontProvider fontProvider;
    //todo: do client for saving
//    private final FormRepository formRepository;
    private static final String HASH_ALGORITHM_NAME = "SHA3-256";

    @Override
    public FormGenerationResponse generate(final FormGenerationRequest formData) throws IOException {
        Assert.notNull(formData, "formData must not be null");
        final byte[] docxBytes = generateDocxFromTemplate(formData);
        final String pdfFileName = formUtils.buildRandomPdfFileName();

        final byte[] pdfBuffer = convertDocxToPdf(docxBytes);
        final String checkSum = new DigestUtils(HASH_ALGORITHM_NAME).digestAsHex(pdfBuffer);
        Files.write(Paths.get("C:\\Users\\murad\\Downloads\\" + pdfFileName), pdfBuffer);

/*
        //можно сохранить docx-файл
        final String checkSum = new DigestUtils(HASH_ALGORITHM_NAME).digestAsHex(docxBytes);
        Files.write(Paths.get("C:\\Users\\BubnovAN\\Downloads\\" + formUtils.buildRandomPdfFileName()), docxBytes);
*/
        return buildFormGenerationResponse(formData.getForm().getDocumentName(), checkSum, pdfFileName);
    }

    private byte[] convertDocxToPdf(final byte[] docxBytes) {
        try {
            return formUtils.convertDocxToPdf(docxBytes, fontProvider);
        } catch (FileIOException exception) {
            log.error("Can not convert new docx to pdf", exception);
            throw new ConvertToPdfException("Can not convert new docx to pdf! ");
        }
    }

    private byte[] generateDocxFromTemplate(final FormGenerationRequest formData) {
        byte[] template = templateHandler.getTemplateByName("templates/" + formData.getForm().getPath());
        try {
            return yargUtils.generateDocxFromTemplate(template, formData);
        } catch (IOException exception) {
            log.error("Can not generate new docx from template", exception);
            throw new GeneratePrintedFormException("Can not generate new docx from template! " +
                    "From data: " + formData.getForm());
        }
    }

    private FormGenerationResponse buildFormGenerationResponse(final String formName,
                                                               final String checkSum,
                                                               final String pdfFileName) {

//        final String link = formUtils.generateLink(pdfFileName).toString();
        return new FormGenerationResponse("link", formName, checkSum, pdfFileName);
    }
}
