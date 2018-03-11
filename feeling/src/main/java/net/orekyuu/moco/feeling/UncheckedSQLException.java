package net.orekyuu.moco.feeling;

import java.sql.SQLException;

public class UncheckedSQLException extends RuntimeException {
    public UncheckedSQLException(SQLException e) {
        super(e);
    }
}
