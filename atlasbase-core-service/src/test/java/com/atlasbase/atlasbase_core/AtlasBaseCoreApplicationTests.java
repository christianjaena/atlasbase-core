package com.atlasbase.atlasbase_core;

import me.escoffier.loom.loomunit.LoomUnitExtension;
import me.escoffier.loom.loomunit.ShouldNotPin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.devtools.restart.enabled=false")
@ExtendWith(LoomUnitExtension.class)
class AtlasBaseCoreApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	@ShouldNotPin
	void shouldNotPin() throws Exception {
		AtlasBaseCoreApplication.main(new String[] {});
	}

}
