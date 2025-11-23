package com.odk.dto;



import com.odk.Entity.Etape;
import com.odk.Entity.Participant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;
import static java.util.stream.Collectors.toList;
import org.mapstruct.Named;




@Mapper(componentModel = "spring")
public interface EtapeMapper {

   EtapeMapper INSTANCE = Mappers.getMapper(EtapeMapper.class);    

    // Convert MissionDTO to entity
   // Etape etapeDTO(EtapeDTO etapeDTO);
//    @Mapping(target = "activite.activitevalidation", ignore = true)
    @Mapping(target = "activite", source = "activite")
    @Mapping(target = "listes", source ="listes",defaultExpression ="java(new ArrayList<>())")
    EtapeDTO toDto(Etape etape);
    
    @Mapping(target = "activite.validations", ignore = true)
    @Mapping(target = "activite", ignore = true) // géré par service
    @Mapping(target = "listes", ignore = true) // géré par service lors de la création
    Etape toEntity(EtapeDTO dto);
    
    List<EtapeDTO> listeEtape(List<Etape> etapes);
// utilitaire si tu veux mapper la liste des participants depuis les listes
//@Named("mapParticipantsFromListes")
//default List<com.odk.dto.ParticipantDTO> mapParticipantsFromListes(List<com.odk.Entity.Liste> listes) {
//return listes.stream()
//.flatMap(l -> l.getParticipants().stream())
//.map(participantMapper::PARTICIPANT_DTO(participant)) // Si besoin d'instance statique, sinon injecte mapper
//.toList();
//}


}





