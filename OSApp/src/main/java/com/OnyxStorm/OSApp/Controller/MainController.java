// src/main/java/com/OnyxStorm/OSApp/Controller/MainController.java
package com.OnyxStorm.OSApp.Controller;

import java.util.List;
import java.util.Optional; // Keep this import if you want to use Optional for notes fetching

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.HttpStatus; // Import for HTTP status codes
import org.springframework.web.server.ResponseStatusException; // Import for throwing HTTP errors

import com.OnyxStorm.OSApp.Model.Users;
import com.OnyxStorm.OSApp.Model.Notes;
import com.OnyxStorm.OSApp.Service.UsersService;
import com.OnyxStorm.OSApp.Service.NotesService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private NotesService notesService;

    @GetMapping("/")
    public ModelAndView loginPage() {
        ModelAndView mv = new ModelAndView("login");
        mv.addObject("user", new Users());   // For login form binding
        mv.addObject("newUser", new Users()); // For signup form binding
        return mv;
    }

    @GetMapping("/login")
    public ModelAndView showLoginForm() {
        ModelAndView mv = new ModelAndView("login");
        mv.addObject("user", new Users());
        mv.addObject("newUser", new Users()); // Ensure signup form binding works
        return mv;
    }

    @PostMapping("/login")
    public ModelAndView loginUser(@RequestParam String email, @RequestParam String password, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        Users user = usersService.findUser(email);

        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("loggedInUser", user);
            mv.setViewName("redirect:/home");
        } else {
            mv.setViewName("login");
            mv.addObject("error", "Invalid email or password");
            mv.addObject("user", new Users()); // Keep user object for form repopulation if needed
            mv.addObject("newUser", new Users());
        }
        return mv;
    }

    @PostMapping("/signup")
    public ModelAndView signupUser(@ModelAttribute Users user) {
        usersService.addUserAcc(user);
        ModelAndView mv = new ModelAndView("login");
        mv.addObject("message", "Signup successful! Please login.");
        mv.addObject("user", new Users());
        mv.addObject("newUser", new Users());
        return mv;
    }

    @GetMapping("/home")
    public ModelAndView homePage(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        Users user = (Users) session.getAttribute("loggedInUser");
        if (user == null) {
            mv.setViewName("redirect:/login");
            return mv;
        }

        List<Notes> notes = notesService.fetchNoteList(user.getId());
        mv.addObject("user", user);
        mv.addObject("notes", notes);
        mv.setViewName("home");
        return mv;
    }

    @GetMapping("/profile")
    public ModelAndView profilePage(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        Users user = (Users) session.getAttribute("loggedInUser");
        if (user == null) {
            mv.setViewName("redirect:/login");
            return mv;
        }
        mv.setViewName("profile");
        mv.addObject("user", user);
        return mv;
    }

    @PostMapping("/saveNote")
    public String saveOrUpdateNote(@RequestParam(value = "notesId", required = false) Integer notesId, 
                                  @RequestParam("title") String incomingTitle, 
                                  @RequestParam("context") String fullContentHtml, 
                                  HttpSession session) {

        Users user = (Users) session.getAttribute("loggedInUser");
        if (user == null) {
            System.out.println("DEBUG: User not logged in. Redirecting to login.");
            return "redirect:/login";
        }

        String finalTitle = incomingTitle;
        if (finalTitle == null || finalTitle.trim().isEmpty() || finalTitle.equals("Untitled Note")) {
             try {
                Document doc = Jsoup.parse(fullContentHtml);
                Element firstHeading = doc.select("h1, h2, h3").first();
                if (firstHeading != null && !firstHeading.text().trim().isEmpty()) {
                    finalTitle = firstHeading.text().trim().substring(0, Math.min(firstHeading.text().trim().length(), 100));
                } else {
                    String plainTextContent = doc.text().trim();
                    if (!plainTextContent.isEmpty()) {
                        finalTitle = plainTextContent.split("\n")[0].trim().substring(0, Math.min(plainTextContent.split("\n")[0].trim().length(), 100));
                    }
                }
            } catch (Exception e) {
                System.err.println("Error extracting title with Jsoup fallback: " + e.getMessage());
            }
        }
        if (finalTitle == null || finalTitle.trim().isEmpty()) {
            finalTitle = "Untitled Note";
        }

        Notes note = new Notes();
        if (notesId != null && notesId != 0) { 
            note.setNotesId(notesId);
        }
        note.setUserId(user.getId()); 
        note.setTitle(finalTitle);
        note.setContext(fullContentHtml);

        System.out.println("DEBUG: Notes object about to be saved/updated:");
        System.out.println("DEBUG:   ID: " + note.getNotesId());
        System.out.println("DEBUG:   User ID: " + note.getUserId());
        System.out.println("DEBUG:   Title: '" + note.getTitle() + "'");
        System.out.println("DEBUG:   Content length: " + note.getContext().length());

        try {
            notesService.saveOrUpdateNote(note); 
            System.out.println("DEBUG: Note saved/updated successfully!");
            if (note.getNotesId() != 0) { 
                 return "redirect:/home?selectedNoteId=" + note.getNotesId(); 
            } else {
                 return "redirect:/home";
            }
        } catch (Exception e) {
            System.err.println("ERROR: Failed to save/update note to database: " + e.getMessage());
            e.printStackTrace(); 
            return "redirect:/home"; 
        }
    }

    @GetMapping("/api/notes/{id}") 
    @ResponseBody 
    public Notes getNoteById(@PathVariable("id") int noteId, HttpSession session) { 
        Users user = (Users) session.getAttribute("loggedInUser");

        if (user == null) {
            System.out.println("DEBUG: Attempt to fetch note by ID for unauthenticated user. Responding with 401.");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not logged in.");
        }

        try {
            Notes note = notesService.fetchNote(noteId, user.getId());

            if (note != null && note.getUserId() == user.getId()) {
                System.out.println("DEBUG: Fetched note with ID '" + noteId + "' for user " + user.getId());
                return note;
            } else {
                System.out.println("DEBUG: Note with ID '" + noteId + "' not found or unauthorized for user " + user.getId() + ". Responding with 404.");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found or unauthorized.");
            }
        } catch (java.util.NoSuchElementException e) {
            System.out.println("DEBUG: Note with ID '" + noteId + "' not found in database. Responding with 404.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found.");
        } catch (Exception e) {
            System.err.println("ERROR: Failed to fetch note due to unexpected error: " + e.getMessage());
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching note.");
        }
    }

   
}