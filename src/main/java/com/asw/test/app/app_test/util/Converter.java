package com.asw.test.app.app_test.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class Converter {

	@Autowired
	private ModelMapper mapper;

	@SuppressWarnings("unchecked")
	public <T> T convert(Object origen, T destino) {
		return ((T) mapper.map(origen, destino.getClass()));
	}

	public <O, T> List<T> convert(List<O> origen, T destino) {
		if (CollectionUtils.isEmpty(origen))
			return Collections.emptyList();
		List<T> retorno = new ArrayList<>();
		origen.stream().forEach(x -> retorno.add( convert(x, destino)));
		return retorno;
	}
}
