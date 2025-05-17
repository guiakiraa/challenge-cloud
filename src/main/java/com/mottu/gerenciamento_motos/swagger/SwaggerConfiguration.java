package com.mottu.gerenciamento_motos.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI configurarSwagger() {
        return new OpenAPI().info(new Info()
                .title("Projeto de Gestão de Motos da empresa Mottu")
                .description("Este projeto oferece uma implementação que possibilita "
                        + "a gestão de motos dentro dos pátios da empresa Mottu")
                .summary("A fim de resolver o problema de perda de motos dentro dos pátios da Mottu, "
                        + "desenvolvemos esse projeto com o intuíto de não deixar as motos ficarem em setores "
                        + "em que elas não deveriam estar"
                )
                .version("v1.0.0")
                .license(new License()
                        .url("www.fiap.com.br")
                        .name("Licença - Projeto de Gestão de Motos - v1.0.0"))
                .termsOfService("Termos de Serviço")
        );
    }
}

