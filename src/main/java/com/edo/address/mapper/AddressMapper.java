package com.edo.address.mapper;

import com.edo.address.model.Address;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface AddressMapper {

    @Select("SELECT * FROM address")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "address1", column = "address1"),
            @Result(property = "address2", column = "address2"),
            @Result(property = "city", column = "city"),
            @Result(property = "country", column = "country"),
            @Result(property = "state", column = "state"),
            @Result(property = "zipCode", column = "zip_code"),
            @Result(property = "userId", column = "user_id")
    })
    List<Address> getAddresses();

    @Insert("INSERT INTO address (address1, address2, city, country, state, zip_code, user_id) VALUES (#{address1}, #{address2}, #{city}, #{country}, #{state}, #{zipCode}, #{userId})")
    Long addAddress(Address address);

    @Select("SELECT * FROM address WHERE id = #{addressId}")
    @ResultMap("getAddresses-void")
    Address getAddress(Long addressId);
}
