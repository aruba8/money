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
import org.apache.commons.lang3.StringEscapeUtils;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
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
                    TreeMap<String, String> accounts = accountsDAO.getAccounts(username);

                    Map<String, Double> accountsMapWithSum = new HashMap<String, Double>();

                    for (String key : accounts.keySet()) {
                        accountsMapWithSum.put(accounts.get(key), transactionsDAO.getSumOfAccount(key));
                    }


                    root.put("username", username);
                    root.put("categories", categories);
                    root.put("accounts", accounts);
                    root.put("accountsWithSum", accountsMapWithSum);
                    template.process(root, writer);
                }
            }
        });

        post(new FreemarkerBasedRoute("/", "home.ftl", cfg) {
            @Override
            protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
                String username = sessionDAO.findUserNameBySessionId(MoneyController.getSessionCookie(request));
                String accountId = StringEscapeUtils.unescapeHtml4(request.queryParams("account"));
                String categoryId = StringEscapeUtils.unescapeHtml4(request.queryParams("category"));
                String comment = StringEscapeUtils.unescapeHtml4(request.queryParams("comment"));
                String expenses = request.queryParams("expenses");

                if (expenses == null || expenses.equals("")) {
                    response.redirect("/");
                }

                Double sum = -Double.parseDouble(expenses);

                transactionsDAO.addTransaction(username, accountId, categoryId, false, sum, comment);

                response.redirect("/");
            }
        });

    }

}
