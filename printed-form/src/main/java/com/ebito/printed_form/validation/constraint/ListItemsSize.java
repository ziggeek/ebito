package com.ebito.printed_form.validation.constraint;


import com.ebito.printed_form.validation.MaxSizeListItemConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = MaxSizeListItemConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ListItemsSize {

    int max() default 100;
    String message() default "The input list cannot contain items with size more than 100";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
