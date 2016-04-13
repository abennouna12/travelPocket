package ig2i.travelPocket;


/**
 * Created by aBennouna on 07/04/2016.
 */
public class Suggestion {
    Float rating;
    String placeName;
    String openClose;
    String picture;
    String phone;
    String mail;
    String latitude;
    String longitude;
    String web;

    Suggestion(){}

    Suggestion (Float rating, String placeName, String openClose, String picture,
                String phone, String mail, String latitude, String longitude, String web) {
        this.rating = rating;
        this.placeName = placeName;
        this.openClose = openClose;
        this.picture = picture;
        this.phone = phone;
        this.mail = mail;
        this.latitude = latitude;
        this.longitude = longitude;
        this.web = web;
    }
}
