package com.olgac.examples.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDto {

    private Long id;

    private String name;

    private String mobile;

    private String address;
}
