package experiment;

/**
 * @author Jan Martiska
 */
public interface DummyConnection extends AutoCloseable {

    void setValid(boolean invalid);

    boolean isValid();

    boolean isClosed();

}
