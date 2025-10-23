package com.atlasbase.atlasbase_core.application.factory;

import com.atlasbase.atlasbase_core.core.model.Metadata;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class MetadataFactory {

	public Metadata create() {
		Metadata metadata = new Metadata();
		metadata.setCreatedBy("SYSTEM");
		metadata.setCreateDate(Instant.now());
		return metadata;
	}

	public Metadata update() {
		Metadata metadata = new Metadata();
		metadata.setUpdatedBy("SYSTEM");
		metadata.setUpdateDate(Instant.now());
		return metadata;
	}

}
