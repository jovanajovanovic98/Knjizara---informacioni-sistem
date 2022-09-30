/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import connection.factory.DBConnectionFactory;
import domain.GenericEntity;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Korisnik
 */
public class RepositoryDBGeneric implements DBRepository<GenericEntity>{
    @Override
    public List<GenericEntity> getAll() throws Exception {
        return null;
    }

    @Override
    public void add(GenericEntity entity) throws Exception {
        
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ").append(entity.getTableName()).append(" (").append(entity.getColumnNamesForInsert()).append(") VALUES (").append(entity.getInsertValues()).append(")");
            String query = sb.toString();
            System.out.println(query);
            Connection connection = DBConnectionFactory.getInstance().getConnection();
            Statement ps = connection.createStatement();
            ps.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                entity.setId(id);
                System.out.println(id);
            }
            rs.close();
            ps.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("Sistem ne moze da zapamti objekat " + entity.getTableName() + " !");

        }
        
    }

    @Override
    public void edit(GenericEntity entity) throws Exception {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE ").append(entity.getTableName()).append(" SET ").append(entity.getColumnNamesValuesUpdate()).append(" WHERE ").append(entity.getWhereClauseDeleteEdit());
            String sql = sb.toString();
            System.out.println(sql);
            Connection connection=DBConnectionFactory.getInstance().getConnection();
            Statement statement=connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Izmena objekta " + entity.getTableName()+" DB error: \n"+ex.getMessage());
        }
    }

    @Override
    public void delete(GenericEntity entity) throws Exception {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM ").append(entity.getTableName()).append(" WHERE ").append(entity.getWhereClauseDeleteEdit());
            String sql = sb.toString();
            System.out.println(sql);
            Connection connection=DBConnectionFactory.getInstance().getConnection();
            Statement statement=connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Brisanje objekta " + entity.getTableName() + " DB error: \n"+ex.getMessage());
        }
    }

    @Override
    public List<GenericEntity> getAll(GenericEntity entity) throws Exception {
        try {
            List<GenericEntity> list = new ArrayList<>();
            Connection connection = DBConnectionFactory.getInstance().getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT ").append(entity.getColumnNamesForGetAll()).append(" FROM ").append(entity.getTableName()).append(" " + entity.getTableName().substring(0, 1)).append(" ");
            if (entity.getJoinClause() != null) {
                sb.append(" JOIN ").append(entity.getJoinClause());
            }
            if(entity.getWhereForGetAll() != null)
                sb.append(" WHERE ").append(entity.getWhereForGetAll());
            String sql = sb.toString();
            System.out.println(sql);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                list.add(Converter.convert(entity, rs));
            }
            rs.close();
            statement.close();
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
