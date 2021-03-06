//    Chromis POS  - The New Face of Open Source POS
//    Copyright (c) (c) 2015-2016
//    http://www.chromis.co.uk
//
//    This file is part of Chromis POS
//
//     Chromis POS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    Chromis POS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Chromis POS.  If not, see <http://www.gnu.org/licenses/>.
package uk.chromis.pos.sales.restaurant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import uk.chromis.data.loader.Session;
import uk.chromis.pos.forms.AppView;
import uk.chromis.pos.forms.DataLogicSystem;

/**
 *
 * @author JDL
 */
public class RestaurantDBUtils {

    private Session s;
    private Connection con;
    private Statement stmt;
    private PreparedStatement pstmt;
    private String SQL;
    private ResultSet rs;
    private AppView m_App;

    /**
     *
     */
    protected DataLogicSystem dlSystem;

    /**
     *
     * @param oApp
     */
    public RestaurantDBUtils(AppView oApp) {
        m_App = oApp;

//get database connection details        
        try {
            s = m_App.getSession();
            con = s.getConnection();
        } catch (SQLException e) {
            System.out.print("No session or connection");
        }

    }

    /**
     *
     * @param newTable
     * @param ticketID
     */
    public void moveCustomer(String newTable, String ticketID) {
        String oldTable = getTableDetails(ticketID);
        if (countTicketIdInTable(ticketID) > 1) {
            setCustomerNameInTable(getCustomerNameInTable(oldTable), newTable);
            setWaiterNameInTable(getWaiterNameInTable(oldTable), newTable);
            setTicketIdInTable(ticketID, newTable);
// remove the data for the old table 
            oldTable = getTableMovedName(ticketID);
            if ((oldTable != null) && (oldTable != newTable)) {
                clearCustomerNameInTable(oldTable);
                clearWaiterNameInTable(oldTable);
                clearTicketIdInTable(oldTable);
                clearTableMovedFlag(oldTable);
            } else {
                oldTable = getTableMovedName(ticketID);
                clearTableMovedFlag(oldTable);
            }
        }
    }

    /**
     *
     * @param custName
     * @param tableName
     */
    public void setCustomerNameInTable(String custName, String tableName) {
        try {
            SQL = "UPDATE PLACES SET CUSTOMER=? WHERE NAME=?";
            pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, custName);
            pstmt.setString(2, tableName);
            pstmt.executeUpdate();
        } catch (Exception e) {
        }
    }

    /**
     *
     * @param custName
     * @param tableID
     */
    public void setCustomerNameInTableById(String custName, String tableID) {
        try {
            SQL = "UPDATE PLACES SET CUSTOMER=? WHERE ID=?";
            pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, custName);
            pstmt.setString(2, tableID);
            pstmt.executeUpdate();
        } catch (Exception e) {
        }
    }

    /**
     *
     * @param custName
     * @param ticketID
     */
    public void setCustomerNameInTableByTicketId(String custName, String ticketID) {
        try {
            SQL = "UPDATE PLACES SET CUSTOMER=? WHERE TICKETID=?";
            pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, custName);
            pstmt.setString(2, ticketID);
            pstmt.executeUpdate();
        } catch (Exception e) {
        }
    }

    /**
     *
     * @param tableName
     * @return
     */
    public String getCustomerNameInTable(String tableName) {
        try {
            SQL = "SELECT CUSTOMER FROM PLACES WHERE NAME='" + tableName + "'";
            stmt = (Statement) con.createStatement();
            rs = stmt.executeQuery(SQL);
            if (rs.next()) {
                String customer = rs.getString("CUSTOMER");
                return (customer);
            }
        } catch (Exception e) {
        }
        return "";
    }

    /**
     *
     * @param tableId
     * @return
     */
    public String getCustomerNameInTableById(String tableId) {
        try {
            SQL = "SELECT CUSTOMER FROM PLACES WHERE ID='" + tableId + "'";
            stmt = (Statement) con.createStatement();
            rs = stmt.executeQuery(SQL);
            if (rs.next()) {
                String customer = rs.getString("CUSTOMER");
                return (customer);
            }
        } catch (Exception e) {
        }
        return "";
    }

    /**
     *
     * @param tableName
     */
    public void clearCustomerNameInTable(String tableName) {
        try {
            SQL = "UPDATE PLACES SET CUSTOMER=null WHERE NAME=?";
            pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, tableName);
            pstmt.executeUpdate();
        } catch (Exception e) {
        }
    }

    /**
     *
     * @param tableID
     */
    public void clearCustomerNameInTableById(String tableID) {
        try {
            SQL = "UPDATE PLACES SET CUSTOMER=null WHERE ID=?";
            pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, tableID);
            pstmt.executeUpdate();
        } catch (Exception e) {
        }
    }

    /**
     *
     * @param waiterName
     * @param tableName
     */
    public void setWaiterNameInTable(String waiterName, String tableName) {
        try {
            SQL = "UPDATE PLACES SET WAITER=? WHERE NAME=?";
            pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, waiterName);
            pstmt.setString(2, tableName);
            pstmt.executeUpdate();
        } catch (Exception e) {
        }
    }

    /**
     *
     * @param waiterName
     * @param tableID
     */
    public void setWaiterNameInTableById(String waiterName, String tableID) {
        try {
            SQL = "UPDATE PLACES SET WAITER=? WHERE ID=?";
            pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, waiterName);
            pstmt.setString(2, tableID);
            pstmt.executeUpdate();
        } catch (Exception e) {
        }
    }

    /**
     *
     * @param tableName
     * @return
     */
    public String getWaiterNameInTable(String tableName) {
        try {
            SQL = "SELECT WAITER FROM PLACES WHERE NAME='" + tableName + "'";
            stmt = (Statement) con.createStatement();
            rs = stmt.executeQuery(SQL);
            if (rs.next()) {
                String waiter = rs.getString("WAITER");
                return (waiter);
            }
        } catch (Exception e) {
        }
        return "";
    }

    /**
     *
     * @param tableID
     * @return
     */
    public String getWaiterNameInTableById(String tableID) {
        try {
            SQL = "SELECT WAITER FROM PLACES WHERE ID='" + tableID + "'";
            stmt = (Statement) con.createStatement();
            rs = stmt.executeQuery(SQL);
            if (rs.next()) {
                String waiter = rs.getString("WAITER");
                return (waiter);
            }
        } catch (Exception e) {
        }
        return "";
    }

    /**
     *
     * @param tableName
     */
    public void clearWaiterNameInTable(String tableName) {
        try {
            SQL = "UPDATE PLACES SET WAITER=null WHERE NAME=?";
            pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, tableName);
            pstmt.executeUpdate();
        } catch (Exception e) {
        }
    }

    /**
     *
     * @param tableID
     */
    public void clearWaiterNameInTableById(String tableID) {
        try {
            SQL = "UPDATE PLACES SET WAITER=null WHERE ID=?";
            pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, tableID);
            pstmt.executeUpdate();
        } catch (Exception e) {
        }
    }

    /**
     *
     * @param ID
     * @return
     */
    public String getTicketIdInTable(String ID) {
        try {
            SQL = "SELECT TICKETID FROM PLACES WHERE ID='" + ID + "'";
            stmt = (Statement) con.createStatement();
            rs = stmt.executeQuery(SQL);
            if (rs.next()) {
                String customer = rs.getString("TICKETID");
                return (customer);
            }
        } catch (Exception e) {
        }
        return "";
    }

    /**
     *
     * @param TicketID
     * @param tableName
     */
    public void setTicketIdInTable(String TicketID, String tableName) {
        try {
            SQL = "UPDATE PLACES SET TICKETID=? WHERE NAME=?";
            pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, TicketID);
            pstmt.setString(2, tableName);
            pstmt.executeUpdate();
        } catch (Exception e) {
        }
    }

    /**
     *
     * @param tableName
     */
    public void clearTicketIdInTable(String tableName) {
        try {
            SQL = "UPDATE PLACES SET TICKETID=null WHERE NAME=?";
            pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, tableName);
            pstmt.executeUpdate();
        } catch (Exception e) {
        }
    }

    /**
     *
     * @param tableID
     */
    public void clearTicketIdInTableById(String tableID) {
        try {
            SQL = "UPDATE PLACES SET TICKETID=null WHERE ID=?";
            pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, tableID);
            pstmt.executeUpdate();
        } catch (Exception e) {
        }
    }

    /**
     *
     * @param ticketID
     * @return
     */
    public Integer countTicketIdInTable(String ticketID) {
        try {
            SQL = "SELECT COUNT(*) AS RECORDCOUNT FROM PLACES WHERE TICKETID='" + ticketID + "'";
            stmt = (Statement) con.createStatement();
            rs = stmt.executeQuery(SQL);
            if (rs.next()) {
                Integer count = rs.getInt("RECORDCOUNT");
                return (count);
            }
        } catch (Exception e) {
        }

        return 0;
    }

    /**
     *
     * @param ticketID
     * @return
     */
    public String getTableDetails(String ticketID) {
        try {
            SQL = "SELECT NAME FROM PLACES WHERE TICKETID='" + ticketID + "'";
            stmt = (Statement) con.createStatement();
            rs = stmt.executeQuery(SQL);
            if (rs.next()) {
                String name = rs.getString("NAME");
                return (name);
            }
        } catch (Exception e) {
        }
        return "";
    }

    /**
     *
     * @param tableID
     */
    public void setTableMovedFlag(String tableID) {
        try {
            SQL = "UPDATE PLACES SET TABLEMOVED='true' WHERE ID=?";
            pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, tableID);
            pstmt.executeUpdate();
        } catch (Exception e) {
        }
    }

    /**
     *
     * @param ticketID
     * @return
     */
    public String getTableMovedName(String ticketID) {
        try {
            SQL = "SELECT NAME FROM PLACES WHERE TICKETID='" + ticketID + "' AND TABLEMOVED ='true'";
            stmt = (Statement) con.createStatement();
            rs = stmt.executeQuery(SQL);
            if (rs.next()) {
                String name = rs.getString("NAME");
                return (name);
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     *
     * @param ticketID
     * @return
     */
    public Boolean getTableMovedFlag(String ticketID) {
        try {
            SQL = "SELECT TABLEMOVED FROM PLACES WHERE TICKETID='" + ticketID + "'";
            stmt = (Statement) con.createStatement();
            rs = stmt.executeQuery(SQL);
            if (rs.next()) {
                return (rs.getBoolean("TABLEMOVED"));
            }
        } catch (Exception e) {
        }
        return (false);
    }

    /**
     *
     * @param tableID
     */
    public void clearTableMovedFlag(String tableID) {
        try {
            SQL = "UPDATE PLACES SET TABLEMOVED = false WHERE NAME=?";
            pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, tableID);
            pstmt.executeUpdate();
        } catch (Exception e) {
        }
    }

    public String getTableOpenedBy(String tableID) {
        try {
            SQL = "SELECT OPENEDBY FROM PLACES WHERE ID = '" + tableID + "'";
            stmt = (Statement) con.createStatement();
            rs = stmt.executeQuery(SQL);
            if (rs.next()) {
                return (rs.getString("OPENEDBY"));
            }
        } catch (Exception e) {
        }
        return "";
    }

    public void setTableLock(String tableID, String user) {
        try {
            SQL = "UPDATE PLACES SET LOCKED = true, OPENEDBY = ? WHERE TICKETID = ?";
            pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, user);
            pstmt.setString(2, tableID);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("lock error");
        }
    }

    public void clearTableLock(String tableID) {
        try {
            SQL = "UPDATE PLACES SET LOCKED = false, OPENEDBY = null WHERE ID = ?";
            pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, tableID);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("clear lock error");
        }
    }

        public void clearTableLockByName(String tableName) {
        try {
            SQL = "UPDATE PLACES SET LOCKED = false, OPENEDBY = null WHERE NAME = ?";
            pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, tableName);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("clear lock error");
        }
    }
    
    
    public void clearTableLockByTicket(String ticketID) {
        try {
            SQL = "UPDATE PLACES SET LOCKED = false, OPENEDBY = null WHERE TICKETID = ?";
            pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, ticketID);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("clear lock error");
        }
    }

    public Boolean getTableLock(String tableID) {
        try {
            SQL = "SELECT LOCKED FROM PLACES WHERE ID='" + tableID + "'";
            stmt = (Statement) con.createStatement();
            rs = stmt.executeQuery(SQL);
            if (rs.next()) {
                return (rs.getBoolean("LOCKED"));
            }
        } catch (Exception e) {

        }
        return false;
    }
}
