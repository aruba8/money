package money.logic;

import com.mongodb.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * author: erik
 */
public class ChartsDAO {
    private final DBCollection accountsCollection;
    private final DBCollection categoriesCollection;
    private final DBCollection transactionsCollection;

    public ChartsDAO(final DB moneyDB) {
        this.accountsCollection = moneyDB.getCollection("accounts");
        this.categoriesCollection = moneyDB.getCollection("categories");
        this.transactionsCollection = moneyDB.getCollection("transactions");

    }

    public JSONArray getSumByCategories(String username, Boolean isIncome) {

        BasicDBList transferList = new BasicDBList();
        BasicDBList catList = new BasicDBList();

        Map userCategoriesList = getActiveCategoriesIds(username);
        for (Object o : userCategoriesList.entrySet()) {
            Map.Entry me = (Map.Entry) o;
            catList.add(me.getKey());
        }

        transferList.add("transfer");

        DBObject match = new BasicDBObject("$match", new BasicDBObject("user", username)
                .append("isIncome", isIncome)
                .append("categoryId", new BasicDBObject("$in", catList)));
        DBObject group = new BasicDBObject("$group", new BasicDBObject("_id", "$categoryId").append("total", new BasicDBObject("$sum", "$sum")));

        AggregationOutput output = transactionsCollection.aggregate(match, group);

        JSONArray jsonArray = new JSONArray();
        try {
            for (DBObject ob : output.results()) {
                JSONObject jsonObject = new JSONObject();
                double total = Double.parseDouble(ob.get("total").toString());
                jsonObject.put(userCategoriesList.get(ob.get("_id").toString()).toString(), Math.abs(total));
                jsonArray.put(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArray;

    }


    public Map getSumByCategoriesMap(String username, Boolean isIncome) {

        BasicDBList transferList = new BasicDBList();
        BasicDBList catList = new BasicDBList();

        Map userCategoriesList = getActiveCategoriesIds(username);
        for (Object o : userCategoriesList.entrySet()) {
            Map.Entry me = (Map.Entry) o;
            catList.add(me.getKey());
        }

        transferList.add("transfer");

        DBObject match = new BasicDBObject("$match", new BasicDBObject("user", username).append("isIncome", isIncome)
                .append("categoryId", new BasicDBObject("$in", catList)));
        DBObject group = new BasicDBObject("$group", new BasicDBObject("_id", "$categoryId").append("total", new BasicDBObject("$sum", "$sum")));


        AggregationOutput output = transactionsCollection.aggregate(match, group);

        Map<String, Double> map = new TreeMap<String, Double>();
        for (DBObject ob : output.results()) {
            map.put(userCategoriesList.get(ob.get("_id").toString()).toString(), Double.parseDouble(ob.get("total").toString()));
        }
        return map;
    }


    private Map getActiveCategoriesIds(String username) {

        DBObject query = new BasicDBObject("user", username).append("isActive", true);
        DBCursor cursor = categoriesCollection.find(query);
        Map<String, String> categoriesMapList = new TreeMap<String, String>();

        while (cursor.hasNext()) {
            DBObject ob = cursor.next();
            categoriesMapList.put(ob.get("_id").toString(), ob.get("categoryName").toString());
        }
        return categoriesMapList;
    }

    public JSONArray getSumByCategoriesForMonth(String username, int month, int year, Boolean isIncome){
        Calendar currMonth = Calendar.getInstance();
        Calendar nextMonth = Calendar.getInstance();
        currMonth.clear();
        nextMonth.clear();
        currMonth.set(Calendar.MONTH, month);
        nextMonth.set(Calendar.MONTH, month + 1);
        currMonth.set(Calendar.YEAR, year);
        nextMonth.set(Calendar.YEAR, year);


        BasicDBList transferList = new BasicDBList();
        BasicDBList catList = new BasicDBList();

        Map userCategoriesList = getActiveCategoriesIds(username);
        for (Object o : userCategoriesList.entrySet()) {
            Map.Entry me = (Map.Entry) o;
            catList.add(me.getKey());
        }

        transferList.add("transfer");

        DBObject match = new BasicDBObject("$match", new BasicDBObject("user", username)
                .append("isIncome", isIncome)
                .append("categoryId", new BasicDBObject("$in", catList))
                .append("date", new BasicDBObject("$gte", currMonth.getTime()).append("$lte", nextMonth.getTime())));
        DBObject group = new BasicDBObject("$group", new BasicDBObject("_id", "$categoryId").append("total", new BasicDBObject("$sum", "$sum")));

        AggregationOutput output = transactionsCollection.aggregate(match, group);

        JSONArray jsonArray = new JSONArray();
        try {
            for (DBObject ob : output.results()) {
                JSONObject jsonObject = new JSONObject();
                double total = Double.parseDouble(ob.get("total").toString());
                jsonObject.put("name", userCategoriesList.get(ob.get("_id").toString()).toString());
                jsonObject.put("y", Math.abs(total));
                jsonArray.put(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

}
