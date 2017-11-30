package com.olgac.examples.model.entitiy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity<Long> {

    private String name;

    private String mobile;

    private String address;

}
