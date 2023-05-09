package com.buzzvil.adserver.external.api.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseVo implements Serializable {
    private List<Double> pctr;
}
