/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.product.data;

/**
 *
 * @author Laud.Ochei
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.product.model.PackageProduct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class PackageProductDaoImpl implements PackageProductDao {
    
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) throws DataAccessException {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
        
        @Override
	public PackageProduct findByNo(Integer productsno) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productsno", productsno);
                String sql = "select * from product WHERE productno=:productno";
		PackageProduct result = null;
		try {
                    result = namedParameterJdbcTemplate.queryForObject(sql, params, new PackageProductDaoImpl.ProductsMapper());
		} catch (EmptyResultDataAccessException e) {
                    // do nothing, return null
		}
		return result;
	}
        
        
        
        
        @Override
	public PackageProduct findById(String productsid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productsid", productsid);
		String sql = "SELECT * FROM product WHERE productid=:productid";
		PackageProduct result = null;
		try {
			result = namedParameterJdbcTemplate.queryForObject(sql, params, new PackageProductDaoImpl.ProductsMapper());
		} catch (EmptyResultDataAccessException e) {
			// do nothing, return null
		}
                return result;
           
	}
    
        
        
        @Override
	public List<PackageProduct> findAll() {
		String sql = "SELECT * FROM product";
		List<PackageProduct> result = namedParameterJdbcTemplate.query(sql, new PackageProductDaoImpl.ProductsMapper());
		return result;
	}
    
        
        @Override
	public void save(PackageProduct products) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            String sql = "insert into pmitem(pmitemid, pmheaderid, jobplanid, locationid, pmitemdesc, systemcond, routeid) "
				+ "VALUES ( :pmitemid, :pmheaderid, :jobplanid, :locationid, :pmitemdesc, :systemcond, :routeid)";
            namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(products), keyHolder, new String[]{"productno"});
            products.setProductno(keyHolder.getKey().intValue());
	}
        
        
        
        @Override
	public void update(PackageProduct products) {
            String sql = "update pmitem set pmitemno=:pmitemno, pmitemid=:pmitemid, pmheaderid=:pmheaderid, jobplanid=:jobplanid, locationid=:locationid, pmitemdesc=:pmitemdesc, systemcond=:systemcond where pmitemno=:pmitemno";
            namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(products));    
	}
        
        
        @Override
	public void delete(Integer productno) {
		String sql = "DELETE FROM product WHERE productpmitemno= :pmitemno";
		namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource("pmitemno", productno));
	}
        
                
        @Override
        public int idExists(String productid) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("productid", productid); 
            String sql = "SELECT count(*) FROM pmitem WHERE pmitemid = :pmitemid";
            int count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
            return count;
        }

        
        @Override
        public int ForeignKeyPackageProduct(String productid) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("productid", productid);
            String sql = "SELECT count(*) FROM product WHERE productid = :productid";
            int count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
            return count;
        }
        
       
        private SqlParameterSource getSqlParameterByModel(PackageProduct products) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
                paramSource.addValue("productno", products.getProductno());
                paramSource.addValue("productid", products.getProductid());
                paramSource.addValue("productname", products.getProductname());
                paramSource.addValue("producdesc", products.getProductdesc());
                paramSource.addValue("productprice", products.getProductprice());
		return paramSource;
	}

        
        
        class ProductsMapper implements RowMapper<PackageProduct> {
                public PackageProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
                PackageProduct products = new PackageProduct();
                products.setProductno(rs.getInt("productno"));
                products.setProductid(rs.getString("productid"));
                products.setProductname(rs.getString("productname")); 
                products.setProductdesc(rs.getString("productdesc"));
                products.setProductprice(rs.getDouble("productprice")); 
                return products;
                }
        }
      
    
}
