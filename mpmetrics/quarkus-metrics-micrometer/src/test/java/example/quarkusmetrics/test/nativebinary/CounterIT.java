package example.quarkusmetrics.test.nativebinary;

import example.quarkusmetrics.test.vm.CounterTest;
import io.quarkus.test.junit.NativeImageTest;

@NativeImageTest
public class CounterIT extends CounterTest {

    // just inherit tests from CounterTest

}
