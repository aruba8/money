package money.routes;

import com.mongodb.DB;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;
import money.MoneyController;
import money.logic.AccountsDAO;
import money.logic.ChartsDAO;
import money.logic.SessionDAO;
import org.json.JSONArray;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;


/**
 * author: erik
 */
public class ChartsRoutes {
    private Configuration cfg;
    private SessionDAO sessionDAO;
    private AccountsDAO accountsDAO;
    private ChartsDAO chartsDAO;

    public ChartsRoutes(final Configuration cfg, final DB moneyDB) {
        this.cfg = cfg;
        this.sessionDAO = new SessionDAO(moneyDB);
        this.accountsDAO = new AccountsDAO(moneyDB);
        this.chartsDAO = new ChartsDAO(moneyDB);
    }

    public void initChartPage() throws IOException {
        get(new FreemarkerBasedRoute("/charts", "charts.ftl", cfg) {
            @Override
            protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
                String cookie = MoneyController.getSessionCookie(request);
                String username = sessionDAO.findUserNameBySessionId(cookie);
                SimpleHash root = new SimpleHash();

                if (username == null) {
                    response.redirect("/login");
                } else {
                    Map catmap = chartsDAO.getSumByCategoriesMap(username, false);
                    root.put("username", username);
                    root.put("catmap", catmap);
                    template.process(root, writer);
                }


            }
        });

        post(new Route("/charts") {
            @Override
            public JSONArray handle(Request request, Response response) {
                String cookie = MoneyController.getSessionCookie(request);
                String username = sessionDAO.findUserNameBySessionId(cookie);

                if(request.queryParams("cq") != null){
                    int month = Integer.parseInt(request.queryParams("month"));
                    int year = Integer.parseInt(request.queryParams("year"));
                    return chartsDAO.getSumByCategoriesForMonth(username, month, year, false);
                } else {
                    return chartsDAO.getSumByCategories(username, false);
                }
            }
        });
    }


}
