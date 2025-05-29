package com.OnyxStorm.OSApp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OnyxStorm.OSApp.Model.Users;
import com.OnyxStorm.OSApp.Repository.UsersRepository;

@Service
public class UsersService {
	private UsersRepository userRepo;
	
	public UsersRepository getUserRepo() {
		return userRepo;
	}
	@Autowired
	public void setUserRepo(UsersRepository userRepo) {
		this.userRepo = userRepo;
	}

	public Users findUser(String email) {
		Users u=userRepo.findUserByMail(email);
		return u;
	}

	public boolean validateLogin(Users u) {
		String pwd=userRepo.fetchPwd(u);
		return u.getPassword().equals(pwd);
	}
	
	public void addUserAcc(Users u) {
		userRepo.save(u);
	}

}
