import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class containing list of tables.
 * @author Tomasz Knapik <u1562595@unimail.hud.ac.uk>
 *
 */
public class TableList {
	
	private static TableList instance = null;
	private List<Table> tables = new ArrayList<Table>();
	
	/**
	 * Constructs table list. It generates list of tables.
	 */
	public TableList() {
		for(int i = 1; i <= 10; i++) {
			this.addTable(i);
		}
	}
	
	/**
	 * Gets an instance of table list. It's a singleton class, and therefore it should be always called by getInstance().
	 * @return
	 */
	public static TableList getInstance() {
		if(TableList.instance == null) {
			TableList.instance = new TableList();
		}
		
		return TableList.instance;
	}
	
	/**
	 * Adds a table by providing a table number.
	 * @param tableNumber
	 * @return
	 */
	public Boolean addTable(int tableNumber) {
		Table newTable = new Table(tableNumber);
		return this.addTable(newTable);
	}
	
	/**
	 * Adds an instance of table to the table list.
	 * @param table
	 * @return
	 */
	public Boolean addTable(Table table) {
		return this.tables.add(table);
	}
	
	/**
	 * Deletes table from the table list.
	 * @param table
	 * @return true if it has been removed
	 */
	public Boolean deleteTable(Table table) {
		return this.tables.remove(table);
	}
	
	/**
	 * Deletes table from the table list by providing index in the table.
	 * @param index
	 * @return returns an object which was removed from table list.
	 */
	public Table deleteTable(int index) {
		return this.tables.remove(index);
	}
	
	/**
	 * Gets list of tables
	 * @return
	 */
	public List<Table> getTables() {
		return this.tables;
	}
	
	/**
	 * String representation of an object
	 */
	public String toString() {
		return "Table List";
	}
}
