package com.jvprojetos17.sale

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application

//TODO:
// 1 - Implementar swagger; -FEITO!
// 2 - Implementar roles para ter determinados acessos definidos pela regra de negócio; -FEITO!
// 3 - Implementar JWT para autenticação; -FEITO!
// 4 - Implementar filtro para somente usuários autenticados tenham acesso a api; -FEITO!
// 5 - Implementar testes unitários e de integração; -FAZER!

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
