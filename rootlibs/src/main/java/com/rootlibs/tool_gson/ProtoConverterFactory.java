package com.rootlibs.tool_gson;//package com.jsyllibs.tool_gson;
//
//import android.text.Annotation;
//
//import org.xml.sax.Parser;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Type;
//
//import okhttp3.RequestBody;
//import okhttp3.ResponseBody;
//import retrofit2.Converter;
//import retrofit2.Retrofit;
//
//public final class ProtoConverterFactory extends Converter.Factory {
//  public static ProtoConverterFactory create() {
//    return new ProtoConverterFactory();
//  }
//
//  @Override
//  public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
//                                                          Retrofit retrofit) {
//      //进行条件判断，如果传进来的Type不是class，则匹配失败
//    if (!(type instanceof Class<?>)) {
//      return null;
//    }
//      //进行条件判断，如果传进来的Type不是MessageLite的实现类，则也匹配失败
//    Class<?> c = (Class<?>) type;
//    if (!MessageLite.class.isAssignableFrom(c)) {
//      return null;
//    }
//
//    Parser<MessageLite> parser;
//    try {
//      Field field = c.getDeclaredField("PARSER");
//      //noinspection unchecked
//      parser = (Parser<MessageLite>) field.get(null);
//    } catch (NoSuchFieldException | IllegalAccessException e) {
//      throw new IllegalArgumentException(
//          "Found a protobuf message but " + c.getName() + " had no PARSER field.");
//    }
//    //返回一个实现了Converter的类，
//    return new ProtoResponseBodyConverter<>(parser);
//  }
//
//  @Override
//  public Converter<?, RequestBody> requestBodyConverter(Type type,
//                                                        Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
//      //进行条件判断，如果传进来的Type不是class，则匹配失败
//    if (!(type instanceof Class<?>)) {
//      return null;
//    }
//    //进行条件判断，如果传进来的Type不是MessageLite的实现类，则也匹配失败
//    if (!MessageLite.class.isAssignableFrom((Class<?>) type)) {
//      return null;
//    }
//    return new ProtoRequestBodyConverter<>();
//  }
//  final class ProtoRequestBodyConverter<T extends MessageLite> implements Converter<T, RequestBody> {
//    private static final MediaType MEDIA_TYPE = MediaType.parse("application/x-protobuf");
//
//    @Override public RequestBody convert(T value) throws IOException {
//      byte[] bytes = value.toByteArray();
//      return RequestBody.create(MEDIA_TYPE, bytes);
//    }
//  }
//  final class ProtoResponseBodyConverter<T extends MessageLite>
//          implements Converter<ResponseBody, T> {
//    private final Parser<T> parser;
//
//    ProtoResponseBodyConverter(Parser<T> parser) {
//      this.parser = parser;
//    }
//
//    @Override public T convert(ResponseBody value) throws IOException {
//      try {
//        return parser.parseFrom(value.byteStream());
//      } catch (InvalidProtocolBufferException e) {
//        throw new RuntimeException(e); // Despite extending IOException, this is data mismatch.
//      } finally {
//        value.close();
//      }
//    }
//  }
//
//}