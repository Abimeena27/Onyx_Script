package com.OnyxStorm.OSApp.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.OnyxStorm.OSApp.Model.Notes;
import com.OnyxStorm.OSApp.Model.Users;

@Repository
public class NotesRepository {
	
	private JdbcTemplate jdbc;
	public JdbcTemplate getJdbc() {
		return jdbc;
	}
    @Autowired
	public void setJdbc(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	private RowMapper<Notes> map=new RowMapper<Notes>() {
		@Override
		public Notes mapRow(ResultSet rs,int rowNum) throws SQLException{
			Notes n1=new Notes();
			n1.setNotesId(rs.getInt("id"));
			n1.setUserId(rs.getInt("user_id"));
			n1.setTitle(rs.getString("title"));
			n1.setContext(rs.getString("content_html"));
			return n1;
		}
	};

	public Notes fetchUserNote(int id, int u_id) {
		// TODO Auto-generated method stu
		String sql= "select * from notes where id=? and user_id=?";
		List<Notes> ln=jdbc.query(sql, map,id,u_id);
		return ln.getFirst();
	}

	public void save(Notes n) {
		// TODO Auto-generated method stub
		String sql="insert into notes(id,user_id,title,content_html) values(?,?,?,?)";
		int num=jdbc.update(sql,n.getNotesId(),n.getUserId(),n.getTitle(),n.getContext());
		System.out.println("Notes Saved: "+num+" Rows Affected");
		
	}

	public List<Notes> findAllNotes(int u_id) {
		// TODO Auto-generated method stub
		String sql= "select * from notes where user_id=?";
		List<Notes> ln=jdbc.query(sql, map,u_id);
		return ln;
	}
	public void updateExistingNote(int id, int u_id, String txt) {
		// TODO Auto-generated method stub
		String sql= "update notes set content_html=? where id=? and user_id=?";
		int n=jdbc.update(sql,txt,id,u_id);
		System.out.println("Update Executed: "+n+"Rows affected");
		
	}
	public void updateExistingNoteTitle(int id, int u_id, String txt) {
		// TODO Auto-generated method stub
		String sql= "update notes set title=? where id=? and user_id=?";
		int n=jdbc.update(sql,txt,id,u_id);
		System.out.println("Update Executed: "+n+"Rows affected");
	}
	public Notes fetchUserNoteByTitle(String title, int id) {
		// TODO Auto-generated method stub
		String sql= "select * from notes where title=? and user_id=?";
		List<Notes> ln=jdbc.query(sql, map,title,id);
		return ln.getFirst();
	}

}
