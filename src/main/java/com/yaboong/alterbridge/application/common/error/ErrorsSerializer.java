package com.yaboong.alterbridge.application.common.error;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.io.IOException;
import java.util.Objects;

/**
 * Created by yaboong on 2019-09-11
 *
 * Validation 결과로 받은 Errors 오브젝트는 기본적으로 Serialization (JSON 변환) 이 불가능하므로,
 * 직접 Serialization 방법을 정의해주고, @JsonComponent 애노테이션을 사용하여 ObjectMapper 에 직접 등록해준다.
 */
@Slf4j
@JsonComponent
public class ErrorsSerializer extends JsonSerializer<Errors> {

    /**
     * Stream 사용시 lambda 함수 내에서 예외처리 해주어야 하므로 사용하지 않음
     */
    @Override
    public void serialize(Errors errors, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();

        errors.getFieldErrors().forEach(fieldError -> {
            try {
                gen.writeStartObject();
                gen.writeStringField("field", fieldError.getField());
                gen.writeStringField("code", fieldError.getCode());
                gen.writeStringField("defaultMessage", fieldError.getDefaultMessage());
                Object rejectedValue = fieldError.getRejectedValue();
                if (Objects.nonNull(rejectedValue)) {
                    gen.writeStringField("rejectedValue", rejectedValue.toString());
                }
                gen.writeEndObject();
            }
            catch (IOException ioe) {
                log.error(ioe.getMessage(), ioe);
            }

        });

        errors.getGlobalErrors().forEach(error -> {
            try {
                gen.writeStartObject();
                gen.writeStringField("objectName", error.getObjectName());
                gen.writeStringField("code", error.getCode());
                gen.writeStringField("defaultMessage", error.getDefaultMessage());
                gen.writeEndObject();
            }
            catch (IOException ioe) {
                log.error(ioe.getMessage(), ioe);
            }
        });

        gen.writeEndArray();
    }
}
