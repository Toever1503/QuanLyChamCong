package com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String version = "1.0";
    private String message;
    private String result;
    private Object data;

    public ResponseDto(String message, String result, Object data) {
        this.message = message;
        this.result = result;
        this.data = data;
    }

    public static ResponseDto of(Object data, String prefix) {
        return new ResponseDto(data != null ? prefix.concat(" successfully!") : prefix.concat(" failed!"), data != null ? "SUCCESS" : "ERROR", data);
    }

}
