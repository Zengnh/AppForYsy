//package com.appforysy.service;
//
//import java.nio.charset.Charset;
//
//public class CustomGsonConverterFactory extends Converter.Factory{
//
//private  final MediaType MEDIA_TYPE=MediaType.parse("application/json; charset=UTF-8");
//
//private  final Char setUTF_8= Charset.forName("UTF-8");
//
//private final Gson gson;
//
//        private CustomGsonConverterFactory(Gson gson){
//
//        if(gson==null)throw new NullPointerException("gson == null");
//
//        this.gson=gson;
//
//        }
//
//public static CustomGsonConverterFactory create(){
//
//        return create(new Gson());
//
//        }
//
//public static CustomGsonConverterFactory create(Gson gson){
//
//        return new CustomGsonConverterFactory(gson);
//
//        }
//
//@Override
//
//public ConverterresponseBodyConverter(Type type,Annotation[]annotations,Retrofit retrofit){
//
//        TypeAdapter adapter=gson.getAdapter(TypeToken.get(type));
//
//        return newCustomGsonResponseBodyConverter<>(gson,adapter);
//
//        }
//
//@Override
//
//publicConverterrequestBodyConverter(Type type,Annotation[]parameterAnnotations,Annotation[]methodAnnotations,Retrofit retrofit){
//
//        TypeAdapter adapter=gson.getAdapter(TypeToken.get(type));
//
//        return newCustomGsonRequestBodyConverter<>(gson,adapter);
//
//        }
//
//final classCustomGsonRequestBodyConverterimplementsConverter{
//
//private finalGsongson;
//
//private finalTypeAdapteradapter;
//
//        CustomGsonRequestBodyConverter(Gson gson,TypeAdapter adapter){
//
//        this.gson=gson;
//
//        this.adapter=adapter;
//
//        }
//
//@Override
//
//publicRequestBodyconvert(Tvalue)throwsIOException{
//
//        Buffer buffer=newBuffer();
//
//        Writer writer=newOutputStreamWriter(buffer.outputStream(),UTF_8);
//
//        JsonWriter jsonWriter=gson.newJsonWriter(writer);
//
//        adapter.write(jsonWriter,value);
//
//        jsonWriter.close();
//
//        returnRequestBody.create(MEDIA_TYPE,buffer.readByteString());
//
//        }
//
//        }
//
//final classCustomGsonResponseBodyConverterimplementsConverter{
//
//private finalGsongson;
//
//private finalTypeAdapteradapter;
//
//        CustomGsonResponseBodyConverter(Gson gson,TypeAdapter adapter){
//
//        this.gson=gson;
//
//        this.adapter=adapter;
//
//        }
//
//@Override
//
//publicTconvert(ResponseBody value)throwsIOException{
//
//        String response=value.string();
//
//        HttpStatus httpStatus=gson.fromJson(response,HttpStatus.class);
//
//        if(httpStatus.isCodeInvalid()){
//
//        value.close();
//
//        throw newApiException(httpStatus.getCode(),httpStatus.getMessage());
//
//        }
//
//        MediaType contentType=value.contentType();
//
//        Charset charset=contentType!=null?contentType.charset(UTF_8):UTF_8;
//
//        InputStream inputStream=newByteArrayInputStream(response.getBytes());
//
//        Reader reader=newInputStreamReader(inputStream,charset);
//
//        JsonReader jsonReader=gson.newJsonReader(reader);
//
//        try{
//
//        returnadapter.read(jsonReader);
//
//        }finally{
//
//        value.close();
//
//        }
//
//        }
//
//        }
//
//        }
