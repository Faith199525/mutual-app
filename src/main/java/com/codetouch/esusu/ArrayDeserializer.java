package com.codetouch.esusu;

import java.util.ArrayList;

import com.codetouch.esusu.dto.EsusuNotificationDTO;
import com.codetouch.esusu.entity.EsusuNotification;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;


public class ArrayDeserializer implements JsonDeserializer {

	Type listType = new TypeToken<ArrayList<EsusuNotificationDTO>>(){}.getType();

	 Gson gson = new GsonBuilder()
	            .registerTypeAdapter(listType, new ArrayDeserializer())
	            .create();
	
	 @Override
	    public ArrayList<EsusuNotificationDTO> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
	            throws JsonParseException {
	        Gson gson = new Gson();
	        return gson.fromJson(((JsonObject) json).get("data"), typeOfT);
	    }
	 
	
}
