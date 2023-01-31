package com.jvprojetos17.sale

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application

//TODO:
// 1 - Implementar swagger;
// 2 - Implementar roles para ter determinados acessos definidos pela regra de negócio;
// 3 - Implementar keycloak para autenticação;
// 4 - Implementar filtro para somente usuários autenticados pelo keycloak tenham acesso a api;

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
