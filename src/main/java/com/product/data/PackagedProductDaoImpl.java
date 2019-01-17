/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.product.data;

import com.product.model.PackageProduct;
import com.product.model.PackagedProduct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Laud.Ochei
 */
    


@Repository
public class PackagedProductDaoImpl implements PackagedProductDao {
    
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) throws DataAccessException {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
        
        
        DataSource dataSource;
        @Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
        
        
        @Override
	public PackagedProduct findByNo(Integer productsno) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productsno", productsno);
                String sql = "select * from product WHERE productno=:productno";
		PackagedProduct result = null;
		try {
                    result = namedParameterJdbcTemplate.queryForObject(sql, params, new PackagedProductDaoImpl.PackagedProductMapper());
		} catch (EmptyResultDataAccessException e) {
                    // do nothing, return null
		}
		return result;
	}
        
        
        
        
        @Override
	public PackagedProduct findById(String packagedproductid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("packagedproductid", packagedproductid);
		String sql = "SELECT * FROM packaedproduct WHERE productid=:productid";
		PackagedProduct result = null;
		try {
			result = namedParameterJdbcTemplate.queryForObject(sql, params, new PackagedProductDaoImpl.PackagedProductMapper());
		} catch (EmptyResultDataAccessException e) {
			// do nothing, return null
		}
                return result;
           
	}
    
        
        
        @Override
	public List<PackagedProduct> findAll() {
         
                List<PackagedProduct> packagedproduct = new ArrayList<PackagedProduct>();
		Connection con = null;
                PreparedStatement stmt = null;
		ResultSet rs = null;
                PreparedStatement stmt1 = null;
		ResultSet rs1 = null;

                
                
                try {
			con = dataSource.getConnection();
                        String sql = "SELECT * FROM packagedproduct";
                        String sql1 = "SELECT * FROM products";
			stmt = con.prepareStatement(sql);
                        stmt1 = con.prepareStatement(sql1);
			rs = stmt.executeQuery();
                        rs1 = stmt1.executeQuery();
                        int prodCount= 0;
                        double sumPrice = 0.0;
			while(rs.next()) {
                            //System.out.println("Check content: " + rs.getInt("packageproductno"));
                                PackagedProduct packaged = new PackagedProduct();
                                packaged.setPackageproductno(rs.getInt("packageproductno"));
                                packaged.setPackageproductid(rs.getString("packageproductId"));
                                packaged.setPackagesid(rs.getString("packageid"));
                                packaged.setProductid(rs.getString("productid"));
                                
                                List<PackageProduct> packageproduct = new ArrayList<PackageProduct>();
                                HashMap<Integer, List<PackageProduct>> map1 = new HashMap<Integer, List<PackageProduct>>();
                                //HashMap<Integer, PackageProduct> map2 = new HashMap<Integer, PackageProduct>();
                                while(rs1.next()) {
                                    if (rs.getString("productid").equals(rs1.getString("productid"))){
                                        //System.out.println("Check content2: " + rs.getString("productid"));
                                        prodCount++;
                                        PackageProduct products = new PackageProduct();
                                        products.setProductno(rs1.getInt("productno"));
                                        products.setProductid(rs1.getString("productid"));
                                        products.setProductname(rs1.getString("productname")); 
                                        products.setProductdesc(rs1.getString("productdesc"));
                                        products.setProductprice(rs1.getDouble("productprice")); 
                                        sumPrice = sumPrice + rs1.getDouble("productprice");
                                        packageproduct.add(products);
                                        map1.put(prodCount, packageproduct);
                                        //map2.put(prodCount, products);
                                    }
                                }
                                
                                packaged.setProductList(map1);
                                //packaged.setProduct(map2);
                                packaged.setTotalPrice(sumPrice);
                                packagedproduct.add(packaged);
			}
                        
                        
		} 
		catch (SQLException e) {
                    e.printStackTrace();
		}
                
		return packagedproduct;
                
                
                
                
                
                
                
                
	}
    
        
        
        
        @Override
	public void save(PackagedProduct packagedproduct) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            String sql = "insert into pmitem(pmitemid, pmheaderid, jobplanid, locationid, pmitemdesc, systemcond, routeid) "
				+ "VALUES ( :pmitemid, :pmheaderid, :jobplanid, :locationid, :pmitemdesc, :systemcond, :routeid)";
            namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(packagedproduct), keyHolder, new String[]{"packagedproductno"});
            packagedproduct.setPackageproductno(keyHolder.getKey().intValue());
	}
        
        
        
        @Override
	public void update(PackagedProduct packagedproduct) {
            String sql = "update pmitem set pmitemno=:pmitemno, pmitemid=:pmitemid, pmheaderid=:pmheaderid, jobplanid=:jobplanid, locationid=:locationid, pmitemdesc=:pmitemdesc, systemcond=:systemcond where pmitemno=:pmitemno";
            namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(packagedproduct));    
	}
        
        
        @Override
	public void delete(Integer productno) {
		String sql = "DELETE FROM product WHERE productpmitemno= :pmitemno";
		namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource("pmitemno", productno));
	}
        
                
        @Override
        public int idExists(String packagedproductid) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("packagedproductid", packagedproductid); 
            String sql = "SELECT count(*) FROM pmitem WHERE pmitemid = :pmitemid";
            int count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
            return count;
        }

        
        @Override
        public int ForeignKeyPackage(String packageid) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("packageid", packageid);
            String sql = "SELECT count(*) FROM packages WHERE packageid = :packageid";
            int count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
            return count;
        }
        
        
        @Override
        public int ForeignKeyProduct(String productid) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("productid", productid);
            String sql = "SELECT count(*) FROM product WHERE productid = :productid";
            int count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
            return count;
        }
                
       
        private SqlParameterSource getSqlParameterByModel(PackagedProduct packagedproduct) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
               
                paramSource.addValue("packageproductno", packagedproduct.getPackageproductno());
                paramSource.addValue("packageproductid", packagedproduct.getPackageproductid());
                paramSource.addValue("productid", packagedproduct.getProductid());
                paramSource.addValue("packageid", packagedproduct.getPackagesid());
		return paramSource;
	}

        
        
        
        
        class PackagedProductMapper implements RowMapper<PackagedProduct> {
             public PackagedProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
                    PackagedProduct packagedProduct = new PackagedProduct();
                    packagedProduct.setPackageproductno(rs.getInt("packageproductno"));
                    packagedProduct.setPackageproductid(rs.getString("packageproductId"));
                    packagedProduct.setPackagesid(rs.getString("packageid"));
                    packagedProduct.setProductid(rs.getString("productid"));
                    return packagedProduct;
                }
                
                
                
                
                
        }
    
        
        
        
        
    
} 


