package com.OnyxStorm.OSApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.OnyxStorm.OSApp.Model.Notes;
import com.OnyxStorm.OSApp.Model.Users;
import com.OnyxStorm.OSApp.Service.NotesService;
import com.OnyxStorm.OSApp.Service.UsersService;

@SpringBootApplication
public class OsAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(OsAppApplication.class, args);
		//Users u=txt.getBean(Users.class);
		//u.setId(7);
		//u.setFullName("Knn2");
		//u.setEmail("knn2@gmail.com");
		//u.setPassword("user@3124");
		
		
		//UsersService us=txt.getBean(UsersService.class);
		//us.addUserAcc(u);
		//System.out.println(us.findUser("abi@gmail.com"));
		//System.out.println(us.validateLogin(u));
		

//		Notes n=txt.getBean(Notes.class);
//		//n.setNotesId(3);
//		//n.setUserId(1);
//	//n.setTitle("My Notes New");
////		//n.setContext("This is my Notes. Feel Free to edit it or Do what you want");
////		
//		NotesService ns=txt.getBean(NotesService.class);
//		//ns.saveNote(n)//		
//		//System.out.println("Notes List of User"+ns.fetchNoteList(1));
//		ns.updateNoteTitle(1, 2, "Updated Title");
//		System.out.println("Notes of user "+ns.fetchNote(1, 1));
////		
//		
	}

}
