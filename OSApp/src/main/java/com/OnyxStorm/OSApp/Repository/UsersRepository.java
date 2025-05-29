package com.OnyxStorm.OSApp.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.OnyxStorm.OSApp.Model.Users;

@Repository
public class UsersRepository {
	
	private JdbcTemplate jdbc;
	
	public JdbcTemplate getJdbc() {
		return jdbc;
	}
	
    @Autowired
	public void setJdbc(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public String fetchPwd(Users u) {
		// TODO Auto-generated method stub
		String sql="select * from users where email=?";
		RowMapper<Users> map=new RowMapper<Users>() {
			@Override
			public Users mapRow(ResultSet rs,int rowNum) throws SQLException{
				Users u1=new Users();
				u1.setId(rs.getInt("id"));
				u1.setFullName(rs.getString("fullname"));
				u1.setEmail(rs.getString("email"));
				u1.setPassword(rs.getString("password_hash"));
				//u.setMemberSince(rs.getDate("member_since"));
				return u1;
			}
		};
       
		List<Users> l= jdbc.query(sql,map,u.getEmail());
		return l.getFirst().getPassword();
	}

	public void save(Users u) {
	    // TODO Auto-generated method stub
	    String sql = "insert into users(fullname,email,password_hash) values(?,?,?)";
	    System.out.println("hiiiiiiiiiiiiiiiiiiiiiiiiiiii");
	    // Debugging statements
	    System.out.println("Full Name: " + u.getFullName());
	    System.out.println("Email: " + u.getEmail());
	    System.out.println("Password: " + u.getPassword());
	    
	    int n = jdbc.update(sql, u.getFullName(), u.getEmail(), u.getPassword());
	    
	    System.out.println("Saved: " + n + " Rows Affected");
	}

	public Users findUserByMail(String email) {
		// TODO Auto-generated method stub
		String sql="select * from users where email=?";
		RowMapper<Users> map=new RowMapper<Users>() {
			@Override
			public Users mapRow(ResultSet rs,int rowNum) throws SQLException{
				Users u1=new Users();
				u1.setId(rs.getInt("id"));
				u1.setFullName(rs.getString("fullname"));
				u1.setEmail(rs.getString("email"));
				u1.setPassword(rs.getString("password_hash"));
				//u.setMemberSince(rs.getDate("member_since"));
				return u1;
			}
		};
       
		List<Users> l= jdbc.query(sql,map,email);
		return l.getFirst();

	}

}
