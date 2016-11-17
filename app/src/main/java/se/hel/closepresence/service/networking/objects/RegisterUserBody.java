package se.hel.closepresence.service.networking.objects;

/**
 * Created by k on 2016-08-11.
 */

import java.security.Timestamp;

public class RegisterUserBody {
    private String api_key = "28742sk238sdkAdhfue243jdfhvnsa1923347"; //Hard coded
    private String first_name;

    public String getLast_name() {
        return last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    private String last_name;

    public RegisterUserBody(String first_name, String last_name) {
        this.first_name = first_name;
        this.last_name = last_name;
    }
}
