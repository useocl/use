package org.tzi.use;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "USE API",
                description = "OpenAPI Dokumentation für die USE API",
                contact = @Contact(
                        name = "Hüseyin Akkiran",
                        email = "Hueseyin.Akkiran@haw-hamburg.de"
                ),
                version = "1.0",
                license = @License(
                        name = "GPL-2.0 license",
                        url = "https://github.com/useocl/use/blob/master/COPYING")),
        servers = {
                @Server(
                        description = "Lokale Umgebung",
                        url = "http://localhost:8080"
                )
        }
)
public class OpenApiConfig {
}