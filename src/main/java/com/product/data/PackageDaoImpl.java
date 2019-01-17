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



import com.product.model.Packages;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class PackageDaoImpl implements PackageDao {
    
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
	public Packages findByNo(Integer productsno) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productsno", productsno);
                String sql = "select * from packages WHERE packagesno=:packagesno";
		Packages result = null;
		try {
                    result = namedParameterJdbcTemplate.queryForObject(sql, params, new PackageDaoImpl.PackagesMapper());
		} catch (EmptyResultDataAccessException e) {
                    // do nothing, return null
		}
		return result;
	}
        
        
        
        
        @Override
	public Packages findById(String productsid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productsid", productsid);
		String sql = "SELECT * FROM product WHERE productid=:productid";
		Packages result = null;
		try {
			result = namedParameterJdbcTemplate.queryForObject(sql, params, new PackageDaoImpl.PackagesMapper());
		} catch (EmptyResultDataAccessException e) {
			// do nothing, return null
		}
                return result;
           
	}
    
        
        
        @Override
	public List<Packages> findAll() {
		String sql = "SELECT * FROM packages";
		List<Packages> result = namedParameterJdbcTemplate.query(sql, new PackageDaoImpl.PackagesMapper());
		return result;
	}
    
        
        
        /*
        @Override
	public List<PackagedProduct> findAllPackagedProducts() {
                        
                
                List<PackagedProduct> packagedproduct = new ArrayList<PackagedProduct>();
		PreparedStatement stmt = null;
                PreparedStatement stmt1 = null;
		ResultSet rs = null;
                ResultSet rs1 = null;
                Connection con = null;
		
                try {
			con = dataSource.getConnection();
                        String sql = "select FROM packagedproducts WHERE packageid = ";
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
                        
			while(rs.next()) {
                                PackagedProduct packaged = new PackagedProduct();
                                
                                packaged.setId(rs.getInt("packageid"));
                                packaged.setName(rs.getInt("packageid"));
                                packaged.setDescription(rs.getInt("packageid"));
                                packaged.setProducts(rs.getInt("packageid"));
                                
                                for (int i = 0; i < loc.size(); i++) {
                                    
                                    
                                    
                                }
                                
                                
                                //Map<Integer, List<Location>> QAParams2 = new HashMap<Integer, List<Location>>();
                                Map<Integer, List<PackagedProduct>> map = new HashMap();
                                map.put(rs.getInt("packageno"), rs.getInt("packageid"));
                                map.put("key2", "value2");
                                packaged.setProducts(map);
                                
                                Products
            
                                packaged.setPrice(rs.getInt("packageid"));
                                packagedproduct.add(packaged);
                        
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
                    try {
                        rs.close();
                        stmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(MaintenanceBuildDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
		}
                
                
                //when finished 
                //insert one record in the maintenance build table to indicate a succesfull build
                
		return builds;
               
	
            
            
            
            
		//String sql = "SELECT * FROM packagedproducts";
		//List<Packages> result = namedParameterJdbcTemplate.query(sql, new PackageDaoImpl.PackagesMapper());
		//return result;
	}
        */
        
        @Override
	public void save(Packages packages) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            String sql = "insert into pmitem(pmitemid, pmheaderid, jobplanid, locationid, pmitemdesc, systemcond, routeid) "
				+ "VALUES ( :pmitemid, :pmheaderid, :jobplanid, :locationid, :pmitemdesc, :systemcond, :routeid)";
            namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(packages), keyHolder, new String[]{"packageno"});
            packages.setPackageno(keyHolder.getKey().intValue());
	}
        
        
        
        @Override
	public void update(Packages  packages) {
            String sql = "update pmitem set pmitemno=:pmitemno, pmitemid=:pmitemid, pmheaderid=:pmheaderid, jobplanid=:jobplanid, locationid=:locationid, pmitemdesc=:pmitemdesc, systemcond=:systemcond where pmitemno=:pmitemno";
            namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(packages));    
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
        public int ForeignKeyPackageProduct(String packageid) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("packageid", packageid);
            String sql = "SELECT count(*) FROM packages WHERE packageid = :packageid";
            int count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
            return count;
        }
        
        
        
        /*
        public List<PackagedProduct> fillProducts(Integer productid) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("packageid", packageid);
            String sql = "SELECT count(*) FROM packages WHERE packageid = :packageid";
            int count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
            return count;  
        }
        */
        
        
        
       
        private SqlParameterSource getSqlParameterByModel(Packages packages) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
                paramSource.addValue("packageno", packages.getPackageno());
                paramSource.addValue("packageid", packages.getPackageid());
                paramSource.addValue("pacfkagename", packages.getPackagename());
                paramSource.addValue("packagedesc", packages.getPackagedesc());
                paramSource.addValue("packageprice", packages.getPackageprice());
		return paramSource;
	}

        
        
        class PackagesMapper implements RowMapper<Packages> {
                public Packages mapRow(ResultSet rs, int rowNum) throws SQLException {
                Packages packages = new Packages();
                packages.setPackageno(rs.getInt("packageno"));
                packages.setPackageid(rs.getString("packageid"));
                packages.setPackagename(rs.getString("packagename"));
                packages.setPackagedesc(rs.getString("packagedesc")); 
                packages.setPackageprice(rs.getFloat("packageprice"));
                return packages;
                }
        }
      
        
} 
