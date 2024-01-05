package com.ebito.printed_form.util;

import com.ebito.printed_form.api.controller.request.FormGenerationRequest;
import com.ebito.printed_form.template.YargWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haulmont.yarg.formatters.factory.DefaultFormatterFactory;
import com.haulmont.yarg.loaders.factory.DefaultLoaderFactory;
import com.haulmont.yarg.loaders.impl.JsonDataLoader;
import com.haulmont.yarg.reporting.Reporting;
import com.haulmont.yarg.reporting.RunParams;
import com.haulmont.yarg.structure.Report;
import com.haulmont.yarg.structure.ReportBand;
import com.haulmont.yarg.structure.ReportOutputType;
import com.haulmont.yarg.structure.impl.BandBuilder;
import com.haulmont.yarg.structure.impl.ReportBuilder;
import com.haulmont.yarg.structure.impl.ReportTemplateBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Map;

import static com.ebito.printed_form.model.Form.*;

/**
 * Class to manage yarg library.
 * <p>
 * Yarg lib fills out templates
 * by prepared data from an java object
 */
@Component
@RequiredArgsConstructor
public class YargUtils {

    private final ObjectMapper objectMapper;

    private ReportTemplateBuilder createReportTemplateDocxBuilder(byte[] template) {
        return new ReportTemplateBuilder()
                .documentContent(template)
                .documentPath("/")
                .documentName("template.docx")
                .outputType(ReportOutputType.docx);
    }

    private Reporting createReporting() {
        Reporting reporting = new Reporting();
        reporting.setFormatterFactory(new DefaultFormatterFactory());
        DefaultLoaderFactory defaultLoaderFactory = new DefaultLoaderFactory().setJsonDataLoader(new JsonDataLoader());
        reporting.setLoaderFactory(defaultLoaderFactory);

        return reporting;
    }

    /**
     * Method fills out form template by data and generates file byte array
     *
     * @param template - a bytes of template
     * @param form     - a form with data
     * @return byte array of filled docx file
     * @throws IOException throw when method cannot find template in hard disc
     */
    public byte[] generateDocxFromTemplate(byte[] template, FormGenerationRequest form) throws IOException {
        Assert.notNull(template, "template must not be null");
        Assert.notNull(form, "form must not be null");

        /* configure yarg */
        ReportBuilder reportBuilder = new ReportBuilder();
        ReportTemplateBuilder reportTemplateBuilder = createReportTemplateDocxBuilder(template);
        reportBuilder.template(reportTemplateBuilder.build());

        /* make form be json */
        YargWrapper yargWrapper = new YargWrapper(form);
        String jsonForm = objectMapper.writeValueAsString(yargWrapper);

        /* mapping of yarg and form */
        BandBuilder bandBuilder = new BandBuilder();
        ReportBand build = bandBuilder.name("main").query("main", "parameter=0 $.main", "json").build();
        reportBuilder.band(build);

        if (REFERENCE_001_BRANCH.equals(form.getForm())) {
            BandBuilder additionalBandBuilder = new BandBuilder();
            ReportBand additionalBand = additionalBandBuilder.name("transaction").query("transaction", "parameter=0 $.main.transactions", "json").build();
            reportBuilder.band(additionalBand);
        }

        Report report = reportBuilder.build();
        Map<String, Object> mapper = Map.of("0", jsonForm);
        Reporting reporting = createReporting();
        RunParams runParams = new RunParams(report).params(mapper);

        /* get bytes of docx file containing form data  */
        return reporting.runReport(runParams).getContent();
    }
}
