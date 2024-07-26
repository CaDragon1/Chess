package Handlers;

import Models.UserData;
import spark.*;

public class RegisterHandler implements Route {
    String username;
    String password;
    String email;

    public Object handle(Request request, Response response) throws Exception {
        username = request.queryParams("username");
        password = request.queryParams("password");
        email = request.queryParams("email");

        return
    }

    public RegisterHandler(Request request, Response response) {
        username = request.session().attribute("username");
    }
}
