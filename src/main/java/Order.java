import java.util.ArrayList;

public class Order {
    public final String firstName;
    public final String lastName;
    public final String address;
    public final int metroStation;
    public final String phone;
    public final int rentTime;
    public final String deliveryDate;
    public final String comment;
    public final ArrayList<String> color;

    public Order(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment, ArrayList<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }


    public static Order getOrderDataWithTwoColors() {
        final String firstName = "Naruto";
        final String lastName = "Uchiha";
        final String address = "Konoha, 142 apt.";
        final int metroStation = 4;
        final String phone = "+7 800 355 35 35";
        final int rentTime = 5;
        final String deliveryDate = "2022-06-06";
        final String comment = "Saske, come new ArrayList<String>(back to Konoha";
        final ArrayList<String> color = new ArrayList<String>();
        color.add("BLACK");
        color.add("GREY");
        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }

    public static Order getOrderDataColorBlack() {
        final String firstName = "Naruto";
        final String lastName = "Uchiha";
        final String address = "Konoha, 142 apt.";
        final int metroStation = 4;
        final String phone = "+7 800 355 35 35";
        final int rentTime = 5;
        final String deliveryDate = "2022-06-06";
        final String comment = "Saske, come new ArrayList<String>(back to Konoha";
        final ArrayList<String> color = new ArrayList<String>();
        color.add("BLACK");
        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }

    public static Order getOrderDataWithOutColor() {
        final String firstName = "Naruto";
        final String lastName = "Uchiha";
        final String address = "Konoha, 142 apt.";
        final int metroStation = 4;
        final String phone = "+7 800 355 35 35";
        final int rentTime = 5;
        final String deliveryDate = "2022-06-06";
        final String comment = "Saske, come new ArrayList<String>(back to Konoha";
        final ArrayList<String> color = new ArrayList<String>();
        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);

    }
}
