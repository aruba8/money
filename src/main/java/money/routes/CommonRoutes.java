package money.routes;

import com.mongodb.DB;
import com.mongodb.DBObject;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;
import money.MoneyController;
import money.logic.SessionDAO;
import money.logic.UserDAO;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spark.Request;
import spark.Response;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;

import static spark.Spark.get;
import static spark.Spark.post;


/**
 * author: erik
 */
public class CommonRoutes {


    private Configuration cfg;
    private SessionDAO sessionDAO;
    private UserDAO userDAO;

    Logger logger = LogManager.getLogger(CommonRoutes.class.getName());


    public CommonRoutes(final Configuration cfg, final DB moneyDB) {
        this.cfg = cfg;
        this.sessionDAO = new SessionDAO(moneyDB);
        this.userDAO = new UserDAO(moneyDB);
    }

    public void initCommonPages() throws IOException {
        get(new FreemarkerBasedRoute("/welcome", "welcome.ftl", cfg) {
            @Override
            protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
                String cookie = MoneyController.getSessionCookie(request);
                String username = sessionDAO.findUserNameBySessionId(cookie);

                if (username == null) {
                    System.out.println("welcome() can't identify the user, redirecting to signup");
                    response.redirect("/signup");

                } else {
                    SimpleHash root = new SimpleHash();

                    root.put("username", username);

                    template.process(root, writer);
                }

            }
        });

        // present the login page
        get(new FreemarkerBasedRoute("/login", "login.ftl", cfg) {
            @Override
            protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
                SimpleHash root = new SimpleHash();

                root.put("username", "");
                root.put("login_error", "");

                template.process(root, writer);
            }
        });

        // process output coming from login form. On success redirect folks to the welcome page
        // on failure, just return an error and let them try again.
        post(new FreemarkerBasedRoute("/login", "login.ftl", cfg) {
            @Override
            protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {

                String username = request.queryParams("username");
                String password = request.queryParams("password");

                logger.trace("Login: User submitted: " + username + "  " + password);

                DBObject user = userDAO.validateLogin(username, password);

                if (user != null) {

                    // valid user, let's log them in
                    String sessionID = sessionDAO.startSession(user.get("username").toString());

                    if (sessionID == null) {
                        response.redirect("/internal_error");
                    } else {
                        // set the cookie for the user's browser
                        response.raw().addCookie(new Cookie("session", sessionID));

                        response.redirect("/welcome");
                    }
                } else {
                    SimpleHash root = new SimpleHash();

                    root.put("username", StringEscapeUtils.escapeHtml4(username));
                    root.put("password", "");
                    root.put("login_error", "Invalid Login");
                    template.process(root, writer);
                }
            }
        });


        // present signup form for blog
        get(new FreemarkerBasedRoute("/signup", "signup.ftl", cfg) {
            @Override
            protected void doHandle(Request request, Response response, Writer writer)
                    throws IOException, TemplateException {

                SimpleHash root = new SimpleHash();

                // initialize values for the form.
                root.put("username", "");
                root.put("password", "");
                root.put("email", "");
                root.put("password_error", "");
                root.put("username_error", "");
                root.put("email_error", "");
                root.put("verify_error", "");

                template.process(root, writer);
            }
        });


        post(new FreemarkerBasedRoute("/signup", "signup.ftl", cfg) {
            @Override
            protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
                String email = request.queryParams("email");
                String username = request.queryParams("username");
                String password = request.queryParams("password");
                String verify = request.queryParams("verify");

                HashMap<String, String> root = new HashMap<String, String>();
                root.put("username", StringEscapeUtils.escapeHtml4(username));
                root.put("email", StringEscapeUtils.escapeHtml4(email));

                if (validateSignup(username, password, verify, email, root)) {
                    // good user
                    System.out.println("Signup: Creating user with: " + username + " " + password);
                    if (!userDAO.addUser(username, password, email)) {
                        // duplicate user
                        root.put("username_error", "Username already in use, Please choose another");
                        template.process(root, writer);
                    } else {
                        // good user, let's start a session
                        String sessionID = sessionDAO.startSession(username);
                        System.out.println("Session ID is" + sessionID);

                        response.raw().addCookie(new Cookie("session", sessionID));
                        response.redirect("/welcome");
                    }
                } else {
                    // bad signup
                    System.out.println("User Registration did not validate");
                    template.process(root, writer);
                }
            }
        });

        // allows the user to logout of the blog
        get(new FreemarkerBasedRoute("/logout", "signup.ftl", cfg) {
            @Override
            protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {

                String sessionID = MoneyController.getSessionCookie(request);

                if (sessionID == null) {
                    // no session to end
                    response.redirect("/login");
                } else {
                    // deletes from session table
                    sessionDAO.endSession(sessionID);

                    // this should delete the cookie
                    Cookie c = getSessionCookieActual(request);
                    c.setMaxAge(0);

                    response.raw().addCookie(c);

                    response.redirect("/login");
                }
            }
        });

        get(new FreemarkerBasedRoute("/internal_error", "internal_error.ftl", cfg) {
            @Override
            protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
                String cookie = MoneyController.getSessionCookie(request);
                String username = sessionDAO.findUserNameBySessionId(cookie);

                SimpleHash root = new SimpleHash();
                root.put("user", username);
                template.process(root, writer);

            }
        });

    }

    // validates that the registration form has been filled out right and username conforms
    public boolean validateSignup(String username, String password, String verify, String email,
                                  HashMap<String, String> errors) {
        String USER_RE = "^[a-zA-Z0-9_-]{3,20}$";
        String PASS_RE = "^.{3,20}$";
        String EMAIL_RE = "^[\\S]+@[\\S]+\\.[\\S]+$";

        errors.put("username_error", "");
        errors.put("password_error", "");
        errors.put("verify_error", "");
        errors.put("email_error", "");

        if (!username.matches(USER_RE)) {
            errors.put("username_error", "invalid username. try just letters and numbers");
            return false;
        }

        if (!password.matches(PASS_RE)) {
            errors.put("password_error", "invalid password.");
            return false;
        }


        if (!password.equals(verify)) {
            errors.put("verify_error", "password must match");
            return false;
        }

        if (!email.equals("")) {
            if (!email.matches(EMAIL_RE)) {
                errors.put("email_error", "Invalid Email Address");
                return false;
            }
        }

        return true;
    }

    // helper function to get session cookie as string
    private Cookie getSessionCookieActual(final Request request) {
        if (request.raw().getCookies() == null) {
            return null;
        }
        for (Cookie cookie : request.raw().getCookies()) {
            if (cookie.getName().equals("session")) {
                return cookie;
            }
        }
        return null;
    }


}
