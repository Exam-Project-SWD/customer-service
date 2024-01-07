package com.example.customerservice.mapper;

import com.example.customerservice.model.dto.AddressDTO;
import com.example.customerservice.model.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper implements Mapper<Address, AddressDTO> {
    @Override
    public Address fromDto(AddressDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Address(
                dto.id(),
                dto.street(),
                dto.number(),
                dto.postalCode()
        );
    }

    @Override
    public AddressDTO fromEntity(Address entity) {
        if (entity == null) {
            return null;
        }

        return new AddressDTO(
                entity.getId(),
                entity.getStreet(),
                entity.getNumber(),
                entity.getPostalCode()
        );
    }
}
