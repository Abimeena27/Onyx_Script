document.addEventListener('DOMContentLoaded', () => {
    const editor = document.getElementById('note-editor');

    // --- Elements for Sidebar & Save Functionality ---
    const newNoteButton = document.querySelector('.new-note-button');
    const noteList = document.querySelector('.note-list');
    const saveNoteButton = document.getElementById('save-note');

    editor.addEventListener('keydown', function (e) {
        // --- Basic Formatting Shortcuts (Ctrl/Cmd + B, I, U) ---
        const isModifierKeyPressed = e.ctrlKey || e.metaKey;
        if (isModifierKeyPressed) {
            switch (e.key.toLowerCase()) {
                case 'b': // Bold
                    document.execCommand('bold');
                    e.preventDefault();
                    break;
                case 'i': // Italic
                    document.execCommand('italic');
                    e.preventDefault();
                    break;
                case 'u': // Underline
                    document.execCommand('underline');
                    e.preventDefault();
                    break;
            }
        }

        if (e.key === ' ') {
            const selection = window.getSelection();
            const range = selection.getRangeAt(0);
            const block = getCurrentBlockElement();

            if (!block) return;

            const text = block.textContent.trim();

            let tag = null;

            if (text === '#') tag = 'h1';
            else if (text === '##') tag = 'h2';
            else if (text === '###') tag = 'h3';
            else if (text === '-') tag = 'li';

            if (tag) {
                e.preventDefault(); // Stop the space from inserting

                if (tag === 'li') {
                    // Handle bullet list
                    const ul = document.createElement('ul');
                    const li = document.createElement('li');
                    li.innerHTML = '&nbsp;';

                    // Remove the markdown (e.g., '-') from the current block's text content
                    if (block.firstChild && block.firstChild.nodeType === Node.TEXT_NODE && block.firstChild.textContent.trim() === text) {
                         block.firstChild.remove();
                    } else {
                        block.innerHTML = ''; // Fallback: clear content and replace
                    }

                    ul.appendChild(li);

                    if (block.parentNode) {
                        block.parentNode.replaceChild(ul, block);
                    } else {
                        editor.appendChild(ul);
                    }
                    placeCaretAtEnd(li);
                } else {
                    const newElem = document.createElement(tag);
                    newElem.innerHTML = block.textContent.substring(text.length).trim() || '&nbsp;';

                    if (block.parentNode) {
                        block.parentNode.replaceChild(newElem, block);
                    } else {
                        editor.appendChild(newElem);
                    }
                    placeCaretAtEnd(newElem);
                }
            }
        }

        // Press Enter to create a new block
        if (e.key === 'Enter') {
            e.preventDefault();

            const selection = window.getSelection();
            const block = getCurrentBlockElement();
            if (!block) return;

            const isListItem = block.nodeName.toLowerCase() === 'li';

            if (isListItem && block.textContent.trim() === '') {
                const parentUl = block.closest('ul');
                const newBlock = document.createElement('div');
                newBlock.innerHTML = '<br>';

                if (parentUl && parentUl.parentNode) {
                    parentUl.parentNode.insertBefore(newBlock, parentUl.nextSibling);
                }
                block.remove();
                if (parentUl && parentUl.children.length === 0) {
                    parentUl.remove();
                }
                placeCaretAtEnd(newBlock);
                return;
            }
            
            const newBlock = document.createElement(isListItem ? 'li' : 'div');
            newBlock.innerHTML = '<br>';

            if (isListItem) {
                block.parentNode.insertBefore(newBlock, block.nextSibling);
            } else {
                editor.insertBefore(newBlock, block.nextSibling);
            }
            
            placeCaretAtEnd(newBlock);
        }
    });

    function getCurrentBlockElement() {
        const selection = window.getSelection();
        if (!selection.rangeCount) return null;
        let node = selection.anchorNode;

        while (node && node !== editor) {
            if (node.parentNode === editor && node.nodeType === Node.ELEMENT_NODE) return node;
            if (node.nodeType === Node.TEXT_NODE && node.parentNode && node.parentNode !== editor && node.parentNode.parentNode === editor) {
                return node.parentNode;
            }
            node = node.parentNode;
        }
        return null;
    }

    function placeCaretAtEnd(el) {
        const range = document.createRange();
        const sel = window.getSelection();
        range.selectNodeContents(el);
        range.collapse(false);
        sel.removeAllRanges();
        sel.addRange(range);
    }

    if (newNoteButton) {
        newNoteButton.addEventListener('click', () => {
            editor.innerHTML = '<div><br></div>';
            editor.focus();

            document.querySelectorAll('.note-item').forEach(item => item.classList.remove('active'));

            const newNoteDiv = document.createElement('div');
            newNoteDiv.classList.add('note-item', 'active');
            newNoteDiv.textContent = 'New Note ' + (noteList.children.length + 1); 
            noteList.appendChild(newNoteDiv);

            newNoteDiv.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
        });
    }
    if (noteList) {
        noteList.addEventListener('click', (event) => {
            if (event.target.classList.contains('note-item')) {
                document.querySelectorAll('.note-item').forEach(item => item.classList.remove('active'));
                event.target.classList.add('active');

                const selectedNoteContent = event.target.textContent; 
                editor.innerHTML = `<div>${selectedNoteContent || '<br>'}</div>`;
                placeCaretAtEnd(editor.lastChild);
            }
        });
    }

    if (saveNoteButton) {
        saveNoteButton.addEventListener('click', () => {
            const noteContentHTML = editor.innerHTML;
            const currentNoteItem = document.querySelector('.note-item.active');

            if (currentNoteItem) {
                let noteTitle = 'Empty Note'; 

                const tempDiv = document.createElement('div');
                tempDiv.innerHTML = noteContentHTML;

                const firstHeading = tempDiv.querySelector('h1, h2, h3');

                if (firstHeading && firstHeading.textContent.trim() !== '') {
                    noteTitle = firstHeading.textContent.trim();
                } else {
                    const firstBlock = tempDiv.querySelector('div, p, li'); 
                    if (firstBlock && firstBlock.textContent.trim() !== '') {
                        noteTitle = firstBlock.textContent.trim().substring(0, 30);
                        if (firstBlock.textContent.trim().length > 30) {
                            noteTitle += '...';
                        }
                    } else if (noteContentHTML.trim() !== '') {
                        const plainText = tempDiv.textContent.trim();
                        if (plainText !== '') {
                            noteTitle = plainText.substring(0, 30);
                            if (plainText.length > 30) {
                                noteTitle += '...';
                            }
                        }
                    }
                }

                currentNoteItem.textContent = noteTitle; 

                alert('Note saved! (Title: ' + noteTitle + ')');
                console.log('Note saved HTML:', noteContentHTML);
            } else {
                alert('Please select or create a note to save.');
            }
        });
    }
});