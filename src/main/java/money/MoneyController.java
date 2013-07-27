package money;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import freemarker.template.Configuration;
import money.routes.*;
import spark.Request;

import javax.servlet.http.Cookie;
import java.io.IOException;

import static spark.Spark.setPort;

/**
 * author: erik
 */
public class MoneyController {
    private final Configuration cfg;
    private final AccountRoutes accountRoutes;
    private final CategoriesRoutes categoriesRoutes;
    private final HomeRoutes homeRoutes;
    private final CommonRoutes commonRoutes;
    private final IncomeRoutes incomeRoutes;

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            new MoneyController("mongodb://localhost");
        } else {
            new MoneyController(args[0]);
        }
    }

    public MoneyController(String mongoURIString) throws IOException {
        final MongoClient mongoClient = new MongoClient(new MongoClientURI(mongoURIString));
        final DB moneyDB = mongoClient.getDB("money");

        cfg = createFreemarkerConfiguration();
        cfg.setDefaultEncoding("UTF-8");

        accountRoutes = new AccountRoutes(cfg, moneyDB);
        categoriesRoutes = new CategoriesRoutes(cfg, moneyDB);
        homeRoutes = new HomeRoutes(cfg, moneyDB);
        commonRoutes = new CommonRoutes(cfg, moneyDB);
        incomeRoutes = new IncomeRoutes(cfg, moneyDB);

        setPort(8082);

        accountRoutes.initHomePage();
        categoriesRoutes.initCategoriesPage();
        homeRoutes.initHomePage();
        commonRoutes.initCommonPages();
        incomeRoutes.initIncomePage();
    }


    private Configuration createFreemarkerConfiguration() {
        Configuration retVal = new Configuration();
        retVal.setClassForTemplateLoading(MoneyController.class, "/freemarker");
        return retVal;
    }


    // helper function to get session cookie as string
    public static String getSessionCookie(final Request request) {
        if (request.raw().getCookies() == null) {
            return null;
        }
        for (Cookie cookie : request.raw().getCookies()) {
            if (cookie.getName().equals("session")) {
                return cookie.getValue();
            }
        }
        return null;
    }


}
