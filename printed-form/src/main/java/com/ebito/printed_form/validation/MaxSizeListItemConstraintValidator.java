package com.ebito.printed_form.validation;

import com.ebito.printed_form.validation.constraint.ListItemsSize;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class MaxSizeListItemConstraintValidator implements ConstraintValidator<ListItemsSize, List<String>> {

    private int max;

    @Override
    public void initialize(ListItemsSize constraint) {
        max = constraint.max();
    }

    @Override
    public boolean isValid(List<String> list, ConstraintValidatorContext context) {
        if (list == null) { 
            return true;
        }
        for (String item : list) {
            if (item == null) {
                return false;
            }
            if (item.length() > max || StringUtils.isBlank(item)) {
                return false;
            }
        }

        return true;
    }
}
