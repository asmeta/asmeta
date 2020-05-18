asm Library

import ../../STDL/StandardLibrary

signature:

	dynamic abstract domain Library

   // Campi di un libro
    domain Isbn   		 subsetof String
    domain Title  		 subsetof String
 	domain AuthorSurname subsetof String
	domain AuthorName    subsetof String
	domain CopiesNumber  subsetof Natural

	domain Book subsetof Prod( Isbn, Title, AuthorSurname, AuthorName, CopiesNumber)

	enum domain State = { COMMAND_SELECTION | BOOK_INSERTION | BOOK_SEARCH}
	enum domain Command = { ADD_BOOK | FIND_BOOK | EXIT}

	dynamic controlled libraryState : State
	dynamic out		   message : Any

   // metodi di un libro
	dynamic controlled bookIsbn :          Book -> Isbn
	//dynamic controlled bookTitle:         Book -> String
	//dynamic controlled bookAuthorSurname: Book -> String
	//dynamic controlled bookAuthorName:    Book -> String
	//dynamic controlled bookCopiesNumber:  Book -> Natural

	dynamic controlled libraryBooks: Library -> Powerset( Book)

	dynamic monitored selectedCommand : Command
	dynamic monitored insertedBook :    Book

	derived bookExists: Prod( Book, Library) -> Boolean

	static theLibrary : Library
	static theBook : Book

definitions:

   // I libri sono identificati per ISBN
	function bookExists( $b in Book, $l in Library) =
		let ( $books = libraryBooks( $l ), $isbn = bookIsbn( $b )) in
			if ( exist $c in $books with bookIsbn( $c ) = $isbn ) then true
			else false
			endif
		endlet

   // In base al comando selezionato si passa allo stato corrispondente
	macro rule r_commandSelection =
		if ( libraryState = COMMAND_SELECTION) then
			par
				// Se aggiunta di un libro
				if ( selectedCommand = ADD_BOOK) then
					par
						libraryState := BOOK_INSERTION
						message := "Aggiungi libro"
					endpar
				endif
			    // Se ricerca di un libro
				if ( selectedCommand = FIND_BOOK) then
					//par
						//libraryState := BOOK_SEARCH
						message := "Cerca libro"
					//endpar
				endif
				// Se esci
				if ( selectedCommand = EXIT) then
					message := "Fine!"
				endif
			endpar
		endif

 	// Aggiunta di un nuovo libro
	macro rule r_addBook =
		if libraryState = BOOK_INSERTION then
			par
				if not( bookExists( insertedBook, theLibrary)) then
					let ( $books = libraryBooks( theLibrary)) in
						par
							$books := including( $books, insertedBook)
							message := "Libro aggiunto correttamente!"
						endpar
					endlet
				endif
				libraryState := COMMAND_SELECTION
			endpar
		endif

    // Regola principale
	main rule r_Main =
		par
			r_commandSelection[]
			r_addBook[]
		endpar

default init initialize:

	function libraryState = COMMAND_SELECTION
	function libraryBooks( $l in Library) = {}
