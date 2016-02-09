package com.edo.address.dao;

import com.edo.address.mapper.AddressMapper;
import com.edo.address.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressDAO {

    @Autowired private AddressMapper addressMapper;

    public List<Address> getAddresses() {
        return addressMapper.getAddresses();
    }

    public Address getAddress(Long addressId) {
        return addressMapper.getAddress(addressId);
    }

    public Long addAddress(Address address) {
        return addressMapper.addAddress(address);
    }
}
