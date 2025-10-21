package com.atlasbase.atlasbase_core.application.factory;

import com.atlasbase.atlasbase_core.core.model.Metadata;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MetadataFactoryTest {

	@InjectMocks
	private MetadataFactory factory;

	@Test
	void shouldCreateMetadata() {
		Metadata metadata = factory.create();
		assertEquals("SYSTEM", metadata.getCreatedBy());
		assertNotNull(metadata.getCreateDate());
	}

	@Test
	void shouldUpdateMetadata() {
		Metadata metadata = factory.update();
		assertEquals("SYSTEM", metadata.getUpdatedBy());
		assertNotNull(metadata.getUpdateDate());
	}

}