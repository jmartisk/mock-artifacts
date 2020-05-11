package org.example;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class QuarkusScript implements QuarkusApplication {

    @Override
    public int run(String... args) throws Exception {
        System.out.println("HELLO");
        return 0;
    }

}
