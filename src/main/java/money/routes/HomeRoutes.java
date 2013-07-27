package money.routes;

import com.mongodb.DB;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;
import money.MoneyController;
import money.logic.AccountsDAO;
import money.logic.CategoriesDAO;
import money.logic.SessionDAO;
import money.logic.TransactionsDAO;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.io.Writer;
import java.util.TreeMap;

import static spark.Spark.get;
import static spark.Spark.post;


/**
 * author: erik
 */
public class HomeRoutes {

    private Configuration cfg;
    private SessionDAO sessionDAO;
    private CategoriesDAO categoriesDAO;
    private AccountsDAO accountsDAO;
    private TransactionsDAO transactionsDAO;

    public HomeRoutes(final Configuration cfg, final DB moneyDB) {
        this.cfg = cfg;
        this.sessionDAO = new SessionDAO(moneyDB);
        this.categoriesDAO = new CategoriesDAO(moneyDB);
        this.accountsDAO = new AccountsDAO(moneyDB);
        this.transactionsDAO = new TransactionsDAO(moneyDB);
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

        post(new FreemarkerBasedRoute("/", "home.ftl", cfg) {
            @Override
            protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
                String username = sessionDAO.findUserNameBySessionId(MoneyController.getSessionCookie(request));
                String accountId = request.queryParams("account");
                String categoryId = request.queryParams("category");
                String comment = request.queryParams("comment");

                Double sum = Double.parseDouble(request.queryParams("expenses"));

                if (sum == 0 || sum == null) {
                    response.redirect("/");
                }

                transactionsDAO.addTransaction(username, accountId, categoryId, false, sum, comment);

                response.redirect("/");


            }
        });

    }

}
