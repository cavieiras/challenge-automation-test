import com.github.javafaker.Faker;

import io.restassured.RestAssured;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import org.example.Entities.Booking;
import org.example.Entities.BookingDates;
import org.example.Entities.User;

//import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static io.restassured.config.LogConfig.logConfig;


public class InvalidData_BookingTests {

    public static User user;

    private static RequestSpecification request;

    public static Faker faker;

    public static BookingDates bookingDates;

    public static Booking booking;

    public static Random random;

    @BeforeAll
    public static void Setup(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        faker = new Faker();

        random = new Random();

        user = new User(faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                "acb.example.com.br",
                "12348547wv",
                faker.phoneNumber().toString());

        bookingDates = new BookingDates(LocalDate.of(random.nextInt(2022)+1, 1,21).toString(),
                LocalDate.now().plusDays(random.nextInt(14)+3).toString());

        booking = new Booking(user.getFirstName(), user.getLastName(),
                (float)faker.number().randomDouble(2, 50, 1000),
                true,bookingDates,"");
        RestAssured.filters(new RequestLoggingFilter(),new ResponseLoggingFilter(), new ErrorLoggingFilter());
    }

    @BeforeEach
    void setRequest() {
        request = given().config(RestAssured.config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .contentType(ContentType.JSON)
                .auth().basic("admin", "password123");
    }

    @Test
    public void createBookingWithInvalidData (){


        Assertions.assertNotEquals(user.getPassword().length(),9);
        if(user.getPassword().length() < 9){
            System.out.println("Password is too short.");
        }

        Assertions.assertNotEquals(user.getPassword().length(),9);
        if(user.getPassword().length() > 9){
            System.out.println("Password is too long.");
        }

    }

    @Test
    public void bookingDateInvalid_CheckInBeforeNow(){

        LocalDate invalidDate = LocalDate.parse(bookingDates.getCheckin());

        if(invalidDate.isBefore(LocalDate.now())){
            System.out.println("You can't book for dates prior today.");
        }

    }
    @Test
    public void InvalidEmail_ReturnFalse(){

          Assertions.assertFalse(user.getEmail().contains("@"));
          System.out.println("Invalid e-mail.");

    }


}
