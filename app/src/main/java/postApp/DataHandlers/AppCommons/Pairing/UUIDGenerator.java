package postApp.DataHandlers.AppCommons.Pairing;

/**
 * A UUID generator class
 */

import java.util.UUID;

public class UUIDGenerator {

    private String uuid;

    /**
     * Makes a random UUID
     */
    public UUIDGenerator() {
        UUID id = UUID.randomUUID();
        this.uuid = id.toString().toUpperCase();
    }

    /**
     * @return the UUID
     */
    public String getUUID() {
        return this.uuid;
    }
}
