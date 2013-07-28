package money.routes;

import com.mongodb.DB;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;
import money.MoneyController;
import money.logic.AccountsDAO;
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
public class TransfersRoutes {
    private Configuration cfg;
    private SessionDAO sessionDAO;
    private TransactionsDAO transactionsDAO;
    private AccountsDAO accountsDAO;

    public TransfersRoutes(final Configuration cfg, final DB moneyDB) {
        this.cfg = cfg;

        this.sessionDAO = new SessionDAO(moneyDB);
        this.transactionsDAO = new TransactionsDAO(moneyDB);
        this.accountsDAO = new AccountsDAO(moneyDB);

    }

    public void initTransfersPages() throws IOException {
        get(new FreemarkerBasedRoute("/transfers", "transfers.ftl", cfg) {
            @Override
            protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
                // get cookie
                String username = sessionDAO.findUserNameBySessionId(MoneyController.getSessionCookie(request));

                if (username == null) {
                    // looks like a bad request. user is not logged in
                    response.redirect("/login");
                } else {
                    SimpleHash root = new SimpleHash();
                    TreeMap<String, String> accounts = accountsDAO.getAccounts(username);

                    Map<String, Double> accountsMapWithSum = new HashMap<String, Double>();

                    for (String key : accounts.keySet()) {
                        accountsMapWithSum.put(accounts.get(key), transactionsDAO.getSumOfAccount(key));
                    }


                    root.put("user", username);
                    root.put("accounts", accounts);
                    root.put("accountsWithSum", accountsMapWithSum);

                    template.process(root, writer);
                }


            }
        });

        post(new FreemarkerBasedRoute("/transfers", "transfers.ftl", cfg) {
            @Override
            protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {

                String username = sessionDAO.findUserNameBySessionId(MoneyController.getSessionCookie(request));
                String accountIdFrom = StringEscapeUtils.unescapeHtml4(request.queryParams("accountFrom"));
                String accountIdIn = StringEscapeUtils.unescapeHtml4(request.queryParams("accountIn"));

                String sumString = request.queryParams("sum");

                if (sumString == null || sumString.equals("")) {
                    SimpleHash root = new SimpleHash();
                    TreeMap accounts = accountsDAO.getAccounts(username);
                    root.put("user", username);
                    root.put("accounts", accounts);
                    root.put("emptySumError", "Сумма перевода не может быть пустой");
                    template.process(root, writer);

                } else {
                    Double sum = Double.parseDouble(sumString);

                    transactionsDAO.addTransaction(username, accountIdFrom, "transfer", false, -sum, "transfer from " + accountIdFrom + " to " + accountIdIn);
                    transactionsDAO.addTransaction(username, accountIdIn, "transfer", true, sum, "transfer from " + accountIdFrom + " to " + accountIdIn);
                    response.redirect("/transfers");
                }


            }
        });
    }


}
