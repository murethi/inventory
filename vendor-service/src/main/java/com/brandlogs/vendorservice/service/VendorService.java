package com.brandlogs.vendorservice.service;

import com.brandlogs.vendorservice.component.DtoToEntityMapper;
import com.brandlogs.vendorservice.component.EntityToDtoMapper;
import com.brandlogs.vendorservice.dto.VendorRequest;
import com.brandlogs.vendorservice.dto.VendorResponse;
import com.brandlogs.vendorservice.entity.Vendor;
import com.brandlogs.vendorservice.exception.ModelNotFoundException;
import com.brandlogs.vendorservice.repository.VendorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class VendorService {
    private final VendorRepository vendorRepository;
    private final DtoToEntityMapper dtoToEntity;
    private final EntityToDtoMapper entityToDto;


    public VendorService(VendorRepository vendorRepository, DtoToEntityMapper dtoToEntity, EntityToDtoMapper entityToDto) {
        this.vendorRepository = vendorRepository;
        this.dtoToEntity = dtoToEntity;
        this.entityToDto = entityToDto;
    }



    public Page<VendorResponse> findAllVendors(){
        PageRequest pageRequest = PageRequest.of(0,50);
        return vendorRepository.findAll(pageRequest)
                .map(entityToDto::mapToVendorResponse);
    }



    public void createVendor(VendorRequest vendorRequest){
        Vendor vendor = dtoToEntity.mapToVendor(vendorRequest);
        vendorRepository.save(vendor);
    }



    public VendorResponse viewVendor(long id){
        return vendorRepository.findById(id)
                .map(entityToDto::mapToVendorResponse)
                .orElseThrow(()->new ModelNotFoundException("Vendor with id "+id+"not found"));
    }

    public void updateVendor(VendorRequest vendorRequest, long id) {
       vendorRepository.findById(id)
               .ifPresentOrElse(vendor -> {
                    vendor.setName(vendorRequest.name());
                    vendor.setEmail(vendorRequest.email());
                    vendor.setContact(vendorRequest.contact());
                    vendor.setLocation(vendorRequest.location());
                    vendorRepository.save(vendor);
                },()->{
                   throw new ModelNotFoundException("Vendor with id "+id+"not found");
               });
    }
}
