package goit.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private PreparedStatement createINSERT;
    private PreparedStatement getByIdSELECT;
    private PreparedStatement getAllSELECT;
    private PreparedStatement setNameUPDATE;
    private PreparedStatement deleteByIdDELETE;

    public ClientService(Connection connection) throws SQLException {
        createINSERT = connection.prepareStatement("INSERT INTO client (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
        getByIdSELECT = connection.prepareStatement("SELECT name FROM client WHERE id=?");
        getAllSELECT = connection.prepareStatement("SELECT id, name FROM client");
        setNameUPDATE = connection.prepareStatement("UPDATE client SET name=? WHERE id=?");
        deleteByIdDELETE = connection.prepareStatement("DELETE FROM client WHERE id=?");
    }

    public long create(String name) throws Exception {
        if (name == null) {
            throw new Exception("Not Null");
        }
        if (name.length()<2 | name.length()>1000){
            throw new Exception("characters<2 >1000");
        }
        createINSERT.setString(1, name);

        createINSERT.executeUpdate();

        long id = -1;
        try (ResultSet rs = createINSERT.getGeneratedKeys()) {
            rs.next();
            id = rs.getLong(1);
        }
        return id;
    }

    public String getById(long id) throws SQLException {
        getByIdSELECT.setLong(1, id);
        try (ResultSet rs = getByIdSELECT.executeQuery()) {
            if (!rs.next()) {
                return null;
            }

            return rs.getString("name");
        }
    }

    public void setName(long id, String name) throws Exception {
        if (name == null) {
            throw new Exception("Not Null");
        }
        if (name.length()<2 | name.length()>1000){
            throw new Exception("characters<2 >1000");
        }
        setNameUPDATE.setString(1, name);
        setNameUPDATE.setLong(2, id);
        setNameUPDATE.executeUpdate();
    }

    public void deleteById(long id) throws SQLException {
        deleteByIdDELETE.setLong(1,id);
        deleteByIdDELETE.executeUpdate();
    }

    public List<Client> listAll() throws SQLException {
        try (ResultSet rs = getAllSELECT.executeQuery()) {
            List<Client> result = new ArrayList<>();
            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getLong("id"));
                client.setName(rs.getString("name"));

                result.add(client);
            }
            return result;
        }
    }
}

