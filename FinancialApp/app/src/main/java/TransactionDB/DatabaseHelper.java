    package TransactionDB;

    import android.content.ContentValues;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;

    import java.util.ArrayList;
    import java.util.Date;
    import java.util.List;

    import Models.Transaction;

    /**
     * Created by Ludwig on 2017-09-20.
     */

    public class DatabaseHelper extends SQLiteOpenHelper {

        // Database Version
        private static final int DATABASE_VERSION = 1;

        // Database Name
        private static final String DATABASE_NAME = "Transaction.db";

        // User table name
        private static final String TABLE_NAME = "transactions";

        // User Table Columns names
        private static final String COLUMN_TRANSACTION_ID = "transaction_id";
        private static final String COLUMN_TRANSACTION_TYPE = "transaction_type";
        private static final String COLUMN_TRANSACTION_AMOUNT = "transaction_amount";
        private static final String COLUMN_TRANSACTION_DATE = "transaction_date";
        private SQLiteDatabase db;
        int balance;

        // create table sql query
        private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TRANSACTION_TYPE + " TEXT,"
                + COLUMN_TRANSACTION_AMOUNT + " TEXT," + COLUMN_TRANSACTION_DATE + " TEXT" + ")";

        // drop table sql query
        private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        // constructor
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_USER_TABLE);
            this.db = db;
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            // drop the table if the user exists
            db.execSQL(DROP_USER_TABLE);

            // creates the table again
            onCreate(db);

        }

        // create the user info
        public void addTransaction(Transaction transaction) {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_TRANSACTION_TYPE, transaction.getType());
            values.put(COLUMN_TRANSACTION_AMOUNT, transaction.getAmount());
            values.put(COLUMN_TRANSACTION_DATE, String.valueOf(transaction.getDate()));

            // insert the row
            db.insert(TABLE_NAME, null, values);
            // close the db when we are done
            db.close();
        }

        // method to fetch all the users
        public List<Transaction> getAllTransactions() {
            // array of columns to fetch
            String[] columns = {
                    COLUMN_TRANSACTION_ID,
                    COLUMN_TRANSACTION_AMOUNT,
                    COLUMN_TRANSACTION_TYPE,
                    COLUMN_TRANSACTION_DATE
            };
            // sorting order
            String sortOrder =
                    COLUMN_TRANSACTION_DATE + " DESC";
            List<Transaction> userList = new ArrayList<Transaction>();

            SQLiteDatabase db = this.getReadableDatabase();

            // query for the table
            Cursor cursor = db.query(TABLE_NAME, //Table to query
                    columns,    //columns to return
                    null,        //columns for the WHERE clause
                    null,        //The values for the WHERE clause
                    null,       //group the rows
                    null,       //filter by row groups
                    sortOrder); //The sort order


            // Traversing through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Transaction transaction = new Transaction();
                    transaction.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_ID))));
                    transaction.setType(cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_TYPE)));
                    transaction.setAmount(cursor.getInt(cursor.getColumnIndex(COLUMN_TRANSACTION_AMOUNT)));
                    transaction.setDate(cursor.getInt(cursor.getColumnIndex(COLUMN_TRANSACTION_DATE)));
                    // Adding the user to the list
                    userList.add(transaction);
                } while (cursor.moveToNext());
            }
            // closes the cursor and db when we are done
            cursor.close();
            db.close();

            // return user list
            return userList;
        }

        // method to update the user info
        public void updateUser(Transaction transaction) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_TRANSACTION_TYPE, transaction.getType());
            values.put(COLUMN_TRANSACTION_AMOUNT, transaction.getAmount());
            values.put(COLUMN_TRANSACTION_DATE, transaction.getDate());

            // updating row
            db.update(TABLE_NAME, values, COLUMN_TRANSACTION_ID + " = ?",
                    new String[]{String.valueOf(transaction.getId())});
            db.close();
        }

        // method to delete user
        public void deleteTransaction(Transaction transaction) {
            SQLiteDatabase db = this.getWritableDatabase();
            // delete user record by id
            db.delete(TABLE_NAME, COLUMN_TRANSACTION_ID + " = ?",
                    new String[]{String.valueOf(transaction.getId())});
            db.close();
        }

        public void deleteAllTransactions()
        {
            db = getWritableDatabase();
            db.delete(DatabaseHelper.TABLE_NAME, null, null);
        }

        public List<Transaction> filterDate(int date) {
            // array of columns to fetch
            String[] columns = {
                    COLUMN_TRANSACTION_ID,
                    COLUMN_TRANSACTION_AMOUNT,
                    COLUMN_TRANSACTION_TYPE,
                    COLUMN_TRANSACTION_DATE
            };
            // sorting order
            String sortOrder =
                    COLUMN_TRANSACTION_DATE + " DESC";
            List<Transaction> userList = new ArrayList<Transaction>();

            SQLiteDatabase db = this.getReadableDatabase();

            // query for the table

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_TRANSACTION_DATE.toString() + " >= " + date + " ORDER BY " + COLUMN_TRANSACTION_DATE.toString() + " DESC";
            Cursor cursor = db.rawQuery(query,null);//The sort order


            // Traversing through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Transaction transaction = new Transaction();
                    transaction.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_ID))));
                    transaction.setType(cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_TYPE)));
                    transaction.setAmount(cursor.getInt(cursor.getColumnIndex(COLUMN_TRANSACTION_AMOUNT)));
                    transaction.setDate(cursor.getInt(cursor.getColumnIndex(COLUMN_TRANSACTION_DATE)));
                    // Adding the user to the list
                    userList.add(transaction);
                } while (cursor.moveToNext());
            }
            // closes the cursor and db when we are done
            cursor.close();
            db.close();

            // return user list
            return userList;
        }

        public List<Transaction> filterPositive(boolean positive) {
            // array of columns to fetch
            String[] columns = {
                    COLUMN_TRANSACTION_ID,
                    COLUMN_TRANSACTION_AMOUNT,
                    COLUMN_TRANSACTION_TYPE,
                    COLUMN_TRANSACTION_DATE
            };
            // sorting order
            List<Transaction> userList = new ArrayList<Transaction>();

            SQLiteDatabase db = this.getReadableDatabase();

            // query for the table
            String query = "";
            if (positive){
                query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_TRANSACTION_AMOUNT.toString() + " <= 0  ORDER BY " + COLUMN_TRANSACTION_DATE.toString() + " DESC";
            }
            else {
                query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_TRANSACTION_AMOUNT.toString() + " >= 0  ORDER BY " + COLUMN_TRANSACTION_DATE.toString() + " DESC";
            }

            Cursor cursor = db.rawQuery(query,null);//The sort order


            // Traversing through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Transaction transaction = new Transaction();
                    transaction.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_ID))));
                    transaction.setType(cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_TYPE)));
                    transaction.setAmount(cursor.getInt(cursor.getColumnIndex(COLUMN_TRANSACTION_AMOUNT)));
                    transaction.setDate(cursor.getInt(cursor.getColumnIndex(COLUMN_TRANSACTION_DATE)));
                    // Adding the user to the list
                    userList.add(transaction);
                } while (cursor.moveToNext());
            }
            // closes the cursor and db when we are done
            cursor.close();
            db.close();

            // return user list
            return userList;
        }

    }
