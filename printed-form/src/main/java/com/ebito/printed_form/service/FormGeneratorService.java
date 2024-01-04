package com.ebito.printed_form.service;


import com.ebito.printed_form.model.FormGenerationRequest;
import com.ebito.printed_form.api.controller.response.FormGenerationResponse;
import com.ebito.printed_form.exception.ConvertToPdfException;
import com.ebito.printed_form.exception.GeneratePrintedFormException;

import java.io.IOException;

/**
 * Interface generating a form
 */
public interface FormGeneratorService {

    /**
     * Method to generate different types of form
     *
     * @param formData external data which can be used as a base for form creating. Can be of different types
     * @return respone with name and link to get form from the service
     * @throws GeneratePrintedFormException when error in template
     * @throws ConvertToPdfException when fails convert to pdf
     */
    FormGenerationResponse generate(FormGenerationRequest formData) throws IOException;
}
