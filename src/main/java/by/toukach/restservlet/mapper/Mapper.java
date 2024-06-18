package by.toukach.restservlet.mapper;


import by.toukach.restservlet.dto.AbstractDTO;
import by.toukach.restservlet.entity.AbstractEntity;

public interface Mapper<D extends AbstractDTO, E extends AbstractEntity> {

    D mapperJsonToDTO(String json);

    String mapperDTOToJson(D dto);


}
