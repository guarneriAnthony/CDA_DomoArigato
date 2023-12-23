package com.lacorp.backend.model.json.hue;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LinkButtonResponse {
    @JsonProperty("/config/linkbutton")
    private boolean linkButton;
}
