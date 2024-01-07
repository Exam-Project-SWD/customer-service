package com.example.customerservice.service;

import com.example.customerservice.mapper.AddressMapper;
import com.example.customerservice.model.dto.AddressDTO;
import com.example.customerservice.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public AddressDTO getAddressById(int id) {
        return addressRepository.findById(id).map(addressMapper::fromEntity).orElse(null);
    }
}
