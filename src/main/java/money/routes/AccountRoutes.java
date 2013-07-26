package money.routes;

import com.mongodb.DB;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;
import money.MoneyController;
import money.logic.AccountsDAO;
import money.logic.SessionDAO;
import org.apache.commons.lang3.StringEscapeUtils;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * author: erik
 */
public class AccountRoutes {
    private Configuration cfg;
    private SessionDAO sessionDAO;
    private AccountsDAO accountsDAO;

    public AccountRoutes(final Configuration cfg, final DB moneyDB) {
        this.cfg = cfg;
        this.sessionDAO = new SessionDAO(moneyDB);
        this.accountsDAO = new AccountsDAO(moneyDB);
    }


    public void initHomePage() throws IOException {
        get(new FreemarkerBasedRoute("/accounts", "accounts.ftl", cfg) {
            @Override
            protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
                String cookie = MoneyController.getSessionCookie(request);
                String username = sessionDAO.findUserNameBySessionId(cookie);
                SimpleHash root = new SimpleHash();
                TreeMap accounts = accountsDAO.getAccounts(username);

                if (username == null) {
                    // looks like a bad request. user is not logged in
                    response.redirect("/login");
                } else {
                    root.put("user", username);
                    root.put("accounts", accounts);
                    root.put("accountsSize", accounts.size());

                    template.process(root, writer);
                }

            }
        });

        post(new FreemarkerBasedRoute("/accounts", "accounts.ftl", cfg) {
            @Override
            protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
                String cookie = MoneyController.getSessionCookie(request);
                String username = sessionDAO.findUserNameBySessionId(cookie);
                String accountName = StringEscapeUtils.escapeHtml4(request.queryParams("accountName"));
                Set<String> params = request.queryParams();
                Set<String> accountsToDelete = new HashSet<String>(params);
                accountsToDelete.remove("add");

                if (request.queryParams("add").equals("true")) {
                    accountsDAO.addAccount(username, accountName);
                } else if (request.queryParams("add").equals("false")) {
                    accountsDAO.deleteAccounts(username, accountsToDelete);
                    System.out.println(accountsToDelete);
                }
                response.redirect("/accounts");
            }
        });

    }


}
