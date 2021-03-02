package com.yolt.yts.sdk.service.usersite.model.login;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SelectField extends FormField {
    private List<SelectOptionValue> selectOptionValues = new ArrayList<>();
    private Integer length;
    private Integer maxLength;
    private SelectOptionValue defaultValue;
}
