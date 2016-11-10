package postApp.DataHandlers.Network;

/**
 * Created by adinH on 2016-11-10.
 */


import java.util.UUID;

public class UUIDGenerator {

    private String uuid;


    public UUIDGenerator()
    {
        UUID id = UUID.randomUUID();
        this.uuid = id.toString().toUpperCase();
    }

    public String getUUID()
    {
        return this.uuid;
    }
}
