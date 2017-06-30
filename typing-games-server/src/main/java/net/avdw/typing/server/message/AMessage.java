package net.avdw.typing.server.message;

public class AMessage {
    public String type;
    public Object message;
    
    public AMessage(Object message) {
        this.type = message.getClass().getSimpleName();
        this.message = message;
    }
    
    @Override
    public String toString() {
        return "AMessage{"+type+"}";
    }
}
