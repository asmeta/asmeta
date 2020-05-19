/** from slides written by Staerk */
asm FileSystem

import ../../STDL/StandardLibrary

signature:

	enum domain FileType = {NORMAL | DIRECTORY}
	enum domain FileOperation = {CREATE | DELETE | MOVE}

	dynamic abstract domain File

	static root: File

	static a1: Agent

	controlled name: File -> String
	controlled type: File -> FileType
	controlled parentDir: File -> File
	controlled children: File -> Powerset(File)

	monitored task: Agent -> FileOperation
	// name of the file given by the user
	monitored argName: Agent -> String
	// type of the file given by the user
	monitored argType: Agent -> FileType
	// name of the parent directory of the file given by the user
	monitored argParent: Agent -> String

	// convert a file name to a File value
	static toFile: String -> File

definitions:

	function toFile($name in String) =
		first(asSequence({$file in File | name($file) = $name : $file }))

	macro rule r_create($name in String, $type in FileType, $parentDir in String) =
		extend File with $newFile do
		par
			name($newFile) := $name
			type($newFile) := $type
			parentDir($newFile) := toFile($parentDir)
			if $type = DIRECTORY then
				children($newFile) := {}
			endif
		endpar

	macro rule r_exec =
	if task(self) = CREATE then
		r_create[argName(self), argType(self), argParent(self)]
	endif


	main rule r_main = forall $a in Agent with true do program($a)

default init s0:

	function name($file in File) =
		if $file = root then
			"root"
		endif

	function type($file in File) =
		if $file = root then
			DIRECTORY
		endif

	function parentDir($file in File) =
		if $file = root then
			undef
		endif

	function children($file in File) =
	if $file = root then
		{}
	endif

agent Agent : r_exec[]
