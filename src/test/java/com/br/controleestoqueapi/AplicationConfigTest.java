package com.br.controleestoqueapi;

import de.cronn.testutils.h2.H2Util;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
@Import(H2Util.class)
public class AplicationConfigTest {
}
