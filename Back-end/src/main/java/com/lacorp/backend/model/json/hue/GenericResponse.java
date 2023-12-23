package com.lacorp.backend.model.json.hue;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenericResponse<T> {
    @JsonProperty("success")
    private T success;
}
