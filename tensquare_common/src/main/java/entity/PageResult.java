package entity;

import java.util.List;

/**
 * 分页返回值
 */
public class PageResult<T> {

    /**
     * 条数
     */
    private long totle;

    /**
     * 行信息
     */
    private List<T> rows;

    public PageResult() {
    }

    public PageResult(long totle, List<T> rows) {
        this.totle = totle;
        this.rows = rows;
    }

    public long getTotle() {
        return totle;
    }

    public void setTotle(long totle) {
        this.totle = totle;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
