package ru.katiafill.bookings.airport.model;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;
import org.postgresql.geometric.PGpoint;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/* Пользовательский тип для преобразования
* PostgresSQL point type в PGpoint, а его в
* уже в свой Point.
*/
public class PointType implements UserType<Point> {

    @Override
    public int getSqlType() {
        return Types.OTHER;
    }

    @Override
    public Class<Point> returnedClass() {
        return Point.class;
    }

    @Override
    public boolean equals(Point x, Point y) {
        if (x == null && y == null)
            return true;
        else if (x == null || y == null)
            return false;
        return x.equals(y);
    }

    @Override
    public int hashCode(Point x) {
        return x.hashCode();
    }

    @Override
    public Point nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
        PGpoint value = (PGpoint) rs.getObject(position);

        if (value == null) {
            return null;
        } else {
            return new Point(value.x, value.y);
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Point value, int index, SharedSessionContractImplementor session) throws SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER);
        } else {
            st.setObject(index, new PGpoint(value.getX(), value.getY()));
        }
    }

    @Override
    public Point deepCopy(Point value) {
        if (value == null)
            return null;

        return new Point(value.getX(), value.getY());
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Point value) {
        return (Serializable) deepCopy(value);
    }

    @Override
    public Point assemble(Serializable cached, Object owner) {
        return deepCopy((Point)cached);
    }

}