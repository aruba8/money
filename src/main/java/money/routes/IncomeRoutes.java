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
import java.util.TreeMap;

import static spark.Spark.get;
import static spark.Spark.post;


/**
 * author: erik
 */
public class IncomeRoutes {
    private Configuration cfg;
    private SessionDAO sessionDAO;
    private TransactionsDAO transactionsDAO;
    private CategoriesDAO categoriesDAO;
    private AccountsDAO accountsDAO;

    public IncomeRoutes(final Configuration cfg, final DB moneyDB) {
        this.cfg = cfg;
        this.sessionDAO = new SessionDAO(moneyDB);
        this.transactionsDAO = new TransactionsDAO(moneyDB);
        this.categoriesDAO = new CategoriesDAO(moneyDB);
        this.accountsDAO = new AccountsDAO(moneyDB);
    }

    public void initIncomePage() throws IOException {
        get(new FreemarkerBasedRoute("/income", "income.ftl", cfg) {
            @Override
            protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
                String cookie = MoneyController.getSessionCookie(request);
                String username = sessionDAO.findUserNameBySessionId(cookie);
                if (username == null) {
                    // looks like a bad request. user is not logged in
                    response.redirect("/login");
                } else {
                    SimpleHash root = new SimpleHash();
                    TreeMap categories = categoriesDAO.getCategories(username, true);
                    TreeMap<String, String> accounts = accountsDAO.getAccounts(username);


                    root.put("user", username);
                    root.put("accounts", accounts);
                    root.put("categories", categories);

                    template.process(root, writer);
                }
            }
        });

        post(new FreemarkerBasedRoute("/income", "income.ftl", cfg) {
            @Override
            protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
                String cookie = MoneyController.getSessionCookie(request);
                String username = sessionDAO.findUserNameBySessionId(cookie);

                String ssum = request.queryParams("sum");
                String categoryId = request.queryParams("category");
                String accountId = request.queryParams("account");
                String comment = StringEscapeUtils.unescapeHtml4(request.queryParams("comment"));

                if (ssum == null || ssum.equals("")) {
                    response.redirect("/income");
                }

                Double sum = Double.parseDouble(ssum);

                transactionsDAO.addTransaction(username, accountId, categoryId, true, sum, comment);

                response.redirect("/income");
            }
        });

    }


}
