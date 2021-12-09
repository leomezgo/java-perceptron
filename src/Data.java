public class Data {

    private static final Data dataSingleton = new Data();

    private Data() {
        System.out.println("Inizializzato il singleton");
    }

    public static synchronized Data getInstance() {
        return dataSingleton;
    }

}
