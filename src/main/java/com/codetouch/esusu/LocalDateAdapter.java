package com.codetouch.esusu;

import java.lang.ProcessBuilder.Redirect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.codetouch.esusu.entity.Esusu;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class LocalDateAdapter implements JsonSerializer<LocalDate>{

	public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE)); // "yyyy-mm-dd"
    }

	@Override
	public JsonElement serialize(LocalDate src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
		// TODO Auto-generated method stub
		return null;
	}
	
	Gson gson = new GsonBuilder()
	        .setPrettyPrinting()
	        .registerTypeAdapter(Esusu.class, new LocalDateAdapter())
	        .create();
}
