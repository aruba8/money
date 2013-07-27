package money.logic;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

import java.util.Date;

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
                .append("sum", -sum)
                .append("date", new Date());


        if (comment != null && !comment.equals("")) {
            query.append("comment", comment);
        }

        transactionsCollection.insert(query);
    }


}
