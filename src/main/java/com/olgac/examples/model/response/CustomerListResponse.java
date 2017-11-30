package com.olgac.examples.model.response;

import com.olgac.examples.model.dto.CustomerDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CustomerListResponse {
    @Getter @Setter
    private List<CustomerDto> customers = new ArrayList<>();
}
