package money.logic;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

import java.util.Date;
import java.util.NoSuchElementException;

/**
 * author: erik
 */
public class TransactionsDAO {
    private final DBCollection transactionsCollection;

    public TransactionsDAO(final DB mongodb) {
        transactionsCollection = mongodb.getCollection("transactions");
    }

    public void addTransaction(String user, String accountId, String categoryId, Boolean income, Double sum, String comment) {
        BasicDBObject query = new BasicDBObject()
                .append("user", user)
                .append("accountId", accountId)
                .append("categoryId", categoryId)
                .append("isIncome", income)
                .append("sum", sum)
                .append("date", new Date());


        if (comment != null && !comment.equals("")) {
            query.append("comment", comment);
        }

        transactionsCollection.insert(query);
    }

    public Double getSumOfAccount(String accountId) {
        BasicDBObject groupQuery = new BasicDBObject("$group", new BasicDBObject("_id", "$accountId").append("sum", new BasicDBObject("$sum", "$sum")));
        BasicDBObject matchQuery = new BasicDBObject("$match", new BasicDBObject("_id", accountId));
        AggregationOutput output = transactionsCollection.aggregate(groupQuery, matchQuery);
        String ds;
        try {
            ds = output.results().iterator().next().get("sum").toString();
        } catch (NoSuchElementException e) {
            return 0D;
        }
        return Double.parseDouble(ds);
    }


}
