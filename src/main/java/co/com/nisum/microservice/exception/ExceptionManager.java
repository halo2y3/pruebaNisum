package co.com.nisum.microservice.exception;

public class ExceptionManager extends RuntimeException{

    private static final long serialVersionUID = 1L;
    public static final String ENTITYWITHSAMEKEY = "Se encontro otro registro con el mismo id";
    public static final String ENTITYNOENTITYTOUPDATE = "No se encontro ningun registro con el id ingresado ";

    public ExceptionManager() {
    }

    public ExceptionManager(String exception) {
        super(exception);
    }

    public class NullEntityExcepcion extends ExceptionManager {
        private static final long serialVersionUID = 1L;

        public NullEntityExcepcion(String info) {
            super("La entidad " + info + " no puede ser nula o vacia");
        }
    }

    public class EmptyFieldException extends ExceptionManager {
        private static final long serialVersionUID = 1L;

        public EmptyFieldException(String info) {
            super("El valor del campo: " + info +" no puede ser nula o vacio");
        }
    }

    public class DeletingException extends ExceptionManager {
        private static final long serialVersionUID = 1L;

        public DeletingException(String info) {
            super("La entidad que esta intentando eliminar " +
                "puede tener informacion relacionada, " +
                "comprobar informacion relacionada, \"" + info + "\"");
        }
    }
    
    public class NotFoundException extends ExceptionManager {
        private static final long serialVersionUID = 1L;

        public NotFoundException(String info) {
            super("No se encontro ningun registro en " + info + " con la informacion ingresada");
        }
    }
    
}
