package tkaczyk.sebastian.persistence.converter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import tkaczyk.sebastian.persistence.converter.exception.JsonConverterException;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

public abstract class JsonConverter <T>{
    private final String fileName;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Type type = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    public JsonConverter(String fileName) {
        this.fileName = fileName;
    }

    public void toJson(T element){
        try(FileWriter fileWriter = new FileWriter(fileName)){
            gson.toJson(element,fileWriter);
        }catch (Exception e){
            throw new JsonConverterException(e.getMessage());
        }
    }

    public Optional<T> fromJson(){
        try(FileReader fileReader = new FileReader(fileName)){
            return Optional.of(gson.fromJson(fileReader,type));
        }catch (Exception e){
            throw new JsonConverterException(e.getMessage());
        }
    }
}
