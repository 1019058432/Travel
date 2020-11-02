package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SellerDaoimpl implements SellerDao {
    JdbcTemplate jdbcTemplate =new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Seller findBySid(int rid) {
        String sql="select * from tab_seller where sid = ?";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Seller>(Seller.class),rid);
    }
}
