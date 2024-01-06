package com.ebito.document_generator.service;


import com.ebito.document_generator.api.controller.response.FormGenerationResponse;
import com.ebito.document_generator.exception.ConvertToPdfException;
import com.ebito.document_generator.exception.GeneratePrintedFormException;
import com.ebito.document_generator.api.controller.request.FormGenerationRequest;

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
