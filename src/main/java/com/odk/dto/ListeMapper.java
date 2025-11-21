package com.odk.dto;


import com.odk.Entity.Liste;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


import java.util.List;

@Mapper(componentModel = "spring")
public interface ListeMapper {

//    ListeMapper INSTANCE = Mappers.getMapper(ListeMapper.class);


    /**
     *
     * @param liste
     * @return
     */

    ListeDTO toDto(Liste liste);
    Liste toEntity(ListeDTO ldto);

    List<ListeDTO> liste(List<Liste> liste);
    
}
