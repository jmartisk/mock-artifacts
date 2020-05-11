package org.example

import io.quarkus.runtime.annotations.QuarkusMain

@QuarkusMain
class TheScript : io.quarkus.runtime.QuarkusApplication {

    override fun run(vararg args: String?): Int {
        println("hello")
        return 0
    }

}