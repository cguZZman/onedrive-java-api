package com.onedrive.api.internal;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

public class InternalDateDeserializer extends JsonDeserializer<Date> {
	@Override
	public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		try {
			return DateDeserializer.instance.deserialize(jp, ctxt);
		} catch (InvalidFormatException e){
			return javax.xml.bind.DatatypeConverter.parseDateTime(jp.getText()).getTime();
		}
	}
}

