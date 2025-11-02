package com.atlasbase.atlasbase_core.application.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class ResponseDto {

	private Map<String, Object> data;

	private Map<String, Object> error;

}
