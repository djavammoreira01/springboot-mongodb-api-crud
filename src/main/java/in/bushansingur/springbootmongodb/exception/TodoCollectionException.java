package in.bushansingur.springbootmongodb.exception;

import java.io.Serial;

public class TodoCollectionException extends Exception{


    @Serial
    private static final long serialVersionUID = -6101569566769078489L;


    public TodoCollectionException(String message) {
        super(message);
    }

    public static String NotFoundException(String id) {
        return "Todo with "+id+" not found!";
    }

    public static String TodoAlreadyExists() {
        return "Todo with given name already exists";
    }


}
