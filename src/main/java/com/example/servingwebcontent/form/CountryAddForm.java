package com.example.servingwebcontent.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CountryAddForm {
    
    @NotBlank(message = "mstcountrycd should not be blank")
    private String mstcountrycd;
    private String mstcountrynanme;
    
    public CountryAddForm() {
    }
    
    public CountryAddForm(String mstcountrycd,String mstcountrynanme) {
        this.mstcountrycd = mstcountrycd;
        this.mstcountrynanme = mstcountrynanme;

    }
}
