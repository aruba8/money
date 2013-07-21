package money.routes;

import com.mongodb.DB;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;
import money.MoneyController;
import money.logic.AccountsDAO;
import money.logic.CategoriesDAO;
import money.logic.SessionDAO;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.io.Writer;
import java.util.TreeMap;

import static spark.Spark.get;


/**
 * author: erik
 */
public class HomeRoutes {

    private Configuration cfg;
    private SessionDAO sessionDAO;
    private CategoriesDAO categoriesDAO;
    private AccountsDAO accountsDAO;

    public HomeRoutes(final Configuration cfg, final DB moneyDB) {
        this.cfg = cfg;
        this.sessionDAO = new SessionDAO(moneyDB);
        this.categoriesDAO = new CategoriesDAO(moneyDB);
        this.accountsDAO = new AccountsDAO(moneyDB);
    }

    public void initHomePage() throws IOException {
        get(new FreemarkerBasedRoute("/", "home.ftl", cfg) {
            @Override
            protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {

                // get cookie
                String username = sessionDAO.findUserNameBySessionId(MoneyController.getSessionCookie(request));

                if (username == null) {
                    // looks like a bad request. user is not logged in
                    response.redirect("/login");
                } else {
                    SimpleHash root = new SimpleHash();
                    TreeMap categories = categoriesDAO.getCategories(username, false);
                    TreeMap accounts = accountsDAO.getAccounts(username);
                    root.put("username", username);
                    root.put("categories", categories);
                    root.put("accounts", accounts);
                    template.process(root, writer);
                }
            }
        });

    }

}
