package entity;
import java.io.Serializable;
import java.util.List;
/**
 * entity class for pagination result displaying
 */
public class PageResult implements Serializable{
	private long total; // total page number
	private List rows; // data collection
	
	public PageResult(long total, List rows) {
		super();
		this.total = total;
		this.rows = rows;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
}
