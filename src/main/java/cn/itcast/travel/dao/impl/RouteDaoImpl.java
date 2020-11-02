package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int findTotalCount(int cid,String rname) {
        String sql = "select count(*) from tab_route where 1 = 1 ";
        StringBuilder stringBuilder=new StringBuilder(sql);
        ArrayList<Object> list = new ArrayList<>();
        if (cid!=0){
            stringBuilder.append(" and cid = ? ");
            list.add(cid);
        }
        if ( null!= rname &&rname.length()>0) {
           stringBuilder.append(" and rname like ? ");
            list.add("%"+rname+"%");
        }
        String new_sql = stringBuilder.toString();
        return template.queryForObject(new_sql,Integer.class,list.toArray());
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
        String sql = "select * from tab_route where 1 = 1  ";
        StringBuilder stringBuilder=new StringBuilder(sql);
        ArrayList<Object> list = new ArrayList<>();
        if (cid!=0){
            stringBuilder.append(" and cid = ? ");
            list.add(cid);
        }
        if ( null!= rname &&rname.length()>0) {
            stringBuilder.append(" and rname like ? ");
            list.add("%"+rname+"%");
        }
        stringBuilder.append(" limit ? , ?");
        list.add(start);
        list.add(pageSize);
        String new_sql = stringBuilder.toString();

        return template.query(new_sql,new BeanPropertyRowMapper<Route>(Route.class),list.toArray());
    }

    @Override
    public Route findOne(int rid) {
        String   sql="select * from tab_route where rid = ?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }
}
