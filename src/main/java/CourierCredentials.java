public class CourierCredentials {
    public final String login;
    public final String password;

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierCredentials getCourierCredentials(Courier courier) {
        return new CourierCredentials(courier.login, courier.password);
    }

    public static CourierCredentials getCourierCredentialsIsNotCorrect(Courier courier) {
        final String password = "12121212";
        return new CourierCredentials(courier.login, password);
    }

    public static CourierCredentials getCourierCredentialsPasswordNull(Courier courier) {
        final String password = null;
        return new CourierCredentials(courier.login, password);
    }
}
