package com.example.servingwebcontent.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

@Data
public class CountrySearchForm {

    @NotBlank(message = "ID should not be blank")
    private String mstCountryCD;
    
    private String countryCd;

    public CountrySearchForm() {
    }

    public CountrySearchForm(String mstCountryCd) {
        this.mstCountryCD = mstCountryCd;
    }
}