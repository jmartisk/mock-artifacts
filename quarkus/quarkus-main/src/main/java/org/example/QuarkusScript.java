package org.example;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class QuarkusScript implements QuarkusApplication {

    // for running the script from inside an IDE
    public static void main(String[] args) {
        Quarkus.run(QuarkusScript.class, args);
    }

    @Override
    public int run(String... args) throws Exception {
        System.out.println("HELLO");
        return 0;
    }

}
