package com.olgac.examples.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olgac.examples.model.dto.CustomerDto;
import com.olgac.examples.model.request.CreateCustomerRequest;
import com.olgac.examples.model.response.CustomerListResponse;
import com.olgac.examples.service.CustomerService;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    public void shouldCreateNewCustomer() throws Exception {
        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest("Mary Fairy", "+905554443322", "Istanbul");

        mockMvc.perform(post("/customer/create")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(createCustomerRequest)))
                .andExpect(status().isCreated());

        ArgumentCaptor<CreateCustomerRequest> requestDtoArgumentCaptor = ArgumentCaptor.forClass(CreateCustomerRequest.class);
        verify(customerService).save(requestDtoArgumentCaptor.capture());

        CreateCustomerRequest capturedRequestDto = requestDtoArgumentCaptor.getValue();
        assertThat(capturedRequestDto.getName(), equalTo("Mary Fairy"));
        assertThat(capturedRequestDto.getMobile(), equalTo("+905554443322"));
        assertThat(capturedRequestDto.getAddress(), equalTo("Istanbul"));

        verifyNoMoreInteractions(customerService);
    }

    @Test
    public void shouldListAllCustomers() throws Exception {
        CustomerDto customerDto1 = new CustomerDto(145L, "John Brown", "+905554443322", "Ankara");
        CustomerDto customerDto2 = new CustomerDto(234L, "Jack Black", "+902223334455", "Izmir");
        CustomerDto customerDto3 = new CustomerDto(565L, "Jimmy Red", "+907776665544", "Antalya");

        CustomerListResponse customerListResponse = new CustomerListResponse(Arrays.asList(customerDto1, customerDto2, customerDto3));

        when(customerService.list()).thenReturn(customerListResponse);

        mockMvc.perform(get("/customer/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.customers.*", hasSize(3)))
                .andExpect(jsonPath("$.customers[0].id", equalTo(145)))
                .andExpect(jsonPath("$.customers[0].name", equalTo("John Brown")))
                .andExpect(jsonPath("$.customers[0].mobile", equalTo("+905554443322")))
                .andExpect(jsonPath("$.customers[0].address", equalTo("Ankara")))
                .andExpect(jsonPath("$.customers[1].id", equalTo(234)))
                .andExpect(jsonPath("$.customers[1].name", equalTo("Jack Black")))
                .andExpect(jsonPath("$.customers[1].mobile", equalTo("+902223334455")))
                .andExpect(jsonPath("$.customers[1].address", equalTo("Izmir")))
                .andExpect(jsonPath("$.customers[2].id", equalTo(565)))
                .andExpect(jsonPath("$.customers[2].name", equalTo("Jimmy Red")))
                .andExpect(jsonPath("$.customers[2].mobile", equalTo("+907776665544")))
                .andExpect(jsonPath("$.customers[2].address", equalTo("Antalya")));

        verify(customerService).list();

        verifyNoMoreInteractions(customerService);
    }
}