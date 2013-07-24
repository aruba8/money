package money.routes;

import com.mongodb.DB;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;
import money.MoneyController;
import money.logic.CategoriesDAO;
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
public class CategoriesRoutes {
    private Configuration cfg;
    private SessionDAO sessionDAO;
    private CategoriesDAO categoriesDAO;

    public CategoriesRoutes(final Configuration cfg, final DB moneyDB) {
        this.cfg = cfg;
        this.sessionDAO = new SessionDAO(moneyDB);
        this.categoriesDAO = new CategoriesDAO(moneyDB);
    }

    public void initCategoriesPage() throws IOException {
        get(new FreemarkerBasedRoute("/categories", "categories.ftl", cfg) {
            @Override
            protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
                String cookie = MoneyController.getSessionCookie(request);
                String username = sessionDAO.findUserNameBySessionId(cookie);
                SimpleHash root = new SimpleHash();

                TreeMap incSet = categoriesDAO.getCategories(username, true);
                TreeMap expSet = categoriesDAO.getCategories(username, false);
                if (username == null) {
                    // looks like a bad request. user is not logged in
                    response.redirect("/login");
                } else {

                    root.put("user", username);
                    root.put("iCategories", incSet);
                    root.put("expCategories", expSet);
                    root.put("iCategoriesSize", incSet.size());
                    root.put("expCategoriesSize", expSet.size());

                    template.process(root, writer);
                }
            }
        });

        post(new FreemarkerBasedRoute("/categories", "categories.ftl", cfg) {
            @Override
            protected void doHandle(Request request, Response response, Writer writer) throws IOException, TemplateException {
                String cookie = MoneyController.getSessionCookie(request);
                String username = sessionDAO.findUserNameBySessionId(cookie);

                String add = request.queryParams("add");
                String categoryName = StringEscapeUtils.escapeHtml4(request.queryParams("categoryName"));
                String typeOption = StringEscapeUtils.escapeHtml4(request.queryParams("type"));
                Set params = request.queryParams();
                Set newParams = new HashSet(params);
                newParams.remove("add");


                Boolean isIncome = null;

                if (typeOption != null && typeOption.equals("income")) {
                    isIncome = true;
                } else if (typeOption != null && typeOption.equals("expenses")) {
                    isIncome = false;
                }

                if (add.equals("true") && isIncome != null) {
                    categoriesDAO.addCategory(username, categoryName, isIncome);
                } else if (add.equals("false")) {
                    System.out.println(newParams);
                    categoriesDAO.deleteCategories(username, newParams);
                }

                response.redirect("/categories");

            }
        });


    }


}
