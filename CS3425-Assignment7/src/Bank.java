import java.io.PrintWriter;
import java.io.File;
/*
 * @author Brett Kriz
 */

import java.sql.*;
import java.lang.Double;
import java.lang.Math;

public class Bank{
    
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    public static final String s_stdwhere = " WHERE customer_id = ? AND account_number = ?";
    // +s_stdwhere

    public static final String expr_SAcc = "SELECT * FROM saving WHERE account_number = ?";
    public static final String expr_CAcc = "SELECT * FROM checking WHERE account_number = ?";

    public static final String expr_isSAcc = "SELECT * FROM saving WHERE account_number = ?";
    public static final String expr_isCAcc = "SELECT * FROM checking WHERE account_number = ?";
    public static final String expr_addCust = "INSERT INTO customer VALUES ?, ?, ?";
    public static final String expr_addSaving = "INSERT INTO saving VALUES ?, ?, ?";
    public static final String expr_addChecking = "INSERT INTO checking VALUES ?, ?, ?";
    public static final String expr_SDepo = "UPDATE saving SET balance = balance + ?"+s_stdwhere;
    public static final String expr_CDepo = "UPDATE checking SET balance = balance + ?"+s_stdwhere;
    public static final String expr_getSBal = "SELECT balance FROM saving"+s_stdwhere;
    public static final String expr_getCBal = "SELECT balance FROM checking"+s_stdwhere;
    public static final String expr_SWith = "UPDATE saving SET balance = balance - ?"+s_stdwhere;
    public static final String expr_CWith = "UPDATE checking SET balance = balance - ?"+s_stdwhere;

    public static final String expr_trans = "START TRANSACTION; UPDATE saving SET balance = balance - ? WHERE account_number = ?; UPDATE checking SET balance = balance + ? WHERE account_number = ?; COMMIT;";

    public static final String expr_display = "SELECT * FROM saving WHERE customer_id = ? FULL OUTER JOIN SELECT * FROM checking WHERE customer_id = ? ";

    // 
    public Bank(){
        // So painful
    }

    public static void main (String args[]) throws SQLException{
// ******************************************************************
        Bank l = new Bank();
        l.connectDb();

        String cmd = args[0];
        String a1 = scrubData(args[1]);
        String a2 = scrubData(args[2]);
        String a3 = scrubData(args[3]);

        Double n3 =  ((Double)Math.abs(Double.parseDouble(a3)));

        switch(cmd){
            case "addcustomer": l.addCustomer(a1, a2, a3); break;
            case "addchecking": l.addCustomer(a1, a2, a3); break;
            case "savingdeposit": l.savingDeposit(a1, a2, n3); break;
            case "checkingdeposit": l.checkingDeposit(a1, a2, n3); break;
            case "display": l.display(a1); break;
            case "transfer": l.transfer(a1, a2, n3); break;    
        }

            /*
            l.select();
            l.withdraw("a' or account_number = 'a001'#", 10); // ' or account_number = 'a001'#
            l.select();
                    */
            l.disconnect();
// ****************************************************************************
    }

    public static String scrubData(String arg){
        if (arg == null){
            return "";
        }
        // Destroy injection formats just to be sure.
        return arg.replaceAll("'", "").replaceAll("-- ", "");
    }

    public boolean check_SavingAcc(String arg){
        Statement s = null;
        ResultSet r = null;

        r = this.select(this.expr_isSAcc, 1, arg);

        return true;
    }

    public boolean check_CheckingAcc(String arg){
        Statement s = null;
        ResultSet r = null;

        r = this.select(this.expr_isCAcc, 1, arg);

        return true;
    }

    public int connectDb(){
            try{
                    conn = 
DriverManager.getConnection("jdbc:mysql://classdb.it.mtu.edu/bakriz","bakriz","letmein1");
            } catch (SQLException e){
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    return 1;

            }
            return 0;
    }

    public void disconnect (){
            try{
                    conn.close();
            }
            catch (SQLException ex){
                    System.out.println("SQLException: " + ex.getMessage());
                    System.out.println("SQLState: " + ex.getSQLState());
                    System.out.println("VendorError: " + ex.getErrorCode());
            }
    }

    private ResultSet select(String expr,int expected_size, String... vars ){
        // Modified to work genericly, giving a resultset 
        // AND checking return size
            PreparedStatement s = null;
            ResultSet r = null;
            int rowcount;

            try {
                    //s = conn.createStatement();
                    //r = s.executeQuery(expr); // "SELECT * FROM account"
                    s = conn.prepareStatement(expr);
                    for(int x = 0; x < vars.length; x++){
                        String var = vars[x];
                        s.setString(x+1, var);
                    }
                    r = s.executeQuery();

                    rowcount = s.executeUpdate();
                    if (rowcount != expected_size && expected_size > -1){
                            System.out.println("Error: " + rowcount + " Rows differes from the expected count of " + expected_size);
                            conn.rollback();
                    }else{
                            System.out.println("Update " + rowcount + " Rows");
                            conn.commit();
                    }
            }
            catch (SQLException ex){
                    System.out.println("SQLException: " + ex.getMessage());
                    System.out.println("SQLState: " + ex.getSQLState());
                    System.out.println("VendorError: " + ex.getErrorCode());
            }
        return r;
    }

    public int withdraw(String acc, double amt){
            PreparedStatement s;
            ResultSet r;
            int rowcount;

            try{
                    conn.setAutoCommit(false);
                    conn.setTransactionIsolation(conn.TRANSACTION_SERIALIZABLE);
            }catch (SQLException ex){
                    ex.printStackTrace();
                    return 0;
            }

            try{

                    String t1 = "select balance from lab2_account";
                    String t2 = "UPDATE lab2_account SET balance = balance - " + amt + " WHERE account_number = ?"; //+ acc + "' ";
                    s = conn.prepareStatement(t2);
                    s.setString(1, acc);


                    rowcount = s.executeUpdate();
                    if (rowcount != 1){
                            System.out.println("SOME THINGS WRONG: updating " + rowcount + "Rows");
                            conn.rollback();
                    }else{
                            System.out.println("Update " + rowcount + "ROWS");
                            conn.commit();
                    }

            }catch (SQLException ex){
                    System.out.println("SQLException: " + ex.getMessage());
                    System.out.println("SQLState: " + ex.getSQLState());
                    System.out.println("VendorError: " + ex.getErrorCode());
            }
            return 1;
    }

    // Assignment 7 Functions below

    public int addCustomer(String name, String ID, String Phone_number){
        int flag = 1;

        this.select(expr_addCust, 1, name, ID, Phone_number);

        return flag;
    }

    public int addSaving(String account_number, String customer_id, Double amount){
        int flag = 1;

        this.select(expr_addSaving,1,account_number, customer_id, amount.toString());

        return flag;
    }

    public int addChecking(String account_number, String customer_id, Double amount){
        int flag = 1;

        this.select(expr_addChecking,1,account_number, customer_id, amount.toString());

        return flag;
    }

    public int savingDeposit(String account_number, String customer_id, Double amount){
        int flag = 1;

        this.select(expr_SDepo, 1, amount.toString(), customer_id, account_number);

        return flag;
    }

    public int checkingDeposit(String account_number, String customer_id, Double amount){
        int flag = 1;

        this.select(expr_CDepo, 1, amount.toString(), customer_id, account_number);

        return flag;
    }

    public int savingWithdraw(String account_number, String customer_id, Double amount) throws SQLException{
        int flag = 1;
        ResultSet res = this.select(expr_getSBal, 1, customer_id, account_number);

        double x = -1;
        try {
            x = res.getDouble("balance");
        }catch (SQLException e){
            // Get out.
            throw e;
        }

        if (x-amount < 0){
            // No good...
            throw new Error("Insufficient Funds! Account "+account_number+" only has $"+x+" (less than "+ amount+")\n");
        }else{
            // Do it
            this.select(expr_SWith, 1, amount.toString(), customer_id, account_number);
        }
        return flag;
    }

    public int checkingWithdraw(String account_number, String customer_id, Double amount) throws SQLException{
        int flag = 1;
        ResultSet res = this.select(expr_getCBal, 1, customer_id, account_number);

        double x = -1;
        try {
            x = res.getDouble("balance");
        }catch (SQLException e){
            // Get out.
            throw e;
        }

        if (x-amount < 0){
            // No good...
            throw new Error("Insufficient Funds! Account "+account_number+" only has $"+x+" (less than "+ amount+")\n");
        }else{
            // Do it
            this.select(expr_CWith, 1, amount.toString(), customer_id, account_number);
        }
        return flag;
    }

    public int display(String id) throws SQLException{
        int flag = 1;
        ResultSet res = this.select(expr_display,-1,id,id);

        this.show(res, id);

        return flag;
    }

    public void show(ResultSet res, String id)throws SQLException{
        if(res.first() == false){
            System.out.println("The argument " + id + " does not recieve any values!");
        } else{
            res.first();
            System.out.println("Account#\t" + res.getString("account_number")
                            + "\tID: " + res.getString("customer_id") 
                            + "\tBalance: " + res.getString("balance"));

            // I hate these result sets SO MUCH.
            while(res.next()){
            System.out.println("Account#\t"
                            + res.getString("account_number")
                            + "\tID: " + res.getString("customer_id") 
                            + "\tBalance: " + res.getString("balance"));
            }
        }
    }

    public int transfer(String saving_AN, String checking_AN, Double amount)throws SQLException{
        // Saving -> Checking
        int flag = 1;
        ResultSet s,c;

        // display checking and saving before trans and after
        s=this.select(expr_SAcc, -1, saving_AN);
        c=this.select(expr_CAcc, -1, checking_AN);
        System.out.println("Savings:");
        this.show(s, saving_AN);
        System.out.println("Checking:");
        this.show(c, checking_AN);

        ResultSet res = this.select("SELECT balance FROM saving WHERE account_number = ?", 1, saving_AN);
        double x = -1;
        try {
            x = res.getDouble("balance");
        }catch (SQLException e){
            // Get out.
            throw e;
        }

        if (x-amount < 0){
            // No good...
            throw new Error("Insufficient Funds! Account "+saving_AN+" only has $"+x+" (less than "+ amount+")\n");
        }else{
            // Do it
            this.select(expr_trans, -1, amount.toString(), saving_AN, amount.toString(), checking_AN);
        }

        System.out.println("~~~~~~~~~~~AFTER~~~~~~~~~~~~");

        s=this.select(expr_SAcc, -1, saving_AN);
        c=this.select(expr_CAcc, -1, checking_AN);
        System.out.println("Savings:");
        this.show(s, saving_AN);
        System.out.println("Checking:");
        this.show(c, checking_AN);



        return flag;
    }
}

