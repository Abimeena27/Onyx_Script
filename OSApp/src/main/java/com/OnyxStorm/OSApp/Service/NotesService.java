// src/main/java/com/OnyxStorm/OSApp/Service/NotesService.java
package com.OnyxStorm.OSApp.Service;

import java.util.List;
import java.util.Optional; // Keep this import for potential future use or if you adapt fetchNote

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OnyxStorm.OSApp.Model.Notes;
import com.OnyxStorm.OSApp.Repository.NotesRepository;

@Service
public class NotesService {
    private NotesRepository notesRepo;

    public NotesRepository getNotesRepo() {
        return notesRepo;
    }

    @Autowired
    public void setNotesRepo(NotesRepository notesRepo) {
        this.notesRepo = notesRepo;
    }

    // Your existing fetchNote method
    public Notes fetchNote(int id, int u_id) {
        Notes n = notesRepo.fetchUserNote(id, u_id);
        return n; // This will return null if notesRepo.fetchUserNote throws NoSuchElementException
                  // and MainController catches it. Or if notesRepo.fetchUserNote is modified to return null
    }

    // Combined save/update method for the controller
    public void saveOrUpdateNote(Notes n) {
        if (n.getNotesId() == 0) { // Assuming 0 indicates a new note (ID not yet assigned by DB)
            notesRepo.save(n); // Insert new note
        } else {
            // Update existing note content and title
            notesRepo.updateExistingNote(n.getNotesId(), n.getUserId(), n.getContext());
            notesRepo.updateExistingNoteTitle(n.getNotesId(), n.getUserId(), n.getTitle());
        }
    }

    // Your existing update methods (can be called directly if needed)
    public void updateNote(int id, int u_id, String txt) {
        notesRepo.updateExistingNote(id, u_id, txt);
    }

    public void updateNoteTitle(int id, int u_id, String txt) {
        notesRepo.updateExistingNoteTitle(id, u_id, txt);
    }

    // Your existing fetchNoteList method
    public List<Notes> fetchNoteList(int u_id) {
        return notesRepo.findAllNotes(u_id);
    }

    // Your existing fetchNoteByTitleAndUserId method
    public Notes fetchNoteByTitleAndUserId(String title, int id) {
        Notes n = notesRepo.fetchUserNoteByTitle(title, id);
        return n;
    }
}